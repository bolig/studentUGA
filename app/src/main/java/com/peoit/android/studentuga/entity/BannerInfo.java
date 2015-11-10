package com.peoit.android.studentuga.entity;

import java.io.Serializable;

/**
 * 首页图片
 * <p/>
 * author:libo
 * time:2015/9/8
 * E-mail:boli_android@163.com
 * last: ...
 */
public class BannerInfo implements Serializable {

    /**
     * id : 1
     * text : 测试1
     * title : 标题
     *  url :  /upload/20150831005057634.jpg
     */
    private int id;
    private String text;
    private String title;
    private String url;

    public void setId(int id) {
        this.id = id;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "BannerInfo{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", title='" + title + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
