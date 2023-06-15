package io.shulie.takin.sdk.kafka.entity;

import java.io.Serializable;

public class TStressTestBaseDTO implements Serializable {

    private String agentId;

    private String appName;

    private long timestamp;


    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getAgentId() {
        return agentId;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

}
