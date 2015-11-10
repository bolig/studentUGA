package com.peoit.android.peoit_lib;

import android.app.Activity;
import android.app.Dialog;
import android.view.View;

import com.peoit.android.peoit_lib.presenter.UIShowPresenter;
import com.peoit.android.peoit_lib.view.CustomActionBar;

/**
 * author:libo
 * time:2015/9/6
 * E-mail:boli_android@163.com
 * last: ...
 */
public interface ActivityBase {

    /**
     * 初始化数据
     */
    void initData();

    /**
     * 初始化界面控件
     */
    void initView();

    /**
     * 初始化事件监听
     */
    void initListener();

    /**
     * 为控件添加数据
     */
    void doUpdataView();

    /**
     * 获取activity对象
     *
     * @return
     */
    Activity getActivity();

    /**
     * 设置界面布局, 通过界面id
     *
     * @param layoutId
     */
    void setContentView(int layoutId);

    /**
     * 设置界面布局，通过View
     *
     * @param contentView
     */
    void setContentView(View contentView);

    /**
     * 设置主背景, 通过layoutid
     *
     * @param layoutId
     */
    void setBaseContentView(int layoutId);

    /**
     * 设置主背景，通过View
     *
     * @param contentView
     */
    void setBaseContentView(View contentView);

    /**
     * 显示Toast
     *
     * @param msg
     */
    void showToast(CharSequence msg);

    /**
     * 显示自定义Toast
     *
     * @param msg
     */
    void showCustomToast(CharSequence msg);

    /**
     * 显示自定义Toast, 并指定位置
     *
     * @param msg
     */
    void showCustomToast(CharSequence msg, int gravity);

    /**
     * 初始化toolbar
     */
    void initToolbar();

    /**
     * 获取UI显示控制类
     *
     * @return
     */
    UIShowPresenter getUIShowPresenter();

    /**
     * 获取toolbar
     *
     * @return
     */
    CustomActionBar getToolbar();

    /**
     * 显示loadingDialog
     *
     * @param msg
     * @return
     */
    Dialog showLoadingDialog(String msg);

    /**
     * 隐藏loadingDialog
     */
    void hideLoadingDialog();

    void onResponseFailure(int errorCode, String errorMsg);
}
