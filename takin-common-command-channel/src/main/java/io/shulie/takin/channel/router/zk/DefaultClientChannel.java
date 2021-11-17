/*
 * Copyright 2021 Shulie Technology, Co.Ltd
 * Email: shulie@shulie.io
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.shulie.takin.channel.router.zk;


import io.shulie.takin.channel.ClientChannel;
import io.shulie.takin.channel.CommandRegistry;
import io.shulie.takin.channel.bean.*;
import io.shulie.takin.channel.handler.CommandHandler;
import io.shulie.takin.channel.impl.DefaultCommandRegistry;
import io.shulie.takin.channel.protocal.ChannelProtocol;
import io.shulie.takin.channel.type.Command;
import io.shulie.takin.channel.utils.HttpUtils;
import io.shulie.takin.channel.utils.StringUtils;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.ChildData;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent.Type;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ScheduledExecutorService;

/**
 * @author: Hengyu
 * @className: ZkServerChannel
 * @date: 2020/12/30 7:50 上午
 * @description: zk server
 */
public class DefaultClientChannel implements ClientChannel {
    private String tenantAppKey;
    private String envCode;
    private ZkClientConfig config;
    private ChannelProtocol protocol;
    private CommandRegistry registry;

    private Logger logger = LoggerFactory.getLogger(DefaultClientChannel.class);
    private ZkPathChildrenCache pathChildrenCache;
    private ZkClient zkClient;
    private String agentId;

    public DefaultClientChannel() {
        this.registry = new DefaultCommandRegistry();
    }

    @Override
    public ClientChannel build(ZkClientConfig config) throws Exception {
        validateParam();
        this.config = config;
        try {
            this.zkClient = NetflixCuratorZkClientFactory.getInstance().create(config);
        } catch (Exception e) {
            logger.error("CommandChannel 初始化ZK配置异常", e);
            throw e;
        }

        return this;
    }

    private void validateParam() {
        if (this.protocol == null) {
            throw new IllegalArgumentException("CommandChannel channelProtocol not set!");
        }
    }

    @Override
    public DefaultClientChannel registerTenantAndEnv(String tenantAppKey,String envCode) {
        this.tenantAppKey = tenantAppKey;
        this.envCode = envCode;
        return this;
    }

    @Override
    public ClientChannel registerHandler(Command command, CommandHandler handler) {
        this.registry.register(command, handler);
        return this;
    }

    @Override
    public ClientChannel setChannelProtocol(ChannelProtocol protocol) {
        this.protocol = protocol;
        return this;
    }

    @Override
    public ClientChannel setScheduledExecutorService(ScheduledExecutorService executorService) {
        return this;
    }


    @Override
    public void close() {
        try {
            this.pathChildrenCache.stop();
            String commandPath = getCommandPath(agentId);
            this.zkClient.deleteQuietly(commandPath);
            this.zkClient.stop();
            logger.info("ChannelCommand client command channel close!");
        } catch (Exception e) {
            logger.error("ChannelCommand zk close exception", e);
        }
    }

    private String getCommandPath(String agentId) {
        StringBuilder builder = new StringBuilder();
        builder.append(Constants.COMMAND_PATH_PREFIX);
        builder.append("/" + agentId);
        return builder.toString();
    }

    @Override
    public void register(String agentId) throws Exception {

        validate(agentId);
        this.agentId = agentId;

        String commandPath = getCommandPath(agentId);
        zkClient.deleteQuietly(commandPath);
        this.pathChildrenCache = zkClient.createPathChildrenCache(commandPath, new ZkChildListener() {
            @Override
            public void call(CuratorFramework client, PathChildrenCacheEvent event) {
                ChildData data = event.getData();
                String path = data.getPath();

                Type type = event.getType();

                if (type == Type.CHILD_ADDED) {
                    receiverCommand(path, data.getData());
                } else if (type == Type.CHILD_UPDATED) {
                    updateCommand(path, data.getData());
                } else if (type == Type.CHILD_REMOVED) {
                    removeCommand(path, data.getData());
                }
            }
        });
        pathChildrenCache.start();
    }

