package com.peoit.android.studentuga.callback;

import com.peoit.android.studentuga.entity.MyHoldOrderInfo;

import java.util.List;

public interface OnHoldOrderDataChangeListener {
    void onDataChange(List<MyHoldOrderInfo> holdOrderInfos);
}
