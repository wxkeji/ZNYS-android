package com.znys.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.znys.controller.AnalysisResultController;
import com.znys.view.widget.ErasibleStarView;

import com.znys.R;

import com.znys.view.widget.VideoSurfaceView;

/**
 * Created by mushroom on 2015/6/8.
 */
public class AnalysisResultView extends LinearLayout {

    private VideoSurfaceView videoSurfaceView;
    private ErasibleStarView erasibleStarView;
    private ImageButton homeButton, knowledgeButton, calendarButton;
    private ImageView handImageView;
    private AnalysisResultController listener;
    private boolean state;

    public AnalysisResultView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void initView(final AnalysisResultController listener) {
        this.listener = listener;
        handImageView = (ImageView) findViewById(R.id.analysis_result_image_hand);
        homeButton = (ImageButton) findViewById(R.id.topbar_button_home);
        knowledgeButton = (ImageButton) findViewById(R.id.topbar_button_knowledge);
        calendarButton = (ImageButton) findViewById(R.id.topbar_button_calendar);
        videoSurfaceView = (VideoSurfaceView) findViewById(R.id.analysis_result_surface_animation);
        erasibleStarView = (ErasibleStarView) findViewById(R.id.analysis_result_view_star);
        erasibleStarView.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                handImageView.setVisibility(GONE);
                handImageView.clearAnimation();
                return false;
            }
        });
        erasibleStarView.post(new Runnable() {
            @Override
            public void run() {
                erasibleStarView.init();
            }
        });
        homeButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.goHome();
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
        state = true;
        findViewById(R.id.button_switch).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.playSoundEffect(R.raw.star_appear);
                if (state) {
                    erasibleStarView.startAnimation(1);
                    videoSurfaceView.setState(false);
                    state = false;
                    listener.playSoundEffect(R.raw.bad);

                } else {
                    erasibleStarView.startAnimation(5);
                    videoSurfaceView.setState(true);
                    state = true;
                    listener.playSoundEffect(R.raw.good);
                }
            }
        });
        this.post(new Runnable() {
            @Override
            public void run() {
                handImageView.setVisibility(VISIBLE);
                Animation handMoveAnimation = new TranslateAnimation(-100.0f, 100.0f, 0.0f, 0.0f);
                handMoveAnimation.setRepeatCount(-1);
                handMoveAnimation.setDuration(1000);
                handImageView.setAnimation(handMoveAnimation);
                handMoveAnimation.start();
            }
        });

    }
}
