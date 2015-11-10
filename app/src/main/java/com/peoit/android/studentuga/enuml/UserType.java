package com.peoit.android.studentuga.enuml;

/**
 * author:libo
 * time:2015/10/30
 * E-mail:boli_android@163.com
 * last: ...
 */
public enum UserType {
    error(-1),

    shangJia(0),
    youKe(1),
    xueSheng(2),
    daoShi(3),
    xueShengHui(4);

    private final int mType;

    UserType(int type) {
        this.mType = type;
    }

    public static UserType getUserType(int type){
        for (int i = 0; i < values().length; i++) {
            if (values()[i].mType == type){
                return values()[i];
            }
        }
        return error;
    }
}
