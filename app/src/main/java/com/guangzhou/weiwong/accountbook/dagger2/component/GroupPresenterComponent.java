package com.guangzhou.weiwong.accountbook.dagger2.component;

import com.guangzhou.weiwong.accountbook.dagger2.ActivityScope;
import com.guangzhou.weiwong.accountbook.dagger2.module.GroupPresenterModule;
import com.guangzhou.weiwong.accountbook.mvp.presenter.IGroupPresenter;
import com.guangzhou.weiwong.accountbook.mvp.view.GroupActivity;

import javax.inject.Inject;

import dagger.Component;

/**
 * Created by Tower on 2016/6/4.
 */
@ActivityScope
@Component (modules = GroupPresenterModule.class)
public interface GroupPresenterComponent {
    void inject(GroupActivity groupActivity);

    IGroupPresenter iGroupPresenter();
}
