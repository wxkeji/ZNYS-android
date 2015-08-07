package com.znys.controller;

/**
 * Created by mushroom on 2015/6/8.
 */
public interface AnalysisResultController {
    void goHome();

    void checkCalendar();

    void checkKnowledge();

    void switchActivity(Class c);

    void playSoundEffect(int resourceId);
}
