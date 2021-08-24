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

import com.alibaba.fastjson.JSON;
import io.shulie.takin.channel.bean.HeartBeat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.util.concurrent.*;

/**
 * @author: Hengyu
 * @className: ZkClientHeartbeat
 * @date: 2020/12/29 10:52 下午
 * @description: clint heartbeats
 */
public class ZkPathHeartbeat {

    public static final String CHARSET_NAME = "UTF-8";
    private ScheduledExecutorService executorService;
    private static int DEFAULT_TIME = 30;
    private ZkClient zkClient;
    private Logger logger = LoggerFactory.getLogger(ZkPathHeartbeat.class);


    public ZkPathHeartbeat(ZkClient zkClient,ScheduledExecutorService executorService) {
        this.zkClient = zkClient;
        this.executorService = executorService;
    }

    /**
     * 启动定时TTL 维护命令AgentId对应路径心跳时间
     *
     * @param path 命令路径
     */
    public void start(String path) {
        final String finalPath = path;
        executorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                HeartBeat beat = generateHeartBeat();
                try {
                    byte[] serialize = serialize(beat);
                    zkClient.updateData(finalPath, serialize);
                } catch (Exception e) {
                    logger.error("CommandChannel heartbeat ttl exception", e);
                }
            }
        }, DEFAULT_TIME, DEFAULT_TIME, TimeUnit.MINUTES);
    }

    public HeartBeat deserialize(byte[] beats) throws UnsupportedEncodingException {
        String beatStr = new String(beats,CHARSET_NAME);
        HeartBeat bean = JSON.parseObject(beatStr,HeartBeat.class);
        return bean;
    }

    public byte[]  serialize(HeartBeat beat) throws UnsupportedEncodingException {
        String ttl = JSON.toJSONString(beat);
        byte[] data = ttl.getBytes(CHARSET_NAME);
        return data;
    }

    public HeartBeat generateHeartBeat() {
        HeartBeat beat = new HeartBeat();
        beat.setTtl(System.currentTimeMillis());
        return beat;
    }

    /**
     * 停止心跳
     */
    public void stop(){
        executorService.shutdown();
    }
}
