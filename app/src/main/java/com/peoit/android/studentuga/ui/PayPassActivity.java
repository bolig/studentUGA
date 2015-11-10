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
import com.peoit.android.studentuga.config.CommonUtil;
import com.peoit.android.studentuga.net.BaseServer;
import com.peoit.android.studentuga.net.server.LoginPassServer;
import com.peoit.android.studentuga.net.server.PayPassServer;

public class PayPassActivity extends BaseActivity implements View.OnClickListener {
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

    private String mUserScanPhone;
    private String mUserScanCode;

    private String mUserCurrentPass;
    private String mUserNewPass;
    private String mUserReNewPass;
    private int passType;
    private PayPassServer patPassServer;

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

    public static final int SETUP_PASS = 0x00000001;
    public static final int FIND_PASS = 0x00000002;
    public static final int MOD_PASS = 0x00000003;

    public static void startThisActivity(Activity mAc, int passType) {
        if (CommonUtil.isLoginAndToLogin(mAc, false)) {
            Intent intent = new Intent(mAc, PayPassActivity.class);
            intent.putExtra("passType", passType);
            mAc.startActivity(intent);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_login_pass);
    }

    @Override
    public void initData() {
        passType = getIntent().getIntExtra("passType", -1);
        if (passType == -1) {
            showToast("数据传输错误");
            finish();
            return;
        }
        patPassServer = new PayPassServer(this);
    }

    private boolean isFind() {
        return passType == FIND_PASS;
    }

    private boolean isMod() {
        return passType == MOD_PASS;
    }

    @Override
    public void initView() {
        assignViews();
        getToolbar().setBack().setTvTitle(isMod() ? "修改支付密码" : isFind() ? "找回支付密码" : "设置支付密码");
        switch (passType) {
            case SETUP_PASS:
                changeUIShow(LoginPassType.scanNewPass);
                break;
            case FIND_PASS:
                changeUIShow(LoginPassType.scanPhone);
                break;
            case MOD_PASS:
                changeUIShow(LoginPassType.scanCurrentPass);
                break;
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
//            if (isMod()) {
//                if (checkScanPass()) {
//                    patPassServer.requestModPayPass(mUserNewPass, mUserCurrentPass);
//                }
//            } else {
//                if (checkScanPass()) {
//                    if (isFind()) {
//
//                    } else {
//                        patPassServer.requestSetUpPayPass(mUserNewPass);
//                    }
//                }
//            }
            switch (passType) {
                case SETUP_PASS:
                    if (checkScanPass()) {
                        patPassServer.requestSetUpPayPass(mUserNewPass);
                    }
                    break;
                case FIND_PASS:
                    if (checkScanPass()) {
                        patPassServer.requestFindPayPass(mUserNewPass, mUserScanCode);
                    }
                    break;
                case MOD_PASS:
                    if (checkScanPass()) {
                        patPassServer.requestModPayPass(mUserNewPass, mUserCurrentPass);
                    }
                    break;
            }
        } else if (v == tvScanPhoneNext) {
            mUserScanPhone = etPhone.getText().toString();
            if (TextUtils.isEmpty(mUserScanPhone)) {
                showToast("手机号不能为空");
                return;
            }
            new LoginPassServer(mAct).requestSendCode(mUserScanPhone, new BaseServer.OnSuccessCallBack() {
                @Override
                public void onSuccess(int mark) {
                    changeUIShow(LoginPassType.scanCode);
                }
            });
        } else if (v == tvScanCodeNext) {
            mUserScanCode = etCode.getText().toString();
            if (TextUtils.isEmpty(mUserScanCode)) {
                showToast("请输入验证码");
                return;
            }
            new LoginPassServer(mAct).requestCheckCode(mUserScanPhone, mUserScanCode, new BaseServer.OnSuccessCallBack() {
                @Override
                public void onSuccess(int mark) {
                    changeUIShow(LoginPassType.scanNewPass);
                }
            });
        }
    }

    /**
     * 检查修改密码新密码输入的有效性
     *
     * @return
     */
    private boolean checkScanPass() {
        if (isMod()) {
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
