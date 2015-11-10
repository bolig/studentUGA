package com.peoit.android.studentuga.ui.adapter;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import com.peoit.android.peoit_lib.adapter.BaseListAdapter;
import com.peoit.android.peoit_lib.adapter.BaseViewHolder;
import com.peoit.android.studentuga.R;

/**
 * author:libo
 * time:2015/10/16
 * E-mail:boli_android@163.com
 * last: ...
 */
public class GoodsStyleValueAdapter extends BaseListAdapter<String> {

    private int mIndex = -1;
    private OnSelectCallBack mSelectCallBack;

    public GoodsStyleValueAdapter(Activity mAc) {
        super(mAc);
    }

    @Override
    protected BaseViewHolder getBaseViewHolder() {
        return new StyleViewHolder();
    }

    private void changeIndex(int index) {
        this.mIndex = index;
        notifyDataSetChanged();
    }

    @Override
    protected void initView(final int position, final String data, ViewHolderBase holderBase, View convertView) {
        StyleViewHolder mHolder = (StyleViewHolder) holderBase;
        mHolder.tvTitle.setText(data);
        mHolder.tvTitle.setSelected(position == mIndex);
        mHolder.tvTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeIndex(position);
                if (mSelectCallBack != null) {
                    mSelectCallBack.onCallBack(data);
                }
            }
        });
    }

    private class StyleViewHolder extends BaseViewHolder {

        private TextView tvTitle;

        @Override
        protected int getConvertViewByLayoutId() {
            return R.layout.dialog_select_goods_style_gv_item;
        }

        @Override
        public void inflaer(View convertView) {
            tvTitle = (TextView) convertView.findViewById(R.id.tv_title);
        }
    }

    public void setSelectCallBack(OnSelectCallBack callBack) {
        this.mSelectCallBack = callBack;
    }

    public interface OnSelectCallBack {
        void onCallBack(String style);
    }
}
