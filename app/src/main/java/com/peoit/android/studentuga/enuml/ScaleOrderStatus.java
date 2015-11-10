package com.peoit.android.studentuga.enuml;

import android.text.TextUtils;

/**
 * author:libo
 * time:2015/10/27
 * E-mail:boli_android@163.com
 * last: ...
 */
public enum ScaleOrderStatus {
    status_fail("fail", "", "", -1, ""),

    status_xiadan("xd", "", "未付款", 0, "等待买家付款..."),
    status_fukuan("fk", "发货", "已付款, 待发货", 0, ""),
    status_cancel("qx", "", "买家已取消", 1, ""),
    status_jichu("jc", "", "已发货, 待签收", 0, "等待买家签收..."),
    status_wancheng("wc", "", "交易完成", 0, "交易完成");

    public final String mOrderStatus;
    public final String mOkButtonText;
    public final String mMark;
    public final int mType;
    public final String mHintText;

    ScaleOrderStatus(String status, String okButtonText, String mark, int type, String hintText) {
        this.mOrderStatus = status;
        this.mOkButtonText = okButtonText;
        this.mMark = mark;
        this.mType = type;
        this.mHintText = hintText;
    }

    public static ScaleOrderStatus getOrderStatus(String status) {
        if (TextUtils.isEmpty(status))
            return status_fail;
        for (int i = 0; i < values().length; i++) {
            ScaleOrderStatus stat = values()[i];
            if (status.equals(stat.mOrderStatus)) {
                return stat;
            }
        }
        return status_fail;
    }
}
