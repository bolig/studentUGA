package com.peoit.android.studentuga.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.peoit.android.peoit_lib.base.BaseActivity;
import com.peoit.android.studentuga.R;
import com.peoit.android.studentuga.net.server.LoginServer;

/**
 * 登录界面
 * <p/>
 * author:libo
 * time:2015/8/4
 * E-mail:boli_android@163.com
 * last: ...
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private EditText etUserName;
    private ImageView ivSelectLateName;
    private EditText etPassword;
    private TextView tvLogin;
    private TextView tvFindPass;
    private TextView tvRegistor;

    private String userName;
    private String password;

    private LoginServer mLoginServer;

    public static void logout_startThisActivity(Activity mAc) {
        Intent intent = new Intent(mAc, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        mAc.startActivity(intent);
    }

    public static void registor_startThisActivity(Activity mAc) {
        Intent intent = new Intent(mAc, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        mAc.startActivity(intent);
    }

    public static void startThisActivity(Activity mAc) {
        Intent intent = new Intent(mAc, LoginActivity.class);
        mAc.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_login);
    }

    @Override
    public void initData() {
        mLoginServer = new LoginServer(this);
    }

    @Override
    public void initView() {
        getToolbar().setTvTitle("登录").setBack();

        etUserName = (EditText) findViewById(R.id.et_userName);
        etPassword = (EditText) findViewById(R.id.et_password);

        ivSelectLateName = (ImageView) findViewById(R.id.iv_selectLateName);

        tvLogin = (TextView) findViewById(R.id.tv_login);
        tvFindPass = (TextView) findViewById(R.id.tv_find_pass);
        tvRegistor = (TextView) findViewById(R.id.tv_registor);
    }

    @Override
    public void initListener() {
        tvLogin.setOnClickListener(this);
        tvFindPass.setOnClickListener(this);
        tvRegistor.setOnClickListener(this);

        ivSelectLateName.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == tvLogin) {
            if (matchUserScan()) {
                mLoginServer.requestLogin(userName, password);
            }
        } else if (v == tvFindPass) {
            LoginPassActivity.startThisActivity(mAct, true);
        } else if (v == tvRegistor) {
            RegistorActivity.startThisActivity(mAct);
        } else if (v == ivSelectLateName) {
            showToast("最近登录");
        }
    }

    /**
     * 检验用户输入格式
     *
     * @return
     */
    private boolean matchUserScan() {
        userName = etUserName.getText().toString();
        if (TextUtils.isEmpty(userName)) {
            showToast("请输入你的账号");
            return false;
        }
        password = etPassword.getText().toString();
        if (TextUtils.isEmpty(password)) {
            showToast("请输入你的密码");
            return false;
        }
        return true;
    }
}
