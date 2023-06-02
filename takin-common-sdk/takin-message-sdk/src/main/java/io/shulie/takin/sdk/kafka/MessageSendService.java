package io.shulie.takin.sdk.kafka;

import java.util.Map;

public interface MessageSendService {

    void init();

    void stop();

    void send(String url, Map<String, String> headers, String body, MessageSendCallBack messageSendCallBack, HttpSender httpSender);

    void send(byte dataType, int version, String content, String ip, MessageSendCallBack messageSendCallBack);

    void send(Object o,MessageSendCallBack messageSendCallBack, HttpSender httpSender);
}
