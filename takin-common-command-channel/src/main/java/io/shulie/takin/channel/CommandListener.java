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

/**
 * @author: Hengyu
 * @className: CommandListener
 * @date: 2020/12/29 10:52 下午
 * @description: 命令处理工具类
 */
public interface CommandListener {

    /**
     * 接收命令进行回调
     * @param packet 命令对象包
     */
    public void call(CommandPacket packet);

}
