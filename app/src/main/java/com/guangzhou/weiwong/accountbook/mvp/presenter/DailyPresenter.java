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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Tower on 2016/6/4.
 */
public class DailyPresenter implements IDailyPresenter {
    private IUploadModel iUploadModel;
    private IDownloadModel iDownloadModel;
    private IDBModel idbModel;
    private IView iView;
    private SimpleDateFormat sdf;

    public DailyPresenter(IUploadModel iUploadModel, IDownloadModel iDownloadModel, IDBModel idbModel) {
        this.iUploadModel = iUploadModel;
        this.idbModel = idbModel;
        this.iDownloadModel = iDownloadModel;
        idbModel.init(IDBModel.TABLE_PERSONAL);
        sdf = new SimpleDateFormat("yyyy/MM/dd");
    }

    @Override
    public void getDailyData(long groupId, Date begin, Date end) {
        getLocal(groupId, begin, end);
    }

    @Override
    public void deleteOneRecord(long tableId) {
        idbModel.deleteData(MyDaoHelperInterface.ID_TABLE, tableId);
    }

    private void getLocal(long groupId, Date begin, Date end) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        MyLog.i(this, "from: " + sdf.format(begin) + ", to: " + sdf.format(end));
        List<TableRecordPersonal> personals = idbModel.getDataByTimeRange(groupId, begin, end);
        List<DailyItem> items = new ArrayList<>();
        for (int i = 0; i < personals.size(); i++) {
            items.add(table2Item(personals.get(i)));
        }
        iView.onLoadResult(IView.DAILY_DATA, items);
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

    public TableRecordPersonal getOneTableRecord(long tableId) {
        return idbModel.getDataById(tableId);
    }

    @Override
    public void onAttach(IView iView) {
        this.iView = iView;
    }

    @Override
    public void onDetach() {

    }
}
