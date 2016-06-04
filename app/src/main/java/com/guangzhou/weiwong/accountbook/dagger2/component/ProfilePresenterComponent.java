package com.guangzhou.weiwong.accountbook.dagger2.component;

import com.guangzhou.weiwong.accountbook.dagger2.ActivityScope;
import com.guangzhou.weiwong.accountbook.dagger2.module.ProfilePresenterModule;
import com.guangzhou.weiwong.accountbook.mvp.presenter.IProfilePresenter;
import com.guangzhou.weiwong.accountbook.mvp.view.ProfileActivity;

import javax.inject.Inject;

import dagger.Component;

/**
 * Created by Tower on 2016/6/4.
 */
@ActivityScope
@Component (modules = ProfilePresenterModule.class)
public interface ProfilePresenterComponent {
    @Inject
    void inject(ProfileActivity profileActivity);

    IProfilePresenter iProfilePresenter();
}
