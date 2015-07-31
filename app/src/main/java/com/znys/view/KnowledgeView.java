package com.znys.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.znys.R;
import com.znys.controller.KnowledgeController;

/**
 * Created by mushroom on 2015/6/16.
 */
public class KnowledgeView extends LinearLayout {

    private ImageButton homeButton;
    private ListView listView;

    public KnowledgeView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void init(final KnowledgeController listener) {
        homeButton = (ImageButton) findViewById(R.id.topbar_button_home);
        listView = (ListView) findViewById(R.id.knowledge_list);
        homeButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.goHome();
            }
        });
    }
}
