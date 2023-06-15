package io.shulie.takin.sdk.kafka;

public interface MessageReceiveCallBack {

    void success(Object o);

    void fail(String errorMessage);
}
