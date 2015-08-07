package com.znys.controller.implementation;

import android.content.Intent;
import android.os.Bundle;

import com.znys.controller.KnowledgeController;
import com.znys.model.service.SoundEffectPlayer;
import com.znys.view.KnowledgeView;

import com.znys.R;

/**
 * Created by mushroom on 2015/6/8.
 */
public class KnowledgeActivity extends BaseActivity implements KnowledgeController {

    private KnowledgeView knowledgeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_knowledge);

        knowledgeView = (KnowledgeView) findViewById(R.id.layout_knowledge);
        knowledgeView.init(this);

        soundEffectPlayer = new SoundEffectPlayer(this);
        soundEffectPlayer.load(R.raw.button_synchronization);
    }

    @Override
    public void goHome() {
        soundEffectPlayer.play(R.raw.button_synchronization);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        this.finish();
    }

    @Override
    public void onBackPressed() {
        soundEffectPlayer.play(R.raw.button_synchronization);
        super.onBackPressed();
    }
}
