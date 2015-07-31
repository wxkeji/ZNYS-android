package com.znys.controller;

/**
 * Created by mushroom on 2015/7/26.
 */
public interface DebugController {

    void back();

    void setLed(int index, int state);

    void getRTC();

    void setRTC(long millisecond);

    void getBattery();

    void setBuzzer(int state);

    void getQuaternion();

}
