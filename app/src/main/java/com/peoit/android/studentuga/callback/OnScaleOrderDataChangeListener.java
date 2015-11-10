package com.peoit.android.studentuga.callback;

import com.peoit.android.studentuga.entity.MyScaleOrderInfo;

import java.util.List;

/**
 * author:libo
 * time:2015/9/16
 * E-mail:boli_android@163.com
 * last: ...
 */
public interface OnScaleOrderDataChangeListener {
    void onDataChange(List<MyScaleOrderInfo> scaleOrderInfos);
}
