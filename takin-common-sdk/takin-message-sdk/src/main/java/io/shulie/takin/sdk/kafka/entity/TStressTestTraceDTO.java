package io.shulie.takin.sdk.kafka.entity;


public class TStressTestTraceDTO extends TStressTestBaseDTO{
    private String traceId;
    private String invokeId;
    private int invokeType;
    private int cost;
    private String middlewareName;
    private String serviceName;
    private String methodName;
    private String resultCode;
    private boolean pressureTest;
    private boolean debugTest;
    private boolean entrance;
    private boolean server;
    private String entranceId;
    private String upAppName;
    private String remoteIp;
    private int port;

    public String getEntranceId() {
        return entranceId;
    }

    public void setEntranceId(String entranceId) {
        this.entranceId = entranceId;
    }

    public String getTraceId() {
        return traceId;
    }

    public void setTraceId(String traceId) {
        this.traceId = traceId;
    }

    public String getInvokeId() {
        return invokeId;
    }

    public void setInvokeId(String invokeId) {
        this.invokeId = invokeId;
    }

    public int getInvokeType() {
        return invokeType;
    }

    public void setInvokeType(int invokeType) {
        this.invokeType = invokeType;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public String getMiddlewareName() {
        return middlewareName;
    }

    public void setMiddlewareName(String middlewareName) {
        this.middlewareName = middlewareName;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public boolean isPressureTest() {
        return pressureTest;
    }

    public void setPressureTest(boolean pressureTest) {
        this.pressureTest = pressureTest;
    }

    public boolean isDebugTest() {
        return debugTest;
    }

    public void setDebugTest(boolean debugTest) {
        this.debugTest = debugTest;
    }

    public boolean isEntrance() {
        return entrance;
    }

    public void setEntrance(boolean entrance) {
        this.entrance = entrance;
    }

    public boolean isServer() {
        return server;
    }

    public void setServer(boolean server) {
        this.server = server;
    }

    public String getUpAppName() {
        return upAppName;
    }

    public void setUpAppName(String upAppName) {
        this.upAppName = upAppName;
    }

    public String getRemoteIp() {
        return remoteIp;
    }

    public void setRemoteIp(String remoteIp) {
        this.remoteIp = remoteIp;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
