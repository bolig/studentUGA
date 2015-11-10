package com.peoit.android.studentuga.ui.adapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.peoit.android.peoit_lib.adapter.BaseListAdapter;
import com.peoit.android.peoit_lib.adapter.BaseViewHolder;
import com.peoit.android.studentuga.R;
import com.peoit.android.studentuga.entity.ExpressInfo;

/**
 * author:libo
 * time:2015/11/5
 * E-mail:boli_android@163.com
 * last: ...
 */
public class ExpressAdapter extends BaseListAdapter<ExpressInfo> {
    public ExpressAdapter(Activity mAc) {
        super(mAc);
    }

    @Override
    protected BaseViewHolder getBaseViewHolder() {
        return new ViewHolder();
    }

    @Override
    protected void initView(int position, final ExpressInfo data, ViewHolderBase holderBase, View convertView) {
        ViewHolder holder = (ViewHolder) holderBase;
        holder.tvName.setText(data.getMark());
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("data", data);
                mAc.setResult(Activity.RESULT_OK, new Intent().putExtras(bundle));
                mAc.finish();
            }
        });
    }

    private class ViewHolder extends BaseViewHolder {

        private TextView tvName;

        @Override
        protected int getConvertViewByLayoutId() {
            return R.layout.act_express_list_item;
        }

        @Override
        public void inflaer(View convertView) {
            tvName = (TextView) convertView.findViewById(R.id.tv_name);
        }
    }
}
