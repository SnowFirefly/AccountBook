package com.guangzhou.weiwong.accountbook.mvp.presenter;

/**
 * Created by Tower on 2016/4/26.
 */
public interface IRegisterPresenter extends IPresenter{
    void register(String user, String email, String password);
}
