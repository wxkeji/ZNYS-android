package com.znys.view.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.FileDescriptor;

import com.znys.R;

/**
 * Created by mushroom on 2015/6/23.
 */
public class VideoSurfaceView extends SurfaceView implements SurfaceHolder.Callback {

    private SurfaceHolder surfaceHolder;
    private DrawingThread drawingThread;
    private Bitmap bitmap1, bitmap2;
    private boolean state = true;
    private FileDescriptor videoFileDescriptor;
    private int surfaceWidth;
    private int surfaceHeight;

    public VideoSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        surfaceHolder = this.getHolder();
        surfaceHolder.addCallback(this);
        drawingThread = new DrawingThread();
    }

    public void setState(boolean state) {
        this.state = state;
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        surfaceWidth = this.getMeasuredWidth();
        surfaceHeight = this.getMeasuredHeight();
        if (drawingThread.getState() == Thread.State.NEW) {
            drawingThread.setIsRunning(true);
            drawingThread.start();
        }

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        drawingThread.setIsRunning(false);
    }

    private class DrawingThread extends Thread {

        private boolean isRunning = false;
        private Paint paint = new Paint();

        public void setIsRunning(boolean isRunning) {
            this.isRunning = isRunning;
        }

        @Override
        public void run() {
            paint.setColor(Color.RED);
            paint.setAntiAlias(true);
            bitmap1 = BitmapFactory.decodeResource(getResources(), R.drawable.good, null);
            bitmap2 = BitmapFactory.decodeResource(getResources(), R.drawable.bad, null);
            float ratio = (float) (bitmap1.getWidth() / bitmap1.getHeight());
            int height = (int) (surfaceWidth / ratio);
            while (isRunning) {
                Canvas canvas = null;
                try {
                    synchronized (surfaceHolder) {
                        canvas = surfaceHolder.lockCanvas();
                        canvas.drawColor(Color.WHITE);
                        if (state == true) {
                            canvas.drawBitmap(bitmap1, null, new Rect(0, 0, surfaceWidth, height), null);
                        } else {
                            canvas.drawBitmap(bitmap2, null, new Rect(0, 0, surfaceWidth, height), null);
                        }
                        sleep(100);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (canvas != null) {
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    }
                }
            }
        }
    }
}
