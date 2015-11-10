package com.peoit.android.studentuga.ui;

import android.os.Bundle;
import android.os.Handler;

import com.peoit.android.peoit_lib.base.BaseActivity;
import com.peoit.android.studentuga.R;
import com.peoit.android.studentuga.net.server.PayPassServer;

/**
 * 引导界面
 * <p/>
 * author:libo
 * time:2015/8/4
 * E-mail:boli_android@163.com
 * last: ...
 */
public class WelActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setDisplayAcionbar(false);
        setContentView(R.layout.act_wel);
    }

    @Override
    public void initData() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                HomeActivity.startThisActivity(mAct);
                finish();
            }
        }, 2000);
    }

    @Override
    public void initView() {
        PayPassServer.getPass("aaa");
    }

    @Override
    public void initListener() {

    }
}
