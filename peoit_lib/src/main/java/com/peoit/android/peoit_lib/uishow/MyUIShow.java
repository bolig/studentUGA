package com.peoit.android.peoit_lib.uishow;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.peoit.android.peoit_lib.R;
import com.peoit.android.peoit_lib.base.BaseUIShow;
import com.peoit.android.peoit_lib.view.CircleImageView;

/**
 * author:libo
 * time:2015/9/17
 * E-mail:boli_android@163.com
 * last: ...
 */
public class MyUIShow extends BaseUIShow {
    private CircleImageView ivErrorImg;
    private TextView tvErrorMsg;
    private TextView tvReload;

    private int mImageResId = -1;
    private String mErrorMsg;
    private String mLoadText;

    public MyUIShow(Context context) {
        super(context);
    }

    @Override
    protected View createErrorLayout(LayoutInflater inflater) {
        return inflater.inflate(R.layout.layout_error1, null);
    }

    @Override
    protected View createLoadingLayout(LayoutInflater inflater) {
        return inflater.inflate(R.layout.layout_loading1, null);
    }

    @Override
    protected void initLoadingLayout(View loadingLayout) {

    }

    @Override
    protected void initErrorLayout(View errorLayout) {
        ivErrorImg = (CircleImageView) errorLayout.findViewById(R.id.iv_error_img);
        tvErrorMsg = (TextView) errorLayout.findViewById(R.id.tv_error_msg);
        tvReload = (TextView) errorLayout.findViewById(R.id.tv_reload);
        if (!TextUtils.isEmpty(mErrorMsg)) {
            tvErrorMsg.setText(mErrorMsg);
        }
        if (mImageResId != -1) {
            ivErrorImg.setImageResource(mImageResId);
        }
        if (!TextUtils.isEmpty(mLoadText)) {
            tvReload.setText(mLoadText);
        }
        if (onReloadListener != null) {
            tvReload.setVisibility(View.VISIBLE);
        } else {
            tvReload.setVisibility(View.INVISIBLE);
        }
        tvReload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onReloadListener.onReload();
            }
        });
    }

    public MyUIShow setErrorImag(int resId) {
        if (ivErrorImg != null) {
            ivErrorImg.setImageResource(resId);
        }
        this.mImageResId = resId;
        return this;
    }

    public MyUIShow setErrorMsg(String msg) {
        if (tvErrorMsg != null) {
            tvErrorMsg.setText(msg);
        }
        this.mErrorMsg = msg;
        return this;
    }

    public MyUIShow setReloadText(String loadText) {
        if (tvReload != null) {
            tvReload.setText(loadText);
        }
        this.mLoadText = loadText;
        return this;
    }

    @Override
    public void setOnReloadListener(OnReloadListener listener) {
        super.setOnReloadListener(listener);
        if (tvReload != null) {
            if (onReloadListener != null) {
                tvReload.setVisibility(View.VISIBLE);
            } else {
                tvReload.setVisibility(View.INVISIBLE);
            }
        }
    }
}
