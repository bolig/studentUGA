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
import com.peoit.android.studentuga.entity.ShopCarContentInfo;

/**
 * author:libo
 * time:2015/9/11
 * E-mail:boli_android@163.com
 * last: ...
 */
public class ShopCarContentAdapter extends BaseListAdapter<ShopCarContentInfo> {

    private boolean isCurentShopToEdit = false;
    private OnDataChangeListener mOndataChangeListener;

    public ShopCarContentAdapter(Activity mAc) {
        super(mAc);
    }

    @Override
    protected BaseViewHolder getBaseViewHolder() {
        return new ViewHolder();
    }

    public void setCurentShopToEdit(boolean isEdit) {
        if (isCurentShopToEdit == isEdit)
            return;
        isCurentShopToEdit = isEdit;
        this.notifyDataSetChanged();
    }

    @Override
    protected void initView(int position, final ShopCarContentInfo data, ViewHolderBase holderBase, View convertView) {
        final ViewHolder mHolder = (ViewHolder) holderBase;
        Glide.with(mAc).load(data.getImageUrl()).into(mHolder.ivGoodsIcon);
        mHolder.llSelectGoods.setSelected(data.isSelect());
        if (isCurentShopToEdit) {
            mHolder.llChangeGoodsStyle.setVisibility(View.VISIBLE);
            mHolder.llGoodsDisplay.setVisibility(View.INVISIBLE);
        } else {
            mHolder.llChangeGoodsStyle.setVisibility(View.INVISIBLE);
            mHolder.llGoodsDisplay.setVisibility(View.VISIBLE);
        }
        mHolder.llSelectGoods.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHolder.llSelectGoods.setSelected(!mHolder.llSelectGoods.isSelected());
                data.setSelect(mHolder.llSelectGoods.isSelected());
                onDataChange();
            }
        });
    }

    private class ViewHolder extends BaseViewHolder {

        private LinearLayout llSelectGoods;
        private ImageView ivGoodsIcon;
        private LinearLayout llGoodsDisplay;
        private TextView tvGoodsTitle;
        private TextView tvGoodsContent;
        private TextView tvCurPrice;
        private TextView tvRealPrice;
        private TextView tvGoodsRemark;
        private TextView tvGoodsCount;
        private LinearLayout llChangeGoodsStyle;
        private TextView tvGoodsCountAtEdit;
        private LinearLayout llGoodsStyle;
        private TextView tvGoodsStyle;
        private TextView tvDelGoods;
        private ImageView ivGoodsCountAdd;
        private ImageView ivGoodsCountMinus;

        @Override
        protected int getConvertViewByLayoutId() {
            return R.layout.frag_shop_car_list_edit_item;
        }

        @Override
        public void inflaer(View convertView) {
            llSelectGoods = (LinearLayout) convertView.findViewById(R.id.ll_selectGoods);
            ivGoodsIcon = (ImageView) convertView.findViewById(R.id.iv_goodsIcon);
            llGoodsDisplay = (LinearLayout) convertView.findViewById(R.id.ll_goodsDisplay);
            tvGoodsTitle = (TextView) convertView.findViewById(R.id.tv_goodsTitle);
            tvGoodsContent = (TextView) convertView.findViewById(R.id.tv_goodsContent);
            tvCurPrice = (TextView) convertView.findViewById(R.id.tv_curPrice);
            tvRealPrice = (TextView) convertView.findViewById(R.id.tv_realPrice);
            tvGoodsRemark = (TextView) convertView.findViewById(R.id.tv_goodsRemark);
            tvGoodsCount = (TextView) convertView.findViewById(R.id.tv_goodsCount);
            llChangeGoodsStyle = (LinearLayout) convertView.findViewById(R.id.ll_changeGoodsStyle);
            ivGoodsCountMinus = (ImageView) convertView.findViewById(R.id.iv_goodsCountMinus);
            tvGoodsCountAtEdit = (TextView) convertView.findViewById(R.id.tv_goodsCountAtEdit);
            ivGoodsCountAdd = (ImageView) convertView.findViewById(R.id.iv_goodsCountAdd);
            llGoodsStyle = (LinearLayout) convertView.findViewById(R.id.ll_goodsStyle);
            tvGoodsStyle = (TextView) convertView.findViewById(R.id.tv_goodsStyle);
            tvDelGoods = (TextView) convertView.findViewById(R.id.tv_delGoods);
        }
    }

    private void onDataChange() {
        if (mOndataChangeListener != null) {
            mOndataChangeListener.onDataChange();
        }
    }

    public void setOnDataChengeListener(OnDataChangeListener mListener) {
        this.mOndataChangeListener = mListener;
    }

    public interface OnDataChangeListener {
        void onDataChange();
    }
}
