package com.peoit.android.studentuga.entity;

import java.io.Serializable;

/**
 * author:libo
 * time:2015/10/10
 * E-mail:boli_android@163.com
 * last: ...
 */
public class GoodsInfo implements Serializable {
    /**
     * imgs : /upImg/201509271706093031503.jpg
     * stime : null
     * count : 123
     * state : Y
     * userid : 15
     * puserid : 15
     * sellcount : 0
     * pid : 0
     * id : 9
     * isstick : N
     * title : 123
     * price : 1213.12
     * details : null
     * typeid : 2
     * salescount : 0
     * imgurl : /upImg/201509271706042641282.jpg
     */
    private String imgs;
    private String stime;
    private int count;
    private String state;
    private int userid;
    private int puserid;
    private int sellcount;
    private int pid;
    private long id;
    private String isstick;
    private String title;
    private double price;
    private String details;
    private int typeid;
    private int salescount;
    private String imgurl;
    private long viewcount;

    public long getViewcount() {
        return viewcount;
    }

    public void setViewcount(long viewcount) {
        this.viewcount = viewcount;
    }

    public void setImgs(String imgs) {
        this.imgs = imgs;
    }

    public void setStime(String stime) {
        this.stime = stime;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public void setPuserid(int puserid) {
        this.puserid = puserid;
    }

    public void setSellcount(int sellcount) {
        this.sellcount = sellcount;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setIsstick(String isstick) {
        this.isstick = isstick;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public void setTypeid(int typeid) {
        this.typeid = typeid;
    }

    public void setSalescount(int salescount) {
        this.salescount = salescount;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public String getImgs() {
        return imgs;
    }

    public String getStime() {
        return stime;
    }

    public int getCount() {
        return count;
    }

    public String getState() {
        return state;
    }

    public int getUserid() {
        return userid;
    }

    public int getPuserid() {
        return puserid;
    }

    public int getSellcount() {
        return sellcount;
    }

    public int getPid() {
        return pid;
    }

    public long getId() {
        return id;
    }

    public String getIsstick() {
        return isstick;
    }

    public String getTitle() {
        return title;
    }

    public double getPrice() {
        return price;
    }

    public String getDetails() {
        return details;
    }

    public int getTypeid() {
        return typeid;
    }

    public int getSalescount() {
        return salescount;
    }

    public String getImgurl() {
        return imgurl;
    }

    @Override
    public String toString() {
        return "GoodsInfo{" +
                "imgs='" + imgs + '\'' +
                ", stime='" + stime + '\'' +
                ", count=" + count +
                ", state='" + state + '\'' +
                ", userid=" + userid +
                ", puserid=" + puserid +
                ", sellcount=" + sellcount +
                ", pid=" + pid +
                ", id=" + id +
                ", isstick='" + isstick + '\'' +
                ", title='" + title + '\'' +
                ", price=" + price +
                ", details='" + details + '\'' +
                ", typeid=" + typeid +
                ", salescount=" + salescount +
                ", imgurl='" + imgurl + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GoodsInfo)) return false;

        GoodsInfo info = (GoodsInfo) o;

        return id == info.id;

    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }
}
