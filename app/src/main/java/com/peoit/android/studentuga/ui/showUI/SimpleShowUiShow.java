package com.peoit.android.studentuga.ui.showUI;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.peoit.android.studentuga.view.CircleImageView;

/**
 * author:libo
 * time:2015/9/22
 * E-mail:boli_android@163.com
 * last: ...
 */
public class SimpleShowUiShow extends BaseShowUiShow {

    private CircleImageView ivErrorImg;
    private TextView tvErrorMsg;
    private TextView tvReload;

    public SimpleShowUiShow(Context context) {
        super(context);
    }

    public SimpleShowUiShow(Context context, boolean isPreload) {
        super(context, null, isPreload);
    }

    public void setIvErrorImg(int resId) {
        ((SimpleShowUICreate) getUICreateBase()).setIvErrorImg(resId);
    }

    public void setTvErrorMsg(String msg) {
        ((SimpleShowUICreate) getUICreateBase()).setTvErrorMsg(msg);
    }

    public void setTvReloadListener(View.OnClickListener listener) {
        setTvReload(null, listener);
    }

    public void setTvReload(String loadText, View.OnClickListener listener) {
        ((SimpleShowUICreate) getUICreateBase()).setTvReload(loadText, listener);
    }

    public void setReLoad(boolean isReLoad){
        ((SimpleShowUICreate) getUICreateBase()).isReload(isReLoad);
    }
}
