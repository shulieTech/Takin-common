package io.shulie.takin.sdk.kafka.impl;

import io.shulie.takin.sdk.kafka.MessageReceiveCallBack;
import io.shulie.takin.sdk.kafka.MessageReceiveService;
import io.shulie.takin.sdk.kafka.entity.MessageEntity;
import io.shulie.takin.sdk.kafka.util.MessageSwitchUtil;
import io.shulie.takin.sdk.kafka.util.PropertiesReader;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class MessageReceiveServiceImpl implements MessageReceiveService {
    private static final Logger LOGGER = LoggerFactory.getLogger(MessageReceiveServiceImpl.class.getName());

    private KafkaConsumer<String, byte[]> kafkaConsumer;
    private MessageDeserializer deserializer;
    private String groupId;
    private List<String> stringValueTopic = new ArrayList<>();

    public MessageReceiveServiceImpl() {
    }

    public MessageReceiveServiceImpl(String groupId) {
        this.groupId = groupId;
    }

    @Override
    public void init() {
        String serverConfig = null;
        String sleepMillStr = null;
        if (!MessageSwitchUtil.isKafkaSdkSwitch()) {
            LOGGER.warn("kafka开关处理关闭状态，不进行发送初始化");
            return;
        }
        try {
            serverConfig = PropertiesReader.getInstance().getProperty("kafka.sdk.bootstrap", "");
            //环境变量优先级高于配置文件
            sleepMillStr = PropertiesReader.getInstance().getProperty("kafka.poll.timeout", "2000");
        } catch (Exception e) {
            LOGGER.error("读取配置文件失败", e);
        }

        if (serverConfig == null || "".equals(serverConfig)) {
            LOGGER.info("kafka配置serverConfig未找到，不进行kafka发送初始化");
            return;
        }
        LOGGER.info("消息接收获取到地址为:{},超时时间为:{}", serverConfig, sleepMillStr);

        String authFlag = PropertiesReader.getInstance().getProperty("kafka.auth.flag", "false");
        LOGGER.info("是否使用权限认证:{}", authFlag);

        Properties props = new Properties();
        props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, 100);
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, serverConfig);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "kafka-sdk-consumer");
        if (groupId != null && !"".equals(groupId)) {
            props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        }
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, org.apache.kafka.common.serialization.StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, org.apache.kafka.common.serialization.ByteArrayDeserializer.class);

        if ("true".equals(authFlag)) {
            String securityProtocol = PropertiesReader.getInstance().getProperty("security.protocol", "");
            String saslMechanism = PropertiesReader.getInstance().getProperty("sasl.mechanism", "");
            String saslJaasConfig = PropertiesReader.getInstance().getProperty("sasl.jaas.config", "");

            props.put("security.protocol", securityProtocol);
            props.put("sasl.mechanism", saslMechanism);
            props.put("sasl.jaas.config", saslJaasConfig);
        }
        kafkaConsumer = new KafkaConsumer<>(props);
        try {
            deserializer = new MessageDeserializer();
        } catch (Exception e) {
            LOGGER.error("初始化反序列化工具失败", e);
        }
        stringValueTopic.add("stress-test-engine-pressure-data");
        stringValueTopic.add("stress-test-agent-monitor");
        stringValueTopic.add("stress-test-api-link-ds-config-check");
        stringValueTopic.add("stress-test-agent-trace");
    }

    @Override
    public void stop() {
        kafkaConsumer.close();
    }

    @Override
    public void receive(List<String> topics, MessageReceiveCallBack callBack) {
        if (kafkaConsumer == null) {
            return;
        }
        kafkaConsumer.subscribe(topics);
        while (true) {
            try {
                ConsumerRecords<String, byte[]> consumerRecords = kafkaConsumer.poll(300);
                consumerRecords.forEach(record -> {
                    try {
                        byte[] bytes = record.value();
                        if (bytes == null) {
                            callBack.fail("接收到消息为空");
                            return;
                        }
                        MessageEntity messageEntity = deserializer.deserializeJSON(bytes, stringValueTopic.contains(record.topic()));
                        callBack.success(messageEntity);
                    } catch (Exception e) {
                        callBack.fail(e.getMessage());
                    }
                });
            } catch (Exception ex) {
                callBack.fail(ex.getMessage());
            }
        }
    }


}
