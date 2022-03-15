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

package io.shulie.takin.parent.exception.advice;

import java.util.Objects;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import io.shulie.takin.common.beans.response.ResponseResult;
import io.shulie.takin.parent.exception.entity.BaseException;
import io.shulie.takin.parent.exception.holder.ExceptionMessageHolder;
import io.shulie.takin.parent.exception.holder.ExceptionMessageHolder.ExceptionEntity;
import io.shulie.takin.parent.exception.utils.ExceptionUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

/**
 * @author shiyajian
 * create: 2020-09-25
 */
@RestControllerAdvice
@Slf4j
public class BaseExceptionAdvice {

    @Autowired
    private ExceptionMessageHolder exceptionMessageHolder;

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ResponseResult<Void> takinExceptionHandler(MethodArgumentTypeMismatchException e, HttpServletRequest request) {
        String uri = request.getRequestURI();
        log.error("URI:[{}]参数格式不匹配，错误信息：{}", uri, e.getMessage());
        return ResponseResult.fail(HttpStatus.BAD_REQUEST.value() + "", "错误的请求参数，参数类型不匹配", "检查参数类型");
    }

    /**
     * 表单
     * @param e
     * @param request
     * @return
     */
    @ExceptionHandler({BindException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ResponseResult<Void> takinBindExceptionHandler(BindException e, HttpServletRequest request) {
        String uri = request.getRequestURI();
        log.error("URI:[{}]参数格式不匹配，错误信息：{}", uri, Objects.requireNonNull(e.getBindingResult().getFieldError()).getDefaultMessage());
        return ResponseResult.fail(HttpStatus.BAD_REQUEST.value() + "",Objects.requireNonNull(e.getBindingResult().getFieldError()).getDefaultMessage() , "检查参数类型");
    }

    /**
     * json
     * @param e
     * @param request
     * @return
     */
    @ExceptionHandler({MethodArgumentNotValidException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ResponseResult<Void> takinMethodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e, HttpServletRequest request) {
        String uri = request.getRequestURI();
        log.error("URI:[{}]参数格式不匹配，错误信息：{}", uri, Objects.requireNonNull(e.getBindingResult().getFieldError()).getDefaultMessage());
        return ResponseResult.fail(HttpStatus.BAD_REQUEST.value() + "",Objects.requireNonNull(e.getBindingResult().getFieldError()).getDefaultMessage() , "检查参数类型");
    }

    @ExceptionHandler(ServletRequestBindingException.class)
    public ResponseResult<Void> takinExceptionHandler(ServletRequestBindingException e, HttpServletRequest request) {
        String uri = request.getRequestURI();
        log.error("URI:[{}]参数校验报错，错误信息：{}", uri, e.getMessage());
        return ResponseResult.fail(HttpStatus.BAD_REQUEST.value() + "", "错误的请求参数，参数校验不通过", "检查参数的校验规则");
    }

    @ExceptionHandler(BaseException.class)
    public ResponseResult<Void> takinExceptionHandler(BaseException e) {
        ExceptionEntity exceptionEntity = exceptionMessageHolder.get(e.getEx(), e.getSource());
        if(StringUtils.isBlank(exceptionEntity.getMessage())) {
            // 填充数据
            exceptionEntity.setMessage(ExceptionUtils.getMessage(e.getEx(),e.getSource(),e));
        }
        return exceptionResolve(e, exceptionEntity);
    }

    @ExceptionHandler(Throwable.class)
    public ResponseResult<Void> exceptionHandler(Throwable e) {
        if(e.getMessage().contains("数据签名异常,请联系管理员")){
            ExceptionEntity  exceptionEntity = new ExceptionEntity();
            exceptionEntity.setCode("19800-T0103");
            exceptionEntity.setDebug("19800-T0103-数据异常,数据签名异常,请联系管理员");
            exceptionEntity.setSolution(ExceptionMessageHolder.DEFAULT_EXCEPTION_SOLUTION_INFO);
            exceptionEntity.setDebug("19800-T0103-数据异常,请联系管理员");
            exceptionEntity.setMessage("19800-T0103-数据异常,数据签名异常,请联系管理员");
            exceptionEntity.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            return exceptionResolve(e, exceptionEntity);
        }
        ExceptionEntity  exceptionEntity = exceptionMessageHolder.defaultException();
        exceptionEntity.setMessage(ExceptionUtils.getPublicMessage(e));
        return exceptionResolve(e, exceptionEntity);
    }

    private ResponseResult<Void> exceptionResolve(Throwable e, ExceptionEntity exceptionEntity) {
        if(StringUtils.isNotBlank(exceptionEntity.getSolution())) {
            log.error(exceptionEntity.getMessage() + "," + exceptionEntity.getSolution(), e);
        }else {
            log.error(exceptionEntity.getMessage(), e);
        }

        ResponseResult<Void> fail = ResponseResult.fail(exceptionEntity.getCode(), exceptionEntity.getMessage(), exceptionEntity.getSolution());
        if (exceptionEntity.getHttpStatus() != HttpStatus.OK) {
            HttpServletResponse response = ((ServletRequestAttributes)Objects.requireNonNull(RequestContextHolder
                .getRequestAttributes())).getResponse();
            assert response != null;
            response.setStatus(exceptionEntity.getHttpStatus().value());
        }
        return fail;
    }
}
