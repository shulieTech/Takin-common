package io.shulie.takin.sdk.pinpoint.udp;

import java.net.InetSocketAddress;

public interface SocketAddressProvider {
    InetSocketAddress resolve();
}