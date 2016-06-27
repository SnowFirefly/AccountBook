package com.guangzhou.weiwong.accountbook.mvp.model.bean;

/**
 * Created by Tower on 2016/6/25.
 */
public class BusData {
    private int dataType;
    private float[] data;

    public BusData(int dataType, float[] data) {
        this.dataType = dataType;
        this.data = data;
    }

    public int getDataType() {
        return dataType;
    }

    public void setDataType(int dataType) {
        this.dataType = dataType;
    }

    public float[] getData() {
        return data;
    }

    public void setData(float[] data) {
        this.data = data;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < data.length; i++) {
            sb.append(i + ": " + data[i] + ", ");
        }
        return "BusData: dataType = " + dataType + "; data[" + sb.toString() + "]";
    }
}
