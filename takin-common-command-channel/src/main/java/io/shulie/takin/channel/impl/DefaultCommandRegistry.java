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

package io.shulie.takin.channel.impl;

import io.shulie.takin.channel.CommandRegistry;
import io.shulie.takin.channel.handler.CommandHandler;
import io.shulie.takin.channel.type.Command;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author: Hengyu
 * @className: DefaultCommandRegistry
 * @date: 2020/12/29 11:42 下午
 * @description:
 */
public class DefaultCommandRegistry implements CommandRegistry {

    private ConcurrentHashMap<String,CommandHandler> registry;

    public DefaultCommandRegistry() {
        this.registry = new ConcurrentHashMap<>();
    }

    @Override
    public void register(Command command, CommandHandler handler) {
        this.registry.put(command.getId(),handler);
    }

    @Override
    public CommandHandler getHandler(String commandId) {
        return this.registry.get(commandId);
    }

}
