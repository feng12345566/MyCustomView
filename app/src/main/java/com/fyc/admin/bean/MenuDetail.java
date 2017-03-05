package com.fyc.admin.bean;

/**
 * Created by Admin on 2017/3/4.
 */

public class MenuDetail {
    private String name;
    private int pageType;
    private String url;
    private String extra;

    public MenuDetail(String name, int pageType, String url) {
        this.name = name;
        this.pageType = pageType;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPageType() {
        return pageType;
    }

    public void setPageType(int pageType) {
        this.pageType = pageType;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }


    public class PageType {
        public static final int LINK = 1;
        public static final int APK = 2;
        public static final int ACTIVITY = 3;
    }
}
