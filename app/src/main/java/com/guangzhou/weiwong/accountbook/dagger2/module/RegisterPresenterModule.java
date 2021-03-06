package com.guangzhou.weiwong.accountbook.dagger2.module;

import com.guangzhou.weiwong.accountbook.dagger2.ActivityScope;
import com.guangzhou.weiwong.accountbook.mvp.presenter.IRegisterPresenter;
import com.guangzhou.weiwong.accountbook.mvp.presenter.RegisterPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Tower on 2016/6/4.
 */
@Module
public class RegisterPresenterModule {

    @ActivityScope
    @Provides
    IRegisterPresenter provideRegisterPresenter() {
        return new RegisterPresenter();
    }
}
