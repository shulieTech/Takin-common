package io.shulie.takin.sdk.kafka.impl;

import cn.chinaunicom.client.UdpThriftSerializer;
import cn.chinaunicom.pinpoint.thrift.dto.TStressTestAgentData;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.crypto.digest.MD5;
import com.pamirs.pradar.log.parser.DataType;
import io.shulie.takin.sdk.kafka.MessageSendCallBack;
import io.shulie.takin.sdk.kafka.MessageSendService;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.thrift.transport.TTransportException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class KafkaSendServiceImpl implements MessageSendService {

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaSendServiceImpl.class.getName());
    private KafkaProducer<String, byte[]> producer;

    private Map<String, String> urlTopicMap;

    private Map<Byte, String> dataTypeTopicMap;

    private UdpThriftSerializer serializer;

    @Override
    public void init(Properties props, String serverConfig, InetSocketAddress socketAddress) {
        if (props == null){
            props = new Properties();
        }
        if (serverConfig == null){
            LOGGER.error("初始化KafkaSendServiceImpl serverConfig不能为空");
            return;
        }
        try {
            serializer = new UdpThriftSerializer();
        } catch (TTransportException e) {
            LOGGER.error("初始化序列化工具失败", e);
        }
        this.initUrlTopicMap(null);
        this.initDataTypeTopicMap(null);
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, serverConfig);
        producer = new KafkaProducer<>(props);
    }

    @Override
    public void stop() {
        producer.close();
    }

    @Override
    public void send(String url, Map<String, String> headers, String body, String key, MessageSendCallBack messageSendCallBack) {
        String topic = urlTopicMap.get(url);
        if (topic == null) {
            messageSendCallBack.fail("没有通过url获取到对应的topic");
            return;
        }
        if (key == null) {
            key = MD5.create().digestHex(body);
        }
        TStressTestAgentData logData = new TStressTestAgentData();
        logData.setStringValue(body);
        if (CollectionUtil.isNotEmpty(headers)){
            if (headers.containsKey("userAppKey")){
                logData.setUserAppKey(headers.get("userAppKey"));
            }
            if (headers.containsKey("tenantAppKey")){
                logData.setTenantAppKey(headers.get("tenantAppKey"));
            }
            if (headers.containsKey("userId")){
                logData.setUserId(headers.get("userId"));
            }
            if (headers.containsKey("envCode")){
                logData.setEnvCode(headers.get("envCode"));
            }
            if (headers.containsKey("agentExpand")){
                logData.setAgentExpand(headers.get("agentExpand"));
            }
        }
        this.sendMessage(messageSendCallBack, topic, key, logData);
    }

    @Override
    public void send(byte dataType, int version, String content, String ip, MessageSendCallBack messageSendCallBack) {
        String topic = dataTypeTopicMap.get(dataType);
        if (topic == null) {
            messageSendCallBack.fail("没有通过dataType获取到对应的topic");
            return;
        }
        String key = MD5.create().digestHex(ip + content);
        TStressTestAgentData logData = new TStressTestAgentData();
        logData.setStringValue(content);
        logData.setDataType(dataType);
        logData.setHostIp(ip);
        logData.setVersion(version + "");
        this.sendMessage(messageSendCallBack, topic, key, logData);
    }

    private void sendMessage(MessageSendCallBack messageSendCallBack, String topic, String key, TStressTestAgentData logData) {
        try {
            byte[] serialize = serializer.serialize(logData);
            ProducerRecord<String, byte[]> producerRecord = new ProducerRecord<String, byte[]>(topic, key, serialize);
            producer.send(producerRecord);
            messageSendCallBack.success();
        } catch (Exception e) {
            messageSendCallBack.fail(e.getMessage());
        }
    }

    /**
     * 初始化url和topic的关联关系
     *
     * @param topicMap
     */
    public void initUrlTopicMap(Map<String, String> topicMap) {
        if (topicMap == null) {
            urlTopicMap = new HashMap<>();
        } else {
            urlTopicMap = topicMap;
        }
        //性能分析数据
        urlTopicMap.put("/api/agent/performance/basedata", "stress-test-agent-performance-basedata");
        //应用上报
        urlTopicMap.put("/api/confcenter/interface/add/interfaceData", "stress-test-confcenter-interface-add-interfaceData");
        //入口规则
        urlTopicMap.put("/api/agent/api/register", "stress-test-agent-api-register");
        //定时上报接入状态
        urlTopicMap.put("/api/application/agent/access/status", "stress-test-application-agent-access-status");
        //上报影子job信息
        urlTopicMap.put("/api/shadow/job/update", "stress-test-shadow-job-update");
        //配置信息上报
        urlTopicMap.put("/api/agent/push/application/config", "stress-test-agent-push-application-config");
        //中间件信息上报
        urlTopicMap.put("/agent/push/application/middleware", "stress-test-agent-push-application-middleware");
        //引擎metrics数据上报
        urlTopicMap.put("/notify/job/pressure/metrics/upload", "stress-test-pressure-metrics-upload");
        //agent版本上报
        urlTopicMap.put("/api/confcenter/applicationmnt/update/applicationAgent", "stress-test-confcenter-applicationmnt-update-applicationagent");
    }

    /**
     * 初始化dataType和topic的关联关系
     *
     * @param topicMap
     */
    public void initDataTypeTopicMap(Map<Byte, String> topicMap) {
        if (topicMap == null) {
            dataTypeTopicMap = new HashMap<>();
        } else {
            dataTypeTopicMap = topicMap;
        }
        dataTypeTopicMap.put(DataType.TRACE_LOG, "stress-test-agent-trace");
        dataTypeTopicMap.put(DataType.MONITOR_LOG, "stress-test-agent-monitor");
        dataTypeTopicMap.put(DataType.AGENT_LOG, "stress-test-agent-log");
        dataTypeTopicMap.put(DataType.PRESSURE_ENGINE_TRACE_LOG, "stress-test-pressure-engine-trace-log");

        //性能分析数据
        dataTypeTopicMap.put(DataType.AGENT_PERFORMANCE_BASEDATA, "stress-test-agent-performance-basedata");
        //应用上报
        dataTypeTopicMap.put(DataType.CENFCENTER_INTERFACE_ADD_INTEFACEDATA, "stress-test-confcenter-interface-add-interfaceData");
        //入口规则
        dataTypeTopicMap.put(DataType.AGENT_API_REGISTER, "stress-test-agent-api-register");
        //定时上报接入状态
        dataTypeTopicMap.put(DataType.APPLICATION_AGENT_ACCESS_STATUS, "stress-test-application-agent-access-status");
        //上报影子job信息
        dataTypeTopicMap.put(DataType.SHADOW_JOB_UPDATE, "stress-test-shadow-job-update");
        //配置信息上报
        dataTypeTopicMap.put(DataType.AGENT_PUSH_APPLICATION_CONFIG, "stress-test-agent-push-application-config");
        //中间件信息上报
        dataTypeTopicMap.put(DataType.AGENT_PUSH_APPLICATION_MIDDLEWARE, "stress-test-agent-push-application-middleware");
        //agent版本更新
        dataTypeTopicMap.put(DataType.CONFCENTER_APPLICATIONMNT_UPDATE_APPLICATIONAGENT, "stress-test-confcenter-applicationmnt-update-applicationagent");
    }

}
