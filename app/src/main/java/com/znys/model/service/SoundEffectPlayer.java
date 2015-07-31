package com.znys.model.service;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

import java.util.HashMap;

/**
 * Created by mushroom on 2015/6/6.
 */
public class SoundEffectPlayer implements SoundPool.OnLoadCompleteListener {

    private HashMap<Integer, Integer> soundMap, stateMap;
    private SoundPool soundPool;
    private Context context;

    public SoundEffectPlayer(Context context) {
        this.context = context;
        soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 1);
        soundMap = new HashMap<>(10);
        stateMap = new HashMap<>(10);
        soundPool.setOnLoadCompleteListener(this);
    }

    public void load(int resourceId) {
        soundMap.put(resourceId, soundPool.load(context, resourceId, 1));
        stateMap.put(soundMap.get(resourceId), 0);
    }

    public void play(int resourceId) {
        if (soundMap.get(resourceId) == null) {
            load(resourceId);
        } else if (stateMap.get(soundMap.get(resourceId)) == 1) {
            soundPool.play(soundMap.get(resourceId), 1.0f, 0.5f, 1, 0, 1.0f);
        }
    }

    /**
     * this function will be call at the end of activity automatically
     */
    public void releaseResource() {
        for (Integer id : soundMap.values()) {
            soundPool.unload(id);
        }
        soundPool.release();
        context = null;
    }

    @Override
    public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
        stateMap.put(sampleId, 1);
    }
}
