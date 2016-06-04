package com.guangzhou.weiwong.accountbook.dagger2.module;

import com.guangzhou.weiwong.accountbook.mvp.presenter.IMainPresenter;
import com.guangzhou.weiwong.accountbook.mvp.presenter.MainPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Tower on 2016/6/4.
 */
@Module
public class MainPresenterModule {
    @Provides
    IMainPresenter provideMainPresenter() {
        return new MainPresenter();
    }
}
