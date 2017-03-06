package com.fyc.admin.fragment.view;


import android.content.Context;
import android.graphics.Canvas;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fyc.admin.mycustomview.R;
import com.fyc.admin.view.DashboardView;

import java.util.Random;

/**
 * Created by Admin on 2017/3/5.
 */

public class DashboardViewFragment extends Fragment {
    private DashboardView dashboardView;
    int value = 100;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);
        dashboardView = (DashboardView) view.findViewById(R.id.dashboardView);
        handler.sendEmptyMessage(0);
        return view;
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            value = (int) (value + (30 - Math.random() * 60));
            if (value < 0) {
                value = 0;
            }
            if (value > 240) {
                value = 240;
            }
            dashboardView.setValue(value);
            sendEmptyMessageDelayed(0, 1000);
        }
    };
}
