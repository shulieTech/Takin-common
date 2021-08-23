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

package io.shulie.takin.job.config;

import javax.sql.DataSource;

import io.shulie.takin.job.config.zk.ZkClientConfig;
import lombok.Data;

/**
 * @author 无涯
 * @Package io.hzq.job.elasticjob.config
 * @date 2021/6/13 10:14 下午
 */
@Data
public class ElasticJobConfig {
    /**
     * zk配置
     */
    private ZkClientConfig zkClientConfig;

    /**
     * 数据库配置
     */
    private DataSource dataSource;


}
