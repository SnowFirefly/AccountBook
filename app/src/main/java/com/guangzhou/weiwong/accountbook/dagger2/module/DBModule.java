package com.guangzhou.weiwong.accountbook.dagger2.module;

import com.guangzhou.weiwong.accountbook.dagger2.ActivityScope;
import com.guangzhou.weiwong.accountbook.mvp.DBModel;
import com.guangzhou.weiwong.accountbook.mvp.model.IDBModel;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Tower on 2016/6/8.
 */
@Module
public class DBModule {
    @ActivityScope
    @Provides
    IDBModel provideDBModel(){
        return new DBModel();
    }
}
