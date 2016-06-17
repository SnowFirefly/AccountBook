package com.guangzhou.weiwong.accountbook.mvp.presenter;

import android.util.Log;

import com.guangzhou.weiwong.accountbook.mvp.model.IDBModel;
import com.guangzhou.weiwong.accountbook.mvp.model.IDownloadModel;
import com.guangzhou.weiwong.accountbook.mvp.model.IUploadModel;
import com.guangzhou.weiwong.accountbook.mvp.model.Network;
import com.guangzhou.weiwong.accountbook.mvp.model.Result.Result;
import com.guangzhou.weiwong.accountbook.mvp.view.IView;
import com.guangzhou.weiwong.accountbook.utils.ApiException;
import com.guangzhou.weiwong.accountbook.utils.ErrorHandler;
import com.guangzhou.weiwong.accountbook.utils.MyLog;
import com.wong.greendao.TableRecordPersonal;

import java.util.Date;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 *
 * Created by Tower on 2016/4/20.
 */
public class MainPresenter implements IMainPresenter {
    private final String TAG = getClass().getName();
    private IView iView;
    private IUploadModel iUploadModel;
    private IDownloadModel iDownloadModel;
    private IDBModel idbModel;

    @Override
    public void onAttach(IView iView) {
        this.iView = iView;
    }

    @Override
    public void onDetach() {

    }

    public MainPresenter(IDBModel idbModel, IUploadModel iUploadModel, IDownloadModel iDownloadModel) {
        this.idbModel = idbModel;
        this.iUploadModel = iUploadModel;
        this.iDownloadModel = iDownloadModel;
        idbModel.init(IDBModel.TABLE_PERSONAL);
    }

    @Override
    public void getCurWeekData(Date mon, Date sun) {
        MyLog.i(this, "getCurWeekData(" + mon + ", " + sun + ")");
        Observable<Result> observable = iDownloadModel.getCurWeekData(mon, sun);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Result>() {
                    @Override
                    public void onCompleted() {
                        MyLog.d(TAG, "onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        MyLog.d(TAG, "onError():" + e.getMessage());
                        ApiException apiException = ErrorHandler.handle(e);
                        MyLog.i(TAG, "apiException: " + apiException);
                        MyLog.i(TAG, "apiException.code: " + apiException.getCode());
                        MyLog.i(TAG, "apiException.msg: " + apiException.getMsg());
                        iView.onError(apiException.getCode() + "," + apiException.getMsg());
                    }

                    @Override
                    public void onNext(Result result) {
                        MyLog.d(TAG, "onNext: " + result.getAck());
                        iView.onLoadResult(IView.CUR_WEEK_DATA, result);
                    }
                });

    }

    @Override
    public void getGroupData() {
        MyLog.d(this, "getGroupData");
    }

    @Override
    public void getLocalData(Date mon, Date sun) {
        MyLog.i(this, "getLocalData(" + mon + ", " + sun + ")");
        idbModel.addData(new TableRecordPersonal(null, 0, "detail", 11, new Date(),
                0L, "category_name", 0L, "buyer", 0, 0, new Date()));
    }
}
