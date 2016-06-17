package com.guangzhou.weiwong.accountbook.dagger2.component;

import com.guangzhou.weiwong.accountbook.dagger2.ActivityScope;
import com.guangzhou.weiwong.accountbook.dagger2.module.LoginPresenterModule;
import com.guangzhou.weiwong.accountbook.mvp.presenter.ILoginPresenter;
import com.guangzhou.weiwong.accountbook.mvp.presenter.LoginPresenter;
import com.guangzhou.weiwong.accountbook.mvp.view.LoginActivity;

import dagger.Component;

/**
 * Created by Tower on 2016/4/25.
 */
@ActivityScope
@Component(modules = LoginPresenterModule.class, dependencies = AppComponent.class)
public interface LoginPresenterComponent {
    void inject(LoginActivity loginActivity);

    ILoginPresenter iLoginPresenter();
}
