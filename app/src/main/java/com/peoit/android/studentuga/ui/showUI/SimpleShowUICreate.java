package com.peoit.android.studentuga.ui.showUI;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.peoit.android.peoit_lib.view.CircleImageView;
import com.peoit.android.studentuga.R;

/**
 * author:libo
 * time:2015/9/22
 * E-mail:boli_android@163.com
 * last: ...
 */
public class SimpleShowUICreate extends BaseShowUICreate {
    private CircleImageView ivErrorImg;
    private TextView tvErrorMsg;
    private TextView tvReload;

    private int mResId = -1;
    private String mErrorMsg;
    private String mLoadText;
    private View.OnClickListener mListener;
    private boolean isReload;

    public SimpleShowUICreate(Context context) {
        super(context);
    }

    @Override
    protected int createLoadingViewByLayoutId() {
        return R.layout.layout_loading1;
    }

    @Override
    protected int createErrorViewByLayoutId() {
        return R.layout.layout_error1;
    }

    @Override
    public void initLoadingView(View loadingView) {

    }

    @Override
    public void initErrorView(View errorView) {
        ivErrorImg = (CircleImageView) errorView.findViewById(R.id.iv_error_img);
        tvErrorMsg = (TextView) errorView.findViewById(R.id.tv_error_msg);
        tvReload = (TextView) errorView.findViewById(R.id.tv_reload);

        if (mResId != -1)
            ivErrorImg.setImageResource(mResId);

        if (!TextUtils.isEmpty(mErrorMsg))
            tvErrorMsg.setText(mErrorMsg);

        if (!TextUtils.isEmpty(mLoadText)) {
            tvReload.setText(mLoadText);
        }

        tvReload.setVisibility(isReload ? View.VISIBLE : View.INVISIBLE);

        tvReload.setOnClickListener(mListener);
    }

    public void setIvErrorImg(int resId) {
        mResId = resId;
        if (ivErrorImg != null) {
            ivErrorImg.setVisibility(View.VISIBLE);
            ivErrorImg.setImageResource(resId);
        }
    }

    public void setTvErrorMsg(String msg) {
        mErrorMsg = msg;
        if (tvErrorMsg != null) {
            tvErrorMsg.setText(msg);
        }
    }

    public void setTvReloadListener(View.OnClickListener listener) {
        setTvReload(null, listener);
    }

    public void setTvReload(String loadText, View.OnClickListener listener) {
        this.mLoadText = loadText;
        this.mListener = listener;
        if (tvReload != null) {
            if (!TextUtils.isEmpty(loadText))
                tvReload.setText(loadText);
            tvReload.setOnClickListener(listener);
        }
        if ((!TextUtils.isEmpty(loadText)) && listener != null)
            isReload(true);
    }

    public void isReload(boolean isReload) {
        this.isReload = isReload;
        if (tvReload != null) {
            tvReload.setVisibility(isReload ? View.VISIBLE : View.INVISIBLE);
        }
    }
}
