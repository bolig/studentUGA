package com.peoit.android.studentuga.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;

import com.peoit.android.peoit_lib.base.BaseActivity;
import com.peoit.android.studentuga.R;

/**
 * 注册协议
 * <p/>
 * author:libo
 * time:2015/11/16
 * E-mail:boli_android@163.com
 * last: ...
 */
public class ProtocolActivity extends BaseActivity implements View.OnClickListener {

    private WebView webView;

    private void assignViews() {
        webView = (WebView) findViewById(R.id.webView);
    }


    public static void startThisActivity(Activity mAc) {
        Intent intent = new Intent(mAc, ProtocolActivity.class);
        mAc.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_protocol);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initView() {
        assignViews();
        getToolbar().setTvTitle("大学生服务协议").setBack();
        webView.loadUrl("file:///android_asset/protocol.htm");
    }

    @Override
    public void initListener() {
    }

    @Override
    public void onClick(View v) {

    }


}

