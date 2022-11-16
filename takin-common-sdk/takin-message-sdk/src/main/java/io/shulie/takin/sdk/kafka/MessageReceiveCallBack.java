package io.shulie.takin.sdk.kafka;

import io.shulie.takin.sdk.kafka.entity.MessageEntity;

public interface MessageReceiveCallBack {

    void success(MessageEntity messageEntity);

    void fail(String errorMessage);
}
