package io.shulie.takin.sdk.kafka.util;

public class MessageSwitchUtil {

   public static boolean isKafkaSdkSwitch (){
      return !"false".equals(PropertiesReader.getInstance().getProperty("kafka.sdk.switch","false"));
   }
}
