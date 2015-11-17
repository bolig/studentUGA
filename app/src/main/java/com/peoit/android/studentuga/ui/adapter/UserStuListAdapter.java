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
import com.peoit.android.studentuga.entity.UserStu;
import com.peoit.android.studentuga.ui.UserGoodsAndCommonActivity;

/**
 * author:libo
 * time:2015/10/10
 * E-mail:boli_android@163.com
 * last: ...
 */
public class UserStuListAdapter extends BaseListAdapter<UserStu> {

    private boolean isDaoXiao = false;

    public UserStuListAdapter(Activity mAc) {
        super(mAc);
    }

    public UserStuListAdapter(Fragment fragment) {
        super(fragment);
    }

    public void setDaoXiao(boolean isDaoXiao) {
        this.isDaoXiao = isDaoXiao;
    }

    @Override
    protected BaseViewHolder getBaseViewHolder() {
        return new ViewHolder();
    }

    @Override
    protected void initView(int position, final UserStu data, ViewHolderBase holderBase, View convertView) {
        ViewHolder mHolder = (ViewHolder) holderBase;
        mHolder.tvTitle.setText(data.getNickname());
        mHolder.tvTitle.setVisibility(View.VISIBLE);
        mHolder.view.setVisibility(View.GONE);
        Glide.with(mAc).load(NetConstants.IMG_HOST + data.getPic()).error(R.drawable.noproduct).into(mHolder.ivIcon);
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserGoodsAndCommonActivity.startThisActivity(mAc,
                        data.getPhone(),
                        data.getId(),
                        data.getPic(),
                        data.getNickname(),
                        data.getQm());
            }
        });
    }

    private class ViewHolder extends BaseViewHolder {
        private ImageView ivIcon;
        private TextView tvTitle;
        private View view;
        private TextView tvInfo;
        private TextView tvPrice;
        private TextView tvPeople;

        @Override
        protected int getConvertViewByLayoutId() {
            return R.layout.act_goods_sort_list_item;
        }

        @Override
        public void inflaer(View convertView) {
            ivIcon = (ImageView) convertView.findViewById(R.id.iv_icon);
            tvTitle = (TextView) convertView.findViewById(R.id.tv_single);
            view = convertView.findViewById(R.id.ll_right);
        }
    }
}
