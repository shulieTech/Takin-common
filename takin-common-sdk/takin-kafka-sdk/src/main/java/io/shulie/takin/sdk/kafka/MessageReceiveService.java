package io.shulie.takin.sdk.kafka;

import sun.nio.ch.ThreadPool;

import java.net.InetSocketAddress;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutorService;

public interface MessageReceiveService {

    void init(Properties props, String serverConfig, String groupId);

    void stop();

    void receive(List<String> topics, MessageReceiveCallBack messageReceiveCallBack, ExecutorService pool, Long sleepSeconds);
}
