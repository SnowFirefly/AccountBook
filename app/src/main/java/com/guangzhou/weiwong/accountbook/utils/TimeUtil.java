package com.guangzhou.weiwong.accountbook.utils;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Created by Tower on 2016/6/18.
 */
public class TimeUtil {
    public static final int BEGIN_YEAR = 2010;
    public static List<String> monthStrList, monthList, yearStrList, yearList;
    public final static String[] months = new String[]{"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug",
            "Sep", "Oct", "Nov", "Dec",};

    public static List<String> getMonthStr() {
        monthStrList = new ArrayList<>();
        monthList = new ArrayList<>();
        int curYear = getYear();
        int curMonth = getMonth();
        for (int i = curYear; i >= TimeUtil.BEGIN_YEAR; i--) {
            if (i == curYear) {         // 今年
                for (int j = curMonth; j > 0; j--) {
                    monthStrList.add(i + " 年 " + j + " 月 ");
                    monthList.add(i + "-" + j + "-1");
                }
            } else {                    // 往年
                for (int j = 12; j > 0; j--) {
                    monthStrList.add(i + " 年 " + j + " 月 ");
                    monthList.add(i + "-" + j + "-1");
                }
            }
        }
        return monthStrList;
    }

    public static List<String> getMonthList() {
        if (monthList == null) {
            getMonthStr();
        }
        return monthList;
    }

    public static List<String> getYearStrList() {
        yearStrList = new ArrayList<>();
        yearList = new ArrayList<>();
        int curYear = getYear();
        for (int i = curYear; i >= TimeUtil.BEGIN_YEAR; i--) {
            yearStrList.add(" " + i + " 年");
            yearList.add(String.valueOf(i));
        }
        return yearStrList;
    }

    public static List<String> getYearList() {
        if (yearList == null) {
            getYearStrList();
        }
        return yearList;
    }
    public static int getMondayPlus() {
        Calendar calendar = Calendar.getInstance();
        // 获得今天是一周的第几天，星期天是第一天，星期一是第二天......并减一
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        if (dayOfWeek == 0) {
            return -6;
        } else if (dayOfWeek == 1) {
            return 0;
        } else {
            return 1 - dayOfWeek;
        }
    }

    public static Date getWeekDay(int weekDay) {
        int mondayPlus = getMondayPlus();
        GregorianCalendar currentDate = new GregorianCalendar();
        currentDate.add(GregorianCalendar.DATE, mondayPlus + weekDay - 1);
        currentDate.set(Calendar.HOUR_OF_DAY, 0);
        currentDate.set(Calendar.MINUTE, 0);
        currentDate.set(Calendar.SECOND, 0);
        return currentDate.getTime();
    }

    public static Date getBeginOfDay(Date date) {
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }

    public static Date getEndOfDay(Date date) {
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        return calendar.getTime();
    }

    public static Date strToDate(String strDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        ParsePosition pos = new ParsePosition(0);
        Date strtodate = formatter.parse(strDate, pos);
        return strtodate;
    }

    public static int getYear() {
        return Calendar.getInstance().get(Calendar.YEAR);
    }

    public static int getMonth() {
        return Calendar.getInstance().get(Calendar.MONTH) + 1;
    }

    public static int getDay(Date date) {
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    public static String getDayOfWeek(Date date) {
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        // 获得今天是一周的第几天，星期天是第一天，星期一是第二天......
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        switch (dayOfWeek) {
            case 1: return "日";
            case 2: return "一";
            case 3: return "二";
            case 4: return "三";
            case 5: return "四";
            case 6: return "五";
            case 7: return "六";
            default:    return " ";
        }
    }

    public static Date getFirstDayOfMonth(int year, int month) {
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DATE, 1);      // 设为当前月的1号
        return calendar.getTime();
    }

    public static String getTwoDay(String to, String from) {
        SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy/MM/dd");
        long day = 0;
        try {
            Date end = myFormatter.parse(to);
            Date begin = myFormatter.parse(from);
            day = (end.getTime() - begin.getTime()) / (24 * 60 * 60 * 1000) + 1;
        } catch (Exception e) {
            return "";
        }
        return day + "";
    }
}
