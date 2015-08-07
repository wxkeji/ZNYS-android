package com.znys.controller.implementation;

import android.os.Bundle;

import com.znys.R;
import com.znys.controller.SettingsController;
import com.znys.view.SettingsView;

/**
 * Created by mushroom on 2015/7/8.
 */
public class SettingsActivity extends BaseActivity implements SettingsController {

    private SettingsView settingsView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_settings);
        settingsView = (SettingsView) findViewById(R.id.layout_settings);
        settingsView.initView(this);
    }

    @Override
    public void onBackPressed() {
        this.finish();
    }

    @Override
    public void back() {
        this.finish();
    }

    @Override
    public void addDevice() {

    }

    @Override
    public void switchDevice() {

    }

    @Override
    public void checkDevice() {

    }

    @Override
    public void checkUser() {

    }

    @Override
    public void switchUser() {

    }

    @Override
    public void addUser() {

    }

    @Override
    public void contact() {
        switchActivity(FeedbackActivity.class);
    }

    @Override
    public void about() {

    }

    @Override
    public void debug() {
        switchActivity(DebugActivity.class);
    }
}
