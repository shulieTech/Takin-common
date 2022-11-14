package io.shulie.takin.sdk.kafka;

import java.net.InetSocketAddress;
import java.util.Map;
import java.util.Properties;

public interface MessageSendService {

    void init(Properties props, String serverConfig, InetSocketAddress socketAddress);

    void stop();

    void send(String url, Map<String, String> headers, String body, String key, MessageSendCallBack messageSendCallBack);

    void send(byte dataType, int version, String content, String ip, MessageSendCallBack messageSendCallBack);
}
