package com.guangzhou.weiwong.accountbook.dagger2.component;

import com.guangzhou.weiwong.accountbook.dagger2.ActivityScope;
import com.guangzhou.weiwong.accountbook.dagger2.module.NetworkModule;
import com.guangzhou.weiwong.accountbook.mvp.model.Network;
import com.guangzhou.weiwong.accountbook.mvp.presenter.LoginPresenter;
import com.guangzhou.weiwong.accountbook.mvp.presenter.RegisterPresenter;

import dagger.Component;

/**
 * Created by Tower on 2016/5/30.
 */
@ActivityScope
@Component (modules = NetworkModule.class)
public interface NetworkComponent {
    void inject(LoginPresenter loginPresenter);
    void inject(RegisterPresenter registerPresenter);

    Network network();
}
