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

package io.shulie.takin.parent.exception.entity;

/**
* @Package io.shulie.takin.exception.entity
* @author 无涯
* @description:可读性的异常接口
* @date 2021/7/1 17:01
*/
public interface ExceptionReadable {

    /**
     * 异常在资源文件中的key
     */
    String getErrorCode();

    /**
     * 如果资源文件中没有找到key，那么对应的默认值
     */
    String getDefaultValue();
}
