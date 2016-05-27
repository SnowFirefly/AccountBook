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
import com.guangzhou.weiwong.accountbook.mvp.model.data.RegisterResult;
import com.guangzhou.weiwong.accountbook.mvp.model.data.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;

import javax.inject.Inject;

import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;

/**
 * Created by Tower on 2016/4/23.
 */
public class NetworkApiService {
    private final String TAG = getClass().getName();
    static ApiService mApiService;

    ApiService provideApiService(){
        OkHttpClient client = new OkHttpClient();
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(String.class, new StringConverter())
                .serializeNulls()
                .create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.book4account.com/")
//                .baseUrl("http://192.168.1.100:80/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                        // for RxJava
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        return retrofit.create(ApiService.class);
    }

    @Inject
    public NetworkApiService(){
        mApiService = provideApiService();
    }

    public NetworkApiService(ApiService apiService){
        mApiService = apiService;
    }

    public Call<User> testLogin(String login_name, String password){
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
        Call<User> call = null;
        call = mApiService.testLogin(jsonObjectTotal.toString());

        return call;
    }

    public Observable<User> login(String login_name, String password){
        Log.d(TAG, "login()");
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
        Observable<User> resultObservable = null;
        resultObservable = mApiService.login(jsonObjectTotal.toString());
        return resultObservable;
    }

    public Observable<RegisterResult> register(String username, String email, String password){
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
        Observable<RegisterResult> resultObservable = null;
        resultObservable = mApiService.register(jsonObjectTotal.toString());
        return resultObservable;
    }

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

    public Observable<RegisterResult> createGroup(String userName, String password, String groupName){
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
        Observable<RegisterResult> resultObservable = null;
        resultObservable = mApiService.createGroup(jsonObjectTotal.toString());
        return resultObservable;
    }
}
