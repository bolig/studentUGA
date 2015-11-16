package com.peoit.android.studentuga.ui.adapter;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.peoit.android.peoit_lib.adapter.BaseListAdapter;
import com.peoit.android.peoit_lib.adapter.BaseViewHolder;
import com.peoit.android.studentuga.R;
import com.peoit.android.studentuga.config.NetConstants;
import com.peoit.android.studentuga.entity.ShopCarInfo;
import com.peoit.android.studentuga.ui.GoodsDetActivity;

/**
 * author:libo
 * time:2015/10/27
 * E-mail:boli_android@163.com
 * last: ...
 */
public class OrderGoodsAdapter extends BaseListAdapter<ShopCarInfo> {

    public OrderGoodsAdapter(Activity mAc) {
        super(mAc);
    }

    public OrderGoodsAdapter(Fragment fragment) {
        super(fragment);
    }

    @Override
    protected BaseViewHolder getBaseViewHolder() {
        return new ViewHolder();
    }

    @Override
    protected void initView(int position, final ShopCarInfo data, ViewHolderBase holderBase, View convertView) {
        ViewHolder mHolder = (ViewHolder) holderBase;
        mHolder.tvGoodsTitle.setText(data.getTitle());
        mHolder.tvGoodsCount.setText("X" + data.getNumber());
        mHolder.tvGoodsPrice.setText("￥" + data.getPrice());
        mHolder.tvGoodsStyle.setText("商品样式:" + (TextUtils.isEmpty(data.getSpec()) ? "暂无样式" : data.getSpec()));
        Glide.with(mAc)
                .load(NetConstants.IMG_HOST + data.getImgurl())
                .error(R.drawable.noproduct)
                .into(mHolder.ivIcon);
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GoodsDetActivity.startThisActivity(mAc, data.getCommodityId() + "", null, false);
            }
        });
    }

    private class ViewHolder extends BaseViewHolder {
        private ImageView ivIcon;
        private TextView tvGoodsTitle;
        private TextView tvGoodsStyle;
        private TextView tvGoodsPrice;
        private TextView tvGoodsCount;

        @Override
        protected int getConvertViewByLayoutId() {
            return R.layout.frag_order_list_item_list_item;
        }

        @Override
        public void inflaer(View convertView) {
            ivIcon = (ImageView) convertView.findViewById(R.id.iv_icon);
            tvGoodsTitle = (TextView) convertView.findViewById(R.id.tv_goodsTitle);
            tvGoodsStyle = (TextView) convertView.findViewById(R.id.tv_goodsStyle);
            tvGoodsPrice = (TextView) convertView.findViewById(R.id.tv_goodsPrice);
            tvGoodsCount = (TextView) convertView.findViewById(R.id.tv_goodsCount);
        }
    }
}