    public String getPathIp(String currAgentId) {
        return currAgentId.split("-")[0];
    }

    void receiverCommand(String commandPath, byte[] data) {

        //com.netflix.curator 客户端时间为空，需要重新获取
        //apache高版本不存在这个问题
        if (data == null || data.length == 0) {
            try {
                data = zkClient.getData(commandPath);
            } catch (Exception e) {
                logger.error("ChannelCommand receiverCommand get data exception ", e);
            }
            if (data == null || data.length == 0) {
                logger.error("ChannelCommand receiverCommand data is null");
                return;
            }
        }


        CommandPacket commandPacket = protocol.deserialize(data);
        if (commandPacket == null) {
            return;
        }
        if (commandPacket.getStatus() != CommandStatus.COMMAND_SEND) {
            return;
        }

        logger.info("ChannelCommand receiverCommand path: {} commandId: {} data: {}", commandPath, commandPacket.getId(), this.protocol.serializeJson(commandPacket));


        //更新命令为执行中
        commandPacket.setStatus(CommandStatus.COMMAND_RUNNING);
        updateCommandPacket(commandPacket, commandPath);

        try {
            //更新命令响应
            CommandHandler handler = registry.getHandler(commandPacket.getSend().getCommandId());
            CommandResponse response = handler.handle(commandPacket);
            commandPacket.setStatus(CommandStatus.COMMAND_COMPLETED_SUCCESS);

            CommandRespType commandRespType = commandPacket.getCommandRespType();
            //提送服务器地址为空，走zk数据回传，否则走http接口推送数据
            if (commandRespType.getValue() == CommandRespType.COMMAND_CALLBACK.getValue()) {
                commandPacket.setResponse(response);
            } else {
                //将响应对象序列化为JSON对象进行数据推送
                boolean result = pushResponseData(commandPacket.getResponsePushUrl(), commandPacket, response);
                if (!result) {
                    commandPacket.setStatus(CommandStatus.COMMAND_COMPLETED_FAIL);
                }
            }
            updateCommandPacket(commandPacket, commandPath);
        } catch (Throwable ex) {
            logger.error("ChannelCommand exec command handler exception!", ex);
            //更新命令为执行中
            commandPacket.setStatus(CommandStatus.COMMAND_COMPLETED_FAIL);
            updateCommandPacket(commandPacket, commandPath);
        }

    }

    private boolean pushResponseData(String pushUrl, CommandPacket commandPacket, CommandResponse response) {
        if (StringUtils.isBlank(pushUrl)) {
            logger.error("ChannelCommand push url is empty，stop push！");
            return false;
        }
        CommandPacket packet = deepCopyPacket(commandPacket, response);
        String json = this.protocol.serializeJson(packet);
        String result = HttpUtils.doPost(this.tenantAppKey,this.envCode, pushUrl, json);
        if (StringUtils.isBlank(result)) {
            return false;
        } else {
            return true;
        }
    }

    private CommandPacket deepCopyPacket(CommandPacket commandPacket, CommandResponse response) {
        CommandPacket packet = new CommandPacket();
        packet.setId(commandPacket.getId());
        packet.setResponse(response);
        packet.setStatus(commandPacket.getStatus());
        packet.setSend(commandPacket.getSend());
        packet.setCommandRespType(commandPacket.getCommandRespType());
        packet.setResponsePushUrl(commandPacket.getResponsePushUrl());
        return packet;
    }

    void updateCommand(String commandPath, byte[] data) {
        logger.info("ChannelCommand updateCommand  path: {}", commandPath);
    }

    void removeCommand(String commandPath, byte[] data) {
        logger.info("ChannelCommand removeCommand  path: {}", commandPath);
    }

    private void updateCommandPacket(CommandPacket commandPacket, String commandPath) {
        byte[] bytes = protocol.serialize(commandPacket);
        try {
            zkClient.updateData(commandPath, bytes);
        } catch (Exception e) {
            logger.error("ChannelCommand client updateData exception", e);
        }
    }


    private void validate(String agentId) {
        if (StringUtils.isBlank(agentId)) {
            throw new IllegalArgumentException("ChannelCommand agentId param cannot be empty ");
        }
    }
}
