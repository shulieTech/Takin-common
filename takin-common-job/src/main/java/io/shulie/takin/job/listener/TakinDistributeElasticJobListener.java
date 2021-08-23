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

package io.shulie.takin.job.listener;

import java.util.Date;

import com.dangdang.ddframe.job.executor.ShardingContexts;
import com.dangdang.ddframe.job.lite.api.listener.AbstractDistributeOnceElasticJobListener;
import io.shulie.takin.utils.date.DateHelper;
import lombok.extern.slf4j.Slf4j;

/**
* @Package io.shulie.takin.job.listener
* @author 无涯
* @description:分布式监听器
* @date 2021/6/22 2:33 下午
*/
@Slf4j
@Deprecated
public class TakinDistributeElasticJobListener extends AbstractDistributeOnceElasticJobListener {

    /**
     * startedTimeoutMilliseconds和completedTimeoutMilliseconds分别用来指定作业开始前和完成后的对应的锁等待最大超时时间
     * @param startedTimeoutMilliseconds
     * @param completedTimeoutMilliseconds
     */
    public TakinDistributeElasticJobListener(long startedTimeoutMilliseconds, long completedTimeoutMilliseconds) {
        super(startedTimeoutMilliseconds, completedTimeoutMilliseconds);
    }

    @Override
    public void doBeforeJobExecutedAtLastStarted(ShardingContexts shardingContexts) {
        log.info("定时任务:===>{} 执行开始时间 {} <===",shardingContexts.getJobName(), DateHelper.getYYYYMMDDHHMMSS(new Date()));
    }

    @Override
    public void doAfterJobExecutedAtLastCompleted(ShardingContexts shardingContexts) {
        log.info("定时任务:===>{} 执行结束时间:{} <===",shardingContexts.getJobName(), DateHelper.getYYYYMMDDHHMMSS(new Date()));
    }
}
