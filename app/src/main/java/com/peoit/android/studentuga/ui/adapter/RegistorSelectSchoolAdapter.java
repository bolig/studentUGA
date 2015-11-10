package com.peoit.android.studentuga.ui.adapter;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import com.peoit.android.peoit_lib.adapter.BaseListAdapter;
import com.peoit.android.peoit_lib.adapter.BaseViewHolder;
import com.peoit.android.studentuga.R;
import com.peoit.android.studentuga.entity.RegistorSchoolInfo;

/**
 * author:libo
 * time:2015/9/23
 * E-mail:boli_android@163.com
 * last: ...
 */
public class RegistorSelectSchoolAdapter extends BaseListAdapter<RegistorSchoolInfo> {

    private OnGetItemListener mItemListener;

    public RegistorSelectSchoolAdapter(Activity mAc) {
        super(mAc);
    }

    @Override
    protected BaseViewHolder getBaseViewHolder() {
        return new ViewHolder();
    }

    @Override
    protected void initView(int position, final RegistorSchoolInfo data, ViewHolderBase holderBase, View convertView) {
        ((ViewHolder) holderBase).tv_name.setText(data.getName());
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mItemListener != null) {
                    mItemListener.onItem(data);
                }
            }
        });
    }

    private class ViewHolder extends BaseViewHolder {

        private TextView tv_name;

        @Override
        protected int getConvertViewByLayoutId() {
            return R.layout.act_registor_select_school_pupop_layout_list_item;
        }

        @Override
        public void inflaer(View convertView) {
            tv_name = (TextView) convertView.findViewById(R.id.tv_name);
        }
    }

    public void setOnItemListener(OnGetItemListener listener) {
        this.mItemListener = listener;
    }

    public interface OnGetItemListener {
        void onItem(RegistorSchoolInfo info);
    }
}
