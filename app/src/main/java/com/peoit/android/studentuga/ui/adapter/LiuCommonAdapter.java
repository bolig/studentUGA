package com.peoit.android.studentuga.ui.adapter;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.peoit.android.peoit_lib.adapter.BaseListAdapter;
import com.peoit.android.peoit_lib.adapter.BaseViewHolder;
import com.peoit.android.studentuga.R;
import com.peoit.android.studentuga.config.NetConstants;
import com.peoit.android.studentuga.entity.LiuCommonInfo;
import com.peoit.android.studentuga.uitl.TimeUtil;
import com.peoit.android.studentuga.view.CircleImageView;
import com.peoit.android.studentuga.view.NoSrcollGridView;

/**
 * author:libo
 * time:2015/11/1
 * E-mail:boli_android@163.com
 * last: ...
 */
public class LiuCommonAdapter extends BaseListAdapter<LiuCommonInfo> {
    public LiuCommonAdapter(Activity mAc) {
        super(mAc);
    }

    @Override
    protected BaseViewHolder getBaseViewHolder() {
        return new ViewHolder();
    }

    @Override
    protected void initView(int position, LiuCommonInfo data, ViewHolderBase holderBase, View convertView) {
        ViewHolder mHolder = (ViewHolder) holderBase;
        mHolder.name.setText(data.getName());
        mHolder.tvHao.setVisibility(View.GONE);
        mHolder.tvStar.setVisibility(View.GONE);
        mHolder.tvContent.setText(data.getText());
        mHolder.tvTime.setText(TimeUtil.getTime(data.getStime()));
        Glide.with(mAc).load(NetConstants.IMG_HOST + data.getPic()).into(mHolder.ivIcon);
//        String ings = data.getImgs();
//        if (TextUtils.isEmpty(ings)) {
//            mHolder.gv.setVisibility(View.GONE);
//        } else {
//            mHolder.gv.setVisibility(View.VISIBLE);
//            String imgs[] = ings.split(",");
//            List<String> imgss = new ArrayList<>();
//            for (int i = 0; i < imgs.length; i++) {
//                imgss.add(NetConstants.IMG_HOST + imgs[i]);
//            }
//            mHolder.imgAdapter.upDateList(imgss);
//        }
    }

    private class ViewHolder extends BaseViewHolder {
        private CircleImageView ivIcon;
        private TextView name;
        private TextView tvStar;
        private TextView tvContent;
        private TextView tvTime;
        private View viewLine;
        private TextView tvHao;
        private NoSrcollGridView gv;
        private ImageAdapter1 imgAdapter;

        @Override
        protected int getConvertViewByLayoutId() {
            return R.layout.act_common_list_item;
        }

        @Override
        public void inflaer(View convertView) {
            ivIcon = (CircleImageView) convertView.findViewById(R.id.iv_icon);
            name = (TextView) convertView.findViewById(R.id.name);
            tvStar = (TextView) convertView.findViewById(R.id.tv_star);
            tvContent = (TextView) convertView.findViewById(R.id.tv_content);
            tvTime = (TextView) convertView.findViewById(R.id.tv_time);
            viewLine = convertView.findViewById(R.id.view_line);
            tvHao = (TextView) convertView.findViewById(R.id.tv_hao);
            gv = (NoSrcollGridView) convertView.findViewById(R.id.gv_imgs);
            imgAdapter = new ImageAdapter1(mAc);
            gv.setAdapter(imgAdapter);
        }
    }
}
