package io.shulie.takin.sdk.kafka.impl;

import cn.chinaunicom.client.ThriftDeserializer;
import cn.chinaunicom.pinpoint.thrift.dto.TStressTestAgentData;
import com.alibaba.fastjson.JSON;

import io.shulie.takin.sdk.kafka.entity.MessageEntity;
import org.apache.thrift.TException;
import org.apache.thrift.transport.TTransportException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * kafka MessageDeserializer
 *
 * @author vincent
 * @date 2022/11/18 14:09
 **/
public class MessageDeserializer implements Serializable {

    private static final long serialVersionUID = -2354963036798541991L;
    private static final Logger logger = LoggerFactory.getLogger(MessageDeserializer.class);

    private static final ThreadLocal<ThriftDeserializer> THREAD_LOCAL = new ThreadLocal<ThriftDeserializer>() {

        @Override
        protected ThriftDeserializer initialValue() {
            ThriftDeserializer thriftDeserializer = null;
            try {
                thriftDeserializer = new ThriftDeserializer();
            } catch (TTransportException e) {
                logger.error("Init serializer failed", e);
            }
            return thriftDeserializer;
        }
    };

    /**
     * 反序列化thrift对象
     * @param bytes
     * @return
     */
    public MessageEntity deserialize(byte[] bytes) {

        try {
            ThriftDeserializer thriftDeserializer = THREAD_LOCAL.get();
            if (thriftDeserializer == null)
            {
                return null;
            }
            TStressTestAgentData tStressTestAgentData = thriftDeserializer.deserialize(bytes);
            MessageEntity messageEntity = new MessageEntity();
            messageEntity.setHeaders(this.getHeaders(tStressTestAgentData));
            Map<String, String> map = new HashMap<>();
            map.put("content", tStressTestAgentData.getStringValue());
            messageEntity.setBody(map);
            return messageEntity;
        } catch (TException e) {
            logger.error("Serialize TStressTestAgentData errror.");
        }
        return null;
    }

    /**
     * 反序列化thrift对象,转为json
     * @param bytes
     * @return
     */
    public MessageEntity deserializeJSON(byte[] bytes) {

        try {
            ThriftDeserializer thriftDeserializer = THREAD_LOCAL.get();
            if (thriftDeserializer == null)
            {
                return null;
            }
            TStressTestAgentData tStressTestAgentData = thriftDeserializer.deserialize(bytes);
            MessageEntity messageEntity = new MessageEntity();
            messageEntity.setHeaders(this.getHeaders(tStressTestAgentData));
            Map map = JSON.parseObject(tStressTestAgentData.getStringValue(), Map.class);
            messageEntity.setBody(map);
            return messageEntity;
        } catch (TException e) {
            logger.error("Serialize TStressTestAgentData errror.");
        }
        return null;
    }

    /**
     * 获取头信息
     * @param tStressTestAgentData
     * @return
     */
    private Map<String, Object> getHeaders(TStressTestAgentData tStressTestAgentData) {
        Map<String, Object> headers = new HashMap<>();
        if (tStressTestAgentData != null) {
            headers.put("userAppKey", tStressTestAgentData.getUserAppKey());
            headers.put("tenantAppKey", tStressTestAgentData.getTenantAppKey());
            headers.put("userId", tStressTestAgentData.getUserId());
            headers.put("envCode", tStressTestAgentData.getEnvCode());
            headers.put("agentExpand", tStressTestAgentData.getAgentExpand());
            headers.put("dataType", tStressTestAgentData.getDataType());
            headers.put("hostIp", tStressTestAgentData.getHostIp());
            headers.put("version", tStressTestAgentData.getVersion());
        }
        return headers;
    }
}
