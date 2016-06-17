package com.guangzhou.weiwong.accountbook.dagger2.module;

import com.guangzhou.weiwong.accountbook.dagger2.ActivityScope;
import com.guangzhou.weiwong.accountbook.dagger2.component.DaggerDownloadComponent;
import com.guangzhou.weiwong.accountbook.dagger2.component.DaggerUploadComponent;
import com.guangzhou.weiwong.accountbook.mvp.model.IDownloadModel;
import com.guangzhou.weiwong.accountbook.mvp.model.IUploadModel;
import com.guangzhou.weiwong.accountbook.mvp.presenter.ILoginPresenter;
import com.guangzhou.weiwong.accountbook.mvp.presenter.LoginPresenter;
import com.guangzhou.weiwong.accountbook.mvp.view.LoginActivity;
import com.guangzhou.weiwong.accountbook.utils.MyLog;

import javax.inject.Inject;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Tower on 2016/5/27.
 */
@Module (includes = { UploadModule.class, DownloadModule.class })
public class LoginPresenterModule {
    private LoginActivity loginActivity;

    /*public LoginPresenterModule(LoginActivity loginActivity) {
        this.loginActivity = loginActivity;
    }*/

    public LoginPresenterModule() {
    }

    /*@Provides
    @ActivityScope
    LoginActivity provideLoginActivity(){
        return this.loginActivity;
    }*/

    @ActivityScope
    @Provides
    ILoginPresenter provideLoginPresenter(IUploadModel iUploadModel, IDownloadModel iDownloadModel){
        return new LoginPresenter(iUploadModel, iDownloadModel);
    }
}
