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

package io.shulie.takin.constants;

/**
 * @author shiyajian
 * create: 2020-09-21
 */
public final class TakinRequestConstant {

    private TakinRequestConstant() { /* no instance */ }

    /**
     * 压测请求的 Header 头
     */
    public static final String CLUSTER_TEST_HEADER_KEY = "User-Agent";

    /**
     * 压测请求对应的值
     * 注意：Agent 里面，目前单词拼写错误，为"Perfomance"，此处将错就错的一直下去了
     */
    public static final String CLUSTER_TEST_HEADER_VALUE = "PerfomanceTest";

    /**
     * 调试请求对应的值
     */
    public static final String DEBUG_CLUSTER_TEST_HEADER_VALUE = "DebugPerfomanceTest";

    /**
     * 流量的客户ID，用于数据隔离，客户ID+场景ID+任务ID构成唯一值
     */
    public static final String CLUSTER_TEST_CUSTOMER_HEADER_VALUE = "Takin-Customer-ID";

    /**
     * 压测任务的场景ID，用于数据隔离，客户ID+场景ID+任务ID构成唯一值
     */
    public static final String CLUSTER_TEST_SCENE_HEADER_VALUE = "Takin-Scene-ID";

    /**
     * 压测任务的ID，用于数据隔离，客户ID+场景ID+任务ID构成唯一值
     */
    public static final String CLUSTER_TEST_TASK_HEADER_VALUE = "Takin-Task-ID";


}
