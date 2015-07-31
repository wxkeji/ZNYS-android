package com.znys.view;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.znys.controller.MainController;
import com.znys.controller.implementation.AnalysisResultActivity;
import com.znys.view.widget.CabinetViewPager;

import com.znys.R;

public class MainView extends LinearLayout {

    private ImageButton synchronizationButton, knowledgeButton, calendarButton, settingsButton;
    private TextView starNumberTextView;
    private CabinetViewPager cabinetViewPager;

    public MainView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void initView(final MainController listener) {
        synchronizationButton = (ImageButton) findViewById(R.id.main_button_synchronization);
        knowledgeButton = (ImageButton) findViewById(R.id.main_button_knowledge);
        calendarButton = (ImageButton) findViewById(R.id.main_button_calendar);
        settingsButton = (ImageButton) findViewById(R.id.main_button_settings);
        starNumberTextView = (TextView) findViewById(R.id.main_text_star_number);
        cabinetViewPager = (CabinetViewPager) findViewById(R.id.main_cabinet);
        cabinetViewPager.init(listener);

        synchronizationButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.synchronize();
            }
        });
        synchronizationButton.setLongClickable(true);
        synchronizationButton.setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                listener.switchActivity(AnalysisResultActivity.class);
                return true;
            }
        });
        settingsButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.configureApp();
            }
        });
        knowledgeButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.checkKnowledge();
            }
        });
        calendarButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.checkCalendar();
            }
        });

        Typeface typeFace = Typeface.createFromAsset(getContext().getAssets(), "fonts/Arial.ttf");
        starNumberTextView.setTypeface(typeFace);
    }

    public void setStarNumber(int number) {
        starNumberTextView.setText("X  " + number);
    }
}
