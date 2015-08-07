package com.znys.controller.implementation;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.znys.model.Constant;
import com.znys.model.service.SoundEffectPlayer;
import com.znys.model.service.WebHelper;
import com.znys.view.CalendarView;

import com.znys.R;
import com.znys.controller.CalendarController;
import com.znys.view.widget.CustomAlertDialog;

import java.io.IOException;
import java.lang.annotation.Target;

/**
 * Created by mushroom on 2015/6/8.
 */
public class CalendarActivity extends BaseActivity implements CalendarController {

    private CalendarView calendarView;
    private WebHelper webHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_calendar);

        calendarView = (CalendarView) findViewById(R.id.layout_calendar);
        calendarView.initView(CalendarActivity.this);

        soundEffectPlayer = new SoundEffectPlayer(this);
        soundEffectPlayer.load(R.raw.flip);
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

    @Override
    public void playSoundEffect() {
        soundEffectPlayer.play(R.raw.flip);
    }

    @Override
    public void onCellItemClick(View view) {
        CustomAlertDialog dialog = new CustomAlertDialog(this, R.layout.control_dialog_prize_lock);
    }

    @Override
    public void connectCloud() {
        new Thread() {
            public void run() {
                webHelper = new WebHelper();
                String jsonString =
                        "{" +
                                "   \"YHNC\" : \"智能牙刷\"," +
                                "   \"YHTX\" : \"http://img00.hc360.com/home-a/201401/201401061052499011.jpg\"," +
                                "   \"YYXXS\" : 23," +
                                "   \"ZGXHCS\" : 0," +
                                "   \"YHNL\" : 4," +
                                "   \"YHXB\" : \"男\"," +
                                "   \"YHDJ\" : 3" +
                        "}";
                try {
                    //webHelper.httpClientSendGet(Constant.WEB_URL);
                    String result = webHelper.httpClientSendPost(Constant.WEB_URL, jsonString);
                    Log.i("Server_debug", "The response is : " + result);
                }
                catch (IOException ioexception) {
                    System.out.println("服务端那边出事了。");
                    ioexception.printStackTrace();
                }
                catch (Exception e) {
                    Log.i("weiwei-debug", "catch the exception!");
                    e.printStackTrace();
                }
            }
        }.start();
    }
}
