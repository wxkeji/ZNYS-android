package com.znys.view.widget;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.Toast;

/**
 * Created by mushroom on 2015/6/17.
 */
public class GroupScrollView extends HorizontalScrollView implements Runnable {
    private View innerView;
    private int x;
    private int width;
    private int step = 30;
    private int delay = 200;
    private boolean enable = false;

    public GroupScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setHorizontalScrollBarEnabled(false);
    }

    @Override
    protected void onFinishInflate() {
        if (getChildCount() == 1) {
            innerView = getChildAt(0);
            run();
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        handler.removeCallbacks(this);
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            if (width == 0) {
                width = innerView.getMeasuredWidth();
                x = -width;
            }
            x += step;
            if(x >= width){
                x = -width;
            }
            innerView.setScrollX(x);
        }
    };

    @Override
    public void run() {

        invalidate();
        if(enable){
            Message message = Message.obtain();
            handler.sendMessage(message);
            handler.postDelayed(this, delay);
        }

    }

}

