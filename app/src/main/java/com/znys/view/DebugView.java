package com.znys.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.znys.controller.DebugController;

import com.znys.R;

/**
 * Created by mushroom on 2015/7/26.
 */
public class DebugView extends LinearLayout {

    private ImageButton topBarBackButton;
    private TextView topBarTitleTextView;
    private TextView topBarRightButton;
    private Switch ledSwitchList[];
    private Switch buzzerSwitch;
    private Button getRTCButton, setRTCButton, getBatteryButton;
    private TextView rtcTextView, batteryTextView;
    private EditText rtcEditText;
    private Button getQuaternion;

    public DebugView(Context context) {
        super(context);
    }

    public DebugView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void initView(final DebugController debugController) {
        ledSwitchList = new Switch[6];
        topBarBackButton = (ImageButton) findViewById(R.id.top_bar_button_left);
        topBarRightButton = (TextView) findViewById(R.id.top_bar_button_right);
        topBarTitleTextView = (TextView) findViewById(R.id.top_bar_title);
        ledSwitchList[0] = (Switch) findViewById(R.id.debug_switch_led_0);
        ledSwitchList[1] = (Switch) findViewById(R.id.debug_switch_led_1);
        ledSwitchList[2] = (Switch) findViewById(R.id.debug_switch_led_2);
        ledSwitchList[3] = (Switch) findViewById(R.id.debug_switch_led_3);
        ledSwitchList[4] = (Switch) findViewById(R.id.debug_switch_led_4);
        ledSwitchList[5] = (Switch) findViewById(R.id.debug_switch_led_5);
        buzzerSwitch = (Switch) findViewById(R.id.debug_switch_buzzer);
        getRTCButton = (Button) findViewById(R.id.debug_button_get_rtc);
        setRTCButton = (Button) findViewById(R.id.debug_button_set_rtc);
        getBatteryButton = (Button) findViewById(R.id.debug_button_get_battery);
        rtcTextView = (TextView) findViewById(R.id.debug_text_rtc);
        rtcEditText = (EditText) findViewById(R.id.debug_edit_rtc);
        batteryTextView = (TextView) findViewById(R.id.debug_text_battery);
        getQuaternion = (Button) findViewById(R.id.debug_button_get_quaternion);

        topBarTitleTextView.setText("蓝牙调试");
        topBarRightButton.setVisibility(GONE);
        topBarBackButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                debugController.back();
            }
        });
        for (int i = 0; i < ledSwitchList.length; i++) {
            ledSwitchList[i].setTag(i + 1);
            ledSwitchList[i].setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked)
                        debugController.setLed((Integer) buttonView.getTag(), 1);
                    else
                        debugController.setLed((Integer) buttonView.getTag(), 0);
                }
            });
        }

        buzzerSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    debugController.setBuzzer(1);
                else
                    debugController.setBuzzer(0);
            }
        });

        getRTCButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                debugController.getRTC();
            }
        });

        setRTCButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = rtcEditText.getText().toString();
                if (text == null || text.equals("")) {
                    return;
                }
                debugController.setRTC(Long.parseLong(text));
            }
        });

        getBatteryButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                debugController.getBattery();
            }
        });

        getQuaternion.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                debugController.getQuaternion();
            }
        });

    }

    public void setRTC(long rtc) {
        rtcTextView.setText(rtc + "");
    }

    public void setBattery(int battery) {
        batteryTextView.setText(battery + "");
    }

}
