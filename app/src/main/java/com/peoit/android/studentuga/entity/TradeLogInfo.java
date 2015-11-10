package com.peoit.android.studentuga.entity;

/**
 * author:libo
 * time:2015/10/29
 * E-mail:boli_android@163.com
 * last: ...
 */
public class TradeLogInfo {
    /**
     * id : 4
     * price : 10000000
     * stime : 1444481430000
     * paytype : sys
     * userid : 16
     * type : 1
     */
    private int id;
    private double price;
    private long stime;
    private String paytype;
    private int userid;
    private int type;

    public void setId(int id) {
        this.id = id;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setStime(long stime) {
        this.stime = stime;
    }

    public void setPaytype(String paytype) {
        this.paytype = paytype;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public double getPrice() {
        return price;
    }

    public long getStime() {
        return stime;
    }

    public String getPaytype() {
        return paytype;
    }

    public int getUserid() {
        return userid;
    }

    public int getType() {
        return type;
    }

    @Override
    public String toString() {
        return "TradeLogInfo{" +
                "id=" + id +
                ", price=" + price +
                ", stime=" + stime +
                ", paytype='" + paytype + '\'' +
                ", userid=" + userid +
                ", type=" + type +
                '}';
    }
}
