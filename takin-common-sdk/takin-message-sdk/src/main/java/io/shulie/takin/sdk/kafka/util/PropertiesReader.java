package io.shulie.takin.sdk.kafka.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author xiaobin.zfb|xiaobin@shulie.io
 * @since 2021/3/2 2:50 下午
 */
public class PropertiesReader {
    private final static PropertiesReader INSTANCE = new PropertiesReader();
    private static final String DEFAULT_RESOURCE_NAME = "kafka-sdk.properties";
    private String resourceName = DEFAULT_RESOURCE_NAME;
    private Properties props;

    public PropertiesReader() {
        if (resourceName == null) {
            throw new IllegalArgumentException("resourceName can't be null!");
        }
        if (!this.resourceName.startsWith("/")) {
            this.resourceName = "/" + this.resourceName;
        }
        init();
    }

    private void init() {
        InputStream in = PropertiesReader.class.getResourceAsStream(resourceName);
        this.props = new Properties();
        try {
            props.load(in);
        } catch (IOException e) {
            throw new RuntimeException("read resource " + resourceName + " error!", e);
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 根据 key 获取配置
     *
     * @param key
     * @return
     */
    private String getProperty(String key) {
        return this.props.getProperty(key);
    }

    /**
     * 根据 key 获取配置
     *
     * @param key
     * @param defaultValue
     * @return
     */
    public String getProperty(String key, String defaultValue) {
        String value = System.getProperty(key);
        if (StringUtils.isBlank(value)) {
            value = getProperty(key);
        }
        if (StringUtils.isBlank(value)) {
            return defaultValue;
        }
        return value;
    }

    public static PropertiesReader getInstance()
    {
        return INSTANCE;
    }
}
