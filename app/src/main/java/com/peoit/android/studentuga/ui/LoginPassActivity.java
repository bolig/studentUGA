package com.peoit.android.studentuga.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.peoit.android.peoit_lib.base.BaseActivity;
import com.peoit.android.studentuga.R;
import com.peoit.android.studentuga.net.BaseServer;
import com.peoit.android.studentuga.net.server.LoginPassServer;

public class LoginPassActivity extends BaseActivity implements View.OnClickListener {
    private LinearLayout llScanCode;
    private LinearLayout llScanPhone;
    private LinearLayout llScanPass;
    private LinearLayout llCurrentPass;

    private TextView tvScanPhoneNext;
    private TextView tvNewCode;
    private TextView tvScanCodeNext;
    private TextView tvCancel;
    private TextView tvSave;

    private View viewLine;

    private EditText etPhone;
    private EditText etCode;
    private EditText etCurrentPass;
    private EditText etNewPass;
    private EditText etReNewPass;

    private LoginPassType lastLoginPassType;
    private boolean isFind;

    private String mUserScanPhone;
    private String mUserScanCode;

    private LoginPassServer loginPassServer;
    private String mUserCurrentPass;
    private String mUserNewPass;
    private String mUserReNewPass;

    private void assignViews() {
        llScanPhone = (LinearLayout) findViewById(R.id.ll_scanPhone);
        llScanCode = (LinearLayout) findViewById(R.id.ll_scanCode);
        llScanPass = (LinearLayout) findViewById(R.id.ll_scanPass);
        llCurrentPass = (LinearLayout) findViewById(R.id.ll_currentPass);

        etPhone = (EditText) findViewById(R.id.et_phone);
        etCode = (EditText) findViewById(R.id.et_code);
        etCurrentPass = (EditText) findViewById(R.id.et_currentPass);
        etNewPass = (EditText) findViewById(R.id.et_newPass);
        etReNewPass = (EditText) findViewById(R.id.et_reNewPass);

        viewLine = findViewById(R.id.view_line);

        tvScanPhoneNext = (TextView) findViewById(R.id.tv_scanPhone_next);
        tvNewCode = (TextView) findViewById(R.id.tv_newCode);
        tvScanCodeNext = (TextView) findViewById(R.id.tv_scanCode_next);
        tvCancel = (TextView) findViewById(R.id.tv_cancel);
        tvSave = (TextView) findViewById(R.id.tv_save);
    }

    public static void startThisActivity(Activity mAc, boolean isFind) {
        Intent intent = new Intent(mAc, LoginPassActivity.class);
        intent.putExtra("isFind", isFind);
        mAc.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_login_pass);
    }

    @Override
    public void initData() {
        isFind = getIntent().getBooleanExtra("isFind", false);
        loginPassServer = new LoginPassServer(this);
    }

    @Override
    public void initView() {
        assignViews();
        getToolbar().setBack().setTvTitle(isFind ? "找回登录密码" : "修改登录密码");
        if (isFind) {
            changeUIShow(LoginPassType.scanPhone);
        } else {
            changeUIShow(LoginPassType.scanCurrentPass);
        }
    }

    @Override
    public void initListener() {
        tvCancel.setOnClickListener(this);
        tvSave.setOnClickListener(this);
        tvNewCode.setOnClickListener(this);
        tvScanPhoneNext.setOnClickListener(this);
        tvScanCodeNext.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == tvCancel) {
            finish();
        } else if (v == tvSave) {
            if (isFind) {
                if (checkScanPass()) {
                    loginPassServer.requestFindLoginPass(mUserScanPhone, mUserScanCode, mUserNewPass);
                }
            } else {
                if (checkScanPass()) {
                    loginPassServer.requestModLoginPass(mUserNewPass, mUserCurrentPass);
                }
            }
        } else if (v == tvNewCode) {
            loginPassServer.requestSendCode(mUserScanPhone, null);
        } else if (v == tvScanPhoneNext) {

            if (checkScanPhone()) {
                loginPassServer.requestSendCode(mUserScanPhone, new BaseServer.OnSuccessCallBack() {
                    @Override
                    public void onSuccess(int mark) {
                        changeUIShow(LoginPassType.scanCode);
                    }
                });
            }
        } else if (v == tvScanCodeNext) {
            if (checkScanCode()) {
                loginPassServer.requestCheckCode(mUserScanPhone, mUserScanCode, new BaseServer.OnSuccessCallBack() {
                    @Override
                    public void onSuccess(int mark) {
                        changeUIShow(LoginPassType.scanNewPass);
                    }
                });
            }
        }
    }

    /**
     * 检查修改密码新密码输入的有效性
     *
     * @return
     */
    private boolean checkScanPass() {
        if (!isFind) {
            mUserCurrentPass = etCurrentPass.getText().toString();
            if (TextUtils.isEmpty(mUserCurrentPass)) {
                showToast("请输入您的当前密码");
                return false;
            }
        }
        mUserNewPass = etNewPass.getText().toString();
        if (TextUtils.isEmpty(mUserNewPass)) {
            showToast("请输入新密码");
            return false;
        }
        mUserReNewPass = etReNewPass.getText().toString();
        if (!mUserNewPass.equals(mUserReNewPass)) {
            showToast("两次密码不一致");
            return false;
        }
        return true;
    }

    /**
     * 检查短信验证码输入状态
     *
     * @return
     */
    private boolean checkScanCode() {
        mUserScanCode = etCode.getText().toString();
        if (TextUtils.isEmpty(mUserScanCode)) {
            showToast("请输入您收到的短信验证码");
            return false;
        }
        return true;
    }

    /**
     * 检查手机号输入状态
     *
     * @return
     */
    private boolean checkScanPhone() {
        mUserScanPhone = etPhone.getText().toString();
        if (TextUtils.isEmpty(mUserScanPhone)) {
            showToast("请输入您的手机号码");
            return false;
        }
        return true;
    }

    /**
     * 更爱界面显示
     *
     * @param type
     */
    public void changeUIShow(LoginPassType type) {
        if (type == lastLoginPassType)
            return;
        llScanPhone.setVisibility(View.GONE);
        llScanCode.setVisibility(View.GONE);
        llScanPass.setVisibility(View.GONE);
        llCurrentPass.setVisibility(View.GONE);
        switch (type) {
            case scanPhone:
                llScanPhone.setVisibility(View.VISIBLE);
                break;
            case scanCode:
                llScanCode.setVisibility(View.VISIBLE);
                break;
            case scanNewPass:
                llScanPass.setVisibility(View.VISIBLE);

                break;
            case scanCurrentPass:
                llScanPass.setVisibility(View.VISIBLE);
                llCurrentPass.setVisibility(View.VISIBLE);
                break;
        }
        lastLoginPassType = type;
    }

    public enum LoginPassType {
        scanPhone,
        scanCode,
        scanNewPass,
        scanCurrentPass
    }
}
