package com.peoit.android.studentuga.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.peoit.android.peoit_lib.base.BaseActivity;
import com.peoit.android.studentuga.R;
import com.peoit.android.studentuga.config.CommonUtil;
import com.peoit.android.studentuga.ui.dialog.PhotoDialog;

/**
 * 选择订单界面
 * <p/>
 * author:libo
 * time:2015/8/4
 * E-mail:boli_android@163.com
 * last: ...
 */
public class MyOrderOptionActivity extends BaseActivity implements View.OnClickListener {
    private LinearLayout llMyHoldOrder;
    private LinearLayout llMyScaleOrder;

    /**
     * findView
     */
    private void assignViews() {
        llMyHoldOrder = (LinearLayout) findViewById(R.id.ll_myHoldOrder);
        llMyScaleOrder = (LinearLayout) findViewById(R.id.ll_myScaleOrder);
    }

    /**
     * 启动当前页
     *
     * @param mAc
     */
    public static void startThisActivity(Activity mAc) {
        if (CommonUtil.isLoginAndToLogin(mAc, false)){
            Intent intent = new Intent(mAc, MyOrderOptionActivity.class);
            mAc.startActivity(intent);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_my_order_option);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initView() {
        assignViews();
        getToolbar().setBack().setTvTitle("我的订单");
        switch (CommonUtil.getUserType()) {
            case shangJia:
            case xueSheng:
                llMyScaleOrder.setVisibility(View.VISIBLE);
                break;
            default:
                llMyScaleOrder.setVisibility(View.GONE);
                break;
        }
    }

    @Override
    public void initListener() {
        llMyHoldOrder.setOnClickListener(this);
        llMyScaleOrder.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == llMyHoldOrder) {
//            showToast("我的拿货单");
            MyOrderActivity.startThisActivity(mAct);
        } else if (v == llMyScaleOrder) {
            final PhotoDialog dialog = new PhotoDialog(this);
            dialog.setText("售卖商品订单", "代销商品订单");
            dialog.setNativeListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    MyScaleOrderActivity.startThisActivity(mAct, MyScaleOrderActivity.MY_My_ORDER);
                }
            });
            dialog.setTakeListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    MyScaleOrderActivity.startThisActivity(mAct, MyScaleOrderActivity.MY_He_ORDER);
                }
            });
            dialog.show();
        }
    }
}
