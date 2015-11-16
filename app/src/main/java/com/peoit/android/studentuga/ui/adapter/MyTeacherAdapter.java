package com.peoit.android.studentuga.ui.adapter;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.peoit.android.peoit_lib.adapter.BaseListAdapter;
import com.peoit.android.peoit_lib.adapter.BaseViewHolder;
import com.peoit.android.studentuga.R;
import com.peoit.android.studentuga.config.NetConstants;
import com.peoit.android.studentuga.entity.BindUserInfo;
import com.peoit.android.studentuga.view.CircleImageView;

/**
 * author:libo
 * time:2015/9/21
 * E-mail:boli_android@163.com
 * last: ...
 */
public class MyTeacherAdapter extends BaseListAdapter<BindUserInfo> {

    public MyTeacherAdapter(Activity mAc) {
        super(mAc);
    }

    public MyTeacherAdapter(Fragment fragment) {
        super(fragment);
    }

    @Override
    protected BaseViewHolder getBaseViewHolder() {
        return new ViewHolder();
    }

    @Override
    protected void initView(int position, BindUserInfo data, ViewHolderBase holderBase, View convertView) {
        ViewHolder mHolder = (ViewHolder) holderBase;
        Glide.with(mAc).load(NetConstants.IMG_HOST + data.getPic()).error(R.drawable.user_avater).into(mHolder.ivIcon);
        mHolder.tvTitle.setText(data.getNickname());
    }

    private class ViewHolder extends BaseViewHolder {
        private CircleImageView ivIcon;
        private TextView tvTitle;
        private ImageView ivNext;

        @Override
        protected int getConvertViewByLayoutId() {
            return R.layout.act_my_teacher_list_item;
        }

        @Override
        public void inflaer(View convertView) {
            ivIcon = (CircleImageView) convertView.findViewById(R.id.iv_icon);
            tvTitle = (TextView) convertView.findViewById(R.id.tv_title);
            ivNext = (ImageView) convertView.findViewById(R.id.iv_next);
        }
    }
}
