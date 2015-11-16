package com.peoit.android.studentuga.entity;

import java.io.Serializable;

/**
 * author:libo
 * time:2015/11/13
 * E-mail:boli_android@163.com
 * last: ...
 */
public class XiaoXiInfo implements Serializable {
    /**
     * uid : null
     * id : 4
     * text : 1231313213123
     * title : 123123
     * flg : Y
     * ctime : 1447168494000
     */
    private String uid;
    private long id;
    private String text;
    private String title;
    private String flg;
    private long ctime;

    private boolean isAdd = false;

    public boolean isAdd() {
        return isAdd;
    }

    public void setAdd(boolean isAdd) {
        this.isAdd = isAdd;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setFlg(String flg) {
        this.flg = flg;
    }

    public void setCtime(long ctime) {
        this.ctime = ctime;
    }

    public String getUid() {
        return uid;
    }

    public long getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public String getTitle() {
        return title;
    }

    public String getFlg() {
        return flg;
    }

    public long getCtime() {
        return ctime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof XiaoXiInfo)) return false;

        XiaoXiInfo that = (XiaoXiInfo) o;

        return id == that.id;

    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }

    @Override
    public String toString() {
        return "XiaoXiInfo{" +
                "uid='" + uid + '\'' +
                ", id=" + id +
                ", text='" + text + '\'' +
                ", title='" + title + '\'' +
                ", flg='" + flg + '\'' +
                ", ctime=" + ctime +
                '}';
    }
}
