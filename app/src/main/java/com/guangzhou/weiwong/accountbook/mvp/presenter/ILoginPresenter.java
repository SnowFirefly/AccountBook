package com.guangzhou.weiwong.accountbook.mvp.presenter;

import com.guangzhou.weiwong.accountbook.mvp.view.IView;

/**
 * Created by Tower on 2016/4/25.
 */
public interface ILoginPresenter extends IPresenter {
    void login(String login_name, String password);

    void login2(String login_name, String password);

    void testLogin(String login_name, String password);

}
