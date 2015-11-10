package com.peoit.android.studentuga.ui.adapter;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import com.peoit.android.peoit_lib.adapter.BaseListAdapter;
import com.peoit.android.peoit_lib.adapter.BaseViewHolder;
import com.peoit.android.studentuga.R;
import com.peoit.android.studentuga.entity.UserInfo;
import com.peoit.android.studentuga.ui.MyRankingActivity;

/**
 * author:libo
 * time:2015/9/22
 * E-mail:boli_android@163.com
 * last: ...
 */
public class MyRankingAdapter extends BaseListAdapter<UserInfo> {
    public MyRankingAdapter(Activity mAc) {
        super(mAc);
    }

    @Override
    protected BaseViewHolder getBaseViewHolder() {
        return new ViewHolder();
    }

    @Override
    protected void initView(int position, UserInfo data, ViewHolderBase holderBase, View convertView) {
        ViewHolder mHolder = (ViewHolder) holderBase;
        mHolder.tvItem1.setText((position + 1) + "");
        mHolder.tvItem2.setText(data.getNickname());
        mHolder.tvItem3.setText(data.getAreaname());
        mHolder.tvItem4.setText(data.getSchoolname());
    }

    private class ViewHolder extends BaseViewHolder {
        private TextView tvItem1;
        private TextView tvItem2;
        private TextView tvItem3;
        private TextView tvItem4;

        @Override
        protected int getConvertViewByLayoutId() {
            return R.layout.act_my_ranking_list_item;
        }

        @Override
        public void inflaer(View convertView) {
            tvItem1 = (TextView) convertView.findViewById(R.id.tv_item1);
            tvItem2 = (TextView) convertView.findViewById(R.id.tv_item2);
            tvItem3 = (TextView) convertView.findViewById(R.id.tv_item3);
            tvItem4 = (TextView) convertView.findViewById(R.id.tv_item4);

            ((MyRankingActivity) mAc).setTextViewWidth(tvItem1);
            ((MyRankingActivity) mAc).setTextViewWidth(tvItem2);
            ((MyRankingActivity) mAc).setTextViewWidth(tvItem3);
            ((MyRankingActivity) mAc).setTextViewWidth(tvItem4);
        }
    }
}
