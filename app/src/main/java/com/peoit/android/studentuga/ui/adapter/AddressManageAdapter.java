package com.peoit.android.studentuga.ui.adapter;

import android.app.Activity;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.TextView;

import com.peoit.android.peoit_lib.adapter.BaseListAdapter;
import com.peoit.android.peoit_lib.adapter.BaseViewHolder;
import com.peoit.android.studentuga.R;
import com.peoit.android.studentuga.entity.AddressInfo;

/**
 * author:libo
 * time:2015/9/30
 * E-mail:boli_android@163.com
 * last: ...
 */
public class AddressManageAdapter extends BaseListAdapter<AddressInfo> {

    public AddressManageAdapter(Activity mAc) {
        super(mAc);
    }

    @Override
    protected BaseViewHolder getBaseViewHolder() {
        return new ViewHolder();
    }

    @Override
    public int getItemViewType(int position) {
        return getItem(position).isDefault() ? 1 : 0;
    }

    @Override
    protected void initView(int position, AddressInfo data, ViewHolderBase holderBase, View convertView) {
        ViewHolder mHolder = (ViewHolder) holderBase;
        mHolder.tvName.setText("收货人:" + data.getConsignorName());
        String address = data.getAddress();
        if (data.isDefault()) {
            address = "默认地址:" + address;
            SpannableStringBuilder builder = new SpannableStringBuilder(address);

            ForegroundColorSpan redSpan = new ForegroundColorSpan(mAc.getResources().getColor(R.color.col_select_p));
            ForegroundColorSpan whiteSpan = new ForegroundColorSpan(mAc.getResources().getColor(R.color.black_2));

            builder.setSpan(redSpan, 0, 4, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            mHolder.tvAddress.setText(builder);
        } else {
            address = "收货地址:" + address;

            mHolder.tvAddress.setText(address);
        }
        mHolder.tvPhone.setText("电话:" + data.getTelephone());
    }

    private class ViewHolder extends BaseViewHolder {
        private TextView tvName;
        private TextView tvPhone;
        private TextView tvAddress;

        @Override
        protected int getConvertViewByLayoutId() {
            return R.layout.act_adress_manage_list_item;
        }

        @Override
        public void inflaer(View convertView) {
            tvName = (TextView) convertView.findViewById(R.id.tv_name);
            tvPhone = (TextView) convertView.findViewById(R.id.tv_phone);
            tvAddress = (TextView) convertView.findViewById(R.id.tv_address);
        }
    }
}
