package com.guangzhou.weiwong.accountbook.utils;

/**
 * 自定义ApiException，携带了异常代码和信息，以及根源Throwable，足够调用者需要
 * Created by Tower on 2016/6/10.
 */
public class ApiException extends Exception {
    private final int code;
    private String msg;

    public static final int UNKNOWN = 1000;
    public static final int PARSE_ERROR = 1001;

    public ApiException(Throwable throwable, int code) {
        super(throwable);
        this.code = code;
//        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
