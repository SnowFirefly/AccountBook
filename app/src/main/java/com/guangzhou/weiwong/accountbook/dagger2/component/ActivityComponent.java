package com.guangzhou.weiwong.accountbook.dagger2.component;

import com.guangzhou.weiwong.accountbook.dagger2.module.ApiServiceModule;
import com.guangzhou.weiwong.accountbook.mvp.MainActivity;
import com.guangzhou.weiwong.accountbook.mvp.presenter.LoginPresenter;
import com.guangzhou.weiwong.accountbook.mvp.view.LoginActivity;

import dagger.Component;
import dagger.Provides;

/**
 * Created by Tower on 2016/4/25.
 */

public interface ActivityComponent {
    void injectActivity(LoginActivity loginActivity);
}
