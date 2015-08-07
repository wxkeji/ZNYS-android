package com.znys.view.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ImageView;

import com.znys.R;

/**
 * Created by mushroom on 2015/7/3.
 */
public class ErasibleStarView extends ImageView {

    private Bitmap shadeBitmap, starBitmap;
    private Canvas mCanvas;
    private Paint mPaint;
    private Path mPath;
    private Rect[] rectList;
    private float mX, mY;
    private boolean isDraw = false;
    private float TOUCH_TOLERANCE = 1.0f;
    private int paintStrokeWidth = 30;
    private int firstPosition;
    private int paddingTop;

    public ErasibleStarView(Context context) {
        super(context);
    }

    public ErasibleStarView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (isDraw) {
            for (int i = 0; i < rectList.length; i++) {
                canvas.drawBitmap(starBitmap, null, rectList[i], null);
            }
            mCanvas.drawPath(mPath, mPaint);
            canvas.drawBitmap(shadeBitmap, 0, 0, null);
        }
    }

    public void init() {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.outHeight = getMeasuredHeight();
        options.outWidth = getMeasuredWidth();
        options.inMutable = true;

        starBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.star, null);
        shadeBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.star_shade, options);
        shadeBitmap.setHasAlpha(true);
        mCanvas = new Canvas(shadeBitmap);
        calculateRectPosition(5);

        mPaint = new Paint();
        mPaint.setAlpha(253);
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeWidth(paintStrokeWidth);

        mPath = new Path();

        isDraw = true;
    }

    public void startAnimation(int starNumber) {
        calculateRectPosition(starNumber);
    }

    private void calculateRectPosition(int starNumber) {
        firstPosition = (int) (((float) getMeasuredWidth() / 2) - ((starNumber / 2.0f) * starBitmap.getWidth()) + 0.5);
        paddingTop = (int) (getMeasuredHeight() / 2.0 - starBitmap.getHeight() / 2.0 + 0.5);
        rectList = new Rect[starNumber];
        for (int i = 0; i < starNumber; i++) {
            rectList[i] = new Rect(firstPosition + i * starBitmap.getWidth(), paddingTop, firstPosition + (i + 1) * starBitmap.getWidth(), paddingTop + starBitmap.getHeight());
        }
        this.invalidate();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (!isDraw) {
            return true;
        }
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                touchDown(event.getX(), event.getY());
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                touchMove(event.getX(), event.getY());
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                touchUp(event.getX(), event.getY());
                invalidate();
                break;
            default:
                break;
        }
        return true;
    }

    private void touchDown(float x, float y) {
        mPath.reset();
        mPath.moveTo(x, y);
        mX = x;
        mY = y;
    }

    private void touchMove(float x, float y) {
        float dx = Math.abs(x - mX);
        float dy = Math.abs(y - mY);
        if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {
            mPath.quadTo(mX, mY, (x + mX) / 2, (y + mY) / 2);
            mX = x;
            mY = y;
        }

    }

    private void touchUp(float x, float y) {
        mPath.lineTo(x, y);
        mCanvas.drawPath(mPath, mPaint);
        mPath.reset();
    }

    public void setPaintStrokeWidth(int paintStrokeWidth) {
        this.paintStrokeWidth = paintStrokeWidth;
    }

    public void setPaddingTop(int paddingTop) {
        this.paddingTop = paddingTop;
    }

    public void setTouchTolerance(float TOUCH_TOLERANCE) {
        this.TOUCH_TOLERANCE = TOUCH_TOLERANCE;
    }

}
