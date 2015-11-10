package com.peoit.android.studentuga.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * author:libo
 * time:2015/10/21
 * E-mail:boli_android@163.com
 * last: ...
 */
public class OrderInfo implements Serializable {
    /**
     * createTime : 1444288217123
     * kdTime : null
     * consignorName : 点点d
     * phone : 774
     * kdType : null
     * count : 2
     * zfType : cyb
     * status : xd
     * kdNo : null
     * remarks : null
     * type : zy
     * id : 4
     * iskd : N
     * title : 123
     * postalCode : 8888
     * isfk : N
     * fkNo : null
     * address : 分开多久亟待解决分
     * userId : 16
     * totalPrice : 2426.24
     */
    private long createTime = -1l;

    private String kdTime;

    private String consignorName;

    private String phone;

    private String kdType;

    private String count;

    private String zfType;

    private String status;

    private String kdNo;

    private String remarks;

    private String type;

    private long id = -1l;

    private String iskd;

    private String title;

    private String postalCode;

    private String isfk;

    private String fkNo;

    private String address;

    private int userId;

    private double totalPrice;

    private List<ShopCarInfo> products = new ArrayList<>();

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public void setKdTime(String kdTime) {
        this.kdTime = kdTime;
    }

    public void setConsignorName(String consignorName) {
        this.consignorName = consignorName;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setKdType(String kdType) {
        this.kdType = kdType;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public void setZfType(String zfType) {
        this.zfType = zfType;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setKdNo(String kdNo) {
        this.kdNo = kdNo;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setIskd(String iskd) {
        this.iskd = iskd;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public void setIsfk(String isfk) {
        this.isfk = isfk;
    }

    public void setFkNo(String fkNo) {
        this.fkNo = fkNo;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public long getCreateTime() {
        return createTime;
    }

    public String getKdTime() {
        return kdTime;
    }

    public String getConsignorName() {
        return consignorName;
    }

    public String getPhone() {
        return phone;
    }

    public String getKdType() {
        return kdType;
    }

    public String getCount() {
        return count;
    }

    public String getZfType() {
        return zfType;
    }

    public String getStatus() {
        return status;
    }

    public String getKdNo() {
        return kdNo;
    }

    public String getRemarks() {
        return remarks;
    }

    public String getType() {
        return type;
    }

    public long getId() {
        return id;
    }

    public String getIskd() {
        return iskd;
    }

    public String getTitle() {
        return title;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getIsfk() {
        return isfk;
    }

    public String getFkNo() {
        return fkNo;
    }

    public String getAddress() {
        return address;
    }

    public int getUserId() {
        return userId;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public List<ShopCarInfo> getProducts() {
        return products;
    }

    public void setProducts(List<ShopCarInfo> products) {
        this.products = products;
    }

    @Override
    public String toString() {
        return "OrderInfo{" +
                "createTime=" + createTime +
                ", kdTime='" + kdTime + '\'' +
                ", consignorName='" + consignorName + '\'' +
                ", phone='" + phone + '\'' +
                ", kdType='" + kdType + '\'' +
                ", count='" + count + '\'' +
                ", zfType='" + zfType + '\'' +
                ", status='" + status + '\'' +
                ", kdNo='" + kdNo + '\'' +
                ", remarks='" + remarks + '\'' +
                ", type='" + type + '\'' +
                ", id=" + id +
                ", iskd='" + iskd + '\'' +
                ", title='" + title + '\'' +
                ", postalCode='" + postalCode + '\'' +
                ", isfk='" + isfk + '\'' +
                ", fkNo='" + fkNo + '\'' +
                ", address='" + address + '\'' +
                ", userId=" + userId +
                ", totalPrice=" + totalPrice +
                ", products=" + products +
                '}';
    }

    public boolean isNull(){
        return id == -1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrderInfo)) return false;

        OrderInfo orderInfo = (OrderInfo) o;

        return id == orderInfo.id;

    }

    @Override
    public int hashCode() {
        return (int) id;
    }
}
