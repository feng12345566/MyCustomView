package com.fyc.admin.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.fyc.admin.bean.User;
import com.fyc.admin.mycustomview.R;
import com.fyc.admin.utils.textview.MySpan;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import me.kaede.tagview.OnTagClickListener;
import me.kaede.tagview.Tag;
import me.kaede.tagview.TagView;

/**
 * Created by Admin on 2017/3/5.
 */

public class EditTextFragment extends Fragment {
    private TagView tagView;
    private EditText editText;
    private TextView sendBtn;
    private final String TAG = getClass().getSimpleName();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edittext, container, false);
        tagView = (TagView) view.findViewById(R.id.tagview);
        editText = (EditText) view.findViewById(R.id.comment_edittext);
        sendBtn = (TextView) view.findViewById(R.id.comment_sendbtn);
        List<User> users = mockUpData();
        for (int i = 0; i < users.size(); i++) {
            Tag tag = new Tag(users.get(i).getNickname());
            tag.tagTextColor = Color.parseColor("#FFFFFF");
            tag.layoutColor = Color.rgb((int) (255 * Math.random()), (int) (255 * Math.random()), (int) (255 * Math.random()));
            tag.layoutColorPress = Color.parseColor("#555555");
            tag.radius = 20f;
            tag.tagTextSize = 14f;
            tag.layoutBorderSize = 1f;
            tag.layoutBorderColor = Color.parseColor("#FFFFFF");
            tag.isDeletable = false;
            tag.tag = users.get(i).getUserId();
            tagView.addTag(tag);
            tagView.setOnTagClickListener(new OnTagClickListener() {
                @Override
                public void onTagClick(int position, Tag tag) {
                    User user = new User((String) tag.tag, tag.text);
                    insertAt(user);
                }
            });
        }

        editText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_DEL) {
                        // 处理删除事件，在选中范围内的span都需要被删除
                        int selectionStart = editText.getSelectionStart();
                        int selectionEnd = editText.getSelectionEnd();
                        MySpan[] spans = editText.getText()
                                .getSpans(0, editText.length(), MySpan.class);
                        for (MySpan span : spans) {
                            int spanStart = editText.getText().getSpanStart(span);
                            int spanEnd = editText.getText().getSpanEnd(span);

                            if (selectionStart > spanStart && selectionStart <= spanEnd) {
                                editText.setSelection(spanStart, selectionEnd);
                                selectionStart = spanStart;
                            }

                            if (selectionEnd >= spanStart && selectionEnd < spanEnd) {
                                editText.setSelection(selectionStart, spanEnd);
                                selectionEnd = spanEnd;
                            }
                        }
                    }
                }
                return false;
            }
        });
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editText.toString().length() == 0) {
                    Toast.makeText(getActivity(), "请输入文字", Toast.LENGTH_SHORT).show();
                } else {
                    new AlertDialog.Builder(getActivity())
                            .setTitle("发送内容")
                            .setMessage(parseData(editText.getText()))
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            })
                            .create().show();
                }
            }
        });
        return view;

    }

    /**
     * 在输入框光标处插入"@somebody"
     *
     * @param user 要插入的用户
     */
    public void insertAt(final User user) {
        int start = editText.getSelectionStart();
        editText.getText().insert(start, "@" + user.getNickname() + " ");
        MySpan span = new MySpan(user.getUserId(), user.getNickname());
        editText.getText()
                .setSpan(span, start, start + user.getNickname().length() + 1,
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
    }

    /**
     * 把输入的文字转换成发送给后台的数据,这里假定“@”相关的格式为<编号,名字>
     *
     * @param ss 输入框中的内容
     * @return 生成的数据
     */
    private String parseData(Spannable ss) {
        MySpan[] spans = ss.getSpans(0, ss.length(), MySpan.class);
        // 对span对象进行排序，在字符串靠前的排在前面
        int position;
        for (int i = 0; i < spans.length; i++) {
            int j = i + 1;
            position = i;
            MySpan temp = spans[i];
            for (; j < spans.length; j++) {
                if (ss.getSpanStart(spans[j]) < ss.getSpanStart(temp)) {
                    temp = spans[j];
                    position = j;
                }
            }
            spans[position] = spans[i];
            spans[i] = temp;
        }

        String pattern = "<%s,%s>";
        StringBuilder sb = new StringBuilder(ss);
        for (int i = spans.length; i > 0; i--) {
            int spanStart = ss.getSpanStart(spans[i - 1]);
            int spanEnd = ss.getSpanEnd(spans[i - 1]);
            sb.replace(spanStart, spanEnd,
                    String.format(pattern, spans[i - 1].getUserId(), spans[i - 1].getNickname()));
        }
        return sb.toString();
    }

    /**
     * 把后台返回的数据处理进行处理, 替换成spannable字符串
     *
     * @param spannableString 后台返回的数据
     */
    private SpannableStringBuilder analyseData(SpannableStringBuilder spannableString) {
        // SpannableStringBuilder output = new SpannableStringBuilder(spannableString);

        String patternStr = "<#(\\d+),((?:[A-Z]|[a-z])+)>";
        Pattern pattern = Pattern.compile(patternStr);
        Matcher matcher = pattern.matcher(spannableString);
        if (matcher.find()) {
            Log.d(TAG, "Find match: " + matcher.group());
            String userId = matcher.group(1);
            String nickname = matcher.group(2);
            String atString = "@" + nickname;

            // 将< , >的格式替换为@ 的格式
            spannableString.replace(matcher.start(), matcher.end(), atString);

            MySpan span = new MySpan(userId, nickname, new MySpan.OnSpanClickListener() {
                @Override
                public void onSpanClick(String userId, String nickname) {
                    Toast.makeText(getActivity(), "nickname: " + nickname, Toast.LENGTH_SHORT).show();
                }
            });
            spannableString.setSpan(span, matcher.start(), matcher.start() + atString.length(),
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

            analyseData(spannableString);
        }

        return spannableString;
    }


    private List<User> mockUpData() {
        List<User> users = new ArrayList<>();
        users.add(new User("#001", "霸气小公主"));
        users.add(new User("#002", "不会飞的天使"));
        users.add(new User("#003", "可爱女汉子"));
        users.add(new User("#004", "白马王子"));
        users.add(new User("#005", "证明小和尚"));
        users.add(new User("#006", "妞妞"));
        users.add(new User("#007", "jack chen"));
        users.add(new User("#008", "刘涛"));
        users.add(new User("#009", "追风筝的人"));
        return users;
    }
}
