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

package io.shulie.takin.job.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
* @Package io.shulie.takin.job.annotation
* @author 无涯
* @description:
* @date 2021/6/11 12:26 下午
*/
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface ElasticSchedulerJob {
    /**
     * 任务名称
     * @return
     */
    String jobName();

    /**
     * cron表达式，用于控制作业触发时间
     * @return
     */
    String cron();

    /**
     * 分片参数
     * @return
     */
    String shardingItemParameters() default "";

    /**
     * 总分片数
     * @return
     */
    int shardingTotalCount() default 1;

    /**
     * 任务描述
     * @return
     */
    String description() default "";
    /**
     * 是否自动失效转移
     * @return
     */
    boolean misfire() default false;

    /**
     * 错过是否重执行
     * @return
     */
    boolean failover() default false;

    /**
     * 作业是否启动时禁止
     * @return
     */
    boolean disabled() default false;

    String jobParameters() default "";

    /**
     * 任务全路径
     * 此处的jobClass需要自己指定，如果通过jobClass.getCanonicalName()的方式，在项目重启时会报错（动态代理生成的job bean对象每次重启都会变化）。
     * @return
     */
   // String jobClassFullPath();

    boolean isEvent() default true;


}
