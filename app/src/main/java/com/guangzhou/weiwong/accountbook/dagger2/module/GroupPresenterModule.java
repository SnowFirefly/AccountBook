package com.guangzhou.weiwong.accountbook.dagger2.module;

import com.guangzhou.weiwong.accountbook.dagger2.ActivityScope;
import com.guangzhou.weiwong.accountbook.mvp.presenter.GroupPresenter;
import com.guangzhou.weiwong.accountbook.mvp.presenter.IGroupPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Tower on 2016/6/4.
 */
@Module
public class GroupPresenterModule {
    @ActivityScope
    @Provides
    IGroupPresenter provideGroupPresenter() {
        return new GroupPresenter();
    }
}
