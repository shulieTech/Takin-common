package io.shulie.takin.sdk.kafka;

public class DataType {
    public static final byte TRACE_LOG = 1;
    public static final byte METRICS_LOG = 2;
    public static final byte MONITOR_LOG = 3;
    public static final byte AGENT_LOG = 4;
    public static final byte PRESSURE_ENGINE_TRACE_LOG = 5;
    public static final byte AGENT_PERFORMANCE_BASEDATA = 6;
    public static final byte CENFCENTER_INTERFACE_ADD_INTEFACEDATA = 7;
    public static final byte AGENT_API_REGISTER = 8;
    public static final byte APPLICATION_AGENT_ACCESS_STATUS = 9;
    public static final byte SHADOW_JOB_UPDATE = 10;
    public static final byte AGENT_PUSH_APPLICATION_CONFIG = 11;
    public static final byte AGENT_PUSH_APPLICATION_MIDDLEWARE = 12;
    public static final byte CONFCENTER_APPLICATIONMNT_UPDATE_APPLICATIONAGENT = 13;
    public static final byte CONFIG_LOG_PARDAR_STATUS = 14;
    public static final byte CONFIG_LOG_PARDAR_CLIENT = 15;
    public static final byte APPLICATION_CENTER_APP_INFO = 16;
    public static final byte API_AGENT_HEARTBEAT = 17;
    public static final byte API_LINK_DS_CONFIG_CHECK = 18;

    public DataType() {
    }
}