package io.shulie.takin.sdk.kafka.entity;

import java.util.Map;

public class MessageEntity {

    private Map<String,Object> headers;

    private Map body;

    public Map<String, Object> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, Object> headers) {
        this.headers = headers;
    }

    public Map getBody() {
        return body;
    }

    public void setBody(Map body) {
        this.body = body;
    }
}
