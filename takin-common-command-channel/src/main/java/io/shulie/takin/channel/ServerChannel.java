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

package io.shulie.takin.channel;

import io.shulie.takin.channel.bean.CommandPacket;
import io.shulie.takin.channel.protocal.ChannelProtocol;

/**
 * @author: Hengyu
 * @className: ServerChannelClient
 * @date: 2020/12/29 10:52 下午
 * @description: 服务端命令发送工具类
 */
public interface ServerChannel {

    /**
     * 服务发送工具类
     * @param command  命令发送包
     * @param listener 监听消息处理状态对象
     * @throws Exception 操作zk异常
     * @return 发送状态 true:发送成功 false:发送失败
     */
    public boolean send(CommandPacket command, CommandListener listener) throws Exception;

    /**
     * 设置序列化工具对象
     *
     * @param protocol 序列化对象
     * @return
     */
    public ServerChannel setChannelProtocol(ChannelProtocol protocol);


    /**
     * 获取正在执行的命令对象
     * @param agentId 获取指定AgentID
     * @param commandId 命令ID
     * @return 不在返回null，存在返回命令对象
     * @throws Exception
     */
    public CommandPacket getCurrentCommand(String agentId,String commandId) throws Exception;


    /**
     * 结束channel通道
     */
    public void close();

}
