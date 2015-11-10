package com.peoit.android.studentuga.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.peoit.android.peoit_lib.base.BasePagerFragment;
import com.peoit.android.studentuga.R;
import com.peoit.android.studentuga.ui.LoginActivity;
import com.peoit.android.studentuga.ui.dialog.PhotoDialog;
import com.peoit.android.studentuga.uitl.BitmapUtils;
import com.peoit.android.studentuga.uitl.ImageCropUtil;
import com.peoit.android.studentuga.uitl.ImagePhotoUtil;
import com.peoit.android.studentuga.uitl.ImageSelectUtil;
import com.peoit.android.studentuga.view.CircleImageView;

/**
 * author:libo
 * time:2015/9/8
 * E-mail:boli_android@163.com
 * last: ...
 */
public class HomeUserCenterFragment extends BasePagerFragment implements View.OnClickListener {

    private CircleImageView ivAvater;
    private TextView tvUserName;
    private TextView tvUserSign;

    private LinearLayout llUserInfo;
    private LinearLayout llMyOrder;
    private LinearLayout llMyGoods;
    private LinearLayout llMyEval;
    private LinearLayout llMyMsg;
    private LinearLayout llWealth;
    private LinearLayout llMyRanking;
    private LinearLayout llMyTeacherOrStudent;

    private TextView tvMyTeacherOrStudent;

    private String nativeImagePath;
    private Toolbar toolbar;

    @Override
    public View createView(LayoutInflater inflater, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frag_user_center, null);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initView() {

        ivAvater = (CircleImageView) findViewById(R.id.iv_avater);

        tvUserName = (TextView) findViewById(R.id.tv_userName);
        tvUserSign = (TextView) findViewById(R.id.tv_userSign);

        llUserInfo = (LinearLayout) findViewById(R.id.ll_userInfo);
        llMyOrder = (LinearLayout) findViewById(R.id.ll_myOrder);
        llMyGoods = (LinearLayout) findViewById(R.id.ll_myGoods);
        llMyEval = (LinearLayout) findViewById(R.id.ll_myEval);
        llMyMsg = (LinearLayout) findViewById(R.id.ll_myMsg);
        llWealth = (LinearLayout) findViewById(R.id.ll_wealth);
        llMyRanking = (LinearLayout) findViewById(R.id.ll_myRanking);
        llMyTeacherOrStudent = (LinearLayout) findViewById(R.id.ll_myTeacherOrStudent);

        tvMyTeacherOrStudent = (TextView) findViewById(R.id.tv_myTeacherOrStudent);
    }

    @Override
    public void initListener() {

        ivAvater.setOnClickListener(this);

        llUserInfo.setOnClickListener(this);

        llMyOrder.setOnClickListener(this);
        llMyGoods.setOnClickListener(this);
        llMyEval.setOnClickListener(this);
        llMyMsg.setOnClickListener(this);
        llWealth.setOnClickListener(this);
        llMyRanking.setOnClickListener(this);
        llMyTeacherOrStudent.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == llMyOrder) {
            showToast("我的订单");
        } else if (v == llMyGoods) {
            showToast("我的商品");
        } else if (v == llMyEval) {
            showToast("商品评论");
        } else if (v == llMyMsg) {
            showToast("我的留言");
        } else if (v == llWealth) {
            showToast("财富中心");
        } else if (v == llMyRanking) {
            showToast("我的排名");
        } else if (v == llMyTeacherOrStudent) {
            showToast("我的导师 or 学生");
        } else if (v == ivAvater) {
            toSelectPhoto();
        } else if (v == llUserInfo) {
            showToast("用户信息");
            LoginActivity.startThisActivity(getActivity());
        }
    }

    private static final int NATIVE_IMG = 0x00000001;
    private static final int TAKE_PHOTOT = 0x0000002;
    private static final int CROP_IMAGE = 0x0000003;

    private void toSelectPhoto() {
        final PhotoDialog dialog = new PhotoDialog(getActivity());
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
        dialog.setNativeListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                ImageSelectUtil.toSelectImg(HomeUserCenterFragment.this, NATIVE_IMG);
            }
        });
        dialog.setTakeListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                ImagePhotoUtil.toTakePhoto(HomeUserCenterFragment.this, false, TAKE_PHOTOT);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == getActivity().RESULT_OK) {
            if (requestCode == NATIVE_IMG) {
                nativeImagePath = ImageSelectUtil.getImgPath(getActivity(), data);
                //ivAvater.setImageBitmap(BitmapUtils.compress(nativeImagePath));
                ImageCropUtil.toPhotoZoom(HomeUserCenterFragment.this, nativeImagePath);
            } else if (TAKE_PHOTOT == requestCode) {
                nativeImagePath = ImagePhotoUtil.getCurrentPath();
                //ivAvater.setImageBitmap(BitmapUtils.compress(nativeImagePath));
                ImageCropUtil.toPhotoZoom(HomeUserCenterFragment.this, nativeImagePath);
            } else if (ImageCropUtil.CROP_REQUESSTCODE == requestCode) {
                nativeImagePath = ImageCropUtil.getCurrentPath();
                ivAvater.setImageBitmap(BitmapUtils.compressBitmap(nativeImagePath));
            }
        }
    }
}
