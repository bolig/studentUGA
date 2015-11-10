package com.peoit.android.studentuga.ui.adapter;

import android.app.Activity;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.peoit.android.peoit_lib.ActivityBase;
import com.peoit.android.peoit_lib.adapter.BaseListAdapter;
import com.peoit.android.peoit_lib.adapter.BaseViewHolder;
import com.peoit.android.studentuga.R;
import com.peoit.android.studentuga.config.CommonUtil;
import com.peoit.android.studentuga.entity.OrderInfo;
import com.peoit.android.studentuga.enuml.OrderStatus;
import com.peoit.android.studentuga.enuml.ScaleOrderStatus;
import com.peoit.android.studentuga.enuml.UserType;
import com.peoit.android.studentuga.net.BaseServer;
import com.peoit.android.studentuga.net.server.OrderServer;
import com.peoit.android.studentuga.ui.AddExpressActivity;
import com.peoit.android.studentuga.ui.PayActivity;
import com.peoit.android.studentuga.view.NoSrcollListView;

/**
 * author:libo
 * time:2015/10/27
 * E-mail:boli_android@163.com
 * last: ...
 */
public class OrderAdapter extends BaseListAdapter<OrderInfo> {
    private final boolean isHold;
    private BaseServer.OnSuccessCallBack mSuccessCallBack;

    public OrderAdapter(Activity mAc, boolean isHold) {
        super(mAc);
        this.isHold = isHold;
    }

    @Override
    protected BaseViewHolder getBaseViewHolder() {
        return new ViewHolder();
    }

    @Override
    protected void initView(int position, final OrderInfo data, ViewHolderBase holderBase, View convertView) {
        ViewHolder mHolder = (ViewHolder) holderBase;
        if (data.getProducts() == null || data.getProducts().size() == 0) {
            convertView.setVisibility(View.GONE);
        } else {
            convertView.setVisibility(View.VISIBLE);
            mHolder.adapter.upDateList(data.getProducts());
        }
        mHolder.tvCountAndPrice.setText("共" + data.getCount() + "件商品, 合计:￥" + data.getTotalPrice());
        mHolder.tvTitle.setText(data.getTitle());
        if (isHold) {
            final OrderStatus status = OrderStatus.getOrderStatus(data.getStatus());
            if (!status.equals(OrderStatus.status_fail)) {
                mHolder.tvOk.setVisibility(TextUtils.isEmpty(status.mOkButtonText) ? View.GONE : View.VISIBLE);
                mHolder.tvHint.setVisibility(TextUtils.isEmpty(status.mHintText) ? View.GONE : View.VISIBLE);
                mHolder.tvDel.setVisibility(status.mType == 1 ? View.VISIBLE : View.GONE);
                mHolder.tvCancel.setVisibility(status.mType == 2 ? View.VISIBLE : View.GONE);

                mHolder.tvStatus.setText(status.mMark);
                mHolder.tvOk.setText(status.mOkButtonText);
                mHolder.tvHint.setText(status.mHintText);
            } else {
                ((ActivityBase) mAc).showToast("数据错误");
            }
            mHolder.tvOk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (status == OrderStatus.status_xiadan) {
                        PayActivity.startThisActivity(mAc, data.getId() + "", data.getTotalPrice() + "", data.getTitle());
                    } else if (status == OrderStatus.status_jichu) {
                        new OrderServer((ActivityBase) mAc).requestFinishOrder(data.getId() + "", mSuccessCallBack);
                    } else if (status == OrderStatus.status_wancheng) {

                    }
                }
            });
        } else {
            final ScaleOrderStatus status = ScaleOrderStatus.getOrderStatus(data.getStatus());
            if (!status.equals(OrderStatus.status_fail)) {
                mHolder.tvOk.setVisibility(!TextUtils.isEmpty(status.mOkButtonText) && CommonUtil.getUserType() == UserType.shangJia ? View.VISIBLE : View.GONE);
                if (status == ScaleOrderStatus.status_fukuan && CommonUtil.getUserType() == UserType.shangJia) {
                    mHolder.tvHint.setVisibility(View.GONE);
                }else {
                    mHolder.tvHint.setVisibility(View.VISIBLE);
                    mHolder.tvHint.setText(status == ScaleOrderStatus.status_fukuan ? "等待卖家发货..." : status.mHintText);
                }
                mHolder.tvDel.setVisibility(status.mType == 1 ? View.VISIBLE : View.GONE);
                mHolder.tvCancel.setVisibility(status.mType == 2 ? View.VISIBLE : View.GONE);

                mHolder.tvStatus.setText(status.mMark);
                mHolder.tvOk.setText(status.mOkButtonText);
//                mHolder.tvHint.setText(status.mHintText);
            } else {
//                ((ActivityBase) mAc).showToast("数据错误");
                mHolder.tvOk.setVisibility(View.GONE);
                mHolder.tvHint.setVisibility(View.VISIBLE);
                mHolder.tvHint.setText("数据错误");
                mHolder.tvDel.setVisibility(View.GONE);
                mHolder.tvCancel.setVisibility(View.GONE);

            }
            mHolder.tvOk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AddExpressActivity.startThisActivityForResult(mAc, data.getId() + "");
                }
            });
        }
        mHolder.tvDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new OrderServer((ActivityBase) mAc).requestCancelOrder(data.getId() + "", mSuccessCallBack);
            }
        });
        mHolder.tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new OrderServer((ActivityBase) mAc).requestCancelOrder(data.getId() + "", mSuccessCallBack);
            }
        });
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
