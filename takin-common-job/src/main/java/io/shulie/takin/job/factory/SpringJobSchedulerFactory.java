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

package io.shulie.takin.job.factory;

import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.dangdang.ddframe.job.event.JobEventConfiguration;
import com.dangdang.ddframe.job.lite.config.LiteJobConfiguration;
import com.dangdang.ddframe.job.lite.spring.api.SpringJobScheduler;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;
import io.shulie.takin.job.ElasticJobProperties;
import io.shulie.takin.job.utils.ElasticJobUtils;

/**
* @Package io.hzq.job.elasticjob.factory
* @author 无涯
* @description:
* @date 2021/6/13 10:34 下午
*/
public class SpringJobSchedulerFactory {

    private ElasticJobProperties elasticJobProperties;

    private ZookeeperRegistryCenter regCenter;

    private JobEventConfiguration jobEventConfiguration;


    public SpringJobSchedulerFactory(
        ElasticJobProperties elasticJobProperties,
        ZookeeperRegistryCenter regCenter) {
        this.elasticJobProperties = elasticJobProperties;
        this.regCenter = regCenter;
    }

    public SpringJobSchedulerFactory(
        ElasticJobProperties elasticJobProperties,
        ZookeeperRegistryCenter regCenter,
        JobEventConfiguration jobEventConfiguration) {
        this.elasticJobProperties = elasticJobProperties;
        this.regCenter = regCenter;
        this.jobEventConfiguration = jobEventConfiguration;
    }
    /**
     * 获取{@link SpringJobScheduler} 对象
     *
     * @param simpleJob 实现SimpleJob类型
     * @param jobName   config job的名称
     * @param isEvent   是否开启历史轨迹
     * @return SpringJobScheduler
     */
    public SpringJobScheduler getSpringJobScheduler(SimpleJob simpleJob, String jobName, Boolean isEvent) {
        ElasticJobProperties.JobConfig jobConfig = elasticJobProperties.getJobConfigMap().get(jobName);
        jobConfig.setEvent(isEvent);

        return createSpringJobScheduler(simpleJob, jobConfig);
    }

    /**
     * 获取{@link SpringJobScheduler} 对象 注解方式
     *
     * @param jobConfig 定时器配置
     * @return SpringJobScheduler
     */
    public SpringJobScheduler getSpringJobScheduler(SimpleJob simpleJob, ElasticJobProperties.JobConfig jobConfig) {
        return createSpringJobScheduler(simpleJob, jobConfig);
    }


    private SpringJobScheduler createSpringJobScheduler(SimpleJob simpleJob, ElasticJobProperties.JobConfig jobConfig) {

        if (jobConfig == null) {
            throw new NullPointerException(String.format("%s 定时器配置为null", jobConfig.getJobName()));
        }

        LiteJobConfiguration liteJobConfiguration = ElasticJobUtils.getLiteJobConfiguration(simpleJob.getClass(), jobConfig);

        if (jobConfig.isEvent() && jobEventConfiguration != null) {
            return new SpringJobScheduler(simpleJob, regCenter, liteJobConfiguration, jobEventConfiguration);
        }
        return new SpringJobScheduler(simpleJob, regCenter, liteJobConfiguration);
    }
}
