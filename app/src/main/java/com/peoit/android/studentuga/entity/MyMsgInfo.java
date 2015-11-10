package com.peoit.android.studentuga.entity;

import java.io.Serializable;

/**
 * 我的留言实体类
 * <p/>
 * author:libo
 * time:2015/9/18
 * E-mail:boli_android@163.com
 * last: ...
 */
public class MyMsgInfo implements Serializable {

    private String title = "libo";

    private String time = "2015-9-18";

    private String content = "测试";

    private String imgUrl = "http://space.flash8.net/bbs/attachments/oldupload/upload/2005-5/200557113314249.jpg";

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    @Override
    public String toString() {
        return "MyMsgInfo{" +
                "title='" + title + '\'' +
                ", time='" + time + '\'' +
                ", content='" + content + '\'' +
                ", imgUrl='" + imgUrl + '\'' +
                '}';
    }
}
