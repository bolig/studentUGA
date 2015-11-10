package com.peoit.android.peoit_lib.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.peoit.android.peoit_lib.UIShowBase;

/**
 * author:libo
 * time:2015/9/6
 * E-mail:boli_android@163.com
 * last: ...
 */
public abstract class BaseUIShow implements UIShowBase {
    protected final Context mContext;
    protected final LayoutInflater inflater;
    protected OnReloadListener onReloadListener;
    protected OnCancelListener onCancelListener;

    protected View loadingLayout;
    protected View errorLayout;

    public BaseUIShow(Context context) {
        this.mContext = context;
        this.inflater = LayoutInflater.from(mContext);
    }

    protected abstract View createErrorLayout(LayoutInflater inflater);

    protected abstract View createLoadingLayout(LayoutInflater inflater);

    public void setOnReloadListener(OnReloadListener listener) {
        this.onReloadListener = listener;
    }

    public void setOnCancelListener(OnCancelListener listener) {
        this.onCancelListener = listener;
    }

    public interface OnReloadListener {
        void onReload();
    }

    public interface OnCancelListener {
        void onCancel();
    }

    @Override
    public View showLoadingLayout() {
        loadingLayout = createLoadingLayout(inflater);
        initLoadingLayout(loadingLayout);
        return loadingLayout;
    }

    protected abstract void initLoadingLayout(View loadingLayout);

    @Override
    public View showErrorLayout() {
        errorLayout = createErrorLayout(inflater);
        initErrorLayout(errorLayout);
        return errorLayout;
    }

    protected abstract void initErrorLayout(View errorLayout);
}
