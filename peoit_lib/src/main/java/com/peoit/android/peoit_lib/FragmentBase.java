package com.peoit.android.peoit_lib;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.peoit.android.peoit_lib.presenter.UIShowPresenter;
import com.peoit.android.peoit_lib.view.CustomActionBar;

/**
 * author:libo
 * time:2015/9/8
 * E-mail:boli_android@163.com
 * last: ...
 */
public interface FragmentBase {

    /**
     * 加载fragment界面
     *
     * @param inflater
     * @return
     */
    View createView(LayoutInflater inflater, Bundle savedInstanceState);

    void initData();

    void initView();

    void initListener();

    /**
     * 获取界面显示控制类
     *
     * @return
     */
    UIShowPresenter getUIShowPresenter();

    /**
     * 显示Toast
     *
     * @param msg
     */
    void showToast(CharSequence msg);

    /**
     * 获取toolbar
     *
     * @return
     */
    CustomActionBar getToolbar();

    /**
     * 回调activity
     *
     * @param callBack
     */
    void onCallBackToActivity(int callBack);

    /**
     * 回调activity， 并传递参数...
     *
     * @param callBack
     */
    void onCallBackToActivity(int callBack, Bundle callBackData);

    View findViewById(int resId);
}
