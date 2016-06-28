package com.guangzhou.weiwong.accountbook.utils;

/**
 * 自定义GsonResponseBodyConverter，抛出服务器约定异常
 * Created by Tower on 2016/6/10.
 */

import com.google.gson.Gson;
import com.guangzhou.weiwong.accountbook.mvp.model.Result.Result;

import java.io.IOException;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Converter;

class GsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {
    private final Gson gson;
//    private final TypeAdapter<T> adapter;
    private final Type type;

//    GsonResponseBodyConverter(Gson gson, TypeAdapter<T> adapter) {
//        this.gson = gson;
//        this.adapter = adapter;
//    }

    public GsonResponseBodyConverter(Gson gson, Type type) {
        this.gson = gson;
        this.type = type;
    }

    @Override public T convert(ResponseBody value) throws IOException {
        String response = value.string();
        try {
            MyLog.d(this, "response >> " + response);
            Result result = gson.fromJson(response, Result.class);
            if (result.getAck().equals("success")) {
                return gson.fromJson(response, type);
            } else {
                throw new ResultException(result.getMessage());
            }
        } finally {
            value.close();
        }

//        JsonReader jsonReader = gson.newJsonReader(value.charStream());
//        try {
//            return adapter.read(jsonReader);
//        } finally {
//            value.close();
//        }
    }
}
