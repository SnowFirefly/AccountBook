package com.guangzhou.weiwong.accountbook.dagger2.module;

import com.guangzhou.weiwong.accountbook.dagger2.ActivityScope;
import com.guangzhou.weiwong.accountbook.mvp.presenter.ChartPresenter;
import com.guangzhou.weiwong.accountbook.mvp.presenter.IChartPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Tower on 2016/6/4.
 */
@Module
public class ChartPresenterModule {
    @ActivityScope
    @Provides
    IChartPresenter provideChartPresenter() {
        return new ChartPresenter();
    }
}
