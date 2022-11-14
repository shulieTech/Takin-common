package io.shulie.takin.sdk.kafka;

public interface MessageSendCallBack {

    void success();

    void fail(String errorMessage);
}
