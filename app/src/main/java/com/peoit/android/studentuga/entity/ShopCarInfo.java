package com.peoit.android.studentuga.entity;

import android.text.TextUtils;

import java.io.Serializable;

/**
 * author:libo
 * time:2015/10/18
 * E-mail:boli_android@163.com
 * last: ...
 */
public class ShopCarInfo implements Serializable {
    /**
     * spec : 绿色，XL
     * createTime : 1443418513000
     * imgs : /upImg/201509271706093031503.jpg
     * status : yd
     * flg : Y
     * userid : 15
     * puserid : 15
     * pCommodityId : 0
     * number : 2
     * id : 9
     * title : 123
     * price : 2426.24
     * details : null
     * typeid : 2
     * commodityId : 9
     * orderId : 0
     * imgurl : /upImg/201509271706042641282.jpg
     */
    private String spec;
    private long createTime;
    private String imgs;
    private String status;
    private String flg;
    private long userid;
    private long puserid;
    private long pCommodityId;
    private int number;
    private long id;
    private String title;
    private double price;
    private String details;
    private int typeid;
    private long commodityId;
    private long orderId;
    private String ruserid;
    private String imgurl;

    private boolean isSelect = false;

    private boolean isChange = false;

    private String lastSpec;
    private int lastNumber;

    public String getRuserid() {
        return ruserid;
    }

    public void setRuserid(String ruserid) {
        this.ruserid = ruserid;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean isSelect) {
        this.isSelect = isSelect;
    }

    public void change() {
        lastNumber = number;
        lastSpec = spec;
    }

    public void restore() {
        number = lastNumber;
        spec = lastSpec;
    }

    public boolean isDataNoChange() {
        return lastNumber == number
                && TextUtils.isEmpty(lastSpec)
                && lastSpec.equals(spec);
    }

    public boolean toMinusCountAndIsZero() {
        if (number > 1) {
            number--;
            return false;
        }
        return true;
    }

    public void toAddCount() {
        ++number;
    }

    public void setSpec(String spec) {
        if (!TextUtils.isEmpty(spec) && !spec.equals(this.spec)) {
            this.spec = spec;
        }
    }

    public boolean isChange() {
        return isChange;
    }

    public void setChange(boolean isChange) {
        this.isChange = isChange;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public void setImgs(String imgs) {
        this.imgs = imgs;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setFlg(String flg) {
        this.flg = flg;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public void setPuserid(int puserid) {
        this.puserid = puserid;
    }

    public void setPCommodityId(int pCommodityId) {
        this.pCommodityId = pCommodityId;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public void setId(long id) {
        this.id = id;
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

    public void setCommodityId(long commodityId) {
        this.commodityId = commodityId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public String getSpec() {
        return spec;
    }

    public long getCreateTime() {
        return createTime;
    }

    public String getImgs() {
        return imgs;
    }

    public String getStatus() {
        return status;
    }

    public String getFlg() {
        return flg;
    }

    public long getUserid() {
        return userid;
    }

    public long getPuserid() {
        return puserid;
    }

    public long getPCommodityId() {
        return pCommodityId;
    }

    public int getNumber() {
        return number;
    }

    public long getId() {
        return id;
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

    public long getCommodityId() {
        return commodityId;
    }

    public long getOrderId() {
        return orderId;
    }

    public String getImgurl() {
        return imgurl;
    }

    @Override
    public String toString() {
        return "ShopCarInfo{" +
                "spec='" + spec + '\'' +
                ", createTime=" + createTime +
                ", imgs='" + imgs + '\'' +
                ", status='" + status + '\'' +
                ", flg='" + flg + '\'' +
                ", userid=" + userid +
                ", puserid=" + puserid +
                ", pCommodityId=" + pCommodityId +
                ", number=" + number +
                ", id=" + id +
                ", title='" + title + '\'' +
                ", price=" + price +
                ", details='" + details + '\'' +
                ", typeid=" + typeid +
                ", commodityId=" + commodityId +
                ", orderId=" + orderId +
                ", imgurl='" + imgurl + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ShopCarInfo)) return false;

        ShopCarInfo that = (ShopCarInfo) o;

        return id == that.id;

    }

    @Override
    public int hashCode() {
        return (int) id;
    }
}
