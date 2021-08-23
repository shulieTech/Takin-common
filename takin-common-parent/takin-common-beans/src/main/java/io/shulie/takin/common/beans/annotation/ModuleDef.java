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

package io.shulie.takin.common.beans.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author shiyajian
 * create: 2020-09-15
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ModuleDef {

    /**
     * 模块名称
     *
     * @return 模块名称
     */
    String moduleName();

    /**
     * 子模块名称
     *
     * @return 子模块名称
     */
    String subModuleName();

    /**
     * 操作日志中的msg对应的Key
     *
     * @return 日志的key
     */
    String logMsgKey();

    /**
     * 操作日志中的msg对应的操作类型
     * @return 日志操作类型
     */
    String opTypes() default "default";

    /**
     * 当 license 过期时候，这个模块的接口是否可用
     * @return
     */
    boolean enableExpired() default true;
}
