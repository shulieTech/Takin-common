package io.shulie.takin.sdk.kafka.util;

public class MessageSwitchUtil {

    public static boolean userKafkaSwitch(){
        String sdkSwitchEnv = System.getProperty("kafka.sdk.switch", "false");
        if ("true".equals(sdkSwitchEnv)){
            return true;
        }
        PropertiesReader reader = new PropertiesReader("kafka-sdk.properties");
        String sdkSwitch = reader.getProperty("kafka.sdk.switch", "false");
        return "true".equals(sdkSwitch);
    }

}
