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

import io.shulie.takin.channel.bean.CommandPacket;

/**
 * @author: Hengyu
 * @className: ChannelProtocal
 * @date: 2020/12/30 9:14 上午
 * @description:
 */
public interface ChannelProtocol {


    /**
     * 序列化
     * @param packet 数据包对象
     * @return 二进制数组
     */
    public byte[] serialize(CommandPacket packet);



    /**
     * JSON序列化
     * @param packet 数据包对象
     * @return 二进制数组
     */
    public String serializeJson(CommandPacket packet);

    /**
     * 反序列化方法
     * @param data 二进制数组
     * @return 数据包对象
     */
    public CommandPacket deserialize(byte[] data);
}
