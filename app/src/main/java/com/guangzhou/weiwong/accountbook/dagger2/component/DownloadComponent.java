package com.guangzhou.weiwong.accountbook.dagger2.component;

import com.guangzhou.weiwong.accountbook.dagger2.ActivityScope;
import com.guangzhou.weiwong.accountbook.dagger2.module.DownloadModule;
import com.guangzhou.weiwong.accountbook.dagger2.module.LoginPresenterModule;
import com.guangzhou.weiwong.accountbook.mvp.MainActivity;
import com.guangzhou.weiwong.accountbook.mvp.model.IDownloadModel;
import com.guangzhou.weiwong.accountbook.mvp.presenter.LoginPresenter;
import com.guangzhou.weiwong.accountbook.mvp.presenter.RegisterPresenter;
import com.guangzhou.weiwong.accountbook.mvp.view.BaseMvpActivity;
import com.guangzhou.weiwong.accountbook.mvp.view.ChartsActivity;
import com.guangzhou.weiwong.accountbook.mvp.view.DailyActivity;
import com.guangzhou.weiwong.accountbook.mvp.view.PasterActivity;
import com.guangzhou.weiwong.accountbook.mvp.view.ProfileActivity;
import com.guangzhou.weiwong.accountbook.mvp.view.RegisterActivity;
import com.guangzhou.weiwong.accountbook.mvp.view.SettleActivity;

import dagger.Component;

/**
 * Created by Tower on 2016/5/30.
 */
@ActivityScope
@Component (modules = DownloadModule.class)
public interface DownloadComponent {
//    void inject(MainActivity mainActivity);
//    void inject(PasterActivity pasterActivity);
//    void inject(ProfileActivity profileActivity);
//    void inject(DailyActivity dailyActivity);
//    void inject(ChartsActivity chartsActivity);
//    void inject(SettleActivity settleActivity);

    void inject(LoginPresenterModule loginPresenterModule);

    IDownloadModel iDownloadModel();
}
