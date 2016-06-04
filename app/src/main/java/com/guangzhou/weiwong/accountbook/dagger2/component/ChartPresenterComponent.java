package com.guangzhou.weiwong.accountbook.dagger2.component;

import com.guangzhou.weiwong.accountbook.dagger2.ActivityScope;
import com.guangzhou.weiwong.accountbook.dagger2.module.ChartPresenterModule;
import com.guangzhou.weiwong.accountbook.mvp.presenter.IChartPresenter;
import com.guangzhou.weiwong.accountbook.mvp.view.ChartsActivity;

import javax.inject.Inject;

import dagger.Component;

/**
 * Created by Tower on 2016/6/4.
 */
@ActivityScope
@Component (modules = ChartPresenterModule.class)
public interface ChartPresenterComponent {
    @Inject
    void inject(ChartsActivity chartsActivity);

    IChartPresenter iChartPresenter();
}
