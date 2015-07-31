package com.znys.model.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.znys.model.Constant;

/**
 * Created by M on 2015/6/28.
 * this class will be woken up every hour by alarm manager
 */
public class AlarmBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getAction().equals(Constant.ACTION_PERIODIC_ALARM)){
            Intent serviceIntent = new Intent(context, BackgroundSynchronizationService.class);
            context.startService(serviceIntent);
        }
    }
}
