package io.shulie.takin.sdk.kafka.entity;

public class TStressTestTracePayloadDTO extends TStressTestBaseDTO {
    private String traceId;
    private String invokeId;
    private int invokeType;
    private boolean pressureTest;
    private String middlewareName;
    private String serviceName;
    private String methodName;
    private String request;
    private String response;
    private String callbackMsg;
    private int requestSize;
    private int responseSize;
    private String ext;
    private String rpcContent;

    public boolean isPressureTest() {
        return pressureTest;
    }

    public void setPressureTest(boolean pressureTest) {
        this.pressureTest = pressureTest;
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

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getCallbackMsg() {
        return callbackMsg;
    }

    public void setCallbackMsg(String callbackMsg) {
        this.callbackMsg = callbackMsg;
    }

    public int getRequestSize() {
        return requestSize;
    }

    public void setRequestSize(int requestSize) {
        this.requestSize = requestSize;
    }

    public int getResponseSize() {
        return responseSize;
    }

    public void setResponseSize(int responseSize) {
        this.responseSize = responseSize;
    }

    public String getExt() {
        return ext;
    }

    public void setExt(String ext) {
        this.ext = ext;
    }

    public String getRpcContent() {
        return rpcContent;
    }

    public void setRpcContent(String rpcContent) {
        this.rpcContent = rpcContent;
    }
}
