package com.znys.view.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

import com.znys.controller.MainController;
import com.znys.model.CabinetViewPagerAdapter;

/**
 * Created by mushroom on 2015/6/25.
 */
public class CabinetViewPager extends ViewPager {

    private CabinetViewPagerAdapter cabinetViewPagerAdapter;

    public CabinetViewPager(Context context) {
        super(context);
    }

    public CabinetViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void init(MainController mainController) {
        cabinetViewPagerAdapter = new CabinetViewPagerAdapter(getContext(), null);
        cabinetViewPagerAdapter.setController(mainController);
        setAdapter(cabinetViewPagerAdapter);
        setCurrentItem(cabinetViewPagerAdapter.getCount());
    }
}
