package com.guangzhou.weiwong.accountbook.dagger2.module;

import com.guangzhou.weiwong.accountbook.mvp.model.ApiService;
import com.guangzhou.weiwong.accountbook.utils.NewGsonConverterFactory;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Tower on 2016/4/23.
 */
@Module
public class ApiServiceModule {

    @Provides
    @Singleton
    ApiService provideApiService(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.book4account.com/")
                // 注册自定义的工厂类（使Retrofit也可以抛出服务器约定异常）
                .addConverterFactory(NewGsonConverterFactory.create())
                // for RxJava
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        return retrofit.create(ApiService.class);
    }
}
