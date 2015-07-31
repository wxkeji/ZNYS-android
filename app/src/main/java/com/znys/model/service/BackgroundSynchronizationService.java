package com.znys.model.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;

import com.znys.model.Constant;
import com.znys.model.entity.DeviceCollection;

/**
 * Created by M on 2015/6/27.
 * this class is to synchronize data with teeth-brush on the background when the data is available
 * it communicate with main activity by sending broadcast
 */
public class BackgroundSynchronizationService extends Service {

    private boolean isRunning;
    private BLEHelper bleHelper;
    private BLEBroadcastReceiver bleBroadcastReceiver;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null && intent.getAction() != null) {
            if (intent.getAction().equals(Constant.ACTION_GET_BLE_BATTERY)) {
                bleHelper.readBattery();
            } else if (intent.getAction().equals(Constant.ACTION_GET_BLE_RTC)) {
                bleHelper.readRTC();
            } else if (intent.getAction().equals(Constant.ACTION_SET_BLE_BUZZER)) {
                bleHelper.writeBuzzer(intent.getByteArrayExtra("data"));
            } else if (intent.getAction().equals(Constant.ACTION_SET_BLE_LED)) {
                bleHelper.writeLed(intent.getByteArrayExtra("data"));
            } else if (intent.getAction().equals(Constant.ACTION_SET_BLE_RTC)) {
                bleHelper.writeRTC(intent.getByteArrayExtra("data"));
            } else if (intent.getAction().equals(Constant.ACTION_GET_BLE_QUATERNION)) {
                bleHelper.readQuaternion();
            }
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        bleBroadcastReceiver = new BLEBroadcastReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Constant.ACTION_SCAN_DEVICE_STATE);
        intentFilter.addAction(Constant.ACTION_BLE_STATE_CHANGE);
        this.registerReceiver(bleBroadcastReceiver, intentFilter);
        isRunning = true;
        bleHelper = new BLEHelper(BackgroundSynchronizationService.this, new DeviceCollection(new DBAccessHelper(BackgroundSynchronizationService.this)));
        bleHelper.turnOnBLE();
    }

    @Override
    public void onDestroy() {
        isRunning = false;
        unregisterReceiver(bleBroadcastReceiver);
        bleHelper.turnOffBLE();
        super.onDestroy();
    }

    private class BLEBroadcastReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            Intent mainIntent = new Intent();
            if(intent.getAction().equals(Constant.ACTION_SCAN_DEVICE_STATE)){
                mainIntent.setAction(Constant.ACTION_FIND_DEVICE);
                if(!intent.getBooleanExtra("STATE", false)){
                    mainIntent.putExtra("FIND_DEVICE", false);
                    sendBroadcast(mainIntent);
                    stopSelf();
                }else{
                    mainIntent.putExtra("FIND_DEVICE", true);
                    sendBroadcast(mainIntent);
                }
            } else if (intent.getAction().equals(Constant.ACTION_BLE_STATE_CHANGE)) {
                if (bleHelper.isBLEOn())
                    bleHelper.turnOnBLE();
            }
        }
    }

}
