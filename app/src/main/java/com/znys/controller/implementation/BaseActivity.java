package com.znys.controller.implementation;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.Toast;

import com.znys.model.service.DBAccessHelper;
import com.znys.model.service.SoundEffectPlayer;

/**
 * Created by ms on 2015/5/4.
 */
public class BaseActivity extends Activity {

    protected DBAccessHelper dbAccessHelper;
    protected MediaPlayer bgmMediaPlayer;
    protected SoundEffectPlayer soundEffectPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbAccessHelper = new DBAccessHelper(this);
    }

    public void switchActivity(Class<?> des) {
        Intent intent = new Intent(this, des);
        startActivity(intent);
    }

    public void showLongToast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_LONG).show();
    }

    public void showShortToast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    public void loadResource() {
    }

    public void releaseResource() {
    }


    @Override
    protected void onDestroy() {
        releaseResource();
        dbAccessHelper.close();
        if (soundEffectPlayer != null) {
            soundEffectPlayer.releaseResource();
        }
        super.onDestroy();
    }
}
