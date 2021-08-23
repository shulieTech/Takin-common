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
