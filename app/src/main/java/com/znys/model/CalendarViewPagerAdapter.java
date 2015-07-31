package com.znys.model;

import android.content.Context;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.znys.controller.CalendarController;
import com.znys.view.widget.CalendarControl;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by mushroom on 2015/6/9.
 */
public class CalendarViewPagerAdapter extends PagerAdapter {

    private CalendarController calendarController;
    private Context context;
    private Map<Integer, String> titleMap;
    private Calendar firstDateOfFirstMonth;

    public CalendarViewPagerAdapter(Context context) {
        this.context = context;
        titleMap = new HashMap<Integer, String>();
        firstDateOfFirstMonth = Calendar.getInstance();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Calendar calendar = (Calendar) (firstDateOfFirstMonth.clone());
        calendar.add(Calendar.DAY_OF_WEEK, 21 * position);
        CalendarControl calendarControl = new CalendarControl(context);
        calendarControl.setDateDisplay(calendar);
        calendarControl.init(calendarController);
        calendarControl.notifyChanges();
        container.addView(calendarControl, 0);
        titleMap.put(position, calendarControl.getCalendarTitle());
        return calendarControl;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void restoreState(Parcelable arg0, ClassLoader arg1) {
    }

    @Override
    public Parcelable saveState() {
        return null;
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    public void setController(CalendarController calendarController) {
        this.calendarController = calendarController;
    }

    public String getCurrentCalendarTitle(int currentIndex) {
        return titleMap.get(currentIndex);
    }

}
