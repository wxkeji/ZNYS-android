package com.znys.model;

import java.util.Calendar;

/**
 * Created by mushroom on 2015/6/9.
 */
public class CalendarGridItem {

    private Integer dayOfMonth;
    private Object data;
    private boolean enabled = true;
    private Calendar date;

    public CalendarGridItem(Integer dom) {
        setDayOfMonth(dom);
    }

    public Integer getDayOfMonth() {
        return dayOfMonth;
    }

    public CalendarGridItem setDayOfMonth(Integer dayOfMonth) {
        this.dayOfMonth = dayOfMonth;
        return this;
    }

    public Object getData() {
        return data;
    }

    public CalendarGridItem setData(Object data) {
        this.data = data;
        return this;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public CalendarGridItem setEnabled(boolean enabled) {
        this.enabled = enabled;
        return this;
    }

    public Calendar getDate() {
        return date;
    }

    public CalendarGridItem setDate(Calendar date) {
        this.date = date;
        return this;
    }

}
