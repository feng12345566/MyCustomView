package com.fyc.admin.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fyc.admin.adapter.BlogApater;
import com.fyc.admin.adapter.ProjectAdapter;
import com.fyc.admin.bean.Blog;
import com.fyc.admin.mycustomview.R;
import com.mylhyl.crlayout.SwipeRefreshAdapterView;
import com.mylhyl.crlayout.SwipeRefreshRecyclerView;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Admin on 2017/3/2.
 */

public class NewBlogFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener,
        SwipeRefreshAdapterView.OnListLoadListener {


    @InjectView(R.id.swipeRefresh)
    SwipeRefreshRecyclerView swipeRefresh;

    private boolean isRefresh = false;
    private BlogApater adapter;
    private int page = 0;
    List<Blog> blogs = new ArrayList<Blog>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_newproject, container, false);
        //ButterKnife.inject(view);
        swipeRefresh = (SwipeRefreshRecyclerView) view.findViewById(R.id.swipeRefresh);
        swipeRefresh.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        swipeRefresh.setOnListLoadListener(this);
        swipeRefresh.setOnRefreshListener(this);
        adapter = new BlogApater(getActivity());
        swipeRefresh.setAdapter(adapter);
        swipeRefresh.getSwipeRefreshLayout().setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light, android.R.color.holo_orange_light, android.R.color.holo_green_light);
        swipeRefresh.setRefreshing(true);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);

    }


    private void getData() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String dayStr = simpleDateFormat.format(new Date());
        String url = "http://www.iygdy.com:13312/app/getNewBlogs/" + URLEncoder.encode(dayStr) + "/" + page;
        RequestParams params = new RequestParams(url);
        params.setAutoRename(true);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                JSONObject jsonObject = JSON.parseObject(result);
                int code = jsonObject.getInteger("code");
                if (code == 0) {
                    String data = jsonObject.getString("data");
                    List<Blog> bs = JSON.parseArray(data, Blog.class);
                    if (isRefresh) {
                        blogs.clear();
                    }
                    if (bs.size() > 0) {
                        blogs.addAll(bs);
                    } else {
                        swipeRefresh.setLoadCompleted(true);
                    }
                    adapter.setBlogs(blogs);

                    if (isRefresh) {
                        swipeRefresh.setRefreshing(false);
                        isRefresh = false;
                    } else {
                        swipeRefresh.setLoading(false);
                    }


                }

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                if (swipeRefresh != null) {
                    if (isRefresh) {
                        swipeRefresh.setRefreshing(false);
                    } else {
                        swipeRefresh.setLoading(false);
                    }
                }
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
        page = 0;
        getData();
    }

    @Override
    public void onListLoad() {
        isRefresh = false;
        page++;
        getData();
    }
}
