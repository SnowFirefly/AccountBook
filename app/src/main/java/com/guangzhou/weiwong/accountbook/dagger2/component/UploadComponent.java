package com.guangzhou.weiwong.accountbook.dagger2.component;

import com.guangzhou.weiwong.accountbook.dagger2.ActivityScope;
import com.guangzhou.weiwong.accountbook.dagger2.module.LoginPresenterModule;
import com.guangzhou.weiwong.accountbook.dagger2.module.UploadModule;
import com.guangzhou.weiwong.accountbook.mvp.MainActivity;
import com.guangzhou.weiwong.accountbook.mvp.MyApplication;
import com.guangzhou.weiwong.accountbook.mvp.model.IUploadModel;
import com.guangzhou.weiwong.accountbook.mvp.presenter.LoginPresenter;
import com.guangzhou.weiwong.accountbook.mvp.view.BaseMvpActivity;
import com.guangzhou.weiwong.accountbook.mvp.view.GroupActivity;
import com.guangzhou.weiwong.accountbook.mvp.view.LoginActivity;
import com.guangzhou.weiwong.accountbook.mvp.view.PasterActivity;
import com.guangzhou.weiwong.accountbook.mvp.view.ProfileActivity;
import com.guangzhou.weiwong.accountbook.mvp.view.RegisterActivity;
import com.guangzhou.weiwong.accountbook.mvp.view.SettleActivity;

import javax.inject.Inject;

import dagger.Component;

/**
 * Created by Tower on 2016/6/4.
 */
@ActivityScope
@Component (modules = UploadModule.class)
public interface UploadComponent {
//    void inject (LoginActivity loginActivity);
//    void inject (RegisterActivity registerActivity);
//    void inject (PasterActivity pasterActivity);
//    void inject (ProfileActivity profileActivity);
//    void inject (SettleActivity settleActivity);
//    void inject (GroupActivity groupActivity);

    void inject (LoginPresenterModule loginPresenterModule);

    IUploadModel iUploadModel();
}
