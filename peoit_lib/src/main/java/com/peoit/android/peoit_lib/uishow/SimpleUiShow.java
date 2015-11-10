package com.peoit.android.peoit_lib.uishow;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.peoit.android.peoit_lib.R;
import com.peoit.android.peoit_lib.base.BaseUIShow;

/**
 * author:libo
 * time:2015/9/6
 * E-mail:boli_android@163.com
 * last: ...
 */
public class SimpleUiShow extends BaseUIShow {

    public SimpleUiShow(Context context) {
        super(context);
    }

    @Override
    protected View createErrorLayout(LayoutInflater inflater) {
        return inflater.inflate(R.layout.layout_nodata, null);
    }

    @Override
    protected View createLoadingLayout(LayoutInflater inflater) {
        return inflater.inflate(R.layout.layout_loading, null);
    }

    @Override
    protected void initLoadingLayout(View loadingLayout) {

    }

    @Override
    protected void initErrorLayout(View errorLayout) {

    }
}
