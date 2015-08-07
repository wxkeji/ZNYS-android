package com.znys.controller.implementation;

import android.os.Bundle;

import com.znys.view.FeedbackView;

import com.znys.R;
import com.znys.controller.FeedbackController;

/**
 * Created by mushroom on 2015/7/11.
 */
public class FeedbackActivity extends BaseActivity implements FeedbackController {

    private FeedbackView feedbackView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_feedback);
        feedbackView = (FeedbackView) findViewById(R.id.layout_feedback);
        feedbackView.initView(this);
    }

    @Override
    public void back() {
        this.finish();
    }

    @Override
    public void submit() {
        this.finish();
    }
}
