package com.guangzhou.weiwong.accountbook.dagger2.component;

import com.guangzhou.weiwong.accountbook.dagger2.ActivityScope;
import com.guangzhou.weiwong.accountbook.dagger2.module.PasterPresenterModule;
import com.guangzhou.weiwong.accountbook.mvp.presenter.IPasterPresenter;
import com.guangzhou.weiwong.accountbook.mvp.view.PasterActivity;

import javax.inject.Inject;

import dagger.Component;

/**
 * Created by Tower on 2016/6/4.
 */
@ActivityScope
@Component (modules = PasterPresenterModule.class)
public interface PasterPresenterComponent {
    @Inject
    void inject(PasterActivity pasterActivity);

    IPasterPresenter iPasterPresenter();
}
