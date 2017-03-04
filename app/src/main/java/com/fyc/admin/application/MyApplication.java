package com.fyc.admin.application;

import android.app.Application;
import android.support.compat.BuildConfig;

import org.xutils.x;

/**
 * Created by Admin on 2017/3/3.
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
        x.Ext.setDebug(BuildConfig.DEBUG); // 是否输出debug日志, 开启debug会影响性能.
    }
}
