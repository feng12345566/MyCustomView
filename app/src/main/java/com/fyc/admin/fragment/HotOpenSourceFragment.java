package com.fyc.admin.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.diegocarloslima.fgelv.lib.FloatingGroupExpandableListView;
import com.diegocarloslima.fgelv.lib.WrapperExpandableListAdapter;
import com.fyc.admin.adapter.HotProjectApater;
import com.fyc.admin.bean.HotProject;
import com.fyc.admin.bean.HotProjectGroup;
import com.fyc.admin.mycustomview.R;
import com.fyc.admin.mycustomview.WebViewActivity;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 2017/3/2.
 */

public class HotOpenSourceFragment extends Fragment {
    private FloatingGroupExpandableListView myList;
    private List<HotProjectGroup> hotProjectGroups;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hotproject, container, false);
        myList = (FloatingGroupExpandableListView) view.findViewById(R.id.my_list);
        myList.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                Intent intent = new Intent(getActivity(), WebViewActivity.class);
                intent.putExtra("url", hotProjectGroups.get(groupPosition).getHotProjectList().get(childPosition).getGitUrl());
                startActivity(intent);
                return true;
            }
        });
        getData();
        return view;
    }


    private void getData() {
        RequestParams requestParams = new RequestParams("http://www.iygdy.com:13312/app/gethotproject");
        x.http().get(requestParams, new Callback.CacheCallback<String>() {
            @Override
            public void onSuccess(String result) {
                JSONObject ret = JSON.parseObject(result);
                if (ret.getIntValue("code") == 0) {
                    hotProjectGroups = new ArrayList<HotProjectGroup>();
                    JSONArray array = ret.getJSONArray("groups");
                    for (int i = 0; i < array.size(); i++) {
                        JSONObject obj = array.getJSONObject(i);
                        HotProjectGroup hotProjectGroup = new HotProjectGroup();
                        hotProjectGroup.setGroupName(obj.getString("name"));
                        JSONArray projects = obj.getJSONArray("projects");
                        List<HotProject> hotProjects = new ArrayList<HotProject>();
                        for (int j = 0; j < projects.size(); j++) {
                            JSONObject prj = projects.getJSONObject(j);
                            HotProject hotPrj = new HotProject();
                            hotPrj.setDesc(prj.getString("descb"));
                            hotPrj.setGifUrl(prj.getString("gif_url"));
                            hotPrj.setGitUrl(prj.getString("git_url"));
                            hotPrj.setTitle(prj.getString("title"));
                            hotProjects.add(hotPrj);
                        }
                        hotProjectGroup.setHotProjectList(hotProjects);
                        hotProjectGroups.add(hotProjectGroup);
                    }
                    HotProjectApater myAdapter = new HotProjectApater(getActivity(), hotProjectGroups);
                    WrapperExpandableListAdapter wrapperAdapter = new WrapperExpandableListAdapter(myAdapter);
                    myList.setAdapter(wrapperAdapter);
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

            @Override
            public boolean onCache(String result) {
                return false;
            }
        });
    }
}
