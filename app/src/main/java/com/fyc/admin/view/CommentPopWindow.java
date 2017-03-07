package com.fyc.admin.view;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.fyc.admin.mycustomview.R;

/**
 * Created by Admin on 2017/3/6.
 */

public class CommentPopWindow {
    private PopupWindow popupWindow;
    private Context context;
    private EditText editText;
    private TextView sendBtn;

    public CommentPopWindow(Context context) {
        this.context = context;
        popupWindow = new PopupWindow(context);
        popupWindow.setWidth(WindowManager.LayoutParams.MATCH_PARENT);
        popupWindow.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        View view = LayoutInflater.from(context).inflate(R.layout.layout_popcomment, null);
        editText = (EditText) view.findViewById(R.id.comment_edittext);
        sendBtn = (TextView) view.findViewById(R.id.comment_sendbtn);
        popupWindow.setContentView(view);
    }

    public void show(View view) {
        popupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);
        editText.requestFocus();
    }


    public void dissmiss() {
        editText.clearFocus();
        popupWindow.dismiss();
    }
}
