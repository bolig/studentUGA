package com.peoit.android.studentuga.callback;

import android.support.v4.app.Fragment;

import java.util.List;

/**
 * author:libo
 * time:2015/9/16
 * E-mail:boli_android@163.com
 * last: ...
 */
public interface MyOrderBase {
    /**
     * 下载我的订单(包括拿货、售货订单)
     */
    void toLoadMyOrder();

    /**
     * 获取显示的Fragment
     *
     * @return
     */
    List<Fragment> getFragment();


}
