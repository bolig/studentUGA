package com.peoit.android.studentuga.ui.showUI;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.peoit.android.studentuga.ShowUICreateBase;

/**
 * author:libo
 * time:2015/9/22
 * E-mail:boli_android@163.com
 * last: ...
 */
public abstract class BaseShowUICreate implements ShowUICreateBase {

    private final Context mContext;
    private final LayoutInflater mInflater;

    private View loadingView;
    private View errorView;

    public BaseShowUICreate(Context context) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public View createLoadingView() {
        loadingView = mInflater.inflate(createLoadingViewByLayoutId(), null);
        initLoadingView(loadingView);
        return loadingView;
    }

    protected abstract void initLoadingView(View loadingView);

    protected abstract int createLoadingViewByLayoutId();

    @Override
    public View createErrorView() {
        errorView = mInflater.inflate(createErrorViewByLayoutId(), null);
        initErrorView(errorView);
        return errorView;
    }

    protected abstract void initErrorView(View errorView);

    protected abstract int createErrorViewByLayoutId();

    public Context getmContext() {
        return mContext;
    }

    public LayoutInflater getmInflater() {
        return mInflater;
    }

    public View getLoadingView() {
        return loadingView;
    }

    public View getErrorView() {
        return errorView;
    }
}
