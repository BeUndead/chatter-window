package com.com.sockets;

import java.util.Map;

public class OutputMessage {

    private Map<String, String> payload;
    private Map<String, Object> headers;


    public void setPayload(final Map<String, String> payload) {
        this.payload = payload;
    }

    public void setHeaders(final Map<String, Object> headers) {
        this.headers = headers;
    }


    public Message getPayload() {
        final Message message = new Message();
        message.setMessage(this.payload.get("message"));
        message.setTo(this.payload.get("to"));
        message.setTimestamp(this.payload.get("timestamp"));
        return message;
    }

    public Map<String, Object> getHeaders() {
        return this.headers;
    }
}
