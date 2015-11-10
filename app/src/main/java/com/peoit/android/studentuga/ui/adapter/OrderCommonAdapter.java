package com.peoit.android.studentuga.ui.adapter;

import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.TextView;

import com.peoit.android.peoit_lib.ActivityBase;
import com.peoit.android.peoit_lib.adapter.BaseListAdapter;
import com.peoit.android.peoit_lib.adapter.BaseViewHolder;
import com.peoit.android.studentuga.R;
import com.peoit.android.studentuga.entity.OrderCommonInfo;
import com.peoit.android.studentuga.net.BaseServer;
import com.peoit.android.studentuga.net.server.OrderServer;
import com.peoit.android.studentuga.ui.AddCommonActivity;
import com.peoit.android.studentuga.view.NoSrcollListView;

/**
 * author:libo
 * time:2015/10/27
 * E-mail:boli_android@163.com
 * last: ...
 */
public class OrderCommonAdapter extends BaseListAdapter<OrderCommonInfo> {
    private BaseServer.OnSuccessCallBack mSuccessCallBack;

    public OrderCommonAdapter(Fragment fragment) {
        super(fragment);
    }

//    public OrderCommonAdapter(Activity mAc) {
//        super(mAc);
//    }

    @Override
    protected BaseViewHolder getBaseViewHolder() {
        return new ViewHolder();
    }

    @Override
    protected void initView(int position, final OrderCommonInfo data, ViewHolderBase holderBase, View convertView) {
        ViewHolder mHolder = (ViewHolder) holderBase;
        mHolder.tvCountAndPrice.setText("共" + data.getNumber() + "件商品, 合计:￥" + data.getPrice());
        mHolder.tvTitle.setText(data.getTitle());
        mHolder.tvCancel.setVisibility(View.GONE);
        mHolder.tvHint.setText("Y".equals(data.getIsly()) ? "您已评价该商品" : "交易完成, 待评价");
        mHolder.tvStatus.setText("Y".equals(data.getIsly()) ? "已评论" : "未评论");
        mHolder.tvDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new OrderServer((ActivityBase) mAc).requestCancelOrder(data.getId() + "", mSuccessCallBack);
            }
        });
        mHolder.tvOk.setVisibility("N".equals(data.getIsly()) ? View.VISIBLE : View.GONE);
        mHolder.tvOk.setText("评论");
        mHolder.tvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddCommonActivity.startThisActivity(mFragment,
                        data.getOrderId() + "",
                        data.getId() + "",
                        data.getShopCarList() != null && data.getShopCarList().size() > 0 ? data.getShopCarList().get(0) : null);
            }
        });
        mHolder.adapter.upDateList(data.getShopCarList());
    }

    private class ViewHolder extends BaseViewHolder {
        private TextView tvTitle;
        private TextView tvStatus;
        private NoSrcollListView lvOrder;
        private TextView tvCountAndPrice;
        private TextView tvCancel;
        private TextView tvOk;
        private TextView tvDel;
        private TextView tvHint;
        private OrderGoodsAdapter adapter;

        @Override
        protected int getConvertViewByLayoutId() {
            return R.layout.frag_hold_order_list_item;
        }

        @Override
        public void inflaer(View convertView) {
            lvOrder = (NoSrcollListView) convertView.findViewById(R.id.lv_order);

            tvTitle = (TextView) convertView.findViewById(R.id.tv_title);
            tvStatus = (TextView) convertView.findViewById(R.id.tv_status);
            tvCountAndPrice = (TextView) convertView.findViewById(R.id.tv_countAndPrice);
            tvCancel = (TextView) convertView.findViewById(R.id.tv_cancel);
            tvOk = (TextView) convertView.findViewById(R.id.tv_ok);
            tvDel = (TextView) convertView.findViewById(R.id.tv_del);
            tvHint = (TextView) convertView.findViewById(R.id.tv_hint);

            tvHint.setVisibility(View.GONE);
            tvDel.setVisibility(View.GONE);

            adapter = new OrderGoodsAdapter(mAc);
            lvOrder.setAdapter(adapter);
        }
    }

    public void setSuccessCallBack(BaseServer.OnSuccessCallBack callBack) {
        this.mSuccessCallBack = callBack;
    }
}
