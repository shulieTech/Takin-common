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

package io.shulie.takin.channel.router.zk.bean;

/**
 * @author: Hengyu
 * @className: CreateMode
 * @date: 2020/12/29 10:20 下午
 * @description: 定义了 znode 节点的创建模式
 */

public enum CreateMode {

    /**
     * The znode will not be automatically deleted upon client's disconnect.
     */
    PERSISTENT,
    /**
     * The znode will not be automatically deleted upon client's disconnect,
     * and its name will be appended with a monotonically increasing number.
     */
    PERSISTENT_SEQUENTIAL,
    /**
     * The znode will be deleted upon the client's disconnect.
     */
    EPHEMERAL,
    /**
     * The znode will be deleted upon the client's disconnect, and its name
     * will be appended with a monotonically increasing number.
     */
    EPHEMERAL_SEQUENTIAL;
}