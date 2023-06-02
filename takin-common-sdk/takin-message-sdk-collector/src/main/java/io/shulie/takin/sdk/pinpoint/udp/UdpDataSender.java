package io.shulie.takin.sdk.pinpoint.udp;

import cn.chinaunicom.pinpoint.thrift.dto.TStressTestAgentData;
import io.shulie.takin.sdk.kafka.entity.MessageSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class UdpDataSender {
    private final Logger logger = LoggerFactory.getLogger(UdpDataSender.class);

    // Caution. not thread safe
    private final DatagramPacket reusePacket = new DatagramPacket(new byte[1], 1);

    private DatagramSocket udpSocket;

    private final UdpSocketAddressProvider socketAddressProvider;

    private final MessageSerializer messageSerializer;
    private static ScheduledExecutorService service = Executors.newScheduledThreadPool(1);

    public UdpDataSender(String host, int port, int timeout, int sendBufferSize,
                         MessageSerializer messageSerializer) {

        if (!HostAndPort.isValidPort(port)) {
            throw new IllegalArgumentException("port out of range:" + port);
        }
        this.messageSerializer = messageSerializer;

        final SocketAddressProvider socketAddressProvider = new DnsSocketAddressProvider(host, port);
        this.socketAddressProvider = new RefreshStrategy(socketAddressProvider);
        final InetSocketAddress currentAddress = this.socketAddressProvider.resolve();
        logger.info("UdpDataSender initialized. host={}", currentAddress);
        // TODO If fail to create socket, stop agent start
        this.udpSocket = createSocket(timeout, sendBufferSize);

        service.scheduleAtFixedRate(() -> {
            DatagramSocket old =  udpSocket;
            udpSocket = createSocket(timeout, sendBufferSize);
            old.close();
        }, 0, 1, TimeUnit.MINUTES);

    }

    public void send(Object message) {

        final InetSocketAddress inetSocketAddress = socketAddressProvider.resolve();
        if (inetSocketAddress.getAddress() == null) {
            logger.error("dns lookup fail host:" + inetSocketAddress);
            return;
        }

        final byte[] byteMessage = messageSerializer.serialize(message, true);
        if (byteMessage == null) {
            logger.error("sendPacket fail. message:{}", (message != null ? message.getClass() : null));
            return;
        }
        final DatagramPacket packet = preparePacket(inetSocketAddress, byteMessage);

        try {
            udpSocket.send(packet);
        } catch (PortUnreachableException pe) {
            this.socketAddressProvider.handlePortUnreachable();
            logger.error("packet send error. size:{}, {}", byteMessage.length, message, pe);
        } catch (IOException e) {
            logger.error("packet send error. size:{}, {}", byteMessage.length, message, e);
        }

    }

    public void stop() {
        udpSocket.close();
    }

    private DatagramSocket createSocket(int timeout, int sendBufferSize) {
        try {
            final DatagramSocket datagramSocket = new DatagramSocket();

            datagramSocket.setSoTimeout(timeout);
            datagramSocket.setSendBufferSize(sendBufferSize);

            return datagramSocket;
        } catch (SocketException e) {
            logger.error("DatagramSocket create fail. Caused:{}", e.getMessage(), e);
            throw new IllegalStateException("DatagramSocket create fail. Cause" + e.getMessage(), e);
        }
    }

    private DatagramPacket preparePacket(InetSocketAddress targetAddress, byte[] byteMessage) {
        // it's safe to reuse because it's single threaded
        reusePacket.setAddress(targetAddress.getAddress());
        reusePacket.setPort(targetAddress.getPort());

        reusePacket.setData(byteMessage, 0, byteMessage.length);
        return reusePacket;
    }

}
