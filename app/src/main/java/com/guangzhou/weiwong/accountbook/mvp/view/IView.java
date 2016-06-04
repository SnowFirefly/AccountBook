package com.guangzhou.weiwong.accountbook.mvp.view;

import com.guangzhou.weiwong.accountbook.mvp.model.Result.Result;

/**
 * Created by Tower on 2016/4/18.
 */
public interface IView {
    void onLoginResult(Result result);

    void onRegisterResult(Result result);

}
