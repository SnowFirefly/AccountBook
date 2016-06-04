package com.guangzhou.weiwong.accountbook.dagger2.component;

import com.guangzhou.weiwong.accountbook.dagger2.ActivityScope;
import com.guangzhou.weiwong.accountbook.dagger2.module.SettlePresenterModule;
import com.guangzhou.weiwong.accountbook.mvp.presenter.ISettlePresenter;
import com.guangzhou.weiwong.accountbook.mvp.presenter.SettlePresenter;
import com.guangzhou.weiwong.accountbook.mvp.view.SettleActivity;

import javax.inject.Inject;

import dagger.Component;

/**
 * Created by Tower on 2016/6/4.
 */
@ActivityScope
@Component (modules = SettlePresenterModule.class)
public interface SettlePresenterComponent {
    @Inject
    void inject(SettleActivity settleActivity);

    ISettlePresenter iSettlePresenter();
}
