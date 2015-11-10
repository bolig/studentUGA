package com.peoit.android.studentuga.ui.adapter;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.peoit.android.peoit_lib.adapter.BaseListAdapter;
import com.peoit.android.peoit_lib.adapter.BaseViewHolder;
import com.peoit.android.peoit_lib.adapter.BaseViewModelAdapter;
import com.peoit.android.studentuga.R;
import com.peoit.android.studentuga.config.NetConstants;
import com.peoit.android.studentuga.entity.GoodsTypeInfo;
import com.peoit.android.studentuga.ui.GoodsSortActivity;
import com.peoit.android.studentuga.view.CircleImageView;
import com.peoit.android.studentuga.view.NoSrcollListView;

/**
 * author:libo
 * time:2015/10/10
 * E-mail:boli_android@163.com
 * last: ...
 */
public class SortTypeAdapter extends BaseListAdapter<GoodsTypeInfo> implements BaseViewModelAdapter.ViewModelAdapterBase<GoodsTypeInfo.TypeVos> {

    private int mOpenPosition = -1;

    public SortTypeAdapter(Activity mAc) {
        super(mAc);
    }

    public SortTypeAdapter(Fragment fragment) {
        super(fragment);
    }

    @Override
    protected BaseViewHolder getBaseViewHolder() {
        return new ViewHolder();
    }

    public void changeOpenItem(int position) {
        if (mOpenPosition != position) {
            this.mOpenPosition = position;
            notifyDataSetChanged();
        } else {
            this.mOpenPosition = -1;
            notifyDataSetChanged();
        }
    }

    @Override
    protected void initView(final int position, final GoodsTypeInfo data, ViewHolderBase holderBase, View convertView) {
        ViewHolder mHolder = (ViewHolder) holderBase;
        mHolder.lvGoodsItem.setVisibility(mOpenPosition == position ? View.VISIBLE : View.GONE);
        mHolder.llItem.setSelected(mOpenPosition == position);
        mHolder.tvTitle.setText(data.getName());
        mHolder.mVosAdapter.upDateList(data.getTypeVos());
        Glide.with(mAc).load(NetConstants.IMG_HOST + data.getImg()).error(R.drawable.test_shop).into(mHolder.tvIcon);
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GoodsSortActivity.startThisActivity(mAc, data.getId() + "");
            }
        });
        mHolder.ivPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeOpenItem(position);
            }
        });
    }

    @Override
    public void initModelView(final int position, final GoodsTypeInfo.TypeVos data, ViewHolderBase holderBase, View convertView) {
        TypeViewHolder mHolder = (TypeViewHolder) holderBase;
        mHolder.tvTitle.setText(data.getName());
        Glide.with(mAc).load(NetConstants.IMG_HOST + data.getImg()).error(R.drawable.noproduct).into(mHolder.tvIcon);
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GoodsSortActivity.startThisActivity(mAc, data.getId() + "");
            }
        });
    }

    @Override
    public BaseViewHolder getViewModelHolder() {
        return new TypeViewHolder();
    }

    private class ViewHolder extends BaseViewHolder {
        private CircleImageView tvIcon;
        private TextView tvTitle;
        private LinearLayout llItem;
        private NoSrcollListView lvGoodsItem;
        private BaseViewModelAdapter<GoodsTypeInfo.TypeVos> mVosAdapter;
        private ImageView ivPlus;

        @Override
        protected int getConvertViewByLayoutId() {
            return R.layout.frag_sort1_list_item;
        }

        @Override
        public void inflaer(View convertView) {
            tvIcon = (CircleImageView) convertView.findViewById(R.id.tv_icon);
            tvTitle = (TextView) convertView.findViewById(R.id.tv_title);
            lvGoodsItem = (NoSrcollListView) convertView.findViewById(R.id.lv_goods_item);
            llItem = (LinearLayout) convertView.findViewById(R.id.ll_item);
            ivPlus = (ImageView) convertView.findViewById(R.id.iv_plus);

            mVosAdapter = new BaseViewModelAdapter<GoodsTypeInfo.TypeVos>(mAc, SortTypeAdapter.this);
            lvGoodsItem.setAdapter(mVosAdapter);
        }
    }

    private class TypeViewHolder extends BaseViewHolder {
        private CircleImageView tvIcon;
        private TextView tvTitle;

        @Override
        protected int getConvertViewByLayoutId() {
            return R.layout.frag_sort1_type_item;
        }

        @Override
        public void inflaer(View convertView) {
            tvIcon = (CircleImageView) convertView.findViewById(R.id.tv_icon);
            tvTitle = (TextView) convertView.findViewById(R.id.tv_title);
        }
    }
}
