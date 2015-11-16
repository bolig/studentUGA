package com.peoit.android.studentuga.ui.adapter;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.peoit.android.peoit_lib.adapter.BaseListAdapter;
import com.peoit.android.peoit_lib.adapter.BaseViewHolder;
import com.peoit.android.studentuga.R;
import com.peoit.android.studentuga.config.NetConstants;
import com.peoit.android.studentuga.entity.ShopCarInfo;

/**
 * author:libo
 * time:2015/10/20
 * E-mail:boli_android@163.com
 * last: ...
 */
public class PayInfoAdapter extends BaseListAdapter<ShopCarInfo> {
    public PayInfoAdapter(Activity mAc) {
        super(mAc);
    }

    @Override
    protected BaseViewHolder getBaseViewHolder() {
        return new ViewHolder();
    }

    @Override
    protected void initView(int position, ShopCarInfo data, ViewHolderBase holderBase, View convertView) {
        ViewHolder mHolder = (ViewHolder) holderBase;
        mHolder.tvCurPrice.setText("￥" + data.getPrice());
        mHolder.tvGoodsCount.setText("数量:" + data.getNumber());
        mHolder.tvGoodsContent.setText(data.getSpec());
        mHolder.tvGoodsTitle.setText(data.getTitle());
        Glide.with(mAc)
                .load(NetConstants.IMG_HOST + data.getImgurl())
                .error(R.drawable.noproduct)
                .into(mHolder.ivGoodsIcon);
    }

    private class ViewHolder extends BaseViewHolder {
        private ImageView ivGoodsIcon;
        private LinearLayout llGoodsDisplay;
        private TextView tvGoodsTitle;
        private TextView tvGoodsContent;
        private TextView tvCurPrice;
        private TextView tvRealPrice;
        private TextView tvGoodsRemark;
        private TextView tvGoodsCount;

        @Override
        protected int getConvertViewByLayoutId() {
            return R.layout.act_pay_info_list_item;
        }

        @Override
        public void inflaer(View convertView) {
            ivGoodsIcon = (ImageView) convertView.findViewById(R.id.iv_goodsIcon);
            llGoodsDisplay = (LinearLayout) convertView.findViewById(R.id.ll_goodsDisplay);
            tvGoodsTitle = (TextView) convertView.findViewById(R.id.tv_goodsTitle);
            tvGoodsContent = (TextView) convertView.findViewById(R.id.tv_goodsContent);
            tvCurPrice = (TextView) convertView.findViewById(R.id.tv_curPrice);
            tvRealPrice = (TextView) convertView.findViewById(R.id.tv_realPrice);
            tvGoodsRemark = (TextView) convertView.findViewById(R.id.tv_goodsRemark);
            tvGoodsCount = (TextView) convertView.findViewById(R.id.tv_goodsCount);
        }
    }
}
