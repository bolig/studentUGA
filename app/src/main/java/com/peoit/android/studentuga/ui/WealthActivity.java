package com.peoit.android.studentuga.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.peoit.android.peoit_lib.base.BaseActivity;
import com.peoit.android.studentuga.R;
import com.peoit.android.studentuga.config.CommonUtil;
import com.peoit.android.studentuga.entity.UserInfo;
import com.peoit.android.studentuga.net.server.UserServer;

/**
 * 财富中心
 * <p/>
 * author:libo
 * time:2015/8/4
 * E-mail:boli_android@163.com
 * last: ...
 */
public class WealthActivity extends BaseActivity implements View.OnClickListener {
    private TextView tvBalance;
    private LinearLayout llPayUp;
    private LinearLayout llToMoney;
    private LinearLayout llWaterCourse;

    private void assignViews() {
        tvBalance = (TextView) findViewById(R.id.tv_balance);
        llPayUp = (LinearLayout) findViewById(R.id.ll_payUp);
        llToMoney = (LinearLayout) findViewById(R.id.ll_toMoney);
        llWaterCourse = (LinearLayout) findViewById(R.id.ll_water_course);
    }

    public static void startThisActivity(Activity mAc) {
        if (CommonUtil.isLoginAndToLogin(mAc, false)) {
            Intent intent = new Intent(mAc, WealthActivity.class);
            mAc.startActivity(intent);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_wealth);
    }

    @Override
    public void initData() {
        new UserServer(this).requestUserInfoById(CommonUtil.currentUser.getId() + "", new UserServer.OnUserInfoCallBack() {
            @Override
            public void onCallBcak(UserInfo info) {
                tvBalance.setText(info.getBalance() + "");
            }
        });
    }

    @Override
    public void initView() {
        assignViews();
        getToolbar().setBack().setTvTitle("财富中心");
    }

    @Override
    public void initListener() {
        llPayUp.setOnClickListener(this);
        llToMoney.setOnClickListener(this);
        llWaterCourse.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == llPayUp) {
            PayUpActivity.startThisActivity(mAct);
        } else if (v == llToMoney) {
            GetMoneyActivity.startThisActivity(mAct);
        } else if (v == llWaterCourse) {
            TradeLogActivity.startThisActivity(mAct);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK){
            initData();
        }
    }
}
