package com.guangzhou.weiwong.accountbook.dagger2.component;

import android.app.Application;

import com.guangzhou.weiwong.accountbook.dagger2.module.ApiServiceModule;
import com.guangzhou.weiwong.accountbook.dagger2.module.AppModule;
import com.guangzhou.weiwong.accountbook.mvp.model.ApiService;
import com.guangzhou.weiwong.accountbook.mvp.model.data.User;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Tower on 2016/5/27.
 */
@Singleton
@Component(modules = {AppModule.class, ApiServiceModule.class})
public interface AppComponent {
    Application getApplication();

    ApiService getService();

//    User getUser();
}
