package com.peoit.android.studentuga.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * author:libo
 * time:2015/11/9
 * E-mail:boli_android@163.com
 * last: ...
 */
public class WeiXinInfo implements Serializable {
    private String appid;
    private String noncestr;
    private String timestamp;
    @SerializedName("package")
    private String packag;
    private String app_signature;
    private String sign_method;

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getNoncestr() {
        return noncestr;
    }

    public void setNoncestr(String noncestr) {
        this.noncestr = noncestr;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getPackag() {
        return packag;
    }

    public void setPackag(String packag) {
        this.packag = packag;
    }

    public String getApp_signature() {
        return app_signature;
    }

    public void setApp_signature(String app_signature) {
        this.app_signature = app_signature;
    }

    public String getSign_method() {
        return sign_method;
    }

    public void setSign_method(String sign_method) {
        this.sign_method = sign_method;
    }

    @Override
    public String toString() {
        return "WeiXinInfo{" +
                "appid='" + appid + '\'' +
                ", noncestr='" + noncestr + '\'' +
                ", timestamp='" + timestamp + '\'' +
                ", packag='" + packag + '\'' +
                ", app_signature='" + app_signature + '\'' +
                ", sign_method='" + sign_method + '\'' +
                '}';
    }
}
