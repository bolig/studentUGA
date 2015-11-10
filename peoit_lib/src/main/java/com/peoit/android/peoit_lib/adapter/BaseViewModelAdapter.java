package com.peoit.android.peoit_lib.adapter;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.view.View;

import java.util.List;

/**
 * author:libo
 * time:2015/9/9
 * E-mail:boli_android@163.com
 * last: ...
 */
public class BaseViewModelAdapter<T> extends BaseEntityAdapter<T> implements BaseEntityAdapter.ViewHolderBase {

    private final ViewModelAdapterBase<T> mAdapterBase;

    public BaseViewModelAdapter(Activity mAc, ViewModelAdapterBase<T> adapterBase) {
        this(mAc, null, adapterBase);
    }

    public BaseViewModelAdapter(Activity mAc, List<T> dates, ViewModelAdapterBase<T> mAdapterBase) {
        super(mAc, -1, dates);
        this.mAdapterBase = mAdapterBase;
        upDateList(datas);
    }

    public BaseViewModelAdapter(Fragment fragment, int layoutId, ViewModelAdapterBase<T> mAdapterBase) {
        this(fragment, null, mAdapterBase);
    }

    public BaseViewModelAdapter(Fragment fragment, List<T> datas, ViewModelAdapterBase<T> mAdapterBase) {
        super(fragment, -1, datas);
        this.mAdapterBase = mAdapterBase;
        upDateList(datas);
    }

    @Override
    protected ViewHolderBase getViewHolder() {
        return mAdapterBase.getViewModelHolder();
    }

    @Override
    protected void initView(int position, T data, ViewHolderBase holderBase, View convertView) {
        mAdapterBase.initModelView(position, data, holderBase, convertView);
    }

    @Override
    public void inflaer(View convertView) {
        mAdapterBase.getViewModelHolder().inflaer(convertView);
    }

    @Override
    protected View inflater() {
        return getLayoutInflater().inflate(mAdapterBase.getViewModelHolder().getConvertViewByLayoutId(), null);
    }

    public interface ViewModelAdapterBase<T> {

        /**
         * 加载界面
         *
         * @param position
         * @param data
         * @param holderBase
         * @param convertView
         */
        void initModelView(int position, T data, ViewHolderBase holderBase, View convertView);

        /**
         * 获取ViewHolder
         *
         * @return
         */
        BaseViewHolder getViewModelHolder();
    }
}
