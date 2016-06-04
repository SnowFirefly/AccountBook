package com.guangzhou.weiwong.accountbook.mvp.presenter;

import android.util.Log;

import com.guangzhou.weiwong.accountbook.dagger2.component.DaggerNetworkComponent;
import com.guangzhou.weiwong.accountbook.mvp.model.Network;
import com.guangzhou.weiwong.accountbook.mvp.model.Result.Result;
import com.guangzhou.weiwong.accountbook.mvp.view.IView;
import com.guangzhou.weiwong.accountbook.utils.MyLog;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Tower on 2016/4/26.
 */
public class RegisterPresenter implements IRegisterPresenter {
    private final String TAG = getClass().getName();
    @Inject Network network;
    private IView iView;

    public RegisterPresenter(){
        DaggerNetworkComponent.builder().build().inject(this);
        network.login("RegisterPresenter", "network.login");
    }

    @Override
    public void register(String user, String email, String password) {
        Log.d(TAG, "register()");
        Observable<Result> observable = network.register(user, email, password);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Result>() {
                    @Override
                    public void onCompleted() {
                        Log.d(TAG, "onCompleted()");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError():" + e.getMessage());
                    }

                    @Override
                    public void onNext(Result result) {
                        MyLog.d(TAG, "onNext()");
                        MyLog.d(TAG, result.toString());
                        iView.onRegisterResult(result);
                    }
                });
    }

    @Override
    public void onAttach(IView iView) {
        this.iView = iView;
    }

    @Override
    public void onDetach() {

    }
}
