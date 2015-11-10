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
import com.peoit.android.studentuga.entity.ExpressInfo;
import com.peoit.android.studentuga.net.server.ExpressServer;

public class AddExpressActivity extends BaseActivity {
    private TextView tvExpressName;
    private EditText etExpressNum;
    private TextView tvDaixiao;
    private String mOrderId;
    private ExpressInfo mExpressInfo;
    private String mExpressNum;

    private void assignViews() {
        tvExpressName = (TextView) findViewById(R.id.tv_expressName);
        etExpressNum = (EditText) findViewById(R.id.et_expressNum);
        tvDaixiao = (TextView) findViewById(R.id.tv_daixiao);
    }

    public static void startThisActivityForResult(Activity mAc, String orderId) {
        Intent intent = new Intent(mAc, AddExpressActivity.class);
        intent.putExtra("oid", orderId);
        mAc.startActivityForResult(intent, 2);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_add_express);
    }

    @Override
    public void initData() {
        mOrderId = getIntent().getStringExtra("oid");
    }

    @Override
    public void initView() {
        assignViews();
        getToolbar().setBack().setTvTitle("发货");
    }

    @Override
    public void initListener() {
        tvDaixiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (match()) {
                    new ExpressServer(mAct).requestAddExpress(mOrderId, mExpressNum, mExpressInfo.getName());
                }
            }
        });
        tvExpressName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ExpressActivity.startThisActivity(mAct);
            }
        });
    }

    private boolean match() {
        if (mExpressInfo == null) {
            showToast("请选择快递");
            return false;
        }
        mExpressNum = etExpressNum.getText().toString();
        if (TextUtils.isEmpty(mExpressNum)) {
            showToast("请输入快递单号");
            return false;
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (data != null) {
                mExpressInfo = (ExpressInfo) data.getSerializableExtra("data");
                tvExpressName.setText(mExpressInfo.getMark());
            }
        }
    }
}
