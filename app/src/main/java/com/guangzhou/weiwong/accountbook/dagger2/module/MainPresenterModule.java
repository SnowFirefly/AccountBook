package com.guangzhou.weiwong.accountbook.dagger2.module;

import com.guangzhou.weiwong.accountbook.mvp.model.IDBModel;
import com.guangzhou.weiwong.accountbook.mvp.model.IDownloadModel;
import com.guangzhou.weiwong.accountbook.mvp.model.IUploadModel;
import com.guangzhou.weiwong.accountbook.mvp.presenter.IMainPresenter;
import com.guangzhou.weiwong.accountbook.mvp.presenter.MainPresenter;

import dagger.Module;
import dagger.Provides;

/**
 *
 * Created by Tower on 2016/6/4.
 */
@Module (includes = { UploadModule.class, DownloadModule.class, DBModule.class })
public class MainPresenterModule {
    @Provides
    IMainPresenter provideMainPresenter(IUploadModel iUploadModel, IDownloadModel iDownloadModel,
                                        IDBModel idbModel) {
        return new MainPresenter(idbModel, iUploadModel, iDownloadModel);
    }
}
