package com.znys.view.widget;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by mushroom on 2015/6/23.
 */
public class CustomAlertDialog {

    private AlertDialog alertDialog;
    private Context context;

    public CustomAlertDialog(Context context, int layoutResourceId) {
        this.context = context;
        this.alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.show();
        alertDialog.setContentView(layoutResourceId);
        alertDialog.setCanceledOnTouchOutside(true);
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
