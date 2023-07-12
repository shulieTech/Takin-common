package io.shulie.takin.client;

import io.shulie.takin.pinpoint.thrift.dto.TStressTestAgentData;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.transport.TMemoryInputTransport;
import org.apache.thrift.transport.TTransportException;

/**
 * Thrift反序列化器实现, 非线程安全
 *
 * @author lixs151
 */
public class ThriftDeserializer {
    private TMemoryInputTransport thriftTransport;
    private TCompactProtocol thriftProtocol;

    public ThriftDeserializer() throws TTransportException {
        thriftTransport = new TMemoryInputTransport();
        thriftProtocol = new TCompactProtocol(thriftTransport);
    }

    public TStressTestAgentData deserialize(byte[] bytes) throws TException {
        TStressTestAgentData logData = new TStressTestAgentData();

        try {
            thriftTransport.reset(bytes);
            logData.read(thriftProtocol);
            return logData;

        } finally {
            thriftTransport.clear();
            thriftProtocol.reset();
        }
    }
}
