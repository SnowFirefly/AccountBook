package com.guangzhou.weiwong.accountbook.mvp.model.Result;

/**
 * 服务器返回结果Bean
 * Created by Tower on 2016/4/22.
 */
public class Result {
    private String ack;
    private Object data;
    private String message;

    @Override
    public String toString() {
        return "{user: ack->" + ack + ", message->" + message + ", data->" + data + "}";
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getAck() {
        return ack;
    }

    public void setAck(String ack) {
        this.ack = ack;
    }

}
