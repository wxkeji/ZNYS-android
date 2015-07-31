package com.znys.view.widget;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.znys.R;

/**
 * Created by mushroom on 2015/7/5.
 */
public class SynchronizationDialog {
    private AlertDialog alertDialog;
    private ImageView imageView;
    private Context context;

    public SynchronizationDialog(Context context) {
        this.context = context;
        this.alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.show();
        alertDialog.setContentView(R.layout.control_dialog_synchronization);
        alertDialog.setCancelable(false);
        imageView = (ImageView) alertDialog.findViewById(R.id.control_dialog_image);
        RotateAnimation rotateAnimation = new RotateAnimation(0.0f, -360.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setInterpolator(new LinearInterpolator());
        rotateAnimation.setDuration(1200);
        rotateAnimation.setRepeatCount(-1);
        imageView.setAnimation(rotateAnimation);
        rotateAnimation.start();
    }

    public void setOnClickListener(int controlId, View.OnClickListener listener) {
        alertDialog.findViewById(controlId).setOnClickListener(listener);
    }

    public void setContentImage(int controlId, int imageResourceId) {
        ((ImageView) alertDialog.findViewById(controlId)).setImageResource(imageResourceId);
    }

    public void setContentImage(int controlId, Bitmap imageBitmap) {
        ((ImageView) alertDialog.findViewById(controlId)).setImageBitmap(imageBitmap);
    }

    public void setContentText(int controlId, String text) {
        ((TextView) alertDialog.findViewById(controlId)).setText(text);
    }

    public void dismiss() {
        alertDialog.dismiss();
    }
}
