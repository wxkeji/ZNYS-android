package com.znys.controller.implementation;

import android.content.Intent;
import android.os.Bundle;

import com.znys.controller.AnalysisResultController;
import com.znys.model.service.SoundEffectPlayer;
import com.znys.view.AnalysisResultView;

import com.znys.R;

/**
 * Created by mushroom on 2015/6/8.
 */
public class AnalysisResultActivity extends BaseActivity implements AnalysisResultController {

    private AnalysisResultView analysisResultView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_analysis_result);

        analysisResultView = (AnalysisResultView) findViewById(R.id.layout_analysis_result);

        analysisResultView.initView(this);

        soundEffectPlayer = new SoundEffectPlayer(this);
        soundEffectPlayer.load(R.raw.top_bar_button_click);
        soundEffectPlayer.load(R.raw.button_synchronization);
        soundEffectPlayer.load(R.raw.good);
        soundEffectPlayer.load(R.raw.bad);
        soundEffectPlayer.load(R.raw.star_appear);
    }


    @Override
    public void goHome() {
        soundEffectPlayer.play(R.raw.button_synchronization);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        this.finish();
    }

    @Override
    public void checkCalendar() {
        soundEffectPlayer.play(R.raw.top_bar_button_click);
        switchActivity(CalendarActivity.class);
    }

    @Override
    public void checkKnowledge() {
        soundEffectPlayer.play(R.raw.top_bar_button_click);
        switchActivity(KnowledgeActivity.class);
    }

    @Override
    public void switchActivity(Class c) {
        soundEffectPlayer.play(R.raw.top_bar_button_click);
        Intent intent = new Intent(this, c);
        startActivity(intent);
    }

    @Override
    public void playSoundEffect(int resourceId) {
        soundEffectPlayer.play(resourceId);
    }

    @Override
    public void onBackPressed() {
        soundEffectPlayer.play(R.raw.button_synchronization);
        super.onBackPressed();
    }

}
