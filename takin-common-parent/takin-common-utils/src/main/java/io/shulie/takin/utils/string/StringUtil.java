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

package io.shulie.takin.utils.string;

import cn.hutool.core.util.StrUtil;
import org.springframework.web.util.JavaScriptUtils;

/**
 * @author shiyajian
 * create: 2020-10-20
 */
public class StringUtil extends StrUtil {

    /**
     * 转义特殊字符
     *
     * @param content 内容
     * @return 转义之后的内容
     */
    public static String converterString(String content) {
        return JavaScriptUtils.javaScriptEscape(content);
    }

    /**
     * 使用format，然后转义特殊字符
     *
     * @param format 规则
     * @param args   规则对应参数
     * @return 转义之后的内容
     */
    public static String format(String format, Object... args) {
        String str = String.format(format, args);
        return JavaScriptUtils.javaScriptEscape(str);
    }
}
