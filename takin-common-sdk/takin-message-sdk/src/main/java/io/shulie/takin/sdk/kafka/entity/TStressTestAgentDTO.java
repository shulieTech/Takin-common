package io.shulie.takin.sdk.kafka.entity;

public class TStressTestAgentDTO {
    private int dataType;
    private String stringValue;
    private String hostIp;
    private String version;
    private String charset;
    private String userAppKey;
    private String tenantAppKey;
    private String userId;
    private String envCode;
    private String agentExpand;

    public int getDataType() {
        return dataType;
    }

    public void setDataType(int dataType) {
        this.dataType = dataType;
    }

    public String getStringValue() {
        return stringValue;
    }

    public void setStringValue(String stringValue) {
        this.stringValue = stringValue;
    }

    public String getHostIp() {
        return hostIp;
    }

    public void setHostIp(String hostIp) {
        this.hostIp = hostIp;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getCharset() {
        return charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    public String getUserAppKey() {
        return userAppKey;
    }

    public void setUserAppKey(String userAppKey) {
        this.userAppKey = userAppKey;
    }

    public String getTenantAppKey() {
        return tenantAppKey;
    }

    public void setTenantAppKey(String tenantAppKey) {
        this.tenantAppKey = tenantAppKey;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEnvCode() {
        return envCode;
    }

    public void setEnvCode(String envCode) {
        this.envCode = envCode;
    }

    public String getAgentExpand() {
        return agentExpand;
    }

    public void setAgentExpand(String agentExpand) {
        this.agentExpand = agentExpand;
    }
}
