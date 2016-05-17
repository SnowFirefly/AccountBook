package com.guangzhou.weiwong.accountbook.mvp.presenter;

import android.util.Log;

import com.guangzhou.weiwong.accountbook.mvp.model.NetworkApiService;
import com.guangzhou.weiwong.accountbook.mvp.model.data.RegisterResult;
import com.guangzhou.weiwong.accountbook.mvp.model.data.User;
import com.guangzhou.weiwong.accountbook.mvp.view.IView;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Tower on 2016/4/26.
 */
public class RegisterPresenter implements IRegisterPresenter {
    private final String TAG = getClass().getName();
    private NetworkApiService networkApiService;
    private IView iView;

    public RegisterPresenter(IView iView){
        networkApiService = new NetworkApiService();
        this.iView = iView;
    }

    @Override
    public void register(String user, String email, String password) {
        Log.d(TAG, "register()");
        Observable<RegisterResult> observable = networkApiService.register(user, email, password);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<RegisterResult>() {
                    @Override
                    public void onCompleted() {
                        Log.d(TAG, "onCompleted()");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError():" + e.getMessage());
                    }

                    @Override
                    public void onNext(RegisterResult registerResult) {
                        Log.d(TAG, "onNext()");
                        Log.d(TAG, registerResult.toString());
                        iView.onRegisterResult(registerResult);
                    }
                });
    }

    @Override
    public void onDetach() {

    }
}
