package com.znys.view.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.Scroller;

import com.znys.controller.CalendarController;

import java.lang.reflect.Field;

import com.znys.model.CalendarViewPagerAdapter;

/**
 * Created by mushroom on 2015/6/9.
 */
public class CalendarControlPager extends ViewPager {

    private CalendarViewPagerAdapter calendarViewPagerAdapter;

    public CalendarControlPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CalendarControlPager(Context context) {
        super(context);
    }

    public void init(CalendarController calendarController) {
        calendarViewPagerAdapter = new CalendarViewPagerAdapter(getContext());
        calendarViewPagerAdapter.setController(calendarController);
        setAdapter(calendarViewPagerAdapter);
        try {
            Field fixedSpeedScroller;
            fixedSpeedScroller = ViewPager.class.getDeclaredField("mScroller");
            fixedSpeedScroller.setAccessible(true);
            FixedSpeedScroller scroller = new FixedSpeedScroller(getContext(), new AccelerateInterpolator());
            fixedSpeedScroller.set(this, scroller);
        } catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException e) {
        }
    }

    /**
     * this method makes viewpager properly wrap it content on the vertical direction
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int maxHeight = 0;
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            child.measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
            int height = child.getMeasuredHeight();
            if (height > maxHeight) maxHeight = height;
        }

        heightMeasureSpec = MeasureSpec.makeMeasureSpec(maxHeight, MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    public int getCount() {
        return calendarViewPagerAdapter.getCount();
    }

    public String getCurrentCalendarTitle() {
        return calendarViewPagerAdapter.getCurrentCalendarTitle(getCurrentItem());
    }

    /**
     * this class fix the scroll bug in viewpager by using reflection to override the private menber
     */
    private class FixedSpeedScroller extends Scroller {

        private int duration = 500;

        public FixedSpeedScroller(Context context, Interpolator interpolator) {
            super(context, interpolator);
        }

        @Override
        public void startScroll(int startX, int startY, int dx, int dy, int systemDuration) {
            super.startScroll(startX, startY, dx, dy, duration);
        }

        @Override
        public void startScroll(int startX, int startY, int dx, int dy) {
            super.startScroll(startX, startY, dx, dy, duration);
        }

        public void setDuration(int duration) {
            this.duration = duration;
        }
    }

}
