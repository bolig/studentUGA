package com.peoit.android.studentuga.ui.adapter;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.peoit.android.peoit_lib.adapter.BaseListAdapter;
import com.peoit.android.peoit_lib.adapter.BaseViewHolder;
import com.peoit.android.studentuga.R;
import com.peoit.android.studentuga.config.CommonUtil;
import com.peoit.android.studentuga.config.NetConstants;
import com.peoit.android.studentuga.entity.MyGoodsInfo;
import com.peoit.android.studentuga.ui.GoodsDetActivity;

/**
 * author:libo
 * time:2015/10/30
 * E-mail:boli_android@163.com
 * last: ...
 */
public class MyGoodsMineAdapter extends BaseListAdapter<MyGoodsInfo> {
    private boolean isDaiXiao;

    public MyGoodsMineAdapter(Activity mAc) {
        super(mAc);
    }

    @Override
    protected BaseViewHolder getBaseViewHolder() {
        return new ViewHolder();
    }

    public void setDaoXiao(boolean isDaiXiao) {
        this.isDaiXiao = isDaiXiao;
    }

    @Override
    protected void initView(int position, final MyGoodsInfo data, ViewHolderBase holderBase, View convertView) {
        ViewHolder mHolder = (ViewHolder) holderBase;
        mHolder.tvTitle.setText(data.getTitle());
        mHolder.tvCount.setText("数量:" + data.getCount());
        mHolder.tvPrice.setText("￥" + data.getPrice());
        Glide.with(mAc)
                .load(NetConstants.IMG_HOST + data.getImgurl())
                .error(R.drawable.noproduct)
                .into(mHolder.ivIcon);
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isDaiXiao) {
                    GoodsDetActivity.startThisActivity(mAc, data.getId() + "", CommonUtil.currentUser.getId() + "", true);
                } else {
                    GoodsDetActivity.startThisActivity(mAc, data.getId() + "", null, false);
                }
            }
        });
    }

    private class ViewHolder extends BaseViewHolder {
        private ImageView ivIcon;
        private TextView tvTitle;
        private TextView tvPrice;
        private TextView tvCount;

        @Override
        protected int getConvertViewByLayoutId() {
            return R.layout.frag_my_goods_list_item;
        }

        @Override
        public void inflaer(View convertView) {
            ivIcon = (ImageView) convertView.findViewById(R.id.iv_icon);
            tvTitle = (TextView) convertView.findViewById(R.id.tv_title);
            tvPrice = (TextView) convertView.findViewById(R.id.tv_price);
            tvCount = (TextView) convertView.findViewById(R.id.tv_count);
        }
    }
}
