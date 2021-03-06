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

package io.shulie.takin.channel.bean;

/**
 * @author: Hengyu
 * @className: CommandStatus
 * @date: 2020/12/29 10:20 下午
 * @description: 命令下发状态
 */
public enum CommandStatus {

    /**
     * 命令已发送
     */
    COMMAND_SEND(0),
    /**
     * 命令正在执行
     */
    COMMAND_RUNNING(1),

    /**
     * 命令执行完成成功
     */
    COMMAND_COMPLETED_SUCCESS(2),
    /**
     * 命令执行完成失败
     */
    COMMAND_COMPLETED_FAIL(3);

    private int status;

    CommandStatus(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}