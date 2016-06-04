package com.guangzhou.weiwong.accountbook.dagger2.module;

import com.guangzhou.weiwong.accountbook.dagger2.ActivityScope;
import com.guangzhou.weiwong.accountbook.mvp.presenter.IProfilePresenter;
import com.guangzhou.weiwong.accountbook.mvp.presenter.ProfilePresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Tower on 2016/6/4.
 */
@Module
public class ProfilePresenterModule {
    @ActivityScope
    @Provides
    IProfilePresenter provideProfilePresenter() {
        return new ProfilePresenter();
    }
}
