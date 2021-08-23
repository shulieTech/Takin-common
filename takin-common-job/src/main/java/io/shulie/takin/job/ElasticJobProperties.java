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

package io.shulie.takin.job;

import java.util.HashMap;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
* @Package io.hzq.job.elasticjob
* @author 无涯
* @description:
* @date 2021/6/13 10:38 下午
*/
@Data
@ToString
@ConfigurationProperties(ElasticJobProperties.PREFIX)
public class ElasticJobProperties {

    public static final String PREFIX = "takin.elasticjob.scheduled";

    private Map<String, JobConfig> jobConfigMap = new HashMap<>();

    @Data
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    public static class JobConfig{
        private String jobName;

       // private SimpleJob jobClass;

        private String cron;

        private int shardingTotalCount;

        private String description;

        private String shardingItemParameters;

        private String jobParameters;

        //private String classFullPath;

        private boolean misfire;

        private boolean failover;

        private boolean disabled;

        private boolean isEvent;
    }
}
