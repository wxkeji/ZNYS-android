package com.znys.view.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.ImageButton;

import com.znys.R;

/**
 * Created by mushroom on 2015/6/25.
 */
public class CabinetGridView extends ImageButton {

    private int state; // 0 lock 1 unlock 2 exchanged
    private Rect rect;
    private Bitmap tag;

    public CabinetGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.post(new Runnable() {
            @Override
            public void run() {
                rect = new Rect();
                tag = BitmapFactory.decodeResource(getResources(), R.drawable.unexchanged_tag);
                invalidate();
            }
        });
    }

    public void setState(int state) {
        this.state = state;
        invalidate();
    }

    public int getState() {
        return state;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (tag != null && state == 1) {
            canvas.drawBitmap(tag, 25, 0, null);
        }
    }
}
