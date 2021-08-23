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

package io.shulie.takin.job.config.zk;

/**
* @Package io.hzq.job.elasticjob.config.zk
* @author 无涯
* @description:ZooKeeper 配置对象
* @date 2021/6/13 10:11 下午
*/
public class ZkClientConfig {

    private String zkServers;
    private int connectionTimeoutMillis = 30000;
    private int sessionTimeoutMillis = 60000;
    private String threadName = "elastic-job";
    /**
     * 命令空间
     */
    private String namespace;


    public ZkClientConfig() {
    }

    public ZkClientConfig(String zkServers) {
        this.zkServers = zkServers;
    }

    public String getZkServers() {
        return zkServers;
    }

    public ZkClientConfig setZkServers(String zkServers) {
        this.zkServers = zkServers;
        return this;
    }

    public int getConnectionTimeoutMillis() {
        return connectionTimeoutMillis;
    }

    public ZkClientConfig setConnectionTimeoutMillis(int connectionTimeoutMillis) {
        this.connectionTimeoutMillis = connectionTimeoutMillis;
        return this;
    }

    public int getSessionTimeoutMillis() {
        return sessionTimeoutMillis;
    }

    public ZkClientConfig setSessionTimeoutMillis(int sessionTimeoutMillis) {
        this.sessionTimeoutMillis = sessionTimeoutMillis;
        return this;
    }

    public String getThreadName() {
        return threadName;
    }

    public void setThreadName(String threadName) {
        this.threadName = threadName;
    }

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }
}
