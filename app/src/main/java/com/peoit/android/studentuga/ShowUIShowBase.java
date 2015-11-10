package com.peoit.android.studentuga;

import android.view.View;

/**
 * author:libo
 * time:2015/9/22
 * E-mail:boli_android@163.com
 * last: ...
 */
public interface ShowUIShowBase {

    ShowUICreateBase getUICreateBase();

    void setRootView(View rootView);

    View createLoadingView();

    View createErrorView();

    View getLoadingView();

    View getErrorView();

    void showLoading();

    void showError();

    void showData();

    boolean isShowing();
}
