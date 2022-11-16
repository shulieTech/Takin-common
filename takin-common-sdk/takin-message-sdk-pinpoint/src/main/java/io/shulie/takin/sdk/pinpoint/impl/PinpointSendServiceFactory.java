package io.shulie.takin.sdk.pinpoint.impl;

import io.shulie.takin.sdk.kafka.MessageSendService;

public class PinpointSendServiceFactory {

    public MessageSendService getKafkaMessageInstance(){
        PinpointSendServiceImpl pinpointSendService = new PinpointSendServiceImpl();
        pinpointSendService.init();
        return pinpointSendService;
    }
}
