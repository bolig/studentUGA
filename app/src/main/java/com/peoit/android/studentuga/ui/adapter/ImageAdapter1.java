package com.peoit.android.studentuga.ui.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.peoit.android.peoit_lib.adapter.BaseListAdapter;
import com.peoit.android.peoit_lib.adapter.BaseViewHolder;
import com.peoit.android.studentuga.R;
import com.peoit.android.studentuga.config.CommonUtil;

import java.util.ArrayList;
import java.util.List;

import me.iwf.photopicker.PhotoPagerActivity;

/**
 * author:libo
 * time:2015/11/4
 * E-mail:boli_android@163.com
 * last: ...
 */
public class ImageAdapter1 extends BaseListAdapter<String> {
    private final int mHeight;
    private boolean mEdit = false;
    private OnSelectCountCallBack mCallBack;

    public ImageAdapter1(Activity mAc) {
        super(mAc);
        mHeight = (CommonUtil.w_screeen - CommonUtil.dip2px(26 + 80 + 4)) / 3;
    }

    @Override
    protected BaseViewHolder getBaseViewHolder() {
        return new ViewHolder();
    }

    @Override
    protected void initView(final int position, final String data, ViewHolderBase holderBase, View convertView) {
//        if (TextUtils.isEmpty(data)) {
//            ((ViewHolder) holderBase).ivImg.setImageResource(R.drawable.test_shop);
//            ((ViewHolder) holderBase).ivDel.setVisibility(View.INVISIBLE);
//            convertView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    PhotoPickerIntent intent = new PhotoPickerIntent(mAc);
//                    intent.setPhotoCount(6);
//                    intent.setShowCamera(true);
//                    intent.setShowGif(false);
//                    mAc.startActivityForResult(intent, 1);
//                }
//            });
//            ((ViewHolder) holderBase).ivDel.setOnClickListener(null);
//        } else {
//            if (mEdit) {
//                ((ViewHolder) holderBase).ivDel.setVisibility(View.VISIBLE);
//            } else {
//                ((ViewHolder) holderBase).ivDel.setVisibility(View.INVISIBLE);
//            }
//            ((ViewHolder) holderBase).ivImg.setImageBitmap(BitmapUtils.compressBitmap(data));
//            convertView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent intent = new Intent(mAc, PhotoPagerActivity.class);
//                    intent.putExtra(PhotoPagerActivity.EXTRA_CURRENT_ITEM, position);
//                    ArrayList<String> imgs = new ArrayList<String>();
//                    imgs.addAll(datas);
//                    intent.putExtra(PhotoPagerActivity.EXTRA_PHOTOS, imgs);
//                    mAc.startActivityForResult(intent, 2);
//                }
//            });
//            ((ViewHolder) holderBase).ivDel.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if (datas.size() == 1) {
//                    }
//                    if (datas.size() == 6 && !TextUtils.isEmpty(datas.get(datas.size() - 1))) {
//                        datas.add("");
//                    }
//                    if (mCallBack != null) {
//                        mCallBack.onCallBack(datas.size() == 6 && !TextUtils.isEmpty(datas.get(datas.size() - 1)) ? 6 : datas.size() - 2);
//                    }
//                    removeDataItem(position);
//                }
//            });
//        }
        Glide.with(mAc).load(data).error(R.drawable.noproduct).into(((ViewHolder) holderBase).ivImg);
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mAc, PhotoPagerActivity.class);
                intent.putExtra(PhotoPagerActivity.EXTRA_CURRENT_ITEM, position);
                ArrayList<String> imgs = new ArrayList<String>();
                imgs.addAll(datas);
                intent.putExtra(PhotoPagerActivity.EXTRA_PHOTOS, imgs);
                mAc.startActivityForResult(intent, 2);
            }
        });
    }

    public List<View> views = new ArrayList<>();

    private class ViewHolder extends BaseViewHolder {
        private ImageView ivImg;
        private ImageView ivDel;

        @Override
        protected int getConvertViewByLayoutId() {
            return R.layout.act_addcommon_list_item1;
        }

        @Override
        public void inflaer(View convertView) {
            ivImg = (ImageView) convertView.findViewById(R.id.iv_img);
            ivDel = (ImageView) convertView.findViewById(R.id.iv_del);
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, mHeight);
            ivImg.setLayoutParams(params);
        }
    }

    public void setOnSelectCountCallBack(OnSelectCountCallBack callBack) {
        this.mCallBack = callBack;
    }

    public interface OnSelectCountCallBack {
        void onCallBack(int count);
    }
}
