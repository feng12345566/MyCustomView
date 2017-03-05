package com.fyc.admin.utils.textview;

import android.text.SpannableStringBuilder;
import android.view.View;
import android.widget.TextView;

import com.ajra.multiactiontextview.InputObject;
import com.ajra.multiactiontextview.MultiActionTextView;
import com.ajra.multiactiontextview.MultiActionTextviewClickListener;

import java.util.ArrayList;

/**
 * Created by Admin on 2017/3/4.
 */

public class MySpanStringBuilder {
    private ArrayList<SpanInputObject> spanInputObjects = new ArrayList<>();
    private SpannableStringBuilder stringBuilder;
    private MyMultiActionTextviewClickListener myMultiActionClickListener;
    private ArrayList<OnTextSpanClickListener> listeners;

    public MySpanStringBuilder() {
        stringBuilder = new SpannableStringBuilder();
        myMultiActionClickListener = new MyMultiActionTextviewClickListener();
        listeners = new ArrayList<OnTextSpanClickListener>();
    }

    public void addTextWithClick(String text, OnTextSpanClickListener onClickListener) {
        if (text != null) {
            SpanInputObject spanInputObject = new SpanInputObject();
            spanInputObject.setStartSpan(stringBuilder.length());
            spanInputObject.setText(text);
            stringBuilder.append(text);
            spanInputObject.setEndSpan(stringBuilder.length());
            spanInputObject
                    .setMultiActionTextviewClickListener(myMultiActionClickListener);
            spanInputObject.setOperationType(listeners.size());
            spanInputObject.setClickable(true);
            listeners.add(onClickListener);
            spanInputObjects.add(spanInputObject);
        }
    }

    public void addTextNoClick(String text) {
        if (text != null) {
            SpanInputObject spanInputObject = new SpanInputObject();
            spanInputObject.setStartSpan(stringBuilder.length());
            spanInputObject.setText(text);
            stringBuilder.append(text);
            spanInputObject.setEndSpan(stringBuilder.length());
            spanInputObject.setStringBuilder(stringBuilder);
            spanInputObjects.add(spanInputObject);
        }
    }


    public void attachToTextView(TextView view, int clickableTextColor) {
        for (int i = 0; i < spanInputObjects.size(); i++) {
            SpanInputObject spanInputObject = spanInputObjects.get(i);
            spanInputObject.setStringBuilder(stringBuilder);
            if (spanInputObject.isClickable()) {
                MultiActionTextView.addActionOnTextViewWithoutLink(spanInputObject);
            }
        }
        MultiActionTextView.setSpannableText(view,
                stringBuilder, clickableTextColor);
    }

    private class MyMultiActionTextviewClickListener implements MultiActionTextviewClickListener {

        @Override
        public void onTextClick(InputObject inputObject) {
            OnTextSpanClickListener listener = listeners.get(inputObject.getOperationType());
            if (listener != null) {
                int start = inputObject.getStartSpan();
                int end = inputObject.getEndSpan();
                listener.onTextSpanClick(inputObject.getOperationType(), stringBuilder.toString().substring(start, end));
            }
        }
    }


    public interface OnTextSpanClickListener {
        public void onTextSpanClick(int opType, String text);
    }
}
