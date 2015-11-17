package com.peoit.android.studentuga.ui.showUI;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.peoit.android.studentuga.ShowUICreateBase;
import com.peoit.android.studentuga.ShowUIShowBase;

/**
 * author:libo
 * time:2015/9/22
 * E-mail:boli_android@163.com
 * last: ...
 */
public class BaseShowUiShow implements ShowUIShowBase {

    private final ShowUICreateBase mCreateBase;
    private final Context mContext;
    private final boolean isPreload;

    public ViewGroup mRootView;

    public BaseShowUiShow(Context context) {
        this(context, null, false);
    }

    public BaseShowUiShow(Context mContext, ShowUICreateBase mCreateBase) {
        this(mContext, mCreateBase, false);
    }

    public BaseShowUiShow(Context context, ShowUICreateBase createBase, boolean isPreload) {
        this.mContext = context;
        this.isPreload = isPreload;
        if (createBase != null) {
            this.mCreateBase = createBase;
        } else {
            this.mCreateBase = new SimpleShowUICreate(mContext);
        }
        if (isPreload) {
            this.mCreateBase.createErrorView();
            this.mCreateBase.createLoadingView();
        }
    }

    @Override
    public void setRootView(View rootView) {
        if (rootView == null)
            throw new NullPointerException(" @libo rootView is null ");
        if (!(rootView instanceof ViewGroup))
            throw new RuntimeException(" @libo rootView not cast to ViewGroup ");
        mRootView = (ViewGroup) rootView;
        if (isPreload) {
            mRootView.addView(mCreateBase.getErrorView());
            mRootView.addView(mCreateBase.getLoadingView());
        }
    }

    @Override
    public View createLoadingView() {
        return mCreateBase.createLoadingView();
    }

    @Override
    public View createErrorView() {
        return mCreateBase.createErrorView();
    }

    @Override
    public View getLoadingView() {
        return mCreateBase.getLoadingView();
    }

    @Override
    public View getErrorView() {
        return mCreateBase.getErrorView();
    }

    @Override
    public void showLoading() {
        if (mRootView == null)
            throw new NullPointerException(" @libo rootView is null ");

        if (mRootView.getVisibility() != View.VISIBLE)
            mRootView.setVisibility(View.VISIBLE);

        if (mCreateBase.getLoadingView() == null) {
            mRootView.addView(mCreateBase.createLoadingView());
        } else {
            mCreateBase.getLoadingView().setVisibility(View.VISIBLE);
        }
        if (mCreateBase.getErrorView() != null)
            mCreateBase.getErrorView().setVisibility(View.GONE);
    }

    @Override
    public void showError() {
        if (mRootView == null)
            throw new NullPointerException(" @libo rootView is null ");

        if (mRootView.getVisibility() != View.VISIBLE)
            mRootView.setVisibility(View.VISIBLE);

        if (mCreateBase.getErrorView() == null) {
            mRootView.addView(mCreateBase.createErrorView());
        } else {
            mCreateBase.getErrorView().setVisibility(View.VISIBLE);
        }
        if (mCreateBase.getLoadingView() != null)
            mCreateBase.getLoadingView().setVisibility(View.GONE);
    }

    @Override
    public void showData() {
        if (mRootView == null)
            throw new NullPointerException(" @libo rootView is null ");

        if (mRootView.getVisibility() == View.VISIBLE)
            mRootView.setVisibility(View.GONE);
    }

    @Override
    public ShowUICreateBase getUICreateBase() {
        return mCreateBase;
    }

    @Override
    public boolean isShowing() {
        if (mCreateBase.getLoadingView() != null) {
            if (mCreateBase.getLoadingView().getVisibility() == View.VISIBLE)
                return true;
        }
        if (mCreateBase.getErrorView() != null) {
            if (mCreateBase.getErrorView().getVisibility() == View.VISIBLE)
                return true;
        }
        return false;
    }
}
