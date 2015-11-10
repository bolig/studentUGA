package com.peoit.android.studentuga.enuml;

import android.text.TextUtils;

/**
 * author:libo
 * time:2015/10/29
 * E-mail:boli_android@163.com
 * last: ...
 */
public enum TradeLogPayWay {
    error("", "无效操作"),
    cyb("cyb", "创业币"),
    zfb("zfb", "支付宝"),
    wx("wx", "微信"),
    sys("sys", "系统后台手动完成");

    public final String mType;
    public final String mTypeWay;

    TradeLogPayWay(String type, String typeWay) {
        this.mType = type;
        this.mTypeWay = typeWay;
    }

    public static TradeLogPayWay getPayWay(String type){
        if (TextUtils.isEmpty(type)) {
            return error;
        }
        for (int i = 0; i < values().length; i++) {
            TradeLogPayWay payWay = values()[i];
            if (type.equals(payWay.mType)) {
                return payWay;
            }
        }
        return error;
    }
}
