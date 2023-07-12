package io.shulie.takin.client;

import io.shulie.takin.pinpoint.thrift.dto.TStressTestAgentData;
import io.shulie.takin.pinpoint.thrift.dto.TStressTestAgentHeartbeatData;
import io.shulie.takin.pinpoint.thrift.dto.TStressTestTraceData;
import io.shulie.takin.pinpoint.thrift.dto.TStressTestTracePayloadData;
import org.apache.thrift.TBase;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.transport.TByteBuffer;
import org.apache.thrift.transport.TTransportException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.ByteBuffer;

/**
 * 适配UDP的Thrift序列化器实现, 非线程安全
 *
 * @author lixs151
 */
public class UdpThriftSerializer {
    private static final Logger LOGGER = LoggerFactory.getLogger(UdpThriftSerializer.class.getName());
    // 最大UDP包大小：65535 - TCP header length(20) - UDP header length(8)
    public static final int UDP_MAX_PACKET_LENGTH = 65507;
    private TByteBuffer thriftTransport;
    private TCompactProtocol thriftProtocol;

    // Pinpoint Collector数据包头
    private final byte[] PINPOINT_COLLECTOR_AGENT_HEADER = {
            (byte) 0xef, // pinpoint collector header signature
            (byte) 0x10, // pinpoint collector header v1
            (byte) 0x02, // the high-order 8 bits of data locator type of pinpoint collector
            (byte) 0x00, // the low-order 8 bits of data locator type of pinpoint collector
    };

    private final byte[] PINPOINT_COLLECTOR_AGENT_TRACE_HEADER = {
            (byte) 0xef, // pinpoint collector header signature
            (byte) 0x10, // pinpoint collector header v1
            (byte) 0x02, // the high-order 8 bits of data locator type of pinpoint collector
            (byte) 0x01, // the low-order 8 bits of data locator type of pinpoint collector
    };

    private final byte[] PINPOINT_COLLECTOR_AGENT_TRACE_PAYLOAD_HEADER = {
            (byte) 0xef, // pinpoint collector header signature
            (byte) 0x10, // pinpoint collector header v1
            (byte) 0x02, // the high-order 8 bits of data locator type of pinpoint collector
            (byte) 0x02, // the low-order 8 bits of data locator type of pinpoint collector
    };

    private final byte[] PINPOINT_COLLECTOR_AGENT_HEARTBEAT_HEADER = {
            (byte) 0xef, // pinpoint collector header signature
            (byte) 0x10, // pinpoint collector header v1
            (byte) 0x02, // the high-order 8 bits of data locator type of pinpoint collector
            (byte) 0x03, // the low-order 8 bits of data locator type of pinpoint collector
    };

    public UdpThriftSerializer() throws TTransportException {
        ByteBuffer byteBuffer = ByteBuffer.allocate(UDP_MAX_PACKET_LENGTH);
        thriftTransport = new TByteBuffer(byteBuffer);
        thriftProtocol = new TCompactProtocol(thriftTransport);
    }

    public byte[] serialize(Object object, boolean addPinpointCollectorHeader) throws TException {
        // 只序列化Thrift类型的对象
        if (object instanceof TBase) {
            TBase tBaseData = (TBase) object;

            try {
                if (addPinpointCollectorHeader) {
                    if (object instanceof TStressTestAgentData) {
                        thriftTransport.write(PINPOINT_COLLECTOR_AGENT_HEADER);
                    }
                    if (object instanceof TStressTestTraceData) {
                        thriftTransport.write(PINPOINT_COLLECTOR_AGENT_TRACE_HEADER);
                    }
                    if (object instanceof TStressTestTracePayloadData) {
                        thriftTransport.write(PINPOINT_COLLECTOR_AGENT_TRACE_PAYLOAD_HEADER);
                    }
                    if (object instanceof TStressTestAgentHeartbeatData) {
                        thriftTransport.write(PINPOINT_COLLECTOR_AGENT_HEARTBEAT_HEADER);
                    }
                }
                tBaseData.write(thriftProtocol);

                thriftTransport.flip();
                return thriftTransport.toByteArray();

            } finally {
                thriftTransport.clear();
                thriftProtocol.reset();
            }

        } else {
            LOGGER.warn("The object about to be serialized is not a compatible type of Thrift: {}, ignored", object.getClass());
            return new byte[0];
        }
    }
}
