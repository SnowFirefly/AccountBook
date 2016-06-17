package com.guangzhou.weiwong.accountbook.mvp.model;

import com.guangzhou.weiwong.accountbook.mvp.model.Result.Result;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by Tower on 2016/4/22.
 */
public interface ApiService {
    @FormUrlEncoded
    @POST("api.php?method=userLogin")
    Observable<Result> login(@Field("data") String dataJson);

    @FormUrlEncoded
    @POST("api.php?method=userLogin")
    Call<Result> testLogin(@Field("data") String dataJson);

    @FormUrlEncoded
    @POST("api.php?method=userLogin")
    Observable<Response<Result>> login2(@Field("data") String dataJson);

    @FormUrlEncoded
    @POST("api.php?method=userRegister")
    Observable<Result> register(@Field("data") String jsonString);

    @FormUrlEncoded
    @POST("api.php?method=userGroupCreate")
    Observable<Result> createGroup(@Field("data") String jsonString);

    @FormUrlEncoded
    @POST("api.php?method=")
    Observable<Result> getCurWeekData(@Field("data") String dataJson);

}

//    @POST("api.php?method=userRegister")
//    Call<Result> register(@Field("username") String username, @Field("password") String password,
//                        @Field("email") String email);