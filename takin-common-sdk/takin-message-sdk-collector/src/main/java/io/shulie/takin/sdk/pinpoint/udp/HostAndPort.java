package io.shulie.takin.sdk.pinpoint.udp;

public class HostAndPort {
    public static final int NO_PORT = -1;

// TODO
//    private final String host;
//    private final int port;
//
//    public HostAndPort(String host, int port) {
//        this.host = host;
//        this.port = port;
//    }


    public static int getValidPortOrNoPort(int port) {
        if (!isValidPort(port)) {
            return NO_PORT;
        }
        return port;
    }

    public static int getPortOrNoPort(int port) {
        if (port < 0) {
            return HostAndPort.NO_PORT;
        }
        return port;
    }

    public static boolean isValidPort(int port) {
        return port >= 0 && port <= 65535;
    }

    public static String toHostAndPortString(String host, int port) {
        return toHostAndPortString(host, port, NO_PORT);
    }

    /**
     * This API does not verification for input args.
     */
    public static String toHostAndPortString(String host, int port, int noPort) {
        // don't validation hostName
        // don't validation port range
        if (noPort == port) {
            return host;
        }
        final int hostLength = host.length();
        final StringBuilder builder = new StringBuilder(hostLength + 6);
        builder.append(host);
        builder.append(':');
        builder.append(port);
        return builder.toString();
    }

}