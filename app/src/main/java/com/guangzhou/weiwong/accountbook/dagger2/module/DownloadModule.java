package com.guangzhou.weiwong.accountbook.dagger2.module;

import com.guangzhou.weiwong.accountbook.dagger2.ActivityScope;
import com.guangzhou.weiwong.accountbook.mvp.model.DownloadModel;
import com.guangzhou.weiwong.accountbook.mvp.model.IDownloadModel;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Tower on 2016/5/30.
 */
@Module
public class DownloadModule {

    @ActivityScope
    @Provides
    IDownloadModel provideDownloadModel(){
        return new DownloadModel();
    }
}
