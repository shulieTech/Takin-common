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

package io.shulie.takin.channel.utils;

/**
 * @author: Hengyu
 * @className: StringUtils
 * @date: 2021/1/7 4:42 下午
 * @description:
 */
public class StringUtils {

    public static boolean isBlank(String target){
        if (target == null || target.trim().isEmpty()){
            return true;
        }
        return false;
    }
}
