package com.fyc.admin.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fyc.admin.adapter.ProjectAdapter;
import com.fyc.admin.mycustomview.R;
import com.mylhyl.crlayout.SwipeRefreshAdapterView;
import com.mylhyl.crlayout.SwipeRefreshRecyclerView;

import org.xutils.common.Callback;
import org.xutils.common.util.LogUtil;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Admin on 2017/3/2.
 */

public class LastOpenSourceFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener,
        SwipeRefreshAdapterView.OnListLoadListener {


    @InjectView(R.id.swipeRefresh)
    SwipeRefreshRecyclerView swipeRefresh;

    private boolean isRefresh = false;


    private ProjectAdapter adapter;
    private JSONArray dataArray;
    private int day = 1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_newproject, container, false);
        //ButterKnife.inject(view);
        swipeRefresh = (SwipeRefreshRecyclerView) view.findViewById(R.id.swipeRefresh);
        swipeRefresh.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        swipeRefresh.setOnListLoadListener(this);
        swipeRefresh.setOnRefreshListener(this);
        dataArray = new JSONArray();
        adapter = new ProjectAdapter(getActivity(), dataArray);
        swipeRefresh.setAdapter(adapter);
        swipeRefresh.setRefreshing(true);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);

    }


    private void getData() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -1 * day);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String dayStr = simpleDateFormat.format(new Date(calendar.getTimeInMillis()));
        Log.e("dayStr", dayStr);
        RequestParams params = new RequestParams("http://www.iygdy.com:13312/app/getlastproject/" + dayStr);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                JSONObject jsonObject = JSON.parseObject(result);
                int code = jsonObject.getInteger("code");
                if (code == 0) {
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    if (jsonArray.size() > 0) {
                        if (isRefresh) {
                            dataArray.clear();
                        }
                        dataArray.addAll(jsonArray);
                        adapter.setJsonArray(dataArray);

                    }

                }
                if (isRefresh) {
                    swipeRefresh.setRefreshing(false);
                } else {
                    swipeRefresh.setLoading(false);
                }
                isRefresh = false;
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

    @Override
    public void onRefresh() {
        isRefresh = true;
        day = 1;
        getData();
    }

    @Override
    public void onListLoad() {
        isRefresh = false;
        day++;
        getData();
    }
}
