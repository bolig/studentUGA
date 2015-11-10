package com.peoit.android.studentuga.ui;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.peoit.android.peoit_lib.base.BaseActivity;
import com.peoit.android.studentuga.R;
import com.peoit.android.studentuga.config.CommonUtil;
import com.peoit.android.studentuga.config.Constants;
import com.peoit.android.studentuga.config.NetConstants;
import com.peoit.android.studentuga.net.server.FlieServer;
import com.peoit.android.studentuga.ui.base.MyApplication;
import com.peoit.android.studentuga.ui.dialog.PhotoDialog;
import com.peoit.android.studentuga.uitl.BitmapUtils;
import com.peoit.android.studentuga.uitl.FileUtil;
import com.peoit.android.studentuga.uitl.ImageCropUtil;
import com.peoit.android.studentuga.uitl.ImagePhotoUtil;
import com.peoit.android.studentuga.uitl.ImageSelectUtil;
import com.peoit.android.studentuga.uitl.ShareUserHelper;
import com.peoit.android.studentuga.view.CircleImageView;

/**
 * 用户资料界面
 * <p/>
 * author:libo
 * time:2015/8/4
 * E-mail:boli_android@163.com
 * last: ...
 */
public class UserInfoActivity extends BaseActivity implements View.OnClickListener {

    private CircleImageView ivAvater;

    private LinearLayout llAvater;
    private LinearLayout llUserInfoCenter;
    private LinearLayout llAddressManage;
    private LinearLayout llLoginPasswordModify;
    private LinearLayout llLoginPasswordFind;
    private LinearLayout llPayPasswordSetUp;
    private LinearLayout llPayPasswordModify;
    private LinearLayout llPayPasswordFind;

    private TextView tvLogout;

    private ShareUserHelper share;
    private String nativeImagePath;

    private void assignViews() {
        llAvater = (LinearLayout) findViewById(R.id.ll_avater);
        ivAvater = (CircleImageView) findViewById(R.id.iv_avater);
        llUserInfoCenter = (LinearLayout) findViewById(R.id.ll_userInfoCenter);
        llAddressManage = (LinearLayout) findViewById(R.id.ll_addressManage);
        llLoginPasswordModify = (LinearLayout) findViewById(R.id.ll_loginPassword_modify);
        llLoginPasswordFind = (LinearLayout) findViewById(R.id.ll_loginPassword_find);
        llPayPasswordSetUp = (LinearLayout) findViewById(R.id.ll_payPassword_setUp);
        llPayPasswordModify = (LinearLayout) findViewById(R.id.ll_payPassword_modify);
        llPayPasswordFind = (LinearLayout) findViewById(R.id.ll_payPassword_find);
        tvLogout = (TextView) findViewById(R.id.tv_logout);
    }

    public static void startThisActivityForResult(Activity mAc) {
        if (CommonUtil.isLoginAndToLogin(mAc, false)) {
            Intent intent = new Intent(mAc, UserInfoActivity.class);
            mAc.startActivityForResult(intent, 1);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_user_info);
    }

    @Override
    public void initData() {
        share = ShareUserHelper.getInstance();
    }

    @Override
    public void initView() {
        assignViews();
        getToolbar().setBack().setTvTitle("用户资料");
        if (!TextUtils.isEmpty(CommonUtil.getUserAvater())) {
            Glide.with(this).load(NetConstants.IMG_HOST + CommonUtil.getUserAvater())
                    .error(R.drawable.user_avater)
                    .into(ivAvater);
        }
    }

    @Override
    public void initListener() {
        tvLogout.setOnClickListener(this);

        llAvater.setOnClickListener(this);
        llUserInfoCenter.setOnClickListener(this);
        llAddressManage.setOnClickListener(this);

        llLoginPasswordFind.setOnClickListener(this);
        llLoginPasswordModify.setOnClickListener(this);

        llPayPasswordSetUp.setOnClickListener(this);
        llPayPasswordModify.setOnClickListener(this);
        llPayPasswordFind.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == llAvater) {
            toSelectPhoto();
        } else if (v == llUserInfoCenter) {
            ModifyUserInfoActivity.startThisActivity(mAct);
        } else if (v == llAddressManage) {
            AdressManageActivity.startThisActivity(mAct, 1, false);
        } else if (v == llLoginPasswordModify) {
            LoginPassActivity.startThisActivity(mAct, false);
        } else if (v == llLoginPasswordFind) {
            LoginPassActivity.startThisActivity(mAct, true);
        } else if (v == llPayPasswordSetUp) {

        } else if (v == llPayPasswordModify) {
            if (!CommonUtil.currentUser.isSetPaypwd()) {
                final AlertDialog dialog = new AlertDialog.Builder(mAct).setTitle("提示").setMessage("您当前还没有设置支付密码").setNeutralButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).setPositiveButton("去设置", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        PayPassActivity.startThisActivity(mAct, PayPassActivity.SETUP_PASS);
                    }
                }).show();
            } else {
                PayPassActivity.startThisActivity(mAct, PayPassActivity.MOD_PASS);
            }
        } else if (v == llPayPasswordFind) {
            if (!CommonUtil.currentUser.isSetPaypwd()) {
                final AlertDialog dialog = new AlertDialog.Builder(mAct)
                        .setTitle("提示")
                        .setMessage("您当前还没有设置支付密码")
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).setPositiveButton("去设置", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                PayPassActivity.startThisActivity(mAct, PayPassActivity.SETUP_PASS);
                            }
                        }).show();
            } else {
                PayPassActivity.startThisActivity(mAct, PayPassActivity.FIND_PASS);
            }
        } else if (v == tvLogout) {
            MyApplication.getInstance().logout();
            finish();
        }
    }

    private static final int NATIVE_IMG = 0x00000001;
    private static final int TAKE_PHOTOT = 0x0000002;

    /**
     * 选择图片
     */
    private void toSelectPhoto() {
        final PhotoDialog dialog = new PhotoDialog(getActivity());
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
        dialog.setNativeListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                ImageSelectUtil.toSelectImg(mAct, NATIVE_IMG);
            }
        });
        dialog.setTakeListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                ImagePhotoUtil.toTakePhoto(mAct, false, TAKE_PHOTOT);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == getActivity().RESULT_OK) {
            if (requestCode == NATIVE_IMG) {
                nativeImagePath = ImageSelectUtil.getImgPath(getActivity(), data);
                ImageCropUtil.toPhotoZoom(mAct, nativeImagePath);
            } else if (TAKE_PHOTOT == requestCode) {
                nativeImagePath = ImagePhotoUtil.getCurrentPath();
                ImageCropUtil.toPhotoZoom(mAct, FileUtil.getCustomPath());
            } else if (ImageCropUtil.CROP_REQUESSTCODE == requestCode) {
                nativeImagePath = ImageCropUtil.getCurrentPath();
                ivAvater.setImageBitmap(BitmapUtils.compressBitmap(nativeImagePath));
                new FlieServer(this).upload(nativeImagePath, new FlieServer.OnFileUploadSuccessListener() {
                    @Override
                    public void onSuccess(String imgUrl) {
                        share.put(Constants.SHARE_USER_AVATER, imgUrl);
                        if (!TextUtils.isEmpty(CommonUtil.getUserAvater())) {
                            Glide.with(mAct).load(NetConstants.IMG_HOST + CommonUtil.getUserAvater())
                                    .error(R.drawable.user_avater)
                                    .into(ivAvater);
                        }
                    }

                    @Override
                    public void onFailure() {
                        showToast("上传失败");
                    }
                });
            }
        }
    }
}
