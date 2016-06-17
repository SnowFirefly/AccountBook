package com.guangzhou.weiwong.accountbook.utils;

import com.google.gson.JsonParseException;

import org.json.JSONException;

import java.text.ParseException;

import retrofit2.adapter.rxjava.HttpException;

/**
 * 网络错误分类及封装
 * Created by Tower on 2016/6/10.
 */
public class ErrorHandler {
    //对应HTTP的状态码
    private static final int UNAUTHORIZED = 401;
    private static final int FORBIDDEN = 403;
    private static final int NOT_FOUND = 404;
    private static final int REQUEST_TIMEOUT = 408;
    private static final int INTERNAL_SERVER_ERROR = 500;
    private static final int BAD_GATEWAY = 502;
    private static final int SERVICE_UNAVAILABLE = 503;
    private static final int GATEWAY_TIMEOUT = 504;
    // 出错提示
    private static final String networkMsg = "";
    private static final String parseMsg = "";
    private static final String unknownMsg = "";

    public static ApiException handle(Throwable throwable) {
        Throwable source = throwable;
        //获取最根源的异常
        while(source.getCause() != null){
            throwable = source;
            source = source.getCause();
        }
        ApiException apiException;
        if (throwable instanceof HttpException) {         // HTTP错误
            HttpException httpException = (HttpException) throwable;
            apiException = new ApiException(throwable, httpException.code());
            switch (httpException.code()) {
                case UNAUTHORIZED:
                case FORBIDDEN:         // 权限错误，需要实现
                case NOT_FOUND:
                case REQUEST_TIMEOUT:
                case GATEWAY_TIMEOUT:
                case INTERNAL_SERVER_ERROR:
                case BAD_GATEWAY:
                case SERVICE_UNAVAILABLE:
                default:
                    apiException.setMsg(networkMsg);        //均视为网络错误
                    break;
            }
        } else if (throwable instanceof ResultException) {          // 服务器返回的错误
            ResultException resultException = (ResultException) throwable;
            apiException = new ApiException(resultException, resultException.getErrCode());
            apiException.setMsg(resultException.getMessage());
        } else if (throwable instanceof JsonParseException
                || throwable instanceof JSONException
                || throwable instanceof ParseException) {
            apiException = new ApiException(throwable, ApiException.PARSE_ERROR);
            apiException.setMsg(parseMsg);      // 均视为解析错误
        } else {
            apiException = new ApiException(throwable, ApiException.UNKNOWN);
            apiException.setMsg(unknownMsg);        // 未知错误
        }
        return apiException;
    }
}
