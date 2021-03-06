package com.fyc.admin.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fyc.admin.mycustomview.MenuDetailListActivity;
import com.fyc.admin.mycustomview.R;
import com.fyc.admin.view.MenuLevelRecycleView;

import java.util.ArrayList;

/**
 * Created by Admin on 2017/3/2.
 */

public class MyViewFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_myview, null);
        MenuLevelRecycleView menuLevelRecycleView = (MenuLevelRecycleView) view.findViewById(R.id.menuLevelRecyclerView);
        menuLevelRecycleView.init(getActivity());
        final ArrayList<String> menus = new ArrayList<String>();
        menus.add("系统视图");
        menus.add("自绘视图");
        menus.add("桌面组件");
        menus.add("多媒体组件");
        menus.add("系统服务");
        menus.add("数据通讯");
        menus.add("数据存储");
        menus.add("硬件传感器");
        menuLevelRecycleView.setMenuList(menus);
        menuLevelRecycleView.setOnItemClickListener(new MenuLevelRecycleView.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int postion) {
                Intent intent = new Intent(getActivity(), MenuDetailListActivity.class);
                intent.putExtra("position", postion);
                intent.putExtra("title", menus.get(postion));
                startActivity(intent);
            }
        });
        return view;
    }
}
