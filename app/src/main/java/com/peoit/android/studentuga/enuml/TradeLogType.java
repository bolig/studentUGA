package com.peoit.android.studentuga.enuml;

/**
 * author:libo
 * time:2015/10/29
 * E-mail:boli_android@163.com
 * last: ...
 */
public enum TradeLogType {
    error(0, "无效操作", false, "无效操作"),

    payUp(1, "充值", true, "+"),
    buyGoods(2, "购买商品", false, ""),
    sellGoods(3, "销售商品", true, "+"),
    embody(4, "申请提现", false, "");


    public final int mType;
    public final String mTypeInfo;
    public final boolean mSelect;
    public final String mMArk;

    TradeLogType(int type, String typeInfo, boolean select, String mark) {
        this.mType = type;
        this.mTypeInfo = typeInfo;
        this.mSelect = select;
        this.mMArk = mark;
    }

    public static TradeLogType getType(int type) {
        for (int i = 0; i < values().length; i++) {
            TradeLogType logType = values()[i];
            if (logType.mType == type) {
                return logType;
            }
        }
        return error;
    }
}
