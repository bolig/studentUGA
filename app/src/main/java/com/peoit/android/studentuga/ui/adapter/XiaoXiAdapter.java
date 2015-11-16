package com.peoit.android.studentuga.ui.adapter;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import com.peoit.android.peoit_lib.adapter.BaseListAdapter;
import com.peoit.android.peoit_lib.adapter.BaseViewHolder;
import com.peoit.android.studentuga.R;
import com.peoit.android.studentuga.entity.XiaoXiInfo;
import com.peoit.android.studentuga.uitl.TimeUtil;

/**
 * author:libo
 * time:2015/11/13
 * E-mail:boli_android@163.com
 * last: ...
 */
public class XiaoXiAdapter extends BaseListAdapter<XiaoXiInfo> {
    public XiaoXiAdapter(Activity mAc) {
        super(mAc);
    }

    @Override
    protected BaseViewHolder getBaseViewHolder() {
        return new ViewHolder();
    }

    @Override
    protected void initView(int position, final XiaoXiInfo data, ViewHolderBase holderBase, View convertView) {
        final ViewHolder holder = (ViewHolder) holderBase;
        holder.tvTitle.setText(data.getTitle());
        holder.tvContent.setText(data.getText());
        holder.tvTime.setText(TimeUtil.getTime(data.getCtime()));
        holder.tvAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                data.setAdd(!data.isAdd());
                holder.tvContent.setMaxLines(data.isAdd() ? Integer.MAX_VALUE : 2);
                holder.tvContent.setText(data.getText());
                holder.tvAdd.setText(data.isAdd() ? "收起" : "更多");
                holder.tvAdd.setSelected(data.isAdd());
            }
        });
        holder.tvContent.setMaxLines(data.isAdd() ? Integer.MAX_VALUE : 2);
        holder.tvContent.setText(data.getText());
        holder.tvAdd.setText(data.isAdd() ? "收起" : "更多");
        holder.tvAdd.setSelected(data.isAdd());
    }

    private class ViewHolder extends BaseViewHolder {
        private TextView tvTitle;
        private TextView tvContent;
        private TextView tvTime;
        private TextView tvAdd;

        @Override
        protected int getConvertViewByLayoutId() {
            return R.layout.act_xiao_xi_list_item;
        }

        @Override
        public void inflaer(View convertView) {
            tvTitle = (TextView) convertView.findViewById(R.id.tv_title);
            tvContent = (TextView) convertView.findViewById(R.id.tv_content);
            tvTime = (TextView) convertView.findViewById(R.id.tv_time);
            tvAdd = (TextView) convertView.findViewById(R.id.tv_add);
        }
    }
}
