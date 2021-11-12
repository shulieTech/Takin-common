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

package io.shulie.takin.common.beans.response;

/**
* @Package io.shulie.takin.common.beans.response
* @author 无涯
* @description:
* @date 2021/6/28 10:23 上午
*/
import java.io.Serializable;

import lombok.Data;

@Data
public class ResponseResult<T> implements Serializable {

    public static final boolean DEFAULT_SUCCESS = true;

    private static final long serialVersionUID = 1L;

    /**
     * 错误信息实体
     */
    private ErrorInfo error;

    /**
     * 返回数据，如果请求失败，则为空
     */
    private T data;

    /**
     * 数据总数
     */
    private Long totalNum;

    /**
     * 成功标记
     */
    private Boolean success;

    private ResponseResult() { /* no instance */ }

    private ResponseResult(T data) {
        this(null, data, DEFAULT_SUCCESS,null);
    }


    private ResponseResult(T data,Long totalNum) {

        this(null, data, DEFAULT_SUCCESS,totalNum);
    }


    private ResponseResult(ErrorInfo error, boolean success) {
        this(error, null, success,null);
    }

    private ResponseResult(ErrorInfo error, T data, boolean success,Long totalNum) {
        this.error = error;
        this.data = data;
        this.success = success;
        this.totalNum= totalNum;
    }

    /**
     * 返回成功,无内容
     *
     * @param <T>
     * @return
     */
    public static <T> ResponseResult<T> success() {
        return new ResponseResult<>(null);
    }

    /**
     * 返回成功
     *
     * @return
     */
    public static <T> ResponseResult<T> success(T data) {
        return new ResponseResult<>(data);
    }


    /**
     * 返回成功
     *
     * @return
     */
    public static <T> ResponseResult<T> success(T data,Long totalNum) {
        return new ResponseResult<>(data,totalNum);
    }

    /**
     * 返回失败，使用传入的错误码
     */
    public static <T> ResponseResult<T> fail(String code, String msgTemplate, String solution) {
        ErrorInfo errorInfo = ErrorInfo.build(code, msgTemplate, solution);
        return new ResponseResult<>(errorInfo, false);
    }

    /**
     * 返回失败，使用传入的错误码
     */
    public static <T> ResponseResult<T> fail(String msgTemplate, String solution) {
        ErrorInfo errorInfo = ErrorInfo.build("500", msgTemplate, solution);
        return new ResponseResult<>(errorInfo, false);
    }

    @Data
    public static class ErrorInfo {

        /**
         * 错误code
         */
        private String code;

        /**
         * 最终展示的信息
         */
        private String msg;

        /**
         * 解决方案
         */
        private String solution;

        public ErrorInfo(){};

        public ErrorInfo(String code, String msg, String solution) {
            this.code = code;
            this.msg = msg;
            this.solution = solution;
        }

        public static ErrorInfo build(String code, String msg, String solution) {
            return new ErrorInfo(code, msg, solution);
        }
    }

}

