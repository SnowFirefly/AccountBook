package com.guangzhou.weiwong.accountbook.dagger2.module;

import com.guangzhou.weiwong.accountbook.dagger2.ActivityScope;
import com.guangzhou.weiwong.accountbook.mvp.model.ApiService;
import com.guangzhou.weiwong.accountbook.mvp.model.NetworkApiService;

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
    NetworkApiService provideNetworkApiService(ApiService apiService){
        return new NetworkApiService(apiService);
    }

    @Provides
    @Singleton
    ApiService provideApiService(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.book4account.com/")
                .addConverterFactory(GsonConverterFactory.create())
                // for RxJava
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        return retrofit.create(ApiService.class);
    }
}
