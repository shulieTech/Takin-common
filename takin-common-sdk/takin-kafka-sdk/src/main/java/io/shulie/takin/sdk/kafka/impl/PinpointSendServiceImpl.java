package io.shulie.takin.sdk.kafka.impl;

import cn.chinaunicom.client.UdpThriftSerializer;
import cn.chinaunicom.client.UdpTransport;
import cn.chinaunicom.pinpoint.thrift.dto.TStressTestAgentData;
import cn.hutool.core.collection.CollectionUtil;
import io.shulie.takin.sdk.kafka.MessageSendCallBack;
import io.shulie.takin.sdk.kafka.MessageSendService;
import org.apache.thrift.transport.TTransportException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.net.SocketException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * 发送消息到Pinpoint，再转到kafka
 * @author zhaoyong
 */
public class PinpointSendServiceImpl implements MessageSendService {
    private static final Logger LOGGER = LoggerFactory.getLogger(PinpointSendServiceImpl.class.getName());
    private UdpTransport udpTransport;
    private InetSocketAddress socketAddress;
    // 可复用的Thrift对象
    private TStressTestAgentData logData = new TStressTestAgentData();
    private UdpThriftSerializer serializer;
    private Map<String, String> urlTopicMap;

    @Override
    public void init(Properties props, String serverConfig, InetSocketAddress socketAddress) {
        this.socketAddress = socketAddress;
        try {
            serializer = new UdpThriftSerializer();
        } catch (TTransportException e) {
            LOGGER.error("初始化序列化工具失败", e);
        }
        this.createUdpTransport();
        this.initUrlTopicMap(null);
    }

    @Override
    public void stop() {
        udpTransport.close();
    }

    @Override
    public void send(String url, Map<String, String> headers, String body, String key, MessageSendCallBack messageSendCallBack) {
        String topic = urlTopicMap.get(url);
        if (topic == null) {
            messageSendCallBack.fail("没有通过url获取到对应的topic");
            return;
        }
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
        try {
            byte[] data = serializer.serialize(logData);
            udpTransport.send(data);
        } catch (Exception e) {
            messageSendCallBack.fail(e.getMessage());
            createUdpTransport();
        }

    }

    @Override
    public void send(byte dataType, int version, String content, String ip, MessageSendCallBack messageSendCallBack) {
        try {
            logData.setStringValue(content);
            logData.setDataType(dataType);
            logData.setHostIp(ip);
            logData.setVersion(version + "");
            byte[] data = serializer.serialize(logData);
            udpTransport.send(data);
        } catch (Exception e) {
            messageSendCallBack.fail(e.getMessage());
            createUdpTransport();
        }
    }

    private boolean createUdpTransport() {
        try {
            if (udpTransport == null || udpTransport.isClosed()) {
                udpTransport = new UdpTransport(socketAddress);
                return true;
            }
        } catch (SocketException e) {
            LOGGER.error("初始化udp链接异常", e);
        }
        return false;
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
    }
}
