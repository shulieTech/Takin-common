package io.shulie.takin.sdk.kafka.util;

import org.apache.commons.lang3.StringUtils;

public class MessageSwitchUtil {

   public static boolean KAFKA_SDK_SWITCH = !StringUtils.equals(PropertiesReader.getInstance().getProperty("kafka.sdk.switch","false"),"false");

}
