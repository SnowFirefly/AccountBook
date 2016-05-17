package com.guangzhou.weiwong.accountbook.mvp.presenter;

/**
 * Created by Tower on 2016/4/25.
 */
public interface ILoginPresenter extends IPresenter {
    void login(String login_name, String password);

    void testLogin(String login_name, String password);


}
