package io.shulie.takin.sdk.kafka.impl;

import io.shulie.takin.sdk.kafka.MessageSendService;

public class KafkaSendServiceFactory {

    public MessageSendService getKafkaMessageInstance(){
        KafkaSendServiceImpl kafkaSendService = new KafkaSendServiceImpl();
        kafkaSendService.init();
        return kafkaSendService;
    }

}
