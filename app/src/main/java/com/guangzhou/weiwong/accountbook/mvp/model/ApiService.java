package com.guangzhou.weiwong.accountbook.mvp.model;

import com.guangzhou.weiwong.accountbook.mvp.model.data.RegisterResult;
import com.guangzhou.weiwong.accountbook.mvp.model.data.User;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HEAD;
import retrofit2.http.Header;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by Tower on 2016/4/22.
 */
public interface ApiService {
    @FormUrlEncoded
    @POST("api.php?method=userLogin")
    Observable<User> login(@Field("data") String dataJson);

    @FormUrlEncoded
    @POST("api.php?method=userLogin")
    Call<User> testLogin(@Field("data") String dataJson);

//    @POST("api.php?method=userRegister")
//    Call<User> register(@Field("username") String username, @Field("password") String password,
//                        @Field("email") String email);
    @FormUrlEncoded
    @POST("api.php?method=userRegister")
    Observable<RegisterResult> register(@Field("data") String jsonString);

    @FormUrlEncoded
    @POST("api.php?method=userGroupCreate")
    Observable<RegisterResult> createGroup(@Field("data") String jsonString);

}
