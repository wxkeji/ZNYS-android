package com.znys.controller.implementation;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;

import com.znys.controller.DebugController;
import com.znys.model.service.BLEContentParser;
import com.znys.model.service.BackgroundSynchronizationService;
import com.znys.model.service.TimeHelper;
import com.znys.view.DebugView;

import com.znys.R;

import com.znys.model.Constant;

/**
 * Created by mushroom on 2015/7/26.
 */
public class DebugActivity extends BaseActivity implements DebugController {

    private DebugView debugView;
    private int ledState = 0;
    private BLEMessageReceiver bleMessageReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_debug_bluetooth);
        debugView = (DebugView) findViewById(R.id.layout_debug);
        debugView.initView(this);
        bleMessageReceiver = new BLEMessageReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Constant.ACTION_GET_BLE_BATTERY);
        intentFilter.addAction(Constant.ACTION_GET_BLE_RTC);
        intentFilter.addAction(Constant.ACTION_GET_BLE_QUATERNION);
        registerReceiver(bleMessageReceiver, intentFilter);
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(bleMessageReceiver);
        super.onDestroy();
    }

    @Override
    public void back() {
        this.finish();
    }

    @Override
    public void setLed(int index, int state) {
        if (state == 1) {
            ledState |= state << index;
        } else {
            ledState &= ((~(1 << index)) & 0xff);
        }
        sendMessage(Constant.ACTION_SET_BLE_LED, new byte[]{(byte) (ledState & 0xff)});
    }

    @Override
    public void getRTC() {
        sendMessage(Constant.ACTION_GET_BLE_RTC, null);
    }

    @Override
    public void setRTC(long millisecond) {
        long rtc = millisecond * 1000 / 625;
        sendMessage(Constant.ACTION_SET_BLE_LED, new byte[]{(byte) (rtc >> 24 & 0xff), (byte) (rtc >> 16 & 0xff), (byte) (rtc >> 8 & 0xff), (byte) (rtc & 0xff)});
    }

    @Override
    public void getBattery() {
        sendMessage(Constant.ACTION_GET_BLE_BATTERY, null);
    }

    @Override
    public void setBuzzer(int state) {
        sendMessage(Constant.ACTION_SET_BLE_BUZZER, new byte[]{(byte) (0xff & state)});
    }

    @Override
    public void getQuaternion() {
        sendMessage(Constant.ACTION_GET_BLE_QUATERNION, null);
    }

    private void sendMessage(String action, byte[] data) {
        Intent intent = new Intent(this, BackgroundSynchronizationService.class);
        intent.setAction(action);
        intent.putExtra("data", data);
        startService(intent);
    }

    private class BLEMessageReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(Constant.ACTION_GET_BLE_BATTERY)) {
                debugView.setBattery((int) intent.getByteArrayExtra("data")[0]);
            } else if (intent.getAction().equals(Constant.ACTION_GET_BLE_RTC)) {
                long result = 0;
                byte[] data = intent.getByteArrayExtra("data");
                result = BLEContentParser.parseRTC(data);
                debugView.setRTC(TimeHelper.fromRTCToMillisecond(result));
            } else if (intent.getAction().equals(Constant.ACTION_GET_BLE_QUATERNION)) {
                byte[] data = intent.getByteArrayExtra("data");
                BLEContentParser.parseQuaternion(data);
                Log.i("weiwei_debug", "Reading quaternion!");
            }
        }
    }

}
