package com.guangzhou.weiwong.accountbook.utils;

import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import static org.junit.Assert.*;

/**
 *
 * Created by Tower on 2016/7/2.
 */
public class TimeUtilTest {

    @Test
    public void testGetMondayPlus() throws Exception {
        int expected = -5;
        int actual;
        Calendar calendar = Calendar.getInstance();
        // 获得今天是一周的第几天，星期天是第一天，星期一是第二天......并减一
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        if (dayOfWeek == 0) {
            actual = -6;
        } else if (dayOfWeek == 1) {
            actual = 0;
        } else {
            actual = 1 - dayOfWeek;
        }
        assertEquals(expected, actual);
    }

    @Test
    public void testGetWeekDay() throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String expected = "2016-07-01", actual;
        actual = sdf.format(TimeUtil.getWeekDay(5));
        assertEquals(expected, actual);
    }

    @Test
    public void testGetYear() throws Exception {

    }

    @Test
    public void testGetMonth() throws Exception {

    }
}