package com.peoit.android.studentuga.entity;

import java.io.Serializable;

/**
 * author:libo
 * time:2015/11/1
 * E-mail:boli_android@163.com
 * last: ...
 */
public class LiuCommonInfo implements Serializable {


    /**
     * id : 1
     * text : null
     * phone : 18623101684
     * stime : 1444501508000
     * flg : Y
     * name : 狗二蛋
     * userid : 16
     * pic : null
     * toid : 16
     */
    private long id;
    private String text;
    private long phone;
    private long stime;
    private String flg;
    private String name;
    private long userid;
    private String pic;
    private String imgs;
    private long toid;

    public void setId(long id) {
        this.id = id;
    }

    public void setUserid(long userid) {
        this.userid = userid;
    }

    public String getImgs() {
        return imgs;
    }

    public void setImgs(String imgs) {
        this.imgs = imgs;
    }

    public void setToid(long toid) {
        this.toid = toid;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setPhone(long phone) {
        this.phone = phone;
    }

    public void setStime(long stime) {
        this.stime = stime;
    }

    public void setFlg(String flg) {
        this.flg = flg;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public void setToid(int toid) {
        this.toid = toid;
    }

    public long getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public long getPhone() {
        return phone;
    }

    public long getStime() {
        return stime;
    }

    public String getFlg() {
        return flg;
    }

    public String getName() {
        return name;
    }

    public long getUserid() {
        return userid;
    }

    public String getPic() {
        return pic;
    }

    public long getToid() {
        return toid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LiuCommonInfo)) return false;

        LiuCommonInfo that = (LiuCommonInfo) o;

        return id == that.id;

    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }

    @Override
    public String toString() {
        return "LiuCommonInfo{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", phone=" + phone +
                ", stime=" + stime +
                ", flg='" + flg + '\'' +
                ", name='" + name + '\'' +
                ", userid=" + userid +
                ", pic='" + pic + '\'' +
                ", toid=" + toid +
                '}';
    }
}
