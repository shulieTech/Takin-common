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

import io.shulie.takin.parent.exception.utils.ExceptionUtils;

/**
 * @author shiyajian
 * create: 2020-09-25
 */
public class BaseException extends RuntimeException {

    private ExceptionReadable ex;

    private Object source;



    public BaseException(ExceptionReadable ex, Object source) {
        super(ExceptionUtils.getCallStackMessage(ex,source));
        this.ex = ex;
        this.source = source;

    }

    /**
     * 通过使用异常链，我们可以提高代码的可理解性、系统的可维护性和友好性。
     * @param ex
     * @param e
     */
    public BaseException(ExceptionReadable ex, Throwable e) {
        super(ExceptionUtils.getCallStackMessage(ex,null),e);
        this.ex = ex;
    }

    /**
     * 通过使用异常链，我们可以提高代码的可理解性、系统的可维护性和友好性。
     * @param ex
     * @param source
     * @param e
     */
    public BaseException(ExceptionReadable ex, Object source , Throwable e) {
        super(ExceptionUtils.getCallStackMessage(ex,source),e);
        this.ex = ex;
        this.source = source;
    }



    public ExceptionReadable getEx() {
        return ex;
    }

    public void setEx(ExceptionReadable ex) {
        this.ex = ex;
    }

    public Object getSource() {
        return source;
    }

    public void setSource(Object source) {
        this.source = source;
    }

    //@Override
    //public synchronized Throwable fillInStackTrace() {
    //    return this;
    //}
}
