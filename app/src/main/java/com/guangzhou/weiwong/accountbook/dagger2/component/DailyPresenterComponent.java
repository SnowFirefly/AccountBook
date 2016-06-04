package com.guangzhou.weiwong.accountbook.dagger2.component;

import com.guangzhou.weiwong.accountbook.dagger2.ActivityScope;
import com.guangzhou.weiwong.accountbook.dagger2.module.DailyPresenterModule;
import com.guangzhou.weiwong.accountbook.mvp.presenter.IDailyPresenter;
import com.guangzhou.weiwong.accountbook.mvp.view.DailyActivity;

import javax.inject.Inject;

import dagger.Component;

/**
 * Created by Tower on 2016/6/4.
 */
@ActivityScope
@Component (modules = DailyPresenterModule.class)
public interface DailyPresenterComponent {
    @Inject
    void inject(DailyActivity dailyActivity);

    IDailyPresenter iDailyPresenter();
}
