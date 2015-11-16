package com.peoit.android.studentuga.entity;

import java.io.Serializable;

/**
 * author:libo
 * time:2015/9/30
 * E-mail:boli_android@163.com
 * last: ...
 */
public class AddressInfo implements Serializable {
    /**
     * id : 1
     * consignorName : 段鸿
     * postalCode : 110
     * isdefault : Y
     * county : 沙坪坝区
     * address : 重庆沙坪坝区
     * flg : Y
     * userId : 15
     * municipality : 重庆市
     * province : 重庆市
     * idcard : 5116221918808076432
     * telephone : 18623101684
     */
    private int id = -1;
    private String consignorName;
    private String postalCode;
    private String isdefault;
    private String county;
    private String address;
    private String flg;
    private int userId;
    private String municipality;
    private String province;
    private String idcard;
    private String telephone;



    public boolean isNull() {
        return id == -1;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setConsignorName(String consignorName) {
        this.consignorName = consignorName;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public void setIsdefault(String isdefault) {
        this.isdefault = isdefault;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setFlg(String flg) {
        this.flg = flg;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setMunicipality(String municipality) {
        this.municipality = municipality;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public int getId() {
        return id;
    }

    public String getConsignorName() {
        return consignorName;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getIsdefault() {
        return isdefault;
    }

    public String getCounty() {
        return county;
    }

    public String getAddress() {
        return address;
    }

    public String getFlg() {
        return flg;
    }

    public int getUserId() {
        return userId;
    }

    public String getMunicipality() {
        return municipality;
    }

    public String getProvince() {
        return province;
    }

    public String getIdcard() {
        return idcard;
    }

    public String getTelephone() {
        return telephone;
    }

    /**
     * 判断当前地址是不是默认地址
     *
     * @return
     */
    public boolean isDefault() {
        return "Y".equals(isdefault);
    }

    /**
     * 设置默认地址
     *
     * @param isDefault
     */
    public void setDefault(boolean isDefault) {
        this.isdefault = isDefault ? "Y" : "N";
    }

    @Override
    public String toString() {
        return "AddressInfo{" +
                "id=" + id +
                ", consignorName='" + consignorName + '\'' +
                ", postalCode='" + postalCode + '\'' +
                ", isdefault='" + isdefault + '\'' +
                ", county='" + county + '\'' +
                ", address='" + address + '\'' +
                ", flg='" + flg + '\'' +
                ", userId=" + userId +
                ", municipality='" + municipality + '\'' +
                ", province='" + province + '\'' +
                ", idcard='" + idcard + '\'' +
                ", telephone='" + telephone + '\'' +
                '}';
    }
}
