package io.shulie.takin.sdk.pinpoint.udp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;

public class DnsSocketAddressProvider implements SocketAddressProvider {
    private final Logger logger = LoggerFactory.getLogger(DnsSocketAddressProvider.class);
    private final String host;
    private final int port;

    private InetSocketAddress oldAddress;

    public DnsSocketAddressProvider(String host, int port) {
        this.host = host;
        this.port = checkPort(port);
    }

    private static int checkPort(int port) {
        if (!HostAndPort.isValidPort(port)) {
            throw new IllegalArgumentException("port out of range:" + port);
        }
        return port;
    }


    @Override
    public InetSocketAddress resolve() {
        try {
            InetAddress inetAddress = getByName(host);
            InetSocketAddress updateAddress = new InetSocketAddress(inetAddress, port);

            checkDnsUpdate(updateAddress);

            return updateAddress;
        } catch (UnknownHostException e) {
            logger.error("dns lookup fail. host:{}", host);
            // expected UnknownHostException from tcp connect timing
            return InetSocketAddress.createUnresolved(host, port);
            // or return null;
        }
    }

    InetAddress getByName(String host) throws UnknownHostException {
        return InetAddress.getByName(host);
    }

    private void checkDnsUpdate(InetSocketAddress updateAddress) {
        synchronized (this) {
            final InetSocketAddress oldAddress = this.oldAddress;
            if (oldAddress != null) {
                if (!oldAddress.equals(updateAddress)) {
                    logger.info("host address updated, host:{} old:{}, update:{}" , host , oldAddress , updateAddress);
                }
            }
            this.oldAddress = updateAddress;
        }
    }

    @Override
    public String toString() {
        return "DnsSocketAddressProvider{" +
                "host='" + host + '\'' +
                ", port=" + port +
                '}';
    }
}
