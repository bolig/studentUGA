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
import com.peoit.android.studentuga.config.CommonUtil;
import com.peoit.android.studentuga.config.NetConstants;
import com.peoit.android.studentuga.entity.GoodsInfo;
import com.peoit.android.studentuga.ui.GoodsDetActivity;

/**
 * author:libo
 * time:2015/10/10
 * E-mail:boli_android@163.com
 * last: ...
 */
public class GoodsListAdapter extends BaseListAdapter<GoodsInfo> {

    private boolean isDaoXiao = false;

    public GoodsListAdapter(Activity mAc) {
        super(mAc);
    }

    public GoodsListAdapter(Fragment fragment) {
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
    protected void initView(int position, final GoodsInfo data, ViewHolderBase holderBase, View convertView) {
        ViewHolder mHolder = (ViewHolder) holderBase;
        mHolder.tvTitle.setText(data.getTitle());
        mHolder.tvPrice.setText("¥" + data.getPrice());
//        mHolder.tvPeople.setText(data.getSellcount() + "人已付款");
        mHolder.tvInfo.setText(data.getDetails());
        mHolder.tvPeople.setText("浏览量" + data.getViewcount());
        Glide.with(mAc).load(NetConstants.IMG_HOST + data.getImgurl()).error(R.drawable.noproduct).into(mHolder.ivIcon);
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GoodsDetActivity.startThisActivity(mAc, data.getId() + "", isDaoXiao ? CommonUtil.currentUser.getId() + "" : null, false);
            }
        });
    }

    private class ViewHolder extends BaseViewHolder {
        private ImageView ivIcon;
        private TextView tvTitle;
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
            tvTitle = (TextView) convertView.findViewById(R.id.tv_title);
            tvInfo = (TextView) convertView.findViewById(R.id.tv_info);
            tvPrice = (TextView) convertView.findViewById(R.id.tv_price);
            tvPeople = (TextView) convertView.findViewById(R.id.tv_people);
        }
    }
}
