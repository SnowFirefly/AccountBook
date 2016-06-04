package com.guangzhou.weiwong.accountbook.dagger2.component;

import com.guangzhou.weiwong.accountbook.dagger2.module.ApiServiceModule;
import com.guangzhou.weiwong.accountbook.mvp.MyApplication;
import com.guangzhou.weiwong.accountbook.mvp.model.ApiService;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Tower on 2016/5/27.
 */
@Singleton
@Component(modules = ApiServiceModule.class)
public interface ApiServiceComponent {
    void inject(MyApplication myApplication);

    ApiService getApiService();
}
