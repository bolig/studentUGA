package com.peoit.android.studentuga.enuml;

import android.text.TextUtils;

/**
 * author:libo
 * time:2015/10/27
 * E-mail:boli_android@163.com
 * last: ...
 */
public enum OrderStatus {
    status_fail("fail", "", "", -1, ""),

    status_xiadan("xd", "支付", "未付款", 2, ""),
    status_fukuan("fk", "", "已付款, 待发货", 0, "等待卖家发货..."),
    status_cancel("qx", "", "已取消", 1, ""),
    status_jichu("jc", "确认收货", "已发货, 待签收", 0, "卖家已发货, 等待收货..."),
    status_wancheng("wc", "", "交易完成", 0, "已确认收货, 交易完成");

    public final String mOrderStatus;
    public final String mOkButtonText;
    public final String mMark;
    public final int mType;
    public final String mHintText;

    OrderStatus(String status, String okButtonText, String mark, int type, String hintText) {
        this.mOrderStatus = status;
        this.mOkButtonText = okButtonText;
        this.mMark = mark;
        this.mType = type;
        this.mHintText = hintText;
    }

    public static OrderStatus getOrderStatus(String status) {
        if (TextUtils.isEmpty(status))
            return status_fail;
        for (int i = 0; i < values().length; i++) {
            OrderStatus stat = values()[i];
            if (status.equals(stat.mOrderStatus)) {
                return stat;
            }
        }
        return status_fail;
    }
}
