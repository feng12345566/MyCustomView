package com.fyc.admin.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatAutoCompleteTextView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fyc.admin.mycustomview.R;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.lang.reflect.Array;
import java.nio.charset.Charset;

/**
 * Created by Admin on 2017/3/5.
 */

public class AutoComplateEditTextFragment extends Fragment {
    private AppCompatAutoCompleteTextView appCompatAutoCompleteTextView;
    private String[] suggestion;
    private ArrayAdapter arrayAdapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_autocomplete, container, false);
        appCompatAutoCompleteTextView = (AppCompatAutoCompleteTextView) view.findViewById(R.id.searchAutoComplete);
        appCompatAutoCompleteTextView.setThreshold(1);
        appCompatAutoCompleteTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String key = s.toString();
                if (key.length() > 0) {
                    getSuggestion(key);
                } else {
                    arrayAdapter.clear();
                }
            }
        });
        return view;
    }

    private void getSuggestion(String key) {

        RequestParams requestParams = new RequestParams();
        requestParams.setUri("https://sp0.baidu.com/5a1Fazu8AA54nxGko9WTAnF6hhy/su");
        requestParams.setCharset("gbk");
        requestParams.addParameter("wd", key);
        requestParams.addParameter("json", 0);
        requestParams.addParameter("p", 3);
        requestParams.addParameter("req", 2);
        requestParams.addParameter("csor", 0);
        x.http().get(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                String json = result.substring(17, result.length() - 2);

                JSONObject ret = JSON.parseObject(json);
                JSONArray array = ret.getJSONArray("s");
                String[] sa = new String[array.size()];
                for (int i = 0; i < sa.length; i++) {
                    sa[i] = array.getString(i);
                }

                suggestion = sa;
                System.out.println(array.toJSONString());
                if (arrayAdapter == null) {
                    arrayAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, suggestion);
                    appCompatAutoCompleteTextView.setAdapter(arrayAdapter);
                } else {
                    arrayAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }
}
