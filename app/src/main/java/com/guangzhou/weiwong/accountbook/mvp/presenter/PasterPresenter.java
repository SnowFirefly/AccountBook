package com.guangzhou.weiwong.accountbook.mvp.presenter;

import com.guangzhou.weiwong.accountbook.mvp.model.IDBModel;
import com.guangzhou.weiwong.accountbook.mvp.model.IDownloadModel;
import com.guangzhou.weiwong.accountbook.mvp.model.IUploadModel;
import com.guangzhou.weiwong.accountbook.mvp.model.bean.Record;
import com.guangzhou.weiwong.accountbook.mvp.view.IView;
import com.guangzhou.weiwong.accountbook.utils.MyLog;
import com.guangzhou.weiwong.accountbook.utils.TimeUtil;
import com.wong.greendao.TableRecordPersonal;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Tower on 2016/6/4.
 */
public class PasterPresenter implements IPasterPresenter {
    private IView iView;
    private IUploadModel iUploadModel;
    private IDownloadModel iDownloadModel;
    private IDBModel idbModel;

    public PasterPresenter(IUploadModel iUploadModel, IDownloadModel iDownloadModel, IDBModel idbModel) {
        this.iUploadModel = iUploadModel;
        this.iDownloadModel = iDownloadModel;
        this.idbModel = idbModel;
        idbModel.init(IDBModel.TABLE_PERSONAL);
    }

    @Override
    public void getOneDayData(Date date) {
        List<Record> records = getLocal(date);
        iView.onLoadResult(IView.ONE_DAY_DATA, records);

    }

    @Override
    public void setOneDayData(Record record) {
        MyLog.i(this, "save time: " + record.getDate());
        TableRecordPersonal personal = record2Personal(record);
        saveLocal(personal);
        uploadToCloud(record);
    }

    private List<Record> getLocal(Date date) {
        MyLog.i(this, "from " + TimeUtil.getBeginOfDay(date) + " to " + TimeUtil.getEndOfDay(date));
        List<TableRecordPersonal> personals = idbModel.getDataByTimeRange(0, TimeUtil.getBeginOfDay(date), TimeUtil.getEndOfDay(date));
        List<Record> records = new ArrayList<>();
        for (TableRecordPersonal personal: personals) {
            records.add(table2Record(personal));
        }
        return records;
    }

    private void uploadToCloud(Record record) {

    }

    private <T> void saveLocal(T bean) {
        MyLog.d(this, "saveLocal");
        idbModel.addData(bean);
    }

    private <T> Record table2Record(T bean) {
        TableRecordPersonal personal = (TableRecordPersonal) bean;
        Record record = new Record(personal.getId(), personal.getBuy_time(), "", 0, personal.getBuyer(), personal.getBuyer_id(),
                personal.getCategory_name(),personal.getCategory_id(), personal.getKind(),
                personal.getMoney(), personal.getDetail(), personal.getState());
        return record;
    }

    private TableRecordPersonal record2Personal(Record record) {
        TableRecordPersonal recordPersonal = new TableRecordPersonal(record.getId(), -1, record.getDetail(),
                record.getMoney(), record.getDate(),  record.getCate(), record.getCateName(),
                record.getUserId(), record.getUserName(), record.getKind(), record.getState(), new Date());
        if (record.getId() == -1) {
            recordPersonal.setId(null);
        }
        return recordPersonal;
    }

    @Override
    public void onAttach(IView iView) {
        this.iView = iView;
    }

    @Override
    public void onDetach() {

    }
}
