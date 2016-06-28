package com.guangzhou.weiwong.accountbook.mvp.view;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.guangzhou.weiwong.accountbook.R;
import com.guangzhou.weiwong.accountbook.mvp.model.bean.BusData;
import com.guangzhou.weiwong.accountbook.utils.BusProvider;
import com.guangzhou.weiwong.accountbook.utils.Const;
import com.guangzhou.weiwong.accountbook.utils.MyLog;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import lecho.lib.hellocharts.gesture.ContainerScrollType;
import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.PieChartView;

/**
 * Created by Tower on 2016/5/12.
 */
public class PieChartFragment extends BaseFragment {
    @Bind(R.id.pieChart) PieChartView pieChartView;
    @Bind(R.id.tv_total) TextView tvTotal;
    private PieChartData pieChartData;
    private float[] cateData = new float[5];
    private float total = 0;
    private int[] sortedIndexs;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chart_pie, container, false);
        ButterKnife.bind(this, view);
        cateData = ((ChartsActivity)getActivity()).getCateData();
        sortedIndexs = sort();
        total = 0;
        for (int i = 0; i < cateData.length; i++) {
            total += cateData[i];
        }
        pieChartData = generatePieChartData(cateData.length);
        pieChartView.setPieChartData(pieChartData);
        pieChartView.setCircleFillRatio(0.8f);
        /** Note: Chart is within ViewPager so enable container scroll mode. **/
        pieChartView.setContainerScrollEnabled(true, ContainerScrollType.HORIZONTAL);
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                prepareDataAnimation();
                pieChartView.startDataAnimation();
            }
        }, 500);
        return view;
    }

    private PieChartData generatePieChartData(int numValues) {
        List<SliceValue> values = new ArrayList<SliceValue>();
        for (int i = 0; i < numValues; ++i) {
            values.add(new SliceValue((float) Math.random() * 30 + 15, ChartUtils.COLORS[sortedIndexs[i]]));
        }
        PieChartData data = new PieChartData(values);
        data.setHasLabels(true);
        data.setHasLabelsOnlyForSelected(false);
        data.setHasLabelsOutside(true);
//        data.setSlicesSpacing(24);
        return data;
    }

    private void prepareDataAnimation() {
        for (int i = 0; i < pieChartData.getValues().size(); i++) {
            SliceValue value = pieChartData.getValues().get(i);
            value.setTarget(cateData[sortedIndexs[i]]).setLabel("¥" + cateData[sortedIndexs[i]] + " (" + format(cateData[sortedIndexs[i]] / total, 2) +"%)");
            value.setColor(ChartUtils.COLORS[sortedIndexs[i]]);
        }
        tvTotal.setText("总金额：¥" + total);
    }

    @Subscribe
    public void receiveData(BusData busData) {
        MyLog.i(this, "receiveData =>> " + busData.toString());
        if (busData.getDataType() == Const.DATA_TYPE_CATE) {
            cateData = busData.getData();
            sortedIndexs = sort();
            total = 0;
            for (int i = 0; i < cateData.length; i++) {
                total += cateData[i];
            }
            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (pieChartData == null) return;
                    prepareDataAnimation();
                    pieChartView.startDataAnimation();
                }
            }, 500);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BusProvider.getBusInstance().register(this);
        MyLog.e(this, "bus: " + BusProvider.getBusInstance());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        BusProvider.getBusInstance().unregister(this);
    }

    // 格式化百分比
    private float format(float value, int num) {
        int temp = (int) (value * 100 * Math.pow(10, num));
        return (float) (temp * 1.0 / Math.pow(10, num));
    }


    // 排序后次序的数组（尽可能避免遮挡）
    private int[] sort() {
        float[] tempData = new float[cateData.length];
        System.arraycopy(cateData, 0, tempData, 0, cateData.length);
        List<Integer> origin = new ArrayList<>();       // 原始次序
        for (int i = 0; i < 5; i++) origin.add(i);
        int[] res = new int[tempData.length];       // 保存结果次序
        int maxIndex, minIndex;
        for (int i = 0; i < tempData.length; i = i + 2) {
            float min = tempData[i], max = min;
            maxIndex = minIndex = i;
            for (int j = i; j < tempData.length; j++) {
                if (tempData[j] <= min) {
                    min = tempData[j];
                    minIndex = j;
                }
                if (tempData[j] >= max) {
                    max = tempData[j];
                    maxIndex = j;
                }
            }
            float temp = tempData[minIndex];
            tempData[minIndex] = tempData[i];
            tempData[i] = temp;
            if ((i + 1) < tempData.length) {
                float temp2 = tempData[maxIndex];
                tempData[maxIndex] = tempData[i + 1];
                tempData[i + 1] = temp2;
            }
        }
        for (int i = 0; i < tempData.length; i++) {
            for (int j = 0; j < origin.size(); j++) {
                if (tempData[i] == cateData[origin.get(j)]) {
                    res[i] = origin.get(j);
                    origin.remove(j);
                    break;
                }
            }
        }
        for (int k = 0; k < res.length; k++) {
            MyLog.i(this, "res: " + res[k]);
        }
        return res;
    }
}
