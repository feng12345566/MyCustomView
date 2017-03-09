package com.fyc.admin.bean;

import io.realm.RealmObject;

/**
 * Created by Admin on 2017/3/8.
 */

public class UrlCollection extends RealmObject {
    private long id;
    private String url;
    private String title;
    private String img;
    private String desc;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }


    public void fromBlog(Blog blog) {
        this.title = blog.getArt_title();
        this.desc = blog.getArt_desc();
        this.img = blog.getArt_pic();
        this.url = blog.getArt_url();
    }
}
