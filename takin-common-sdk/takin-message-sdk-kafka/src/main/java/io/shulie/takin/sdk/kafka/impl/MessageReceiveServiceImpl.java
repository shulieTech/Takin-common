package io.shulie.takin.sdk.kafka.impl;

import cn.chinaunicom.client.ThriftDeserializer;
import cn.chinaunicom.pinpoint.thrift.dto.TStressTestAgentData;
import io.shulie.takin.sdk.kafka.MessageReceiveCallBack;
import io.shulie.takin.sdk.kafka.MessageReceiveService;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.thrift.transport.TTransportException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutorService;

public class MessageReceiveServiceImpl implements MessageReceiveService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MessageReceiveServiceImpl.class.getName());

    private KafkaConsumer<String, byte[]> kafkaConsumer;
    private ThriftDeserializer deserializer;

    @Override
    public void init(Properties props, String serverConfig, String groupId) {
        if (props == null) {
            props = new Properties();
        }
        if (!props.containsKey(ConsumerConfig.MAX_POLL_RECORDS_CONFIG)) {
            props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, 100);
        }
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, serverConfig);
        if (groupId != null) {
            props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        }

        kafkaConsumer = new KafkaConsumer<>(props);
        try {
            deserializer = new ThriftDeserializer();
        } catch (TTransportException e) {
            LOGGER.error("初始化反序列化工具失败", e);
        }
    }

    @Override
    public void stop() {
        kafkaConsumer.close();
    }

    @Override
    public void receive(List<String> topics, MessageReceiveCallBack callBack, ExecutorService pool, Long sleepMillis) {
        kafkaConsumer.subscribe(topics);
        while (true) {
            pool.execute(() -> {
                ConsumerRecords<String, byte[]> consumerRecords = kafkaConsumer.poll(Duration.ofMillis(300));
                consumerRecords.forEach(record -> {
                    try {
                        byte[] bytes = record.value();
                        if (bytes == null) {
                            callBack.fail("接收到消息为空");
                            return;
                        }
                        TStressTestAgentData tStressTestAgentData = deserializer.deserialize(bytes);
                        callBack.success(tStressTestAgentData);
                    } catch (Exception e) {
                        callBack.fail(e.getMessage());
                    }
                });
            });

            try {
                Thread.sleep(sleepMillis);
            } catch (InterruptedException e) {
                LOGGER.error("休眠出现异常", e);
            }
        }

    }
}
