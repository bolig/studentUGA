package com.peoit.android.peoit_lib.adapter;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.view.View;

/**
 * 实体适配器
 *
 * @create libo 2015/1/22
 * @finish ...
 */
public abstract class BaseListAdapter<T> extends BaseEntityAdapter<T> {

    private BaseViewHolder baseViewHolder;

    public BaseListAdapter(Activity mAc) {
        super(mAc, -1);
    }

    public BaseListAdapter(Fragment fragment) {
        super(fragment, -1);
    }

    @Override
    protected ViewHolderBase getViewHolder() {
        baseViewHolder = getBaseViewHolder();
        return baseViewHolder;
    }

    protected abstract BaseViewHolder getBaseViewHolder();

    @Override
    protected View inflater() {
        return baseViewHolder.createView(getLayoutInflater());
    }
}
