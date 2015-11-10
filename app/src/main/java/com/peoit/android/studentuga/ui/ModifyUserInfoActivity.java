package com.peoit.android.studentuga.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.peoit.android.peoit_lib.base.BaseActivity;
import com.peoit.android.studentuga.R;
import com.peoit.android.studentuga.config.CommonUtil;
import com.peoit.android.studentuga.entity.UserInfo;
import com.peoit.android.studentuga.net.BaseServer;
import com.peoit.android.studentuga.net.server.UserServer;

/**
 * 修改用户信息界面
 * <p/>
 * author:libo
 * time:2015/8/4
 * E-mail:boli_android@163.com
 * last: ...
 */
public class ModifyUserInfoActivity extends BaseActivity implements View.OnClickListener {
    private TextView tvPhone;
    private TextView tvNickName;
    private EditText etNickName;
    private TextView tvNickNameMod;
    private TextView tvUserSgin;
    private EditText etUserSgin;
    private TextView tvUserSginMod;
    private TextView tvZone;
    private TextView tvSchool;
    private TextView tvStudentNum;
    private TextView tvCancel;
    private TextView tvSave;

    private ShowType lastNickNameShowType = ShowType.showNickName;
    private ShowType lastUserSignShowType = ShowType.showUserSign;

    private String mUserNickName;
    private String mUserSign;
    private UserServer userServer;
    private UserInfo currentUser;

    private void assignViews() {
        tvPhone = (TextView) findViewById(R.id.tv_phone);
        tvNickName = (TextView) findViewById(R.id.tv_nickName);
        etNickName = (EditText) findViewById(R.id.et_nickName);
        tvNickNameMod = (TextView) findViewById(R.id.tv_nickName_mod);
        tvUserSgin = (TextView) findViewById(R.id.tv_userSgin);
        etUserSgin = (EditText) findViewById(R.id.et_userSgin);
        tvUserSginMod = (TextView) findViewById(R.id.tv_userSgin_mod);
        tvZone = (TextView) findViewById(R.id.tv_zone);
        tvSchool = (TextView) findViewById(R.id.tv_school);
        tvStudentNum = (TextView) findViewById(R.id.tv_studentNum);
        tvCancel = (TextView) findViewById(R.id.tv_cancel);
        tvSave = (TextView) findViewById(R.id.tv_save);
    }

    public static void startThisActivity(Activity mAc) {
        if (CommonUtil.isLoginAndToLogin(mAc, false)){
            Intent intent = new Intent(mAc, ModifyUserInfoActivity.class);
            mAc.startActivity(intent);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_modify_user_info);
    }

    @Override
    public void initData() {
        userServer = new UserServer(this);
        currentUser = CommonUtil.currentUser;
    }

    @Override
    public void initView() {
        assignViews();
        getToolbar().setBack().setTvTitle("用户基本信息");
        tvPhone.setText(currentUser.getPhone());
        tvNickName.setText(currentUser.getNickname());
        tvUserSgin.setText(currentUser.getQm());
        tvZone.setText(currentUser.getAreaname());
        tvSchool.setText(currentUser.getSchoolname());
        tvStudentNum.setText(currentUser.getStudentno());
    }

    @Override
    public void initListener() {
        tvUserSginMod.setOnClickListener(this);
        tvNickNameMod.setOnClickListener(this);

        tvCancel.setOnClickListener(this);
        tvSave.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == tvNickNameMod) {
            changeUIShow(lastNickNameShowType);
        } else if (v == tvUserSginMod) {
            changeUIShow(lastUserSignShowType);
        } else if (v == tvSave) {
            saveInfo();
        } else if (v == tvCancel) {
            finish();
        }
    }

    /**
     * 联网上传数据信息
     */
    private void saveInfo() {
        if (!TextUtils.isEmpty(mUserNickName) && !TextUtils.isEmpty(mUserSign)) {
            userServer.requestModifyUserNickName(mUserNickName, new BaseServer.OnSuccessCallBack() {
                @Override
                public void onSuccess(int mark) {
                    CommonUtil.currentUser.setNickname(mUserNickName);
                    CommonUtil.saveCurrentUser(CommonUtil.currentUser);
                    userServer.requestModifyUserSign(mUserSign, new BaseServer.OnSuccessCallBack() {
                        @Override
                        public void onSuccess(int mark) {
                            CommonUtil.currentUser.setQm(mUserSign);
                            CommonUtil.saveCurrentUser(CommonUtil.currentUser);
                            showToast("上传成功");
                            finish();
                        }
                    });
                }
            });
        } else if (!TextUtils.isEmpty(mUserNickName) && TextUtils.isEmpty(mUserSign)) {
            userServer.requestModifyUserNickName(mUserNickName, new BaseServer.OnSuccessCallBack() {
                @Override
                public void onSuccess(int mark) {
                    CommonUtil.currentUser.setNickname(mUserNickName);
                    CommonUtil.saveCurrentUser(CommonUtil.currentUser);
                    showToast("上传成功");
                    finish();
                }
            });
        } else if (TextUtils.isEmpty(mUserNickName) && !TextUtils.isEmpty(mUserSign)) {
            userServer.requestModifyUserSign(mUserSign, new BaseServer.OnSuccessCallBack() {
                @Override
                public void onSuccess(int mark) {
                    CommonUtil.currentUser.setQm(mUserSign);
                    CommonUtil.saveCurrentUser(CommonUtil.currentUser);
                    showToast("上传成功");
                    finish();
                }
            });
        } else if (TextUtils.isEmpty(mUserNickName) && TextUtils.isEmpty(mUserSign)) {
            finish();
        }
    }

    /**
     * 改变界面展示方式
     *
     * @param type
     */
    public void changeUIShow(ShowType type) {
        switch (type) {
            case showNickName:
            case modifyNickName:
                if (type == ShowType.modifyNickName)
                    checkScanUserNickName();
                type = (type == ShowType.showNickName ? ShowType.modifyNickName : ShowType.showNickName);
                tvNickNameMod.setText(type == ShowType.showNickName ? "修改" : "确定");
                tvNickName.setVisibility(type == ShowType.showNickName ? View.VISIBLE : View.INVISIBLE);
                etNickName.setVisibility(type == ShowType.showNickName ? View.INVISIBLE : View.VISIBLE);
                lastNickNameShowType = type;
                break;
            case showUserSign:
            case modifyUserSign:
                if (type == ShowType.modifyUserSign)
                    checkScanUserSign();
                type = (type == ShowType.showUserSign ? ShowType.modifyUserSign : ShowType.showUserSign);
                tvUserSginMod.setText(type == ShowType.showUserSign ? "修改" : "确定");
                tvUserSgin.setVisibility(type == ShowType.showUserSign ? View.VISIBLE : View.INVISIBLE);
                etUserSgin.setVisibility(type == ShowType.showUserSign ? View.INVISIBLE : View.VISIBLE);
                lastUserSignShowType = type;
                break;
        }
    }

    private void checkScanUserSign() {
        mUserSign = etUserSgin.getText().toString();
        if (!TextUtils.isEmpty(mUserSign)) {
            tvUserSgin.setText(mUserSign);
            etUserSgin.setText("");
        }
    }

    /**
     * 检查用户昵称输入
     */
    private void checkScanUserNickName() {
        mUserNickName = etNickName.getText().toString();
        if (!TextUtils.isEmpty(mUserNickName)) {
            tvNickName.setText(mUserNickName);
            etNickName.setText("");
        }
    }

    public enum ShowType {
        showNickName,
        modifyNickName,
        showUserSign,
        modifyUserSign
    }
}
