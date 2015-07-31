package com.znys.view.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.ImageButton;

import com.nostra13.universalimageloader.core.ImageLoader;

import com.znys.R;
import com.znys.model.CalendarGridItem;

/**
 * Created by mushroom on 2015/6/24.
 */
public class CalendarCellView extends ImageButton {

    private ImageLoader imageLoader;
    private Paint paint;
    private Bitmap bitmap;
    private Rect rect;
    private int width, height;
    private boolean hasPrize;

    public CalendarCellView(Context context, AttributeSet attrs) {
        super(context, attrs);
        imageLoader = ImageLoader.getInstance();
        int rand = (int) System.currentTimeMillis() % 8;
        if (rand == 0) {
            bitmap = imageLoader.loadImageSync("drawable://" + R.drawable.prize);
            hasPrize = true;
        } else if (rand < 6) {
            bitmap = imageLoader.loadImageSync("drawable://" + R.drawable.star);
            hasPrize = false;
        } else {
            bitmap = imageLoader.loadImageSync("drawable://" + R.drawable.unhappy);
            hasPrize = true;
        }

        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setTextSize(24);
        paint.setTextAlign(Paint.Align.CENTER);
        rect = new Rect(8, 12, width - 8, height - 8);
        this.post(new Runnable() {
            @Override
            public void run() {
                height = CalendarCellView.this.getMeasuredHeight();
                width = CalendarCellView.this.getMeasuredWidth();
                CalendarCellView.this.setMinimumHeight(width);
                height = width;
                rect = new Rect(8, 12, width - 8, height - 8);
            }
        });
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setColor(Color.parseColor("#33cc00"));
        canvas.drawCircle(1, 1, 40, paint);
        Integer dayOfMonth = ((CalendarGridItem) (this.getTag())).getDayOfMonth();
        if (dayOfMonth == 1) {
            paint.setColor(Color.RED);
            canvas.drawText(dayOfMonth.toString(), 17, 25, paint);
        } else {
            paint.setColor(Color.WHITE);
            canvas.drawText(dayOfMonth.toString(), 17, 25, paint);
        }
        if (bitmap != null) {
            canvas.drawBitmap(bitmap, null, rect, null);
        }
        if (!hasPrize) {
            canvas.drawText((int) System.currentTimeMillis() % 8 + "", width / 2, height / 2 + 12, paint);
        }
    }
}
