package com.znys.model.service;

import java.util.Calendar;

/**
 * Created by mushroom on 2015/7/25.
 */
public class TimeHelper {

    public static long fromRTCToMillisecond(long RTC) {
        return RTC * 625 / 1000;
    }

    public static long fromMillisecondToRTC(long millisecond) {
        return millisecond * 1000 / 625;
    }

    public static long getNowMillisecondWithinThisWeek() {
        Calendar nowCalendar = Calendar.getInstance();
        Calendar startCalendar = (Calendar) nowCalendar.clone();
        return 0;
    }

}
