package com.guangzhou.weiwong.accountbook.mvp.model;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import com.guangzhou.weiwong.accountbook.mvp.MyApplication;
import com.guangzhou.weiwong.accountbook.mvp.model.Result.Result;
import com.guangzhou.weiwong.accountbook.utils.MyLog;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;

import javax.inject.Inject;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;

/**
 * Created by Tower on 2016/4/23.
 */
public class Network {
    private final String TAG = getClass().getName();
    static ApiService mApiService;

    ApiService provideApiService(){
        OkHttpClient client = new OkHttpClient();
        Gson gson = new GsonBuilder()
//                .registerTypeAdapter(String.class, new StringConverter())
//                .serializeNulls()
//                .registerTypeAdapterFactory(new NulStringToEmptyAdapterFactory<>())
                .create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.book4account.com/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                        // for RxJava
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        return retrofit.create(ApiService.class);
    }

    @Inject
    public Network(){
        mApiService = MyApplication.getApiService();
        MyLog.i(this, "mApiService: " + mApiService);
    }


    public Network(ApiService apiService){
        mApiService = apiService;
    }

    public Call<Result> testLogin(String login_name, String password){
        Log.d(TAG, "testLogin()");
        JSONObject jsonObject = new JSONObject();
        JSONObject jsonObjectTotal = new JSONObject();
        try {
            jsonObject.put("username", login_name);
            jsonObject.put("password", password);
            jsonObjectTotal.put("user", jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.i(TAG, jsonObjectTotal.toString());
        Call<Result> call = mApiService.testLogin(jsonObjectTotal.toString());
        return call;
    }

    public Observable<Result> login(String login_name, String password){
        Log.d(TAG, "login(" + login_name + ", " + password + ")");
        JSONObject jsonObject = new JSONObject();
        JSONObject jsonObjectTotal = new JSONObject();
        try {
            jsonObject.put("username", login_name);
            jsonObject.put("password", password);
            jsonObjectTotal.put("user", jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.i(TAG, jsonObjectTotal.toString());
        Observable<Result> resultObservable = null;
        resultObservable = mApiService.login(jsonObjectTotal.toString());
        return resultObservable;
    }

    public Observable<Response<Result>> login2(String login_name, String password){
        Log.d(TAG, "login2(" + login_name + ", " + password + ")");
        JSONObject jsonObject = new JSONObject();
        JSONObject jsonObjectTotal = new JSONObject();
        try {
            jsonObject.put("username", login_name);
            jsonObject.put("password", password);
            jsonObjectTotal.put("user", jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.i(TAG, jsonObjectTotal.toString());
        Observable<Response<Result>> resultObservable = null;
        resultObservable = mApiService.login2(jsonObjectTotal.toString());
        return resultObservable;
    }

    public Observable<Result> register(String username, String email, String password){
        Log.d(TAG, "register()");
        JSONObject jsonObject = new JSONObject();
        JSONObject jsonObjectTotal = new JSONObject();
        try {
            jsonObject.put("username", username);
            jsonObject.put("password", password);
            jsonObject.put("email", email);
            jsonObjectTotal.put("data", jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.i(TAG, jsonObjectTotal.toString());
        Observable<Result> resultObservable = null;
        resultObservable = mApiService.register(jsonObjectTotal.toString());
        return resultObservable;
    }

    public Observable<Result> createGroup(String userName, String password, String groupName){
        Log.d(TAG, "register()");
        JSONObject jsonObjectTotal = new JSONObject(),
                jsonObject = new JSONObject(),
                jsonObject1 = new JSONObject();
        try {
            jsonObject.put("username", userName);
            jsonObject.put("password", password);
            jsonObject1.put("group_name", groupName);
            jsonObjectTotal.put("user", jsonObject);
            jsonObjectTotal.put("data", jsonObject1);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.i(TAG, jsonObjectTotal.toString());
        Observable<Result> resultObservable = null;
        resultObservable = mApiService.createGroup(jsonObjectTotal.toString());
        return resultObservable;
    }


    // 转换Gson中的null （好像没作用）
    public class StringConverter implements JsonSerializer<String>, JsonDeserializer<String> {

        @Override
        public String deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            return json.getAsJsonPrimitive().getAsString();
        }

        @Override
        public JsonElement serialize(String src, Type typeOfSrc, JsonSerializationContext context) {
            if (src == null) {
                return new JsonPrimitive("");
            } else {
                return new JsonPrimitive(src);
            }
        }
    }

    // 转换Gson中的null （好像没作用）
    public class NulStringToEmptyAdapterFactory<T> implements TypeAdapterFactory {

        public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
            Class<T> rawType = (Class<T>) type.getRawType();
            if (rawType != String.class) {
                return null;
            }
            return (TypeAdapter<T>) new StringNullAdapter();
        }
    }

    public class StringNullAdapter extends TypeAdapter<String> {
        @Override
        public String read(JsonReader in) throws IOException {
            if (in.peek() == JsonToken.NULL) {
                in.nextNull();
                return "";
            }
            return in.nextString();
        }

        @Override
        public void write(JsonWriter out, String value) throws IOException {
            if (value == null) {
                out.nullValue();
                return;
            }
            out.value(value);
        }
    }

}
