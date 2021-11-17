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

package io.shulie.takin.channel.router.zk;

import java.util.concurrent.ThreadFactory;


import io.shulie.takin.channel.exception.ChannelException;
import io.shulie.takin.channel.router.zk.impl.NetflixCuratorZkClient;
import io.shulie.takin.channel.utils.StringUtils;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.ZooKeeper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Description CuratorZkClient 实例工厂
 * @Author guohz
 * @mail guohaozhu@shulie.io
 * @Date 2020/12/29 20:11
 */
public class NetflixCuratorZkClientFactory {

    private static final Logger logger = LoggerFactory.getLogger(NetflixCuratorZkClientFactory.class);

    private static final class NetflixCuratorZkClientFactoryHolder {
        public final static NetflixCuratorZkClientFactory INSTANCE = new NetflixCuratorZkClientFactory();
    }

    public static NetflixCuratorZkClientFactory getInstance() {
        return NetflixCuratorZkClientFactoryHolder.INSTANCE;
    }

    private NetflixCuratorZkClientFactory() {
    }

    public ZkClient create(final ZkClientConfig spec) throws Exception {
        if (StringUtils.isBlank(spec.getZkServers())) {
            throw new ChannelException("zookeeper servers is empty.");
        }
        String path = ZooKeeper.class.getProtectionDomain().getCodeSource().getLocation().toString();
        logger.info("Load ZooKeeper from {}", path);

        CuratorFramework client = CuratorFrameworkFactory.builder()
                .connectString(spec.getZkServers())
                .retryPolicy(new ExponentialBackoffRetry(1000, 3))
                .connectionTimeoutMs(spec.getConnectionTimeoutMillis())
                .sessionTimeoutMs(spec.getSessionTimeoutMillis())
                .threadFactory(new ThreadFactory() {
                    @Override
                    public Thread newThread(Runnable r) {
                        return new Thread(r, spec.getThreadName());
                    }
                })
                .build();
        client.start();
        logger.info("ZkClient started: {}", spec.getZkServers());
        NetflixCuratorZkClient theClient = new NetflixCuratorZkClient(client, spec.getZkServers());
        return theClient;

    }
}
