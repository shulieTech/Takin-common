package io.shulie.takin.sdk.pinpoint.udp;

import java.net.InetSocketAddress;
import java.util.concurrent.TimeUnit;

public class RefreshStrategy implements UdpSocketAddressProvider {
    // JDK default DNS Cache time : 30
    public static final long DEFAULT_PORT_UNREACHABLE_REFRESH_DELAY = TimeUnit.SECONDS.toMillis(30);
    public static final long NORMAL_REFRESH_DELAY = TimeUnit.MINUTES.toMillis(5);

    private final SocketAddressProvider socketAddressProvider;
    private final long normalRefreshDelay;
    private final long portUnreachableRefreshDelay;

    private InetSocketAddress socketAddress;
    private long lastRefreshTime;
    private boolean portUnreachableState = false;


    public RefreshStrategy(SocketAddressProvider socketAddressProvider) {
        this(socketAddressProvider, NORMAL_REFRESH_DELAY, DEFAULT_PORT_UNREACHABLE_REFRESH_DELAY);
    }

    public RefreshStrategy(SocketAddressProvider socketAddressProvider, long normalRefreshDelay, long portUnreachableRefreshDelay) {
        this.socketAddressProvider =socketAddressProvider;
        this.normalRefreshDelay = normalRefreshDelay;
        this.portUnreachableRefreshDelay = portUnreachableRefreshDelay;
    }

    @Override
    public void handlePortUnreachable() {
        this.portUnreachableState = true;
    }

    @Override
    public InetSocketAddress resolve() {
        final boolean refresh = needRefresh();
        if (refresh) {
            final InetSocketAddress newAddress = socketAddressProvider.resolve();
            if (isResolved(newAddress)) {
                this.socketAddress = newAddress;
            }
        }

        return this.socketAddress;
    }

    public boolean isResolved(InetSocketAddress newAddress) {
        if (newAddress == null) {
            return false;
        }
        return !newAddress.isUnresolved();
    }

    private boolean needRefresh() {
        final boolean portUnreachableStatus = resetPortUnreachableState();
        final long deadline = getDeadline(portUnreachableStatus);
        final long currentTimeMillis = tick();
        if (currentTimeMillis > deadline) {
            // reset deadLine
            this.lastRefreshTime = currentTimeMillis;
            return true;
        }
        return false;

    }


    long tick() {
        return System.currentTimeMillis();
    }

    private long getDeadline(boolean portUnreachableStatus) {
        if (portUnreachableStatus) {
            return lastRefreshTime + portUnreachableRefreshDelay;
        } else {
            return lastRefreshTime + normalRefreshDelay;
        }
    }


    private boolean resetPortUnreachableState() {
        final boolean currentState = this.portUnreachableState;
        this.portUnreachableState = false;
        return currentState;
    }


}

