package com.guangzhou.weiwong.accountbook.utils;

/**
 * 该类用于捕获服务器约定的错误类型
 * Created by Tower on 2016/6/10.
 */
public class ResultException extends RuntimeException{
    private int errCode = 0;

    public ResultException(String detailMessage) {
        super(detailMessage);

    }

    public int getErrCode() {
        return errCode;
    }
}
