package com.peoit.android.studentuga.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.peoit.android.peoit_lib.base.BaseActivity;
import com.peoit.android.studentuga.R;
import com.peoit.android.studentuga.alipays.AliPayBase;
import com.peoit.android.studentuga.alipays.AliPayServer;
import com.peoit.android.studentuga.config.CommonUtil;
import com.peoit.android.studentuga.net.BaseServer;
import com.peoit.android.studentuga.net.server.PayUpServer;
import com.peoit.android.studentuga.net.server.WeiXinServer;
import com.peoit.android.studentuga.uitl.MyLogger;
import com.peoit.android.studentuga.weixin.WeiXinHelper;

public class PayUpActivity extends BaseActivity implements View.OnClickListener {
    private EditText etPayUpMoney;

    private LinearLayout llPayUpZfb;
    private LinearLayout llPayUpWx;
    private LinearLayout llPayUpXsf;

    private TextView tvPayUp;

    private ShowPayMode currentPayMode;
    private String mAmont;
    private WeiXinServer mWeiXinServer;

    private void assignViews() {
        etPayUpMoney = (EditText) findViewById(R.id.et_payUp_money);

        llPayUpZfb = (LinearLayout) findViewById(R.id.ll_payUp_zfb);
        llPayUpWx = (LinearLayout) findViewById(R.id.ll_payUp_wx);
        llPayUpXsf = (LinearLayout) findViewById(R.id.ll_payUp_xsf);

        tvPayUp = (TextView) findViewById(R.id.tv_pay_up);
    }

    public static void startThisActivity(Activity mAc) {
        if (CommonUtil.isLoginAndToLogin(mAc, false)) {
            Intent intent = new Intent(mAc, PayUpActivity.class);
            mAc.startActivityForResult(intent, 1);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_pay_up);
    }

    @Override
    public void initData() {
        mWeiXinServer = new WeiXinServer(mAct);
    }

    @Override
    public void initView() {
        assignViews();
        getToolbar().setBack().setTvTitle("充值");
        changeUIShow(ShowPayMode.zfb);
    }

    @Override
    public void initListener() {
        llPayUpZfb.setOnClickListener(this);
        llPayUpWx.setOnClickListener(this);
        llPayUpXsf.setOnClickListener(this);

        tvPayUp.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == llPayUpZfb) {
            changeUIShow(ShowPayMode.zfb);
        } else if (v == llPayUpWx) {
            changeUIShow(ShowPayMode.wx);
        } else if (v == llPayUpXsf) {
            changeUIShow(ShowPayMode.xsf);
        } else if (v == tvPayUp) {
            if (match()) {
                switch (currentPayMode) {
                    case zfb:
                        alipay();
                        break;
                    case wx:
                        weixin();
                        break;
                }
            }
        }
    }

    private void weixin() {
        new PayUpServer(this).requestPayUp(currentPayMode.mPayType, mAmont, new PayUpServer.OnPayUpCallBack() {
            @Override
            public void onCallBack(int mark, final String orderNum) {
                switch (mark) {
                    case -1:
                        showToast("支付失败");
                        break;
                    case 0:
                        final WeiXinHelper helper = new WeiXinHelper(mAct, orderNum, "weixin");
                        helper.setOnSuccessCallBack(new BaseServer.OnSuccessCallBack() {
                            @Override
                            public void onSuccess(int mark) {
                                switch (mark) {
                                    case 1:
                                        helper.genPayReq();
                                        MyLogger.e("mark = " + mark);
                                        helper.sendPayReq();
                                        break;
                                }
                            }
                        });
                        helper.toWeiXin();
//                        mWeiXinServer.getAccessToken(new BaseServer.OnSuccessCallBack() {
//                            @Override
//                            public void onSuccess(int mark) {
//                                MyLogger.e(" mark = " + mark);
//                                if (mark == 1){
//                                    mWeiXinServer.requestPrepayId("充值创业币", mAmont, orderNum);
//                                }
//                            }
//                        });
//                        WeiXinHelper1 helper1 = new WeiXinHelper1(mAct);
//                        helper1.sendPay(orderNum);
                        break;
                }
            }
        });
    }

    private void alipay() {
        new PayUpServer(this).requestPayUp(currentPayMode.mPayType, mAmont, new PayUpServer.OnPayUpCallBack() {
            @Override
            public void onCallBack(int mark, String orderNum) {
                switch (mark) {
                    case -1:
                        showToast("支付失败");
                        break;
                    case 0:
                        AliPayServer.toPay(mAct, orderNum, "创业币充值", "大学生创业大赛app创业币", mAmont, new AliPayBase.OnPayListener() {
                            @Override
                            public void onSuccess(String res) {
                                showToast("充值成功");
                                setResult(RESULT_OK, new Intent());
                                finish();
                            }

                            @Override
                            public void onFailed(String msg) {
                                showToast("充值失败");
                            }

                            @Override
                            public void on8000() {
                                showToast("等待支付结果确认");
                            }
                        });
                        break;
                }
            }
        });
    }

    private boolean match() {
        mAmont = etPayUpMoney.getText().toString();
        if (TextUtils.isEmpty(mAmont)) {
            showToast("请输入充值金额");
            return false;
        }
        try {
            int a = Integer.valueOf(mAmont);
        } catch (Exception e) {
            e.printStackTrace();
            showToast("充值金额必须为整数");
            return false;
        }
        return true;
    }

    /**
     * 改变界面显示
     *
     * @param mode
     */
    private void changeUIShow(ShowPayMode mode) {
        if (currentPayMode != null && mode == currentPayMode) {
            return;
        }

        llPayUpZfb.setSelected(false);
        llPayUpWx.setSelected(false);
        llPayUpXsf.setSelected(false);

        currentPayMode = mode;

        switch (mode) {
            case zfb:
                llPayUpZfb.setSelected(true);
                break;
            case wx:
                llPayUpWx.setSelected(true);
                break;
            case xsf:
                llPayUpXsf.setSelected(true);
                break;
        }
    }

    private enum ShowPayMode {
        zfb("bck"),
        wx("wxzf"),
        xsf("");

        public final String mPayType;

        ShowPayMode(String type) {
            mPayType = type;
        }
    }
}
