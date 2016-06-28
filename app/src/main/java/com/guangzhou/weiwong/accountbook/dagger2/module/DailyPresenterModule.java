package com.guangzhou.weiwong.accountbook.dagger2.module;

import com.guangzhou.weiwong.accountbook.dagger2.ActivityScope;
import com.guangzhou.weiwong.accountbook.mvp.model.IDBModel;
import com.guangzhou.weiwong.accountbook.mvp.model.IDownloadModel;
import com.guangzhou.weiwong.accountbook.mvp.model.IUploadModel;
import com.guangzhou.weiwong.accountbook.mvp.presenter.DailyPresenter;
import com.guangzhou.weiwong.accountbook.mvp.presenter.IDailyPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Tower on 2016/6/4.
 */
@Module(includes = { UploadModule.class, DownloadModule.class, DBModule.class })
public class DailyPresenterModule {
    @ActivityScope
    @Provides
    IDailyPresenter provideDailyPresenter(IUploadModel iUploadModel, IDownloadModel iDownloadModel,
                                          IDBModel iDbModel) {
        return new DailyPresenter(iUploadModel, iDownloadModel, iDbModel);
    }
}
