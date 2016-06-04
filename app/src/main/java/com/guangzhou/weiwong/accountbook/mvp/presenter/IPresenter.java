package com.guangzhou.weiwong.accountbook.mvp.presenter;

import com.guangzhou.weiwong.accountbook.mvp.view.IView;

/**
 * Created by Tower on 2016/4/18.
 */
public interface IPresenter {

    void onAttach(IView iView);

    void onDetach();    //


}
