package com.peoit.android.studentuga.entity;

import java.io.Serializable;

/**
 * author:libo
 * time:2015/11/4
 * E-mail:boli_android@163.com
 * last: ...
 */
public class SelectImgInfo implements Serializable {

    private String imgPath;

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SelectImgInfo)) return false;

        SelectImgInfo that = (SelectImgInfo) o;

        return !(imgPath != null ? !imgPath.equals(that.imgPath) : that.imgPath != null);

    }

    @Override
    public int hashCode() {
        return imgPath != null ? imgPath.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "SelectImgInfo{" +
                "imgPath='" + imgPath + '\'' +
                '}';
    }
}
