package com.znys.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.znys.R;
import com.znys.controller.SettingsController;

/**
 * Created by mushroom on 2015/7/8.
 */
public class SettingsView extends LinearLayout {

    private ImageButton topBarBackButton;
    private TextView topBarRightButton;
    private RelativeLayout feedbackButton;
    private RelativeLayout debugButton;

    public SettingsView(Context context) {
        super(context);
    }

    public SettingsView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void initView(final SettingsController settingsController) {
        topBarBackButton = (ImageButton) findViewById(R.id.top_bar_button_left);
        topBarRightButton = (TextView) findViewById(R.id.top_bar_button_right);
        feedbackButton = (RelativeLayout) findViewById(R.id.settings_button_feedback);
        debugButton = (RelativeLayout) findViewById(R.id.settings_button_debug);
        topBarRightButton.setVisibility(GONE);
        topBarBackButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                settingsController.back();
            }
        });
        feedbackButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                settingsController.contact();
            }
        });
        debugButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                settingsController.debug();
            }
        });
    }
}
