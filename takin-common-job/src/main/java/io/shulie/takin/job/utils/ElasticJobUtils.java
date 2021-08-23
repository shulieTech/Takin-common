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

package io.shulie.takin.job.utils;

import java.util.Arrays;
import java.util.List;

import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.dangdang.ddframe.job.config.JobCoreConfiguration;
import com.dangdang.ddframe.job.config.simple.SimpleJobConfiguration;
import com.dangdang.ddframe.job.lite.config.LiteJobConfiguration;
import io.shulie.takin.job.ElasticJobProperties;
import io.shulie.takin.job.config.zk.ZkClientConfig;
import org.apache.commons.lang3.StringUtils;

/**
 * @author 无涯
 * @Package io.hzq.job.elasticjob.utils
 * @date 2021/6/13 10:18 下午
 */
public class ElasticJobUtils {

    /**
     * 校验zk配置
     * @param clientConfig
     */
    public static void checkZKConfig(ZkClientConfig clientConfig) {
        if(clientConfig == null) {
            throw new RuntimeException("未读取到配置中心zk，请核对是否存在`ZkClientConfig`");
        }
        if (StringUtils.isEmpty(clientConfig.getZkServers())) {
            throw new RuntimeException("配置中心zk地址没有填写，请核对校验`zkServers`");
        }

        if (StringUtils.isEmpty(clientConfig.getNamespace())) {
            throw new RuntimeException("配置中心zk命名空间没有填写，请核对校验`namespace`");
        }

    }

    private static List<String> jobTypeNameList = Arrays.asList("SimpleJob", "DataflowJob", "ScriptJob");

    /**
     * 获取 {@link LiteJobConfiguration} 对象
     *
     * @param jobClass               定时器实现类
     * @param jobName                定时器名称
     * @param cron                   定时参数
     * @param shardingTotalCount     作业分片总数
     * @param shardingItemParameters 当前参数 可以为null
     * @param jobParameters          作业自定义参数 可以为null
     * @return {@link LiteJobConfiguration}
     */
    @SuppressWarnings("all")
    public static LiteJobConfiguration getLiteJobConfiguration(Class<? extends SimpleJob> jobClass, ElasticJobProperties.JobConfig jobConfig) {

        // 解决CGLIB代理问题
        String jobTypeName = jobClass.getInterfaces()[0].getSimpleName();
        if (!jobTypeNameList.contains(jobTypeName)) {
            // jobTypeName = jobClass.getSuperclass().getInterfaces()[0].getSimpleName();
            jobClass = (Class<? extends SimpleJob>) jobClass.getSuperclass();
        }

        // 定义作业核心配置
        JobCoreConfiguration simpleCoreConfig = JobCoreConfiguration
            .newBuilder(jobConfig.getJobName(), jobConfig.getCron(), jobConfig.getShardingTotalCount())
            .shardingItemParameters(jobConfig.getShardingItemParameters())
            .description(jobConfig.getDescription())
            .jobParameter(jobConfig.getJobParameters())
            .misfire(jobConfig.isMisfire())
            .failover(jobConfig.isFailover())
            //.jobProperties("job_exception_handler","com.finup.bestriver.handler.BestRiverExceptionHandler")
            .build();

        // 定义SIMPLE类型配置
        SimpleJobConfiguration simpleJobConfig = new SimpleJobConfiguration(simpleCoreConfig, jobClass.getCanonicalName());

        // 定义Lite作业根配置
        return LiteJobConfiguration.newBuilder(simpleJobConfig).overwrite(true)
            .disabled(jobConfig.isDisabled())
            .build();
    }
}
