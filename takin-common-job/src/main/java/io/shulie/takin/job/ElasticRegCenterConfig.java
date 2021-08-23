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

import javax.sql.DataSource;

import com.dangdang.ddframe.job.event.JobEventConfiguration;
import com.dangdang.ddframe.job.event.rdb.JobEventRdbConfiguration;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperConfiguration;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;
import io.shulie.takin.job.config.ElasticJobConfig;
import io.shulie.takin.job.config.zk.ZkClientConfig;
import io.shulie.takin.job.utils.ElasticJobUtils;

/**
 * Job注册中心
 * @author 无涯
 * @Package io.shulie.takin.web.app.conf
 * @date 2021/6/9 4:00 下午
 */
public class ElasticRegCenterConfig {

   private ElasticJobConfig elasticJobConfig;

   public ElasticRegCenterConfig(ElasticJobConfig elasticJobConfig) {
        this.elasticJobConfig = elasticJobConfig;
    }
    public ZookeeperRegistryCenter regCenter() {
        if(elasticJobConfig == null) {
            throw new RuntimeException("初始化失败");
        }
        ElasticJobUtils.checkZKConfig(elasticJobConfig.getZkClientConfig());
        ZkClientConfig zkClientConfig = elasticJobConfig.getZkClientConfig();
        ZookeeperConfiguration zookeeperConfiguration = new ZookeeperConfiguration(zkClientConfig.getZkServers(),
            zkClientConfig.getNamespace());
        zookeeperConfiguration.setConnectionTimeoutMilliseconds(zkClientConfig.getConnectionTimeoutMillis());
        zookeeperConfiguration.setSessionTimeoutMilliseconds(zkClientConfig.getSessionTimeoutMillis());
        ZookeeperRegistryCenter zookeeperRegistryCenter = new ZookeeperRegistryCenter(zookeeperConfiguration);
        zookeeperRegistryCenter.init();
        return zookeeperRegistryCenter;
    }

    /**
     * Elastic-Job会自动在指定的数据库中创建JOB_EXECUTION_LOG和JOB_STATUS_TRACE_LOG两张表
     * @return
     */
    public JobEventConfiguration jobEventConfiguration() {
        if(elasticJobConfig == null) {
            throw new RuntimeException("初始化失败");
        }
        DataSource dataSource = elasticJobConfig.getDataSource();
        if(dataSource == null) {
            // 不进行初始化
            throw new RuntimeException("数据库初始化配置未配置");
        }
        return new JobEventRdbConfiguration(dataSource);
    }

}
