package com.znys.model;

import android.content.Context;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.znys.controller.MainController;
import com.znys.view.widget.CabinetView;

import com.znys.R;

/**
 * Created by mushroom on 2015/6/25.
 */
public class CabinetViewPagerAdapter extends PagerAdapter {

    private MainController mainController;
    private Context context;
    private Object cabinetCollection;

    public CabinetViewPagerAdapter(Context context, Object cabinetCollection) {
        this.context = context;
        this.cabinetCollection = cabinetCollection;
    }

    public void setController(MainController mainController) {
        this.mainController = mainController;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        CabinetView cabinetView = (CabinetView) LayoutInflater.from(context).inflate(R.layout.layout_cabinet, null);
        cabinetView.init(mainController);
        container.addView(cabinetView, 0);
        return cabinetView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public Parcelable saveState() {
        return null;
    }
}
