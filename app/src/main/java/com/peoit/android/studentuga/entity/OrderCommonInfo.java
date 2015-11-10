package com.peoit.android.studentuga.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * author:libo
 * time:2015/11/1
 * E-mail:boli_android@163.com
 * last: ...
 */
public class OrderCommonInfo implements Serializable {
    /**
     * spec : 绿色，XL
     * createTime : 1443418513000
     * imgs : /upImg/201509271706093031503.jpg
     * status : yd
     * flg : Y
     * isly : Y
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
    private String isly;
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
    private String imgurl;

    public List<ShopCarInfo> getShopCarList() {
        List<ShopCarInfo> infos = new ArrayList<>();
        ShopCarInfo info = new ShopCarInfo();
        info.setImgurl(imgurl);
        info.setImgs(imgs);
        info.setPrice(price);
        info.setSpec(spec);
        info.setNumber(number);
        info.setTitle(title);
        info.setCommodityId(commodityId);
        info.setCreateTime(createTime);
        info.setDetails(details);
        info.setOrderId(orderId);
        infos.add(info);
        return infos;
    }

    public void setSpec(String spec) {
        this.spec = spec;
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

    public void setIsly(String isly) {
        this.isly = isly;
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

    public void setId(int id) {
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

    public void setCommodityId(int commodityId) {
        this.commodityId = commodityId;
    }

    public void setOrderId(int orderId) {
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

    public String getIsly() {
        return isly;
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
        return "OrderCommonInfo{" +
                "spec='" + spec + '\'' +
                ", createTime=" + createTime +
                ", imgs='" + imgs + '\'' +
                ", status='" + status + '\'' +
                ", flg='" + flg + '\'' +
                ", isly='" + isly + '\'' +
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
}
