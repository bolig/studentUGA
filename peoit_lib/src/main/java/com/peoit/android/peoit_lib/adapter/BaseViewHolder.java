package com.peoit.android.peoit_lib.adapter;

import android.view.LayoutInflater;
import android.view.View;

/**
 * author:libo
 * time:2015/9/7
 * E-mail:boli_android@163.com
 * last: ...
 */
public abstract class BaseViewHolder implements BaseEntityAdapter.ViewHolderBase {

    public View createView(LayoutInflater mInflater) {
        return mInflater.inflate(getConvertViewByLayoutId(), null);
    }

    protected abstract int getConvertViewByLayoutId();
}
