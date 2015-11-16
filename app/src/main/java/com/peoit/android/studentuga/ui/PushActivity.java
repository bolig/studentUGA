package com.peoit.android.studentuga.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;

import com.peoit.android.peoit_lib.base.BaseActivity;
import com.peoit.android.studentuga.R;

import org.json.JSONException;
import org.json.JSONObject;

public class PushActivity extends BaseActivity {
    private TextView tvTitle;
    private TextView tvContent;
    private String mMsg;
    private String mData;
    private String mContent;

    private void assignViews() {
        tvTitle = (TextView) findViewById(R.id.tv_title);
        tvContent = (TextView) findViewById(R.id.tv_content);
    }

    public static void startThisActivity(Context mAc, String msg, String data) {
        Intent intent = new Intent(mAc, PushActivity.class);
        intent.putExtra("msg", msg);
        intent.putExtra("data", data);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK);
        mAc.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_push);
    }

    @Override
    public void initData() {
        mMsg = getIntent().getStringExtra("msg");
        mData = getIntent().getStringExtra("data");
        mContent = "";
        try {
            JSONObject json = new JSONObject(mData);
            mContent = json.getString("text");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (TextUtils.isEmpty(mContent)) {
            showToast("数据传输错误");
            finish();
            return;
        }
    }

    @Override
    public void initView() {
        assignViews();
        getToolbar().setTvTitle("通知详情");
        tvContent.setText(mContent);
    }

    @Override
    public void initListener() {

    }
}
