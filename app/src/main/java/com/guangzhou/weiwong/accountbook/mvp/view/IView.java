package com.guangzhou.weiwong.accountbook.mvp.view;

import com.guangzhou.weiwong.accountbook.mvp.model.Result.Result;

/**
 * Created by Tower on 2016/4/18.
 */
public interface IView {
    int GROUP_DATA = 0;
    int CUR_WEEK_DATA = 1;


    void onSignResult(String resultMsg);        // 登录注册结果

    void onError(String errorMsg);          // 网络错误（包括服务器错误）回调

    <T> void onLoadResult(int type, T bean);          // 获取数据

    void onCommitResult(String resultMsg);     // 提交数据结果

    //    void onRegisterResult(Result result);
}
