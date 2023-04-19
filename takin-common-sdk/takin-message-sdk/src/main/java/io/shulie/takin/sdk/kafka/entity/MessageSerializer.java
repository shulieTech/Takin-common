package io.shulie.takin.sdk.kafka.entity;

import cn.chinaunicom.client.UdpThriftSerializer;
import cn.chinaunicom.pinpoint.thrift.dto.TStressTestAgentData;
import org.apache.thrift.TBase;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TByteBuffer;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.nio.ByteBuffer;

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
     * 反序列化thrift对象
     *
     * @return
     */
    public byte[] serialize(TStressTestAgentData tStressTestAgentData,boolean addHeader) {

        try {
            UdpThriftSerializer udpThriftSerializer = THREAD_LOCAL.get();
            if (udpThriftSerializer == null) {
                return null;
            }
            return udpThriftSerializer.serialize(tStressTestAgentData,addHeader);
        } catch (TException e) {
            logger.error("Serialize TStressTestAgentData error. {}", e.toString());
        }
        return null;
    }

    public static void main(String[] args) throws Exception {
        byte[] PINPOINT_COLLECTOR_HEADER = new byte[] { -17, 16, 2, 0 };

        TStressTestAgentData tBaseData = new TStressTestAgentData();
        tBaseData.setDataType((byte) 15);
        tBaseData.setStringValue("{\"agentId\":\"172.18.182.140-7276\",\"simulatorVersion\":\"5.6.0.0\",\"address\":\"172.18.182.140\",\"simulatorFileConfigs\":\"{\\\"nacos.serverAddr\\\":\\\"10.125.131.12:18848\\\",\\\"pradar.data.pusher.timeout\\\":\\\"60000\\\",\\\"pradar.metrics.log.version\\\":\\\"1.1\\\",\\\"server.charset\\\":\\\"UTF-8\\\",\\\"pradar.charset\\\":\\\"UTF-8\\\",\\\"pradar.monitor.log.version\\\":\\\"13\\\",\\\"pradar.switch.save.business.trace\\\":\\\"true\\\",\\\"pradar.max.httpPool.size\\\":\\\"30\\\",\\\"shulie.agent.expand\\\":\\\"{\\\\\\\"deptLowest\\\\\\\":\\\\\\\"TEST\\\\\\\"}\\\",\\\"unsafe.enable\\\":\\\"true\\\",\\\"pradar.data.pusher\\\":\\\"udp\\\",\\\"rabbitmq.admin.api.zk.control\\\":\\\"true\\\",\\\"pradar.trace.log.version\\\":\\\"17\\\",\\\"pradar.data.pusher.pinpoint.collector.address\\\":\\\"10.125.131.12:9996\\\",\\\"trace.samplingInterval\\\":\\\"1\\\",\\\"auto.create.queue.rabbitmq\\\":\\\"false\\\",\\\"pradar.config.fetch.type\\\":\\\"http\\\",\\\"pradar.config.fetch.interval\\\":\\\"60\\\",\\\"pradar.server.zk.path\\\":\\\"/config/log/pradar/server\\\",\\\"kafka.sdk.switch\\\":\\\"true\\\",\\\"pradar.push.server.http.path\\\":\\\"\\\",\\\"is.kafka.message.headers\\\":\\\"true\\\",\\\"pradar.config.fetch.unit\\\":\\\"SECONDS\\\",\\\"user_module\\\":\\\"~/.modules;\\\",\\\"rabbitmq.admin.api.zk.control.path\\\":\\\"/config/log/pradar/plugin/rabbitmq\\\",\\\"pradar.push.server.http.enable.gzip\\\":\\\"true\\\",\\\"register.name\\\":\\\"kafka\\\"}\",\"appName\":\"tank-gateway-demo-local-test-5\",\"errorCode\":\"\",\"pid\":\"7276\",\"agentLanguage\":\"JAVA\",\"userId\":\"1\",\"agentStatus\":\"INSTALLED\",\"errorMsg\":\"Pradar Cluster Tester is not ready now:tank-gateway-demo-local-test-5\",\"agentFileConfigs\":\"{\\\"simulator.zk.servers\\\":\\\"10.125.131.12:3181\\\",\\\"user.app.key\\\":\\\"ed45ef6b-bf94-48fa-b0c0-15e0285365d2\\\",\\\"nacos.serverAddr\\\":\\\"10.125.131.12:18848\\\",\\\"pradar.user.id\\\":\\\"1\\\",\\\"tro.web.url\\\":\\\"http://127.0.0.1:80/web\\\",\\\"cluster.name\\\":\\\"xixian\\\",\\\"pradar.data.pusher.pinpoint.collector.address\\\":\\\"10.125.131.12:9996\\\",\\\"simulator.zk.connection.timeout.ms\\\":\\\"30000\\\",\\\"pradar.biz.log.divider\\\":\\\"false\\\",\\\"simulator.zk.session.timeout.ms\\\":\\\"60000\\\",\\\"tenant.app.key\\\":\\\"ed45ef6b-bf94-48fa-b0c0-15e0285365d2\\\",\\\"pradar.logback.isprintTrace\\\":\\\"true\\\",\\\"simulator.log.path\\\":\\\"agentlogs\\\",\\\"kafka.sdk.switch\\\":\\\"true\\\",\\\"register.name\\\":\\\"kafka\\\",\\\"pradar.env.code\\\":\\\"test\\\"}\",\"moduleLoadResult\":\"true\",\"envCode\":\"test\",\"port\":\"33847\",\"service\":\"http://127.0.0.1:33847/simulator\",\"host\":\"172.18.182.140\",\"name\":\"7276@UNKNOWN\",\"agentVersion\":\"5.3.4.1\",\"tenantAppKey\":\"ed45ef6b-bf94-48fa-b0c0-15e0285365d2\",\"md5\":\"\",\"status\":\"false\"}");

//        ByteBuffer byteBuffer = ByteBuffer.allocate(65507);
//        TByteBuffer thriftTransport = new TByteBuffer(byteBuffer);
//        TCompactProtocol thriftProtocol = new TCompactProtocol((TTransport)thriftTransport);
//        thriftTransport.write(PINPOINT_COLLECTOR_HEADER);
//        tBaseData.write((TProtocol)thriftProtocol);
//        thriftTransport.flip();
//        byte[] bytes = thriftTransport.toByteArray();
//        System.out.println(bytes.length);

        UdpThriftSerializer udpThriftSerializer = new UdpThriftSerializer();
        byte[] serialize = udpThriftSerializer.serialize(tBaseData, true);
        System.out.println(serialize.length);
    }
}
