package com.guangzhou.weiwong.accountbook.mvp.model.data;

/**
 * Created by Tower on 2016/4/27.
 */
public class RegisterResult {
    private String ack;
    private String data;
    private String message;

    @Override
    public String toString() {
        return "{user: ack->" + ack + ", message->" + message + ", data->" + data + "}";
    }

    public String getAck() {
        return ack;
    }

    public void setAck(String ack) {
        this.ack = ack;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
