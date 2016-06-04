package com.guangzhou.weiwong.accountbook.dagger2.component;

import com.guangzhou.weiwong.accountbook.dagger2.ActivityScope;
import com.guangzhou.weiwong.accountbook.dagger2.module.RegisterPresenterModule;
import com.guangzhou.weiwong.accountbook.mvp.presenter.IRegisterPresenter;
import com.guangzhou.weiwong.accountbook.mvp.view.RegisterActivity;

import javax.inject.Inject;

import dagger.Component;

/**
 * Created by Tower on 2016/6/4.
 */
@ActivityScope
@Component (modules = RegisterPresenterModule.class)
public interface RegisterPresenterComponent {
    @Inject void inject(RegisterActivity registerActivity);

    IRegisterPresenter iRegisterPresenter();
}
