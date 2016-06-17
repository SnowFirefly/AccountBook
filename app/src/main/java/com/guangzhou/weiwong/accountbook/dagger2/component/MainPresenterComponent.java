package com.guangzhou.weiwong.accountbook.dagger2.component;

import com.guangzhou.weiwong.accountbook.dagger2.ActivityScope;
import com.guangzhou.weiwong.accountbook.dagger2.module.MainPresenterModule;
import com.guangzhou.weiwong.accountbook.mvp.MainActivity;
import com.guangzhou.weiwong.accountbook.mvp.presenter.IMainPresenter;

import javax.inject.Inject;

import dagger.Component;

/**
 * Created by Tower on 2016/6/4.
 */
@ActivityScope
@Component (modules = MainPresenterModule.class)
public interface MainPresenterComponent {
    void inject(MainActivity mainActivity);

    IMainPresenter iMainPresenter();
}
