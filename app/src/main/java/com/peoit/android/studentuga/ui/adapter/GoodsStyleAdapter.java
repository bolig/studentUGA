package com.peoit.android.studentuga.ui.adapter;

import android.app.Activity;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.peoit.android.peoit_lib.adapter.BaseListAdapter;
import com.peoit.android.peoit_lib.adapter.BaseViewHolder;
import com.peoit.android.studentuga.R;
import com.peoit.android.studentuga.entity.GoodsStyleInfo;
import com.peoit.android.studentuga.view.NoSrcollGridView;

import java.util.HashMap;
import java.util.Map;

/**
 * author:libo
 * time:2015/10/16
 * E-mail:boli_android@163.com
 * last: ...
 */
public class GoodsStyleAdapter extends BaseListAdapter<GoodsStyleInfo> {
    private OnGoodsStyleListener mListener;
    private String mStyleStr;
    private int mErrorPosition = -1;

    public GoodsStyleAdapter(Activity mAc) {
        super(mAc);
    }

    @Override
    protected BaseViewHolder getBaseViewHolder() {
        return new ViewHolder();
    }

    private Map<Integer, String> mGoodsStyle = new HashMap<>();

    @Override
    protected void initView(final int position, GoodsStyleInfo data, ViewHolderBase holderBase, View convertView) {
        final ViewHolder mHolder = (ViewHolder) holderBase;
        mHolder.tvTitle.setText(data.getName());
        mHolder.mAdapter.upDateList(data.getSpecVals());
        mHolder.mAdapter.setSelectCallBack(new GoodsStyleValueAdapter.OnSelectCallBack() {
            @Override
            public void onCallBack(String style) {
                mGoodsStyle.put(position, style);
                changeData();
            }
        });
//        mHolder.gvStyle.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                String mStyle = mHolder.mAdapter.getDatas().get(position);
//            }
//        });
    }

    private void changeData() {
        if (mListener != null) {
            boolean isAll = getIsAll();
            mListener.onGoodsStyle(isAll, mErrorPosition, mStyleStr);
        }
    }

    private boolean getIsAll() {
        mStyleStr = "";
        for (int i = 0; i < getCount(); i++) {
            String style = mGoodsStyle.get(i);
            if (TextUtils.isEmpty(style)) {
                mErrorPosition = i;
                return false;
            }
            mStyleStr += style + (i == getCount() - 1 ? "" : ",");
        }
        return true;
    }

    private class ViewHolder extends BaseViewHolder {
        private TextView tvTitle;
        private NoSrcollGridView gvStyle;
        private GoodsStyleValueAdapter mAdapter;

        @Override
        protected int getConvertViewByLayoutId() {
            return R.layout.dialog_select_goods_style_list_item;
        }

        @Override
        public void inflaer(View convertView) {
            tvTitle = (TextView) convertView.findViewById(R.id.tv_title);
            gvStyle = (NoSrcollGridView) convertView.findViewById(R.id.gv_style);
            mAdapter = new GoodsStyleValueAdapter(mAc);
            gvStyle.setAdapter(mAdapter);
        }
    }

    public void setOnGoodsStyleListener(OnGoodsStyleListener listener) {
        this.mListener = listener;
    }

    public interface OnGoodsStyleListener {
        void onGoodsStyle(boolean isAll, int position, String style);
    }
}
