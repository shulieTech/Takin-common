package io.shulie.takin.sdk.kafka;

import cn.chinaunicom.pinpoint.thrift.dto.TStressTestAgentData;

import java.util.Map;

public interface MessageReceiveCallBack {

    void success(TStressTestAgentData tStressTestAgentData);

    void fail(String errorMessage);
}
