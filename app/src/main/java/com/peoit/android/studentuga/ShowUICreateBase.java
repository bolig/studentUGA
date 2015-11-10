package com.peoit.android.studentuga;

import android.view.View;

/**
 * author:libo
 * time:2015/9/22
 * E-mail:boli_android@163.com
 * last: ...
 */
public interface ShowUICreateBase {

    View createLoadingView();

    View createErrorView();

    View getLoadingView();

    View getErrorView();

}
