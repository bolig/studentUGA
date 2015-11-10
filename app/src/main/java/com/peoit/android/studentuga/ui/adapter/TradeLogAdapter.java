package com.peoit.android.studentuga.ui.adapter;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import com.peoit.android.peoit_lib.adapter.BaseListAdapter;
import com.peoit.android.peoit_lib.adapter.BaseViewHolder;
import com.peoit.android.studentuga.R;
import com.peoit.android.studentuga.entity.TradeLogInfo;
import com.peoit.android.studentuga.enuml.TradeLogPayWay;
import com.peoit.android.studentuga.enuml.TradeLogType;
import com.peoit.android.studentuga.uitl.TimeUtil;

/**
 * author:libo
 * time:2015/10/29
 * E-mail:boli_android@163.com
 * last: ...
 */
public class TradeLogAdapter extends BaseListAdapter<TradeLogInfo> {

    public TradeLogAdapter(Activity mAc) {
        super(mAc);
    }

    @Override
    protected BaseViewHolder getBaseViewHolder() {
        return new ViewHolder();
    }

    @Override
    protected void initView(int position, TradeLogInfo data, ViewHolderBase holderBase, View convertView) {
        ViewHolder mHolder = (ViewHolder) holderBase;
        TradeLogType type = TradeLogType.getType(data.getType());
        mHolder.tvType.setText(type.mTypeInfo);
        mHolder.tvPayMoney.setText(type.mMArk + data.getPrice());
        mHolder.tvPayMoney.setSelected(type.mSelect);
        mHolder.tvTime.setText(TimeUtil.getTime(data.getStime()));
        mHolder.tvPayWay.setText("交易方式:" + TradeLogPayWay.getPayWay(data.getPaytype()).mTypeWay);
    }

    private class ViewHolder extends BaseViewHolder {
        private TextView tvType;
        private TextView tvTime;
        private TextView tvPayWay;
        private TextView tvPayMoney;

        @Override
        protected int getConvertViewByLayoutId() {
            return R.layout.act_trade_log;
        }

        @Override
        public void inflaer(View convertView) {
            tvType = (TextView) convertView.findViewById(R.id.tv_type);
            tvTime = (TextView) convertView.findViewById(R.id.tv_time);
            tvPayWay = (TextView) convertView.findViewById(R.id.tv_payWay);
            tvPayMoney = (TextView) convertView.findViewById(R.id.tv_payMoney);
        }
    }
}
