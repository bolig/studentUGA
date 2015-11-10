package com.peoit.android.studentuga.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.peoit.android.peoit_lib.base.BaseActivity;
import com.peoit.android.studentuga.R;
import com.peoit.android.studentuga.net.server.SignInServer;

public class SignInActivity extends BaseActivity {
    private EditText etName;
    private EditText etMajor;
    private EditText etPhoneNum;
    private TextView tvSignIn;
    private SignInServer mSignServer;
    private String mName;
    private String mMajor;
    private String mPhone;

    public static void startThisActivity(Fragment frag) {
        Intent intent = new Intent(frag.getActivity(), SignInActivity.class);
        frag.startActivityForResult(intent, 1);
    }

    private void assignViews() {
        etName = (EditText) findViewById(R.id.et_name);
        etMajor = (EditText) findViewById(R.id.et_major);
        etPhoneNum = (EditText) findViewById(R.id.et_phoneNum);

        tvSignIn = (TextView) findViewById(R.id.tv_signIn);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_sign_in);
    }

    @Override
    public void initData() {
        mSignServer = new SignInServer(this);
    }

    @Override
    public void initView() {
        assignViews();
        getToolbar().setTvTitle("我要报名").setBack();
    }

    @Override
    public void initListener() {
        tvSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (match()){
                    mSignServer.requestSignIn(mMajor, mName, mPhone);
                }
            }
        });
    }

    private boolean match() {
        mName = etName.getText().toString();
        if (TextUtils.isEmpty(mName)) {
            showToast("请输入您的姓名");
            return false;
        }
        mMajor = etMajor.getText().toString();
        if (TextUtils.isEmpty(mMajor)) {
            showToast("请输入您的专业");
            return false;
        }
        mPhone = etPhoneNum.getText().toString();
        if (TextUtils.isEmpty(mPhone)) {
            showToast("请输入您的手机号");
            return false;
        }
        return true;
    }
}
