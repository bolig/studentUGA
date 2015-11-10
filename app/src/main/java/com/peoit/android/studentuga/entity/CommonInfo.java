package com.peoit.android.studentuga.entity;

import java.io.Serializable;

/**
 * author:libo
 * time:2015/11/1
 * E-mail:boli_android@163.com
 * last: ...
 */
public class CommonInfo implements Serializable {
    /**
     * id : 1
     * text : null
     * phone : 18623101684
     * stime : 1444501508000
     * flg : Y
     * name : 狗二蛋
     * star : 5
     * userid : 16
     * pic : null
     * type : com
     * toid : 16
     */
    private long id;
    private String text;
    private long phone;
    private long stime;
    private String flg;
    private String name;
    private int star;
    private long userid;
    private String pic;
    private String imgs;
    private String type;
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

    public void setStar(int star) {
        this.star = star;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public void setType(String type) {
        this.type = type;
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

    public int getStar() {
        return star;
    }

    public long getUserid() {
        return userid;
    }

    public String getPic() {
        return pic;
    }

    public String getType() {
        return type;
    }

    public long getToid() {
        return toid;
    }

    @Override
    public String toString() {
        return "CommonInfo{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", phone=" + phone +
                ", stime=" + stime +
                ", flg='" + flg + '\'' +
                ", name='" + name + '\'' +
                ", star=" + star +
                ", userid=" + userid +
                ", pic='" + pic + '\'' +
                ", type='" + type + '\'' +
                ", toid=" + toid +
                '}';
    }
}
