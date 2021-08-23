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

package org.springframework.boot.autoconfigure.takin;

import io.shulie.takin.parent.exception.holder.ExceptionMessageHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.takin.properties.BaseExceptionProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author shiyajian
 * create: 2020-09-25
 */
@Configuration
@EnableConfigurationProperties(BaseExceptionProperties.class)
public class BaseExceptionAutoConfiguration {

    @Autowired
    private BaseExceptionProperties baseExceptionProperties;

    @Bean
    public ExceptionMessageHolder exceptionMessageHolder() {
        return new ExceptionMessageHolder(baseExceptionProperties);
    }

}
