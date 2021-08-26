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

package io.shulie.takin.job.parser;

import java.util.Map;
import java.util.Optional;

import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.dangdang.ddframe.job.lite.api.JobScheduler;
import com.dangdang.ddframe.job.lite.spring.api.SpringJobScheduler;
import io.shulie.takin.job.ElasticJobProperties;
import io.shulie.takin.job.annotation.ElasticSchedulerJob;
import io.shulie.takin.job.factory.SpringJobSchedulerFactory;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.annotation.AnnotationUtils;

/**
 * {@link JobScheduler} registered
 *
 * @author purgeyao
 * @since 1.0
 */
@Slf4j
public class JobConfParser implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    private SpringJobSchedulerFactory springJobSchedulerFactory;

    public JobConfParser(SpringJobSchedulerFactory springJobSchedulerFactory) {
        this.springJobSchedulerFactory = springJobSchedulerFactory;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {

        this.applicationContext = applicationContext;

        Map<String, Object> beanMap = applicationContext.getBeansWithAnnotation(ElasticSchedulerJob.class);
        beanMap.forEach((className, confBean) -> {

            // 获取注解信息组装配置
            Class<?> jobClass = confBean.getClass();
            if (!SimpleJob.class.isAssignableFrom(jobClass)) {
                throw new BeanCreationException(String.format("[JobConfParser] %s 初始化异常 请实现 %s", jobClass, SimpleJob.class));
            }
            ElasticSchedulerJob config = AnnotationUtils.findAnnotation(jobClass, ElasticSchedulerJob.class);
            ElasticJobProperties.JobConfig jobConfig = createJobConfig(config, className);

            // 构建SpringJobScheduler对象初始化
            SpringJobScheduler springJobScheduler = springJobSchedulerFactory.getSpringJobScheduler((SimpleJob) confBean, jobConfig);
            springJobScheduler.init();
            log.info("JobScheduler:{} registered successfully", jobConfig.getJobName());
        });

    }

    private ElasticJobProperties.JobConfig createJobConfig(ElasticSchedulerJob config, String className) {

        String jobName = config.jobName();
        String cron = applicationContext.getEnvironment().resolvePlaceholders(config.cron());

        if (StringUtils.isBlank(jobName)) {
            String projectName = applicationContext.getId();
            jobName = String.format("%s_%s", projectName.toUpperCase(), className);
            log.info("[createJobConfig] default job name {}", jobName);
        }

        // 分片配置处理
        int shardingTotalCount = config.shardingTotalCount();
        if(config.isSharding()) {
            // 分片
            shardingTotalCount = Optional.ofNullable(applicationContext.getEnvironment().getProperty("job.sharding.total.Count"))
                .map(Integer::valueOf)
                .orElse(config.shardingTotalCount());
        }

        return new ElasticJobProperties.JobConfig(jobName, cron,
            shardingTotalCount, config.description(),
            config.shardingItemParameters(),config.jobParameters(),
            config.misfire(),config.failover(),config.disabled(),
            config.isEvent());
    }
}
