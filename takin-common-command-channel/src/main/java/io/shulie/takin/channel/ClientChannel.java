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

import io.shulie.takin.channel.handler.CommandHandler;
import io.shulie.takin.channel.protocal.ChannelProtocol;
import io.shulie.takin.channel.router.zk.ZkClientConfig;
import io.shulie.takin.channel.type.Command;

import java.util.concurrent.ScheduledExecutorService;

/**
 * @author: Hengyu
 * @className: ServerChannelClient
 * @date: 2020/12/29 10:52 下午
 * @description: 服务端命令发送工具类
 */
public interface ClientChannel {


    /**
     * 构建客户端影子通道对象,初始化ZK对象，必须在尾部调用
     * @param config ZK配置对象
     * @return 返回 影子配置对象
     * @throws Exception 初始化配置失败
     */
    public ClientChannel build(ZkClientConfig config) throws Exception;

    /**
     * 注册Agent 授权key
     * @param userAppKey 注册节点Agent节点
     * @return channel对象
     */
    public ClientChannel registerUserAppKey(String userAppKey);

    /**
     * 注册监听工具方法
     * @param agentId 注册节点Agent节点
     * @throws Exception zk操作异常
     */
    public void register(String agentId) throws Exception;


    /**
     * 注册命令处理Handler
     * @param command 命令对象
     * @param handler 命令处理对象
     * @return
     */
    public ClientChannel registerHandler(Command command, CommandHandler handler);

    /**
     * 设置序列化工具对象
     * @param protocol 序列化对象
     * @return
     */
    public ClientChannel setChannelProtocol(ChannelProtocol protocol);

    /**
     * 设置定时执行任务线程组
     * @param executorService 定时执行任务线程
     * @return
     */
    public ClientChannel setScheduledExecutorService(ScheduledExecutorService executorService);

    /**
     * 结束channel通道
     */
    public void close();
}
