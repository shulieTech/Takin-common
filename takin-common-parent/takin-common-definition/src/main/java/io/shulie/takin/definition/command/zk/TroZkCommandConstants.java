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

package io.shulie.takin.definition.command.zk;

/**
 * Agent 和控制台交互的时候，下发各种命令的时候，这是命令的路径
 *
 * @author shiyajian
 * create: 2020-12-09
 */
public final class takinZkCommandConstants {

    private takinZkCommandConstants() { /* no instance */ }

    public static final String NAME_SPACE = "/takin/command";

    /**
     * dump 内存文件
     */
    public static final String DUMP_MEMORY_FILE_PATH = "/dumpMemory/file/path";

    /**
     * 方法追踪交互zk节点
     */
    public static final String TRACE_MANAGE_DEPLOY_PATH = "/trace/sample";

}
