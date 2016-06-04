package com.guangzhou.weiwong.accountbook.dagger2.module;

import com.guangzhou.weiwong.accountbook.dagger2.ActivityScope;
import com.guangzhou.weiwong.accountbook.mvp.presenter.IPasterPresenter;
import com.guangzhou.weiwong.accountbook.mvp.presenter.PasterPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Tower on 2016/6/4.
 */
@Module
public class PasterPresenterModule {
    @ActivityScope
    @Provides
    IPasterPresenter providePasterPresenter() {
        return new PasterPresenter();
    }
}
