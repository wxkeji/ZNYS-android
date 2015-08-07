package com.znys.view.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import com.znys.R;
import com.znys.controller.MainController;

/**
 * Created by mushroom on 2015/6/25.
 */
public class CabinetView extends LinearLayout {

    private CabinetGridView prizeButton[];

    public CabinetView(Context context) {
        super(context);
    }

    public CabinetView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void init(final MainController listener) {
        prizeButton = new CabinetGridView[8];
        prizeButton[0] = (CabinetGridView) findViewById(R.id.cabinet_button_prize_0);
        prizeButton[1] = (CabinetGridView) findViewById(R.id.cabinet_button_prize_1);
        prizeButton[2] = (CabinetGridView) findViewById(R.id.cabinet_button_prize_2);
        prizeButton[3] = (CabinetGridView) findViewById(R.id.cabinet_button_prize_3);
        prizeButton[4] = (CabinetGridView) findViewById(R.id.cabinet_button_prize_4);
        prizeButton[5] = (CabinetGridView) findViewById(R.id.cabinet_button_prize_5);
        prizeButton[6] = (CabinetGridView) findViewById(R.id.cabinet_button_prize_6);
        prizeButton[7] = (CabinetGridView) findViewById(R.id.cabinet_button_prize_7);
        prizeButton[0].setImageResource(R.drawable.prize_duck);
        prizeButton[1].setImageResource(R.drawable.prize_bear);
        prizeButton[2].setImageResource(R.drawable.prize_dragon);
        prizeButton[3].setImageResource(R.drawable.prize_car);
        prizeButton[4].setImageResource(R.drawable.prize_ball);
        prizeButton[5].setImageResource(R.drawable.prize_lock);
        prizeButton[6].setImageResource(R.drawable.prize_lock);
        prizeButton[7].setImageResource(R.drawable.prize_lock);
        prizeButton[4].setState(1);
        for (int i = 0; i < prizeButton.length; i++) {
            prizeButton[i].setTag(Integer.valueOf(i));
            prizeButton[i].setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onPrizeButtonClick(view);
                }
            });
        }
    }

}
