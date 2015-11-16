package com.peoit.android.studentuga.ui.adapter;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import com.peoit.android.peoit_lib.adapter.BaseListAdapter;
import com.peoit.android.peoit_lib.adapter.BaseViewHolder;
import com.peoit.android.studentuga.R;
import com.peoit.android.studentuga.entity.GetMoneyInfo;

/**
 * author:libo
 * time:2015/10/29
 * E-mail:boli_android@163.com
 * last: ...
 */
public class GetMoneyAdapter extends BaseListAdapter<GetMoneyInfo> {

    public GetMoneyAdapter(Activity mAc) {
        super(mAc);
    }

    @Override
    protected BaseViewHolder getBaseViewHolder() {
        return new ViewHolder();
    }

    @Override
    protected void initView(int position, GetMoneyInfo data, ViewHolderBase holderBase, View convertView) {
        ViewHolder mHolder = (ViewHolder) holderBase;
        mHolder.tvPayWay.setText("提现金额:" + data.getMoney());
        mHolder.tvType.setText("提现状态:" + getType(data.getState()));
    }

    private String getType(String state) {
        if ("O".equals(state)) {
            return " 审批中";
        } else if ("Y".equals(state)) {
            return " 已经审批通过";
        } else if ("N".equals(state)) {
            return " 审批未通过";
        }
        return "错误状态";
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
            tvTime.setVisibility(View.GONE);
            tvPayWay = (TextView) convertView.findViewById(R.id.tv_payWay);
            tvPayMoney = (TextView) convertView.findViewById(R.id.tv_payMoney);
            tvPayMoney.setVisibility(View.GONE);
        }
    }
}
