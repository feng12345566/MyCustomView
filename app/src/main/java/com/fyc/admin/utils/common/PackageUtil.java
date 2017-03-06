package com.fyc.admin.utils.common;

import android.content.Context;
import android.content.pm.ApplicationInfo;
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
            if (isUserApp(packageInfo)) {
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
        }
        return appList;
    }


    /**
     * 是否是系统软件或者是系统软件的更新软件
     *
     * @return
     */
    public static boolean isSystemApp(PackageInfo pInfo) {
        return ((pInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0);
    }

    public static boolean isSystemUpdateApp(PackageInfo pInfo) {
        return ((pInfo.applicationInfo.flags & ApplicationInfo.FLAG_UPDATED_SYSTEM_APP) != 0);
    }

    public static boolean isUserApp(PackageInfo pInfo) {
        return (!isSystemApp(pInfo) && !isSystemUpdateApp(pInfo));
    }
}
