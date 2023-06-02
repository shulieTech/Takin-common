package io.shulie.takin.sdk.kafka.entity;

public class TStressTestAgentHeartbeatDTO {
    private String agentId;
    private String simulatorVersion;
    private String address;
    private String appName;
    private short pid;
    private String agentLanguage;
    private long userId;
    private String agentStatus;
    private String jvmArgsCheck;
    private String jdk;
    private String jvmArgs;
    private String envCode;
    private String host;
    private String name;
    private String agentVersion;
    private String tenantAppKey;
    private String service;
    private String md5;
    private boolean status;
    private boolean moduleLoadResult;
    private String errorCode;
    private String errorMsg;

    public String getAgentId() {
        return agentId;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId;
    }

    public String getSimulatorVersion() {
        return simulatorVersion;
    }

    public void setSimulatorVersion(String simulatorVersion) {
        this.simulatorVersion = simulatorVersion;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public short getPid() {
        return pid;
    }

    public void setPid(short pid) {
        this.pid = pid;
    }

    public String getAgentLanguage() {
        return agentLanguage;
    }

    public void setAgentLanguage(String agentLanguage) {
        this.agentLanguage = agentLanguage;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getAgentStatus() {
        return agentStatus;
    }

    public void setAgentStatus(String agentStatus) {
        this.agentStatus = agentStatus;
    }

    public String getJvmArgsCheck() {
        return jvmArgsCheck;
    }

    public void setJvmArgsCheck(String jvmArgsCheck) {
        this.jvmArgsCheck = jvmArgsCheck;
    }

    public String getJdk() {
        return jdk;
    }

    public void setJdk(String jdk) {
        this.jdk = jdk;
    }

    public String getJvmArgs() {
        return jvmArgs;
    }

    public void setJvmArgs(String jvmArgs) {
        this.jvmArgs = jvmArgs;
    }

    public String getEnvCode() {
        return envCode;
    }

    public void setEnvCode(String envCode) {
        this.envCode = envCode;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAgentVersion() {
        return agentVersion;
    }

    public void setAgentVersion(String agentVersion) {
        this.agentVersion = agentVersion;
    }

    public String getTenantAppKey() {
        return tenantAppKey;
    }

    public void setTenantAppKey(String tenantAppKey) {
        this.tenantAppKey = tenantAppKey;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public boolean isModuleLoadResult() {
        return moduleLoadResult;
    }

    public void setModuleLoadResult(boolean moduleLoadResult) {
        this.moduleLoadResult = moduleLoadResult;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
