package com.guangzhou.weiwong.accountbook.utils;

import com.squareup.otto.Bus;

/**
 * Created by Tower on 2016/6/23.
 */
public class BusProvider {
    private static class BusHolder {
        public static final Bus bus = new Bus();
    }

    public static Bus getBusInstance() {
        return BusHolder.bus;
    }
}
