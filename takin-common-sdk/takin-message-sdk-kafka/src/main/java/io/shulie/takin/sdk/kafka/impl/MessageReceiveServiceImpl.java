package io.shulie.takin.sdk.kafka.impl;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.shulie.takin.sdk.kafka.MessageReceiveCallBack;
import io.shulie.takin.sdk.kafka.MessageReceiveService;
import io.shulie.takin.sdk.kafka.entity.*;
import io.shulie.takin.sdk.kafka.util.MessageSwitchUtil;
import io.shulie.takin.sdk.kafka.util.PropertiesReader;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class MessageReceiveServiceImpl implements MessageReceiveService {
    private static final Logger LOGGER = LoggerFactory.getLogger(MessageReceiveServiceImpl.class.getName());

    private KafkaConsumer<String, String> kafkaConsumer;
    private ObjectMapper objMap;
    private String groupId;
    private final List<String> stringValueTopic = new ArrayList<>();

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

        objMap = new ObjectMapper();
        stringValueTopic.add("stress-test-engine-pressure-data");
        stringValueTopic.add("stress-test-agent-monitor");
        stringValueTopic.add("stress-test-api-link-ds-config-check");
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
                ConsumerRecords<String, String> consumerRecords = kafkaConsumer.poll(300);
                consumerRecords.forEach(record -> {
                    try {
                        String value = record.value();
                        if (value == null || "".equals(value)) {
                            callBack.fail("接收到消息为空");
                            return;
                        }
                        //stress-test-api-agent-heartbeat
                        //stress-test-agent-trace
                        //stress-test-agent-trace-payload (附加信息)
                        switch (record.topic()) {
                            case "stress-test-api-agent-heartbeat":
                                TStressTestAgentHeartbeatDTO heartbeatDTO = objMap.readValue(value, TStressTestAgentHeartbeatDTO.class);
                                callBack.success(heartbeatDTO);
                                break;
                            case "stress-test-agent-trace":
                                TStressTestTraceDTO traceDTO = objMap.readValue(value, TStressTestTraceDTO.class);
                                callBack.success(traceDTO);
                                break;
                            case "stress-test-agent-trace-payload":
                                TStressTestTracePayloadDTO tracePayloadDTO = objMap.readValue(value, TStressTestTracePayloadDTO.class);
                                callBack.success(tracePayloadDTO);
                                break;
                            default:
                                TStressTestAgentDTO tStressTestAgentDTO = objMap.readValue(value, TStressTestAgentDTO.class);
                                callBack.success(deserializeJSON(tStressTestAgentDTO, stringValueTopic.contains(record.topic())));
                        }
                    } catch (Exception e) {
                        callBack.fail(e.getMessage());
                    }
                });
            } catch (Exception ex) {
                callBack.fail(ex.getMessage());
            }
        }
    }

    /**
     * 反序列化thrift对象,转为json
     *
     * @param tStressTestAgentDTO
     * @return
     */
    public MessageEntity deserializeJSON(TStressTestAgentDTO tStressTestAgentDTO, boolean isStringValue) {

        MessageEntity messageEntity = new MessageEntity();
        messageEntity.setHeaders(this.getHeaders(tStressTestAgentDTO));
        Map map;
        if (isStringValue) {
            map = new HashMap<>();
            map.put("content", tStressTestAgentDTO.getStringValue());
        } else {
            map = JSON.parseObject(tStressTestAgentDTO.getStringValue(), Map.class);
        }
        messageEntity.setBody(map);
        return messageEntity;
    }

    /**
     * 获取头信息
     *
     * @param agentDTO 通用对象
     * @return
     */
    private Map<String, Object> getHeaders(TStressTestAgentDTO agentDTO) {
        Map<String, Object> headers = new HashMap<>();
        if (agentDTO != null) {
            headers.put("userAppKey", agentDTO.getUserAppKey());
            headers.put("tenantAppKey", agentDTO.getTenantAppKey());
            headers.put("userId", agentDTO.getUserId());
            headers.put("envCode", agentDTO.getEnvCode());
            headers.put("agentExpand", agentDTO.getAgentExpand());
            headers.put("dataType", agentDTO.getDataType());
            headers.put("hostIp", agentDTO.getHostIp());
            headers.put("version", agentDTO.getVersion());
        }
        return headers;
    }
}
