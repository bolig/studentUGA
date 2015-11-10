package com.peoit.android.studentuga.ui.adapter;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.peoit.android.peoit_lib.adapter.BaseListAdapter;
import com.peoit.android.peoit_lib.adapter.BaseViewHolder;
import com.peoit.android.studentuga.R;
import com.peoit.android.studentuga.entity.MyMsgInfo;
import com.peoit.android.studentuga.view.CircleImageView;

/**
 * author:libo
 * time:2015/9/18
 * E-mail:boli_android@163.com
 * last: ...
 */
public class MyMsgAdapter extends BaseListAdapter<MyMsgInfo> {

    public MyMsgAdapter(Activity mAc) {
        super(mAc);
    }

    @Override
    protected BaseViewHolder getBaseViewHolder() {
        return new ViewHolder();
    }

    @Override
    protected void initView(int position, MyMsgInfo data, ViewHolderBase holderBase, View convertView) {
        ViewHolder mHolder = (ViewHolder) holderBase;
        Glide.with(mAc).load(data.getImgUrl()).into(mHolder.ivIcon);
        mHolder.tvUserName.setText(data.getTitle());
        mHolder.tvTime.setText(data.getTime());
        mHolder.tvContent.setText(data.getContent());
    }

    private class ViewHolder extends BaseViewHolder {
        private CircleImageView ivIcon;
        private TextView tvUserName;
        private TextView tvTime;
        private TextView tvContent;

        @Override
        protected int getConvertViewByLayoutId() {
            return R.layout.act_my_msg_list_item;
        }

        @Override
        public void inflaer(View convertView) {
            ivIcon = (CircleImageView) convertView.findViewById(R.id.iv_icon);
            tvUserName = (TextView) convertView.findViewById(R.id.tv_userName);
            tvTime = (TextView) convertView.findViewById(R.id.tv_time);
            tvContent = (TextView) convertView.findViewById(R.id.tv_content);
        }
    }
}
