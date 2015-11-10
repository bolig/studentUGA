package com.peoit.android.studentuga.entity;

/**
 * author:libo
 * time:2015/9/9
 * E-mail:boli_android@163.com
 * last: ...
 */
public class HomeListItemInfo {

    private String imgUrl = "http://img.taopic.com/uploads/allimg/130501/240451-13050106450911.jpg";

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    @Override
    public String toString() {
        return "HomeListItemInfo{" +
                "imgUrl='" + imgUrl + '\'' +
                '}';
    }
}
