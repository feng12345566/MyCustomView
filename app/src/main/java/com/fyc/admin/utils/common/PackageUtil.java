package com.fyc.admin.utils.common;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.graphics.drawable.Drawable;

import com.fyc.admin.bean.ApkInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 2017/3/5.
 */

public class PackageUtil {
    public static ArrayList<ApkInfo> getInstalledPackages(Context context) {
        ArrayList<ApkInfo> appList = new ArrayList<ApkInfo>(); //用来存储获取的应用信息数据
        List<PackageInfo> packages = context.getPackageManager().getInstalledPackages(0);

        for (int i = 0; i < packages.size(); i++) {
            PackageInfo packageInfo = packages.get(i);
            ApkInfo tmpInfo = new ApkInfo();
            tmpInfo.appName = packageInfo.applicationInfo.loadLabel(context.getPackageManager()).toString();
            if (tmpInfo.appName.length() > 24) {
                tmpInfo.appName = tmpInfo.appName.substring(0, 24) + "...";
            }
            tmpInfo.packageName = packageInfo.packageName;
            tmpInfo.versionName = packageInfo.versionName;
            tmpInfo.versionCode = packageInfo.versionCode;
            Drawable icon = packageInfo.applicationInfo.loadIcon(context.getPackageManager());
            if (icon != null) {
                tmpInfo.appIcon = DrawableUtil.drawableToByte(icon).trim().replaceAll("\n", "");
            } else {
                tmpInfo.appIcon = "";
            }
            appList.add(tmpInfo);
        }
        return appList;
    }
}
