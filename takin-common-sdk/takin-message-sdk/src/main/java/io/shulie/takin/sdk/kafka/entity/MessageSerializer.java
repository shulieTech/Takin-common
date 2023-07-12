package io.shulie.takin.sdk.kafka.entity;

import io.shulie.takin.client.UdpThriftSerializer;
import org.apache.thrift.TException;
import org.apache.thrift.transport.TTransportException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;

/**
 * kafka MessageDeserializer
 *
 * @author vincent
 * @date 2022/11/18 14:09
 **/
public class MessageSerializer implements Serializable {

    private static final Logger logger = LoggerFactory.getLogger(MessageSerializer.class);


    private static final ThreadLocal<UdpThriftSerializer> THREAD_LOCAL = new ThreadLocal<UdpThriftSerializer>() {

        @Override
        protected UdpThriftSerializer initialValue() {
            UdpThriftSerializer udpThriftSerializer = null;
            try {
                udpThriftSerializer = new UdpThriftSerializer();
            } catch (TTransportException e) {
                logger.error("Init serializer failed", e);
            }
            return udpThriftSerializer;
        }
    };
    private static final long serialVersionUID = -8568857619974944085L;

    /**
     * 序列化thrift对象
     *
     * @return
     */
    public byte[] serialize(Object o, boolean addHeader) {

        try {
            UdpThriftSerializer udpThriftSerializer = THREAD_LOCAL.get();
            if (udpThriftSerializer == null) {
                return null;
            }
            return udpThriftSerializer.serialize(o, addHeader);
        } catch (TException e) {
            logger.error("Serialize thrift error. {}", e.toString());
        }
        return null;
    }

}
