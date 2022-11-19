package io.shulie.takin.sdk.pinpoint.impl;

import cn.chinaunicom.client.UdpThriftSerializer;
import cn.chinaunicom.client.UdpTransport;
import cn.chinaunicom.pinpoint.thrift.dto.TStressTestAgentData;
import cn.hutool.core.collection.CollectionUtil;
import com.pamirs.pradar.log.parser.DataType;
import io.shulie.takin.sdk.kafka.HttpSender;
import io.shulie.takin.sdk.kafka.MessageSendCallBack;
import io.shulie.takin.sdk.kafka.MessageSendService;
import io.shulie.takin.sdk.kafka.entity.MessageSerializer;
import io.shulie.takin.sdk.kafka.util.MessageSwitchUtil;
import io.shulie.takin.sdk.kafka.util.PropertiesReader;
import org.apache.thrift.transport.TTransportException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.net.SocketException;
import java.util.HashMap;
import java.util.Map;

/**
 * 发送消息到Pinpoint，再转到kafka
 *
 * @author zhaoyong
 */
public class PinpointSendServiceImpl implements MessageSendService {
    private static final Logger LOGGER = LoggerFactory.getLogger(PinpointSendServiceImpl.class.getName());
    private UdpTransport udpTransport;
    private InetSocketAddress socketAddress;
    private Map<String, Byte> urlDataTypeMap;
    private static MessageSerializer messageSerializer = new MessageSerializer();

    @Override
    public void init() {
        if (!MessageSwitchUtil.KAFKA_SDK_SWITCH) {
            LOGGER.warn("pinpoint推送开关已关闭，不进行初始化");
            return;
        }
        try {
           String property = PropertiesReader.getInstance().getProperty("pradar.data.pusher.pinpoint.collector.addres","");
            LOGGER.info("获取到推送地址为:{}", property);
            String[] node = property.split(":");
            socketAddress = new InetSocketAddress(node[0], Integer.parseInt(node[1]));
        } catch (Exception e) {
            LOGGER.error("解析推送地址失败", e);
        }

        if (socketAddress == null) {
            LOGGER.error("初始化KafkaSendServiceImpl socketAddress");
            return;
        }
        this.createUdpTransport();
        this.initUrlDataTypeMap(null);
    }

    @Override
    public void stop() {
        udpTransport.close();
    }

    @Override
    public void send(String url, Map<String, String> headers, String body, MessageSendCallBack messageSendCallBack, HttpSender httpSender) {
        if (socketAddress == null || udpTransport == null) {
            httpSender.sendMessage();
            return;
        }
        if (url == null) {
            messageSendCallBack.fail("url不能为空");
            return;
        }
        Byte dataType = urlDataTypeMap.get(url);
        if (dataType == null) {
            messageSendCallBack.fail("没有通过url获取到对应的dataType");
            return;
        }
        TStressTestAgentData logData = new TStressTestAgentData();
        logData.setStringValue(body);
        logData.setDataType(dataType);
        if (CollectionUtil.isNotEmpty(headers)) {
            if (headers.containsKey("userAppKey")) {
                logData.setUserAppKey(headers.get("userAppKey"));
            }
            if (headers.containsKey("tenantAppKey")) {
                logData.setTenantAppKey(headers.get("tenantAppKey"));
            }
            if (headers.containsKey("userId")) {
                logData.setUserId(headers.get("userId"));
            }
            if (headers.containsKey("envCode")) {
                logData.setEnvCode(headers.get("envCode"));
            }
            if (headers.containsKey("agentExpand")) {
                logData.setAgentExpand(headers.get("agentExpand"));
            }
        }
        try {
            byte[] data = messageSerializer.serialize(logData, true);
            udpTransport.send(data);
        } catch (Exception e) {
            messageSendCallBack.fail(e.getMessage());
            createUdpTransport();
        }

    }

    @Override
    public void send(byte dataType, int version, String content, String ip, MessageSendCallBack messageSendCallBack) {
        try {
            if (content == null) {
                messageSendCallBack.fail("发送内容不能为空");
            }
            TStressTestAgentData logData = new TStressTestAgentData();
            logData.setStringValue(content);
            logData.setDataType(dataType);
            logData.setHostIp(ip);
            logData.setVersion(version + "");
            byte[] data = messageSerializer.serialize(logData, true);
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
    public void initUrlDataTypeMap(Map<String, Byte> topicMap) {
        if (topicMap == null) {
            urlDataTypeMap = new HashMap<>();
        } else {
            urlDataTypeMap = topicMap;
        }
        //性能分析数据
        urlDataTypeMap.put("/api/agent/performance/basedata", DataType.AGENT_PERFORMANCE_BASEDATA);
        //应用上报
        urlDataTypeMap.put("/api/confcenter/interface/add/interfaceData", DataType.CENFCENTER_INTERFACE_ADD_INTEFACEDATA);
        //入口规则
        urlDataTypeMap.put("/api/agent/api/register", DataType.AGENT_API_REGISTER);
        //定时上报接入状态
        urlDataTypeMap.put("/api/application/agent/access/status", DataType.APPLICATION_AGENT_ACCESS_STATUS);
        //上报影子job信息
        urlDataTypeMap.put("/api/shadow/job/update", DataType.SHADOW_JOB_UPDATE);
        //配置信息上报
        urlDataTypeMap.put("/api/agent/push/application/config", DataType.AGENT_PUSH_APPLICATION_CONFIG);
        //中间件信息上报
        urlDataTypeMap.put("/agent/push/application/middleware", DataType.AGENT_PUSH_APPLICATION_MIDDLEWARE);
        //agent版本更新
        urlDataTypeMap.put("/api/confcenter/applicationmnt/update/applicationAgent", DataType.CONFCENTER_APPLICATIONMNT_UPDATE_APPLICATIONAGENT);
        //agent节点状态，zk路径
        urlDataTypeMap.put("/config/log/pradar/status", DataType.CONFIG_LOG_PARDAR_STATUS);
        urlDataTypeMap.put("/config/log/pradar/client", DataType.CONFIG_LOG_PARDAR_CLIENT);
        //新增应用
        urlDataTypeMap.put("/api/application/center/app/info", DataType.APPLICATION_CENTER_APP_INFO);
        //agent心跳
        urlDataTypeMap.put("api/agent/heartbeat", DataType.API_AGENT_HEARTBEAT);
    }
}
