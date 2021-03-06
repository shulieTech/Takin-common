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

package org.springframework.boot.autoconfigure.takin.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author shiyajian
 * create: 2020-09-25
 */
@ConfigurationProperties(prefix = "takin.exception")
@Component
@Data
public class BaseExceptionProperties {

    private String messageFilesPath = "exception";

    private String httpStatusFileName = "http_status.properties";

    private String debugFileName = "debug.properties";

    private String messageFileName = "message.properties";

    private String solutionFileName = "solution.properties";
}
