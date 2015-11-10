package com.peoit.android.studentuga.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.peoit.android.peoit_lib.base.BaseActivity;
import com.peoit.android.studentuga.R;
import com.peoit.android.studentuga.config.CommonUtil;

/**
 * 提现界面
 * <p/>
 * author:libo
 * time:2015/8/4
 * E-mail:boli_android@163.com
 * last: ...
 */
public class GetMoneyActivity extends BaseActivity {

    private EditText etGetMoney;
    private TextView tvGetMoney;

    private void assignViews() {
        etGetMoney = (EditText) findViewById(R.id.et_get_money);
        tvGetMoney = (TextView) findViewById(R.id.tv_get_money);
    }

    public static void startThisActivity(Activity mAc) {
        if (CommonUtil.isLoginAndToLogin(mAc, false)) {
            Intent intent = new Intent(mAc, GetMoneyActivity.class);
            mAc.startActivity(intent);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_get_money);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initView() {
        assignViews();
        getToolbar().setTvTitle("提现").setBack();
    }

    @Override
    public void initListener() {
        tvGetMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
