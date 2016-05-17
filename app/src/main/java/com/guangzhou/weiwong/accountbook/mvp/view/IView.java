package com.guangzhou.weiwong.accountbook.mvp.view;

import com.guangzhou.weiwong.accountbook.mvp.model.data.RegisterResult;
import com.guangzhou.weiwong.accountbook.mvp.model.data.User;

/**
 * Created by Tower on 2016/4/18.
 */
public interface IView {
    void onLoginResult(User user);

    void onRegisterResult(RegisterResult user);

}
