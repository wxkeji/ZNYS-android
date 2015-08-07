package com.znys.controller;

import android.view.View;

public interface MainController {
    void synchronize();

    void checkCalendar();

    void checkKnowledge();

    void configureApp();

    void switchActivity(Class c);

    void onPrizeButtonClick(View view);
}
