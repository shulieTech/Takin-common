package io.shulie.takin.sdk.kafka.impl;

import io.shulie.takin.sdk.kafka.MessageReceiveService;
import io.shulie.takin.sdk.kafka.MessageSendService;

public class KafkaSendServiceFactory {

    public MessageSendService getKafkaMessageInstance(){
        KafkaSendServiceImpl kafkaSendService = new KafkaSendServiceImpl();
        kafkaSendService.init();
        return kafkaSendService;
    }

    public MessageReceiveService getKafkaMessageReceiveInstance(){
        MessageReceiveServiceImpl messageReceiveService = new MessageReceiveServiceImpl();
        messageReceiveService.init();
        return messageReceiveService;
    }

    public MessageReceiveService getKafkaMessageReceiveInstance(String groupId){
        MessageReceiveServiceImpl messageReceiveService = new MessageReceiveServiceImpl(groupId);
        messageReceiveService.init();
        return messageReceiveService;
    }
}
