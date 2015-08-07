package com.znys.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.znys.R;
import com.znys.controller.FeedbackController;

/**
 * Created by mushroom on 2015/7/12.
 */
public class FeedbackView extends LinearLayout {

    private ImageButton topBarBackButton;
    private TextView topBarSubmitButton, topBarTitleTextView;
    private TextView contactTextView;
    private EditText contentEditText, contactEditText;
    private ImageView contentLine, contactLine;
    private ViewGroup.LayoutParams contentLineLP;
    private ViewGroup.LayoutParams contactLineLP;

    public FeedbackView(Context context) {
        super(context);
    }

    public FeedbackView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void initView(final FeedbackController feedbackController) {
        topBarBackButton = (ImageButton) findViewById(R.id.top_bar_button_left);
        topBarSubmitButton = (TextView) findViewById(R.id.top_bar_button_right);
        topBarTitleTextView = (TextView) findViewById(R.id.top_bar_title);
        contactTextView = (TextView) findViewById(R.id.feedback_text_contact_title);
        contentEditText = (EditText) findViewById(R.id.feedback_text_content);
        contactEditText = (EditText) findViewById(R.id.feedback_text_contact);
        contentLine = (ImageView) findViewById(R.id.feedback_line_content);
        contactLine = (ImageView) findViewById(R.id.feedback_line_contact);
        topBarBackButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                feedbackController.back();
            }
        });
        topBarSubmitButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                feedbackController.submit();
            }
        });
        topBarTitleTextView.setText(getResources().getString(R.string.settings_contact));
        contentEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    contentLineLP.height = 2;
                    contentLine.setLayoutParams(contentLineLP);
                    contentLine.setBackgroundColor(getResources().getColor(R.color.top_bar));
                    contactLineLP.height = 1;
                    contactLine.setLayoutParams(contactLineLP);
                    contactLine.setBackgroundColor(getResources().getColor(R.color.line));
                    contactTextView.setTextColor(getResources().getColor(R.color.gray_light));
                }

            }
        });
        contactEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    contactLineLP.height = 2;
                    contactLine.setLayoutParams(contactLineLP);
                    contactLine.setBackgroundColor(getResources().getColor(R.color.top_bar));
                    contentLineLP.height = 1;
                    contentLine.setLayoutParams(contentLineLP);
                    contentLine.setBackgroundColor(getResources().getColor(R.color.line));
                    contactTextView.setTextColor(getResources().getColor(R.color.top_bar));
                }

            }
        });
        this.post(new Runnable() {

            @Override
            public void run() {
                contentLineLP = contentLine.getLayoutParams();
                contactLineLP = contactLine.getLayoutParams();
            }
        });
    }

    public String getContent() {
        return contentEditText.getText().toString();
    }

    public String getContact() {
        return contactEditText.getText().toString();
    }

}
