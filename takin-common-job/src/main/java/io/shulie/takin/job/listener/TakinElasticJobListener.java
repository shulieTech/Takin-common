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
import com.dangdang.ddframe.job.lite.api.listener.ElasticJobListener;
import io.shulie.takin.utils.date.DateHelper;
import lombok.extern.slf4j.Slf4j;


/**
* @Package io.shulie.takin.job.impl.listener
* @author 无涯
* @description:
* @date 2021/6/11 12:28 下午
*/
@Slf4j
@Deprecated
public class TakinElasticJobListener implements ElasticJobListener {

    @Override
    public void beforeJobExecuted(ShardingContexts shardingContexts) {
        log.info("定时任务:===>{} 执行开始时间 {} <===",shardingContexts.getJobName(), DateHelper.getYYYYMMDDHHMMSS(new Date()));
    }

    @Override
    public void afterJobExecuted(ShardingContexts shardingContexts) {
        log.info("定时任务:===>{} 执行结束时间:{} <===",shardingContexts.getJobName(), DateHelper.getYYYYMMDDHHMMSS(new Date()));
    }
}
