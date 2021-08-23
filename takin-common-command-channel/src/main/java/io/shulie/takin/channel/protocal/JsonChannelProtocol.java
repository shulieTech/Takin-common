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

package io.shulie.takin.channel.protocal;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import io.shulie.takin.channel.bean.CommandPacket;

/**
 * @author: Hengyu
 * @className: JSONChannelProtocol
 * @date: 2020/12/30 9:17 上午
 * @description:
 */
public class JsonChannelProtocol implements ChannelProtocol {

    @Override
    public byte[] serialize(CommandPacket packet) {
        String json = JSON.toJSONString(packet, SerializerFeature.DisableCircularReferenceDetect);
        byte[] bytes = json.getBytes();
        return bytes;
    }

    @Override
    public String serializeJson(CommandPacket packet) {
        String json = JSON.toJSONString(packet, SerializerFeature.DisableCircularReferenceDetect);
        return json;
    }

    @Override
    public CommandPacket deserialize(byte[] data) {
        String jsonStr = new String(data);
        CommandPacket commandPacket = JSON.parseObject(jsonStr, CommandPacket.class);
        return commandPacket;
    }


}
