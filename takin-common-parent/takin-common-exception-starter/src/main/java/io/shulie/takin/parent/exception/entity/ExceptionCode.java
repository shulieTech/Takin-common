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
 * @author shiyajian
 * create: 2020-09-04
 */
public enum ExceptionCode  implements ExceptionReadable {

    /**
     * 异常信息ExceptionCode
     */
    HTTP_UTIL_URL_EMPTY("takin-util-http-0001", "url is empty or clazz is null"),
    HTTP_REQUEST_ERROR("takin-util-http-0002", "HTTP request is error"),
    HTTP_LOGIN_ERROR("takin-user-login-0001", "takin user login error");
    private String errorCode;

    private String defaultValue;

    ExceptionCode(String errorCode, String defaultValue) {
        this.errorCode = errorCode;
        this.defaultValue = defaultValue;
    }

    @Override
    public String getErrorCode() {
        return this.errorCode;
    }

    @Override
    public String getDefaultValue() {
        return this.defaultValue;
    }
}
