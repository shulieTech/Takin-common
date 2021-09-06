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

package io.shulie.takin.definition.config.zk;

/**
 * Agent 和控制台交互的时候，如果通过zk拿参数，路径在这里
 * @author shiyajian
 * create: 2020-12-09
 */
public final class TakinZkConfigConstants {

    private TakinZkConfigConstants() { /* no instance */ }

    public static final String NAME_SPACE = "/takin/config";

    /**
     * 白名单，按应用名保存
     */
    public static final String ALLOW_LIST_PARENT_PATH = "/allow_list";

    /**
     * 黑名单
     */
    public static final String BLOCK_LIST_PARENT_PATH = "/block_list";

    /**
     * 影子库表，按应用名保存
     */
    public static final String SHADOW_DB_PARENT_PATH = "/shadow_db";

    /**
     * 影子job，按应用名保存
     */
    public static final String SHADOW_JOB_PARENT_PATH = "/shadow_job";

    /**
     * 挡板信息，按应用名保存
     */
    public static final String LINK_GUARD_PARENT_PATH = "/guard";

    /**
     * 全局压测开关
     */
    public static final String CLUSTER_TEST_SWITCH_PATH = "/switch/cluster_test";

    /**
     * 全局白名单开关
     */
    public static final String ALLOW_LIST_SWITCH_PATH = "/switch/allow_list";
}
