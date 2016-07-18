package com.guangzhou.weiwong.accountbook.mvp.presenter;

import com.guangzhou.weiwong.accountbook.R;
import com.guangzhou.weiwong.accountbook.mvp.model.IDBModel;
import com.guangzhou.weiwong.accountbook.mvp.model.IDownloadModel;
import com.guangzhou.weiwong.accountbook.mvp.model.IUploadModel;
import com.guangzhou.weiwong.accountbook.mvp.model.bean.Record;
import com.guangzhou.weiwong.accountbook.mvp.model.item.DailyItem;
import com.guangzhou.weiwong.accountbook.mvp.view.IView;
import com.guangzhou.weiwong.accountbook.utils.Const;
import com.guangzhou.weiwong.accountbook.utils.MyLog;
import com.guangzhou.weiwong.accountbook.utils.TimeUtil;
import com.wong.greendao.MyDaoHelperInterface;
import com.wong.greendao.TableRecordPersonal;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Tower on 2016/6/4.
 */
public class DailyPresenter implements IDailyPresenter {
    private IUploadModel iUploadModel;
    private IDownloadModel iDownloadModel;
    private IDBModel idbModel;
    private IView iView;
    private SimpleDateFormat sdf;
    private Subscription subscription;

    public DailyPresenter(IUploadModel iUploadModel, IDownloadModel iDownloadModel, IDBModel idbModel) {
        this.iUploadModel = iUploadModel;
        this.idbModel = idbModel;
        this.iDownloadModel = iDownloadModel;
        idbModel.init(IDBModel.TABLE_PERSONAL);
        sdf = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault());
    }

    @Override
    public void getDailyData(long groupId, Date begin, Date end) {
        getLocal(groupId, begin, end);
    }

    @Override
    public void deleteOneRecord(long tableId) {
        idbModel.deleteData(MyDaoHelperInterface.ID_TABLE, tableId);
    }

    private void getLocal(final long groupId, final Date begin, final Date end) {
        MyLog.i(this, "from: " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(begin)
                + ", to: " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(end));
        /*List<TableRecordPersonal> personals = idbModel.getDataByTimeRange(groupId, begin, end);
        List<DailyItem> items = new ArrayList<>();
        for (int i = 0; i < personals.size(); i++) {
            items.add(table2Item(personals.get(i)));
        }
        iView.onLoadResult(IView.DAILY_DATA, items);*/

        Observable<List<TableRecordPersonal>> observable = Observable.create(new Observable.OnSubscribe<List<TableRecordPersonal>>() {
            @Override
            public void call(Subscriber<? super List<TableRecordPersonal>> subscriber) {
                List<TableRecordPersonal> personals = idbModel.getDataByTimeRange(groupId, begin, end);
                subscriber.onNext(personals);
                subscriber.onCompleted();
            }
        });
        subscription = observable.subscribeOn(Schedulers.io())
                .map(new Func1<List<TableRecordPersonal>, List<DailyItem>>() {
                    @Override
                    public List<DailyItem> call(List<TableRecordPersonal> tableRecordPersonals) {
                        List<DailyItem> dailyItems = new ArrayList<>();
                        for (int i = 0; i < tableRecordPersonals.size(); i++) {
                            dailyItems.add(table2Item(tableRecordPersonals.get(i)));
                        }
                        return dailyItems;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<DailyItem>>() {
                    @Override
                    public void call(List<DailyItem> dailyItems) {
                        iView.onLoadResult(IView.DAILY_DATA, dailyItems);
                    }
                });
    }

    private void getCloudData() {

    }

    private <T> DailyItem table2Item(T bean) {
        TableRecordPersonal personal = (TableRecordPersonal) bean;
        int kindIconId = personal.getKind() == Const.MONEY_KIND_SPEND ?
                R.drawable.ic_kind_spend : R.drawable.ic_kind_earn;
        DailyItem item = new DailyItem(R.drawable.icon_head0, personal.getBuyer(),
                personal.getMoney(), kindIconId, TimeUtil.getDayOfWeek(personal.getBuy_time()),
                sdf.format(personal.getBuy_time()), personal.getDetail(),
                personal.getCategory_name(), personal.getId());
        return item;
    }

    public TableRecordPersonal getOneTableRecord(final long tableId) {
        return idbModel.getDataById(tableId);
    }

    public void saveToSDCard() {
        List<TableRecordPersonal> personals = idbModel.getAllData();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        StringBuilder sb = new StringBuilder();
        for (TableRecordPersonal personal : personals) {
            sb.append("id:").append(personal.getId()).append(", ")
                    .append("cloud_id:").append(personal.getCloud_id()).append(", ")
                    .append("detail:").append(personal.getDetail()).append(", ")
                    .append("money:").append(personal.getMoney()).append(", ")
                    .append("buy_time:").append(sdf.format(personal.getBuy_time())).append(", ")
                    .append("category_id:").append(personal.getCategory_id()).append(", ")
                    .append("category_name:").append(personal.getCategory_name()).append(", ")
                    .append("buyer_id:").append(personal.getBuyer_id()).append(", ")
                    .append("buyer:").append(personal.getBuyer()).append(", ")
                    .append("kind:").append(personal.getKind()).append(", ")
                    .append("state").append(personal.getState()).append("\n");
        }
        try {
            File dir = new File(android.os.Environment.getExternalStorageDirectory()
                    + "/AccountBook");
            if (!dir.exists()) {
                dir.mkdir();
            }
            FileOutputStream fos = new FileOutputStream(android.os.Environment.getExternalStorageDirectory()
                    + "/AccountBook/db_backup.txt");
            fos.write(sb.toString().getBytes());
            fos.close();
            iView.onLoadResult(IView.ALL_DATA, "数据已保存在SDcard：" + android.os.Environment.getExternalStorageDirectory()
                    + "/AccountBook/db_backup.txt");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onAttach(IView iView) {
        this.iView = iView;
    }

    @Override
    public void onDetach() {
        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }
}
