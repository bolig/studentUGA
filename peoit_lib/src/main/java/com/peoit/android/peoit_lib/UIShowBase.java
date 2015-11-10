package com.peoit.android.peoit_lib;

import android.view.View;

/**
 * author:libo
 * time:2015/9/6
 * E-mail:boli_android@163.com
 * last: ...
 */
public interface UIShowBase {
//    /**
//     * 加载数据加载界面
//     *
//     * @return
//     */
//    View createLoadingLayout();
//
//    /**
//     * 加载错误局面
//     *
//     * @return
//     */
//    View createErrorLayout();

//    /**
//     * 初始化加载界面
//     *
//     */
//    void initLoadingLayout();
//
//    /**
//     * 初始化数据加载失败界面
//     *
//     */
//    void initErrorLayout();

    /**
     * 显示错误界面
     */
    View showErrorLayout();

    /**
     * 展示数据加载界面
     */
    View showLoadingLayout();

}
