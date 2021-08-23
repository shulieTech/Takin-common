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
