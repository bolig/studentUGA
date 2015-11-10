package com.peoit.android.peoit_lib.presenter;

import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;

import com.peoit.android.peoit_lib.ActivityBase;
import com.peoit.android.peoit_lib.base.BasePresenter;
import com.peoit.android.peoit_lib.base.BaseUIShow;
import com.peoit.android.peoit_lib.uishow.MyUIShow;

/**
 * 加载界面和错误界面控制类
 * <p/>
 * author:libo
 * time:2015/9/6
 * E-mail:boli_android@163.com
 * last: ...
 */
public class UIShowPresenter extends BasePresenter {

    private final FrameLayout uiShow_layout;
    private final BaseUIShow uishow;
    private View loading_layout;
    private View error_layout;

    public UIShowPresenter(ActivityBase mActBase, FrameLayout fl_uishow) {
        super(mActBase);
        this.uiShow_layout = fl_uishow;
        this.uishow = new MyUIShow(mActBase.getActivity());
    }

    /**
     * 展示加载界面
     */
    public void showLoading() {
        if (uiShow_layout == null)
            return;
        if (loading_layout == null) {
            loading_layout = uishow.showLoadingLayout();
            uiShow_layout.addView(loading_layout);
        } else {
            loading_layout.setVisibility(View.VISIBLE);
        }
        if (error_layout != null) {
            error_layout.setVisibility(View.GONE);
        }
    }

    /**
     * 展示加载失败界面
     */
    public void showError() {
        showError(-1, null);
    }

    /**
     * 展示加载失败界面
     */
    public void showError(int resId, String msg) {
        if (uiShow_layout == null)
            return;
        if (resId > 0) {
            ((MyUIShow) uishow).setErrorImag(resId);
        }
        if (!TextUtils.isEmpty(msg)) {
            ((MyUIShow) uishow).setErrorMsg(msg);
        }
        if (error_layout == null) {
            error_layout = uishow.showErrorLayout();
            uiShow_layout.addView(error_layout);
        } else {
            error_layout.setVisibility(View.VISIBLE);
        }
        if (loading_layout != null) {
            loading_layout.setVisibility(View.GONE);
        }
    }

    /**
     * 展示加载失败界面
     */
    public void showError(int resId) {
        showError(resId, null);
    }

    public void setReLoad(String reLoadText, BaseUIShow.OnReloadListener listener) {
        if (!TextUtils.isEmpty(reLoadText)) {
            ((MyUIShow) uishow).setReloadText(reLoadText);
        }
        ((MyUIShow) uishow).setOnReloadListener(listener);
    }

    /**
     * 展示数据界面
     */
    public void showData() {
        if (loading_layout != null) {
            loading_layout.setVisibility(View.GONE);
        }
        if (error_layout != null) {
            error_layout.setVisibility(View.GONE);
        }
    }

    public BaseUIShow getUIShowView() {
        return uishow;
    }

    public void setOnReloadListener(BaseUIShow.OnReloadListener listener) {
        uishow.setOnReloadListener(listener);
    }

    public void setOnCancelListener(BaseUIShow.OnCancelListener listener) {
        uishow.setOnCancelListener(listener);
    }
}
