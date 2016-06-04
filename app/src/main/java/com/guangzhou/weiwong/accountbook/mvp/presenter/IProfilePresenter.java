package com.guangzhou.weiwong.accountbook.mvp.presenter;

/**
 * Created by Tower on 2016/6/4.
 */
public interface IProfilePresenter extends IPresenter {
    void getProfileInfo();      // 获取个人信息

    void setProfileInfo();      // 提交个人信息

    void getAllData4Per();      // 获取个人所有收支情况
}
