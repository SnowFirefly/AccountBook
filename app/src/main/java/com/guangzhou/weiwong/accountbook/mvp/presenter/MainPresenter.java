package com.guangzhou.weiwong.accountbook.mvp.presenter;

import com.guangzhou.weiwong.accountbook.mvp.model.IDBModel;
import com.guangzhou.weiwong.accountbook.mvp.model.IDownloadModel;
import com.guangzhou.weiwong.accountbook.mvp.model.IUploadModel;
import com.guangzhou.weiwong.accountbook.mvp.model.Result.Result;
import com.guangzhou.weiwong.accountbook.mvp.model.bean.BusData;
import com.guangzhou.weiwong.accountbook.mvp.view.IView;
import com.guangzhou.weiwong.accountbook.utils.ApiException;
import com.guangzhou.weiwong.accountbook.utils.Const;
import com.guangzhou.weiwong.accountbook.utils.ErrorHandler;
import com.guangzhou.weiwong.accountbook.utils.MyLog;
import com.guangzhou.weiwong.accountbook.utils.TimeUtil;
import com.wong.greendao.TableRecordPersonal;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
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
    private Subscription subscription;

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
                        MyLog.i(TAG, "apiException: " + apiException.getMessage());
                        MyLog.i(TAG, "apiException.code: " + apiException.getCode());
                        MyLog.i(TAG, "apiException.msg: " + apiException.getMsg());
//                        iView.onError(apiException.getCode() + "," + apiException.getMsg());
                        String message = apiException.getMessage();
                        iView.onError(message.substring(message.indexOf(":") + 1));
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
//        List<String> weekStrs = new ArrayList<>();
//        List<TableRecordPersonal> personals = new ArrayList<>();
//        float spend = 0, earn = 0;
//        for (int i = 1; i <= 7; i++) {
//            personals = idbModel.getDataByTimeRange(0, TimeUtil.getWeekDay(i), TimeUtil.getWeekDay(i + 1));
//            spend = 0;  earn = 0;
//            for (TableRecordPersonal personal : personals) {
//                if (personal.getKind() == Const.MONEY_KIND_SPEND) {
//                    spend += personal.getMoney();
//                } else if (personal.getKind() == Const.MONEY_KIND_EARN) {
//                    earn += personal.getMoney();
//                }
//            }
//            if (spend != 0 || earn != 0) {
//                weekStrs.add(i - 1, "\n支出 " + spend + "元" + "\n收入 " + earn + "元");
//            } else {
//                weekStrs.add(i - 1, " ");
//            }
//        }
//        iView.onLoadResult(IView.CUR_WEEK_DATA, weekStrs);

        Observable<List<String>> observable = Observable.create(new Observable.OnSubscribe<List<String>>() {
            @Override
            public void call(Subscriber<? super List<String>> subscriber) {
                List<String> weekStrs = new ArrayList<>();
                float spend = 0, earn = 0;
                for (int i = 1; i <= 7; i++) {
                    List<TableRecordPersonal> personals = idbModel.getDataByTimeRange(0, TimeUtil.getWeekDay(i), TimeUtil.getWeekDay(i + 1));
                    spend = 0;  earn = 0;
                    for (TableRecordPersonal personal : personals) {
                        if (personal.getKind() == Const.MONEY_KIND_SPEND) {
                            spend += personal.getMoney();
                        } else if (personal.getKind() == Const.MONEY_KIND_EARN) {
                            earn += personal.getMoney();
                        }
                    }
                    if (spend != 0 || earn != 0) {
                        weekStrs.add(i - 1, "\n支出 " + spend + "元" + "\n收入 " + earn + "元");
                    } else {
                        weekStrs.add(i - 1, " ");
                    }
                }
                subscriber.onNext(weekStrs);
            }
        });
        subscription = observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<String>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        MyLog.e(this, e.getMessage());
                        MyLog.e(this, e.toString());
                    }

                    @Override
                    public void onNext(List<String> weekStrs) {
                        iView.onLoadResult(IView.CUR_WEEK_DATA, weekStrs);
                    }
                });
    }
}
