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
import io.shulie.takin.channel.bean.CommandResponse;
import io.shulie.takin.channel.bean.CommandSend;
import io.shulie.takin.channel.handler.CommandHandler;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: Hengyu
 * @className: CommandHandler
 * @date: 2020/12/29 11:29 下午
 * @description: 定义命令处理
 */
public class UploadFileHandler implements CommandHandler {

    @Override
    public CommandResponse handle(CommandPacket packet) {


        CommandResponse response = new CommandResponse();
        Map<String, Object> param = new HashMap<>(8);
        param.put("test","test");
        response.setParam(param);

        CommandSend send = packet.getSend();
        Object index = send.getParam().get("index");
        System.out.println("client receiver index: "+ index);

        return response;
    }

}
