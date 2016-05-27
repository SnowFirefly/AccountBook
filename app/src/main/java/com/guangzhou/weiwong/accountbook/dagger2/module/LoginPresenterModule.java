package com.guangzhou.weiwong.accountbook.dagger2.module;

import com.guangzhou.weiwong.accountbook.dagger2.ActivityScope;
import com.guangzhou.weiwong.accountbook.mvp.presenter.LoginPresenter;
import com.guangzhou.weiwong.accountbook.mvp.view.LoginActivity;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Tower on 2016/5/27.
 */
@Module
public class LoginPresenterModule {
    private LoginActivity loginActivity;

    public LoginPresenterModule(LoginActivity loginActivity) {
        this.loginActivity = loginActivity;
    }

    @Provides
    @ActivityScope
    LoginActivity provideLoginActivity(){
        return this.loginActivity;
    }

    @Provides
    @ActivityScope
    LoginPresenter provideLoginPresenter(){
        return new LoginPresenter(loginActivity);
    }
}
