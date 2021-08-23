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
import io.shulie.takin.channel.type.Command;

/**
 * @author: Hengyu
 * @className: CommandRegistry
 * @date: 2020/12/29 10:52 下午
 * @description: 命令注册中心
 */
public interface CommandRegistry {

    /**
     * 发送工具类
     * @param command 命令对象
     * @param handler 监听处理对象
     */
    public void register(Command command, CommandHandler handler);

    /**
     * 获取命令处理对象
     * @param commandId 命令类型Id
     * @return 命令处理对象
     */
    public CommandHandler getHandler(String commandId);

}
