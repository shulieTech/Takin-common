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
 * @className: CommandPacket
 * @date: 2020/12/29 10:20 下午
 * @description: 命令传输对象包
 */
public class CommandPacket{


    private String id;

    /**
     * 命令状态
     */
    private CommandStatus status;


    /**
     * 命令状态
     */
    private CommandRespType commandRespType = CommandRespType.COMMAND_CALLBACK;

    /**
     * 命令状态
     */
    private String responsePushUrl;


    /**
     * 命令发送对象
     */
    private CommandSend send;

    /**
     * 命令响应
     */
    private CommandResponse response;


    public CommandSend getSend() {
        return send;
    }

    public void setSend(CommandSend send) {
        this.send = send;
    }

    public CommandResponse getResponse() {
        return response;
    }

    public void setResponse(CommandResponse response) {
        this.response = response;
    }

    public CommandStatus getStatus() {
        return status;
    }

    public void setStatus(CommandStatus status) {
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public CommandRespType getCommandRespType() {
        return commandRespType;
    }

    public void setCommandRespType(CommandRespType commandRespType) {
        this.commandRespType = commandRespType;
    }

    public String getResponsePushUrl() {
        return responsePushUrl;
    }

    public void setResponsePushUrl(String responsePushUrl) {
        this.responsePushUrl = responsePushUrl;
    }



}