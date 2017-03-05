package com.fyc.admin.fragment;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.dalong.marqueeview.MarqueeView;
import com.fyc.admin.mycustomview.R;
import com.fyc.admin.utils.textview.MySpanStringBuilder;

import butterknife.ButterKnife;
import butterknife.InjectView;
import thereisnospon.codeview.CodeView;
import thereisnospon.codeview.CodeViewTheme;

/**
 * Created by Admin on 2017/3/4.
 */

public class TextViewFragment extends Fragment {
    private MarqueeView mMarqueeView;
    private final static String CODE = "<TextView\n" +
            "   android:layout_width=\"match_parent\"\n" +
            "   android:layout_height=\"wrap_content\"\n" +
            "   android:layout_marginTop=\"8dp\"\n" +
            "   android:singleLine=\"true\"\n" +
            "   android:ellipsize=\"marquee\"\n" +
            "   android:focusable=\"true\"\n" +
            "   android:focusableInTouchMode=\"true\"\n" +
            "   android:text=\"大量学生反映，除了上课学习英语、课下复习和作业以外，许多家长都为孩子报名了英语补习班。课外补习占据了学生原本就有限的课外活动、休闲娱乐时间，不仅使学生感到负担沉重，甚至也影响了睡眠和休息。上海儿童发展研究中心的调查显示,只有17.2%的小学生、19.1%的初中生达到了标准的睡眠时间;来自深圳的调查显示,超过42%的深圳中小学生处于睡眠不足的状态。\"/>\n";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_textview, container, false);
        TextView text = (TextView) view.findViewById(R.id.text);
        CodeView codeView = (CodeView) view.findViewById(R.id.codeview);
        CodeView codeView2 = (CodeView) view.findViewById(R.id.codeview2);
        mMarqueeView = (MarqueeView) view.findViewById(R.id.mMarqueeView);
        mMarqueeView.setText("大量学生反映，除了上课学习英语、课下复习和作业以外，许多家长都为孩子报名了英语补习班。课外补习占据了学生原本就有限的课外活动、休闲娱乐时间，不仅使学生感到负担沉重，甚至也影响了睡眠和休息。上海儿童发展研究中心的调查显示,只有17.2%的小学生、19.1%的初中生达到了标准的睡眠时间;来自深圳的调查显示,超过42%的深圳中小学生处于睡眠不足的状态。");
        mMarqueeView.startScroll();
        codeView.setTheme(CodeViewTheme.ANDROIDSTUDIO).fillColor();
        codeView2.setTheme(CodeViewTheme.ANDROIDSTUDIO).fillColor();
        //这里的CODE 为需要显示的代码，类型为String，使用的时候自己替换下。
        codeView.showCode(CODE);
        codeView2.showCode("<com.dalong.marqueeview.MarqueeView\n" +
                "    android:id=\"@+id/mMarqueeView\"\n" +
                "    android:layout_width=\"match_parent\"\n" +
                "    android:layout_height=\"50dp\"\n" +
                "    app:direction=\"left\"\n" +
                "    app:isRepeat=\"true\"\n" +
                "    app:speed=\"60\"\n" +
                "    app:startPoint=\"start\"\n" +
                "    app:textSize=\"16sp\"\n" +
                "    app:textcolor=\"@color/colorAccent\" />");


        StateListDrawable bg = new StateListDrawable();
        bg.addState(new int[]{android.R.attr.state_pressed}, new ColorDrawable(Color.parseColor("#C4C4C4")));
        bg.addState(new int[]{android.R.attr.state_enabled}, null);
        MySpanStringBuilder mySpanStringBuilder = new MySpanStringBuilder();
        text.setBackground(bg);
        mySpanStringBuilder.addTextWithClick("张三", new MySpanStringBuilder.OnTextSpanClickListener() {
            @Override
            public void onTextSpanClick(int opType, String text) {
                Toast.makeText(getActivity(), String.format("“%s”被点击", text), Toast.LENGTH_SHORT).show();
            }
        });
        mySpanStringBuilder.addTextNoClick(" 回复 ");
        mySpanStringBuilder.addTextWithClick("李四", new MySpanStringBuilder.OnTextSpanClickListener() {
            @Override
            public void onTextSpanClick(int opType, String text) {
                Toast.makeText(getActivity(), String.format("“%s”被点击", text), Toast.LENGTH_SHORT).show();
            }
        });
        mySpanStringBuilder.addTextNoClick(" : ");
        mySpanStringBuilder.addTextNoClick("中间那条线就是基线，基线到上面那条线的距离就是ascent，基线到下面那条线的距离就是descent。");
        mySpanStringBuilder.attachToTextView(text, Color.parseColor("#5CACEE"));
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        mMarqueeView.startScroll();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
}
