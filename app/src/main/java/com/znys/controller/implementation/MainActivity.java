package com.znys.controller.implementation;

import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.znys.model.service.SoundEffectPlayer;
import com.znys.view.widget.CabinetGridView;

import com.znys.R;
import com.znys.controller.MainController;
import com.znys.model.Constant;
import com.znys.model.service.BackgroundSynchronizationService;
import com.znys.view.widget.CustomAlertDialog;
import com.znys.view.MainView;
import com.znys.view.widget.SynchronizationDialog;

@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
public class MainActivity extends BaseActivity implements MainController {

    private boolean hasConnectedDevice;
    private MainView mainView;
    private ProgressDialog progressDialog;
    private SynchronizationDialog synchronizationDialog;
    private SyncStateBroadcastReceiver syncStateBroadcastReceiver;
    private long lastTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_main);

        mainView = (MainView) findViewById(R.id.layout_main);
        mainView.initView(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            hasConnectedDevice = false;
            syncStateBroadcastReceiver = new SyncStateBroadcastReceiver();
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(Constant.ACTION_SYNC_OK);
            intentFilter.addAction(Constant.ACTION_FIND_DEVICE);
            this.registerReceiver(syncStateBroadcastReceiver, intentFilter);
            registerAlarmEvent();
        }

        soundEffectPlayer = new SoundEffectPlayer(this);
        bgmMediaPlayer = MediaPlayer.create(this, R.raw.background_01);
        soundEffectPlayer.load(R.raw.top_bar_button_click);
        soundEffectPlayer.load(R.raw.alert_dialog);
        soundEffectPlayer.load(R.raw.button_synchronization);
        mainView.post(new Runnable() {
            @Override
            public void run() {
                bgmMediaPlayer.setLooping(true);
                //bgmMediaPlayer.start();
            }
        });
    }

    private void registerAlarmEvent(){
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        Intent intent = new Intent();
        intent.setAction(Constant.ACTION_PERIODIC_ALARM);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, Constant.ALARM_INTENT_CODE, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        //alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), Constant.ALARM_INTERVAL, pendingIntent);
        alarmManager.cancel(pendingIntent);
    }

    @Override
    public void onBackPressed() {
        if ((System.currentTimeMillis() - lastTime) > 2000) {
            Toast.makeText(this, "press one more to exit", Toast.LENGTH_SHORT).show();
            lastTime = System.currentTimeMillis();
        } else {
            finish();
        }
    }

    @Override
    public void synchronize() {
        soundEffectPlayer.play(R.raw.button_synchronization);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            if (hasConnectedDevice) {
                // Todo prompt a dialog to enter a name for tooth
            }
            Intent intent = new Intent(this, BackgroundSynchronizationService.class);
            startService(intent);
            synchronizationDialog = new SynchronizationDialog(this);
        } else {
            Toast.makeText(this, "系统版本过低，请使用安卓4.3或以上的手机", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void checkCalendar() {
        switchActivity(CalendarActivity.class);
    }

    @Override
    public void checkKnowledge() {
        switchActivity(KnowledgeActivity.class);
    }

    @Override
    public void configureApp() {
        switchActivity(SettingsActivity.class);
    }

    @Override
    public void switchActivity(Class c) {
        soundEffectPlayer.play(R.raw.top_bar_button_click);
        Intent intent = new Intent(this, c);
        startActivity(intent);
    }

    @Override
    public void onPrizeButtonClick(View view) {
        final CabinetGridView cabinetGridView = (CabinetGridView) view;
        soundEffectPlayer.play(R.raw.alert_dialog);
        final CustomAlertDialog dialog;
        if ((Integer) (view.getTag()) == 0 && cabinetGridView.getState() == 0) {
            dialog = new CustomAlertDialog(this, R.layout.control_dialog_prize_exchanged);
            dialog.setContentImage(R.id.control_dialog_image_prize, R.drawable.prize_duck);
            dialog.setOnClickListener(R.id.control_dialog_image_prize, new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });
        } else if ((Integer) (view.getTag()) == 1 && cabinetGridView.getState() == 0) {
            dialog = new CustomAlertDialog(this, R.layout.control_dialog_prize_exchanged);
            dialog.setContentImage(R.id.control_dialog_image_prize, R.drawable.prize_bear);
            dialog.setOnClickListener(R.id.control_dialog_image_prize, new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });
        } else if ((Integer) (view.getTag()) == 2 && cabinetGridView.getState() == 0) {
            dialog = new CustomAlertDialog(this, R.layout.control_dialog_prize_exchanged);
            dialog.setContentImage(R.id.control_dialog_image_prize, R.drawable.prize_dragon);
            dialog.setOnClickListener(R.id.control_dialog_image_prize, new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });
        } else if ((Integer) (view.getTag()) == 3 && cabinetGridView.getState() == 0) {
            dialog = new CustomAlertDialog(this, R.layout.control_dialog_prize_exchanged);
            dialog.setContentImage(R.id.control_dialog_image_prize, R.drawable.prize_car);
            dialog.setOnClickListener(R.id.control_dialog_image_prize, new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });
        } else if ((Integer) (view.getTag()) == 4 && cabinetGridView.getState() == 1) {
            dialog = new CustomAlertDialog(this, R.layout.control_dialog_prize_available);
            dialog.setContentImage(R.id.control_dialog_image_prize, R.drawable.prize_ball);
            dialog.setOnClickListener(R.id.control_dialog_button_exchange, new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(MainActivity.this, "success", Toast.LENGTH_SHORT).show();
                    cabinetGridView.setState(2);
                    dialog.dismiss();
                }
            });
        } else if ((Integer) (view.getTag()) == 4) {
            dialog = new CustomAlertDialog(this, R.layout.control_dialog_prize_exchanged);
            dialog.setContentImage(R.id.control_dialog_image_prize, R.drawable.prize_ball);
            dialog.setOnClickListener(R.id.control_dialog_image_prize, new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });
        }
    }

    @Override
    public void releaseResource() {
        super.releaseResource();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            unregisterReceiver(syncStateBroadcastReceiver);
        }
        bgmMediaPlayer.stop();

        stopService(new Intent(this, BackgroundSynchronizationService.class));
    }

    private class SyncStateBroadcastReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().equals(Constant.ACTION_FIND_DEVICE)) {
                if (!intent.getBooleanExtra("FIND_DEVICE", false)) {
                    if (synchronizationDialog != null) {
                        showShortToast(getString(R.string.device_not_found));
                        synchronizationDialog.dismiss();
                        synchronizationDialog = null;
                    }
                } else {
                    hasConnectedDevice = true;
                    showShortToast("设备连接成功可以进行调试");
                    synchronizationDialog.dismiss();
                    synchronizationDialog = null;
                }
            }else{

            }
        }
    }

}
