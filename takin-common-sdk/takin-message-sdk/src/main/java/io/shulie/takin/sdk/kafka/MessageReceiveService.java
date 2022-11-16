package io.shulie.takin.sdk.kafka;

import java.util.List;

public interface MessageReceiveService {

    void init();

    void stop();

    void receive(List<String> topics, MessageReceiveCallBack messageReceiveCallBack);
}
