package com.znys.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.znys.R;
import com.znys.controller.CalendarController;
import com.znys.view.widget.CalendarControlPager;

/**
 * Created by mushroom on 2015/6/9.
 */
public class CalendarView extends LinearLayout {

    private CalendarControlPager calendarControlPager;
    private TextView goalTextView, titleTextView;
    private ImageButton homeButton, arrowLeftButton, arrowRightButton, cloudButton;
    private CalendarController listener;

    public CalendarView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void initView(final CalendarController listener) {
        this.listener = listener;
        calendarControlPager = (CalendarControlPager) findViewById(R.id.calendar_calendar_control);
        goalTextView = (TextView) findViewById(R.id.calendar_text_goal);
        titleTextView = (TextView) findViewById(R.id.calendar_title);
        homeButton = (ImageButton) findViewById(R.id.topbar_button_home);
        arrowLeftButton = (ImageButton) findViewById(R.id.calendar_button_arrow_left);
        arrowRightButton = (ImageButton) findViewById(R.id.calendar_button_arrow_right);
        cloudButton = (ImageButton) findViewById(R.id.topbar_button_cloud);
        calendarControlPager.init(listener);
        homeButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.goHome();
            }
        });
        cloudButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.connectCloud();
            }
        });
        arrowLeftButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                calendarControlPager.setCurrentItem((calendarControlPager.getCurrentItem() - 1) < 0 ? 0 : (calendarControlPager.getCurrentItem() - 1));
            }
        });
        arrowRightButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                calendarControlPager.setCurrentItem(calendarControlPager.getCurrentItem() + 1);
            }
        });
        calendarControlPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    arrowLeftButton.setVisibility(INVISIBLE);
                } else if (position == calendarControlPager.getCount()) {
                    arrowRightButton.setVisibility(INVISIBLE);
                } else {
                    arrowLeftButton.setVisibility(VISIBLE);
                    arrowRightButton.setVisibility(VISIBLE);
                }
                titleTextView.setText(calendarControlPager.getCurrentCalendarTitle());
                listener.playSoundEffect();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        this.post(new Runnable() {
            @Override
            public void run() {
                titleTextView.setText(calendarControlPager.getCurrentCalendarTitle());
                arrowLeftButton.setVisibility(INVISIBLE);
            }
        });
    }

    public void setGoal(String goal) {
        goalTextView.setText(goal);
    }
}
