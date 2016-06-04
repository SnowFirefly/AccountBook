package com.guangzhou.weiwong.accountbook.dagger2.component;

import com.guangzhou.weiwong.accountbook.dagger2.ActivityScope;
import com.guangzhou.weiwong.accountbook.dagger2.module.UploadModule;
import com.guangzhou.weiwong.accountbook.mvp.MainActivity;
import com.guangzhou.weiwong.accountbook.mvp.model.IUploadModel;
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
    @Inject void inject (LoginActivity loginActivity);
    @Inject void inject (RegisterActivity registerActivity);
    @Inject void inject (PasterActivity pasterActivity);
    @Inject void inject (ProfileActivity profileActivity);
    @Inject void inject (SettleActivity settleActivity);
    @Inject void inject (GroupActivity groupActivity);

    IUploadModel iUploadModel();
}
