package com.fyc.admin.utils.textview;

import com.ajra.multiactiontextview.InputObject;

/**
 * Created by Admin on 2017/3/4.
 */

public class SpanInputObject extends InputObject {
    private String text;
    private boolean clickable;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isClickable() {
        return clickable;
    }

    public void setClickable(boolean clickable) {
        this.clickable = clickable;
    }
}
