package com.guangzhou.weiwong.accountbook.mvp.view;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.guangzhou.weiwong.accountbook.R;

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
    PieChartData data;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chart_pie, container, false);
        ButterKnife.bind(this, view);
        data = generatePieChartData();
        pieChartView.setPieChartData(data);
        /** Note: Chart is within ViewPager so enable container scroll mode. **/
        pieChartView.setContainerScrollEnabled(true, ContainerScrollType.HORIZONTAL);
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                prepareDataAnimation();
                pieChartView.startDataAnimation();
            }
        }, 1000);
        return view;
    }

    private PieChartData generatePieChartData() {
        int numValues = 6;

        List<SliceValue> values = new ArrayList<SliceValue>();
        for (int i = 0; i < numValues; ++i) {
            values.add(new SliceValue((float) Math.random() * 30 + 15, ChartUtils.pickColor()));
        }

        PieChartData data = new PieChartData(values);
        data.setHasLabels(true);
        data.setSlicesSpacing(24);
        return data;
    }

    private void prepareDataAnimation() {
        for (SliceValue value : data.getValues()) {
            value.setTarget((float) Math.random() * 30 + 15);
        }
    }
}
