package com.guangzhou.weiwong.accountbook.dagger2.module;

import com.guangzhou.weiwong.accountbook.dagger2.ActivityScope;
import com.guangzhou.weiwong.accountbook.mvp.presenter.DailyPresenter;
import com.guangzhou.weiwong.accountbook.mvp.presenter.IDailyPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Tower on 2016/6/4.
 */
@Module
public class DailyPresenterModule {
    @ActivityScope
    @Provides
    IDailyPresenter provideDailyPresenter() {
        return new DailyPresenter();
    }
}
