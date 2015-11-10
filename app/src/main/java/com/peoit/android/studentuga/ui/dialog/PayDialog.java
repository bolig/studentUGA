package com.peoit.android.studentuga.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.peoit.android.studentuga.R;

/**
 * author:libo
 * time:2015/10/26
 * E-mail:boli_android@163.com
 * last: ...
 */
public class PayDialog extends Dialog {
    private View viewBg;
    private TextView tvPayMoney;
    private TextView tvBlance;
    private EditText etPayPass;
    private TextView tvCancel;
    private TextView tvPay;
    private String mMoney;
    private String mBlance;
    private View.OnClickListener mListener;

    private void assignViews() {
        viewBg = findViewById(R.id.view_bg);
        tvPayMoney = (TextView) findViewById(R.id.tv_payMoney);
        tvBlance = (TextView) findViewById(R.id.tv_blance);
        etPayPass = (EditText) findViewById(R.id.et_payPass);
        tvCancel = (TextView) findViewById(R.id.tv_cancel);
        tvPay = (TextView) findViewById(R.id.tv_pay);
    }

    public PayDialog(Context context) {
        super(context, R.style.dialog);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE |
//                WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        setContentView(R.layout.pay_dialog);
        // 经测试在代码里直接声明透明状态栏更有效
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
            localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
        }
        assignViews();
        updataView();
    }

    private void updataView() {
        if (!TextUtils.isEmpty(mMoney)) {
            tvPayMoney.setText("￥" + mMoney);
        }
        if (!TextUtils.isEmpty(mBlance)) {
            tvBlance.setText(mBlance);
        }
        tvPay.setOnClickListener(mListener);
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    public void setMessage(String money, String blance) {
        this.mMoney = money;
        this.mBlance = blance;
        if (tvBlance != null) {
            tvBlance.setText("创业币(可用余额:￥" + blance + ")");
        }
        if (tvPayMoney != null) {
            tvPayMoney.setText("￥" + money);
        }
    }

    public void setListener(View.OnClickListener listener) {
        this.mListener = listener;
        if (tvPay != null) {
            tvPay.setOnClickListener(listener);
        }
    }

    public String getPass(){
        return etPayPass.getText().toString();
    }
}
