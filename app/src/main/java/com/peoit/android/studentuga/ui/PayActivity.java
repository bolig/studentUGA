package com.peoit.android.studentuga.ui;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.peoit.android.peoit_lib.base.BaseActivity;
import com.peoit.android.studentuga.R;
import com.peoit.android.studentuga.alipays.AliPayBase;
import com.peoit.android.studentuga.alipays.AliPayServer;
import com.peoit.android.studentuga.config.CommonUtil;
import com.peoit.android.studentuga.entity.UserInfo;
import com.peoit.android.studentuga.net.BaseServer;
import com.peoit.android.studentuga.net.server.PayToOrderServer;
import com.peoit.android.studentuga.net.server.PayUpServer;
import com.peoit.android.studentuga.net.server.UserServer;
import com.peoit.android.studentuga.ui.dialog.PayDialog;
import com.peoit.android.studentuga.weixin.WeiXinHelper;

public class PayActivity extends BaseActivity implements View.OnClickListener {
    private LinearLayout llPayUpCyb;
    private TextView tvBalance;
    private LinearLayout llPayUpZfb;
    private LinearLayout llPayUpWx;
    private LinearLayout llPayUpXsf;
    private TextView tvPayUp;
    private PayType lastPayType;
    private String mOrderIds;
    private double mMyBalance;
    private String mMoney;
    private TextView tvPayUpToChuangYeBi;
    private TextView tvMoney;
    private String mOrderTitle;

    private void assignViews() {
        llPayUpCyb = (LinearLayout) findViewById(R.id.ll_payUp_cyb);
        tvBalance = (TextView) findViewById(R.id.tv_balance);
        llPayUpZfb = (LinearLayout) findViewById(R.id.ll_payUp_zfb);
        llPayUpWx = (LinearLayout) findViewById(R.id.ll_payUp_wx);
        llPayUpXsf = (LinearLayout) findViewById(R.id.ll_payUp_xsf);
        tvPayUp = (TextView) findViewById(R.id.tv_pay_up);
        tvPayUpToChuangYeBi = (TextView) findViewById(R.id.tv_payUp);
        tvMoney = (TextView) findViewById(R.id.tv_money);
    }

    public static void startThisActivity(Activity mAc, String ids, String money, String title) {
        if (CommonUtil.isLoginAndToLogin(mAc, false)) {
            Intent intent = new Intent(mAc, PayActivity.class);
            intent.putExtra("ids", ids);
            intent.putExtra("money", money);
            intent.putExtra("title", title);
            mAc.startActivityForResult(intent, 1);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_pay);
    }

    @Override
    public void initData() {
        mOrderIds = getIntent().getStringExtra("ids");
        mMoney = getIntent().getStringExtra("money");
        mOrderTitle = getIntent().getStringExtra("title");
        if (TextUtils.isEmpty(mOrderIds)) {
            showToast("数据传输错误");
            finish();
            return;
        }
    }

    @Override
    public void initView() {
        assignViews();
        getToolbar().setBack().setTvTitle("支付");
        new UserServer(this).requestUserInfoById(new UserServer.OnUserInfoCallBack() {
            @Override
            public void onCallBcak(UserInfo info) {
                mMyBalance = info.getBalance();
                tvBalance.setText("可用余额(￥" + info.getBalance() + ")");
            }
        });
        tvMoney.setText("￥" + mMoney);
        changeUIShow(PayType.CHUANG_YE_BI);
    }

    @Override
    public void initListener() {
        tvPayUp.setOnClickListener(this);
        llPayUpCyb.setOnClickListener(this);
        llPayUpZfb.setOnClickListener(this);
        llPayUpWx.setOnClickListener(this);
        llPayUpXsf.setOnClickListener(this);
        tvPayUpToChuangYeBi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PayUpActivity.startThisActivity(mAct);
            }
        });
    }

    private void changeUIShow(PayType type) {
        if (lastPayType == type)
            return;
        lastPayType = type;
        llPayUpCyb.setSelected(false);
        llPayUpZfb.setSelected(false);
        llPayUpWx.setSelected(false);
        llPayUpXsf.setSelected(false);
        switch (type) {
            case CHUANG_YE_BI:
                llPayUpCyb.setSelected(true);
                break;
            case ZHI_FU_BAO:
                llPayUpZfb.setSelected(true);
                break;
            case WEI_XIN:
                llPayUpWx.setSelected(true);
                break;
            case XIN_SHOU_FU:
                llPayUpXsf.setSelected(true);
                break;
        }
    }

    @Override
    public void onClick(View v) {
        if (v == llPayUpCyb) {
            changeUIShow(PayType.CHUANG_YE_BI);
        } else if (v == llPayUpZfb) {
            changeUIShow(PayType.ZHI_FU_BAO);
        } else if (v == llPayUpWx) {
            changeUIShow(PayType.WEI_XIN);
        } else if (v == llPayUpXsf) {
            changeUIShow(PayType.XIN_SHOU_FU);
        } else if (v == tvPayUp) {
            switch (lastPayType) {
                case CHUANG_YE_BI:
                    payByChuangYeBi();
                    break;
                case ZHI_FU_BAO:
                    payByZhiFuBao();
                    break;
                case WEI_XIN:
                    payByWeiXin();
                    break;
                case XIN_SHOU_FU:
                    payByXinShouFu();
                    break;
            }
        }
    }

    /**
     * 使用兴首付支付
     */
    private void payByXinShouFu() {

    }

    /**
     * 使用微信支付
     */
    private void payByWeiXin() {
        new PayToOrderServer(mAct).requestPayByZhiFuBaoOrWeiXin(lastPayType.mType, mOrderIds, new PayUpServer.OnPayUpCallBack() {
            @Override
            public void onCallBack(int mark, String orderNum) {
                switch (mark) {
                    case -1:
                        showToast("支付失败");
                        break;
                    case 0:
                        int aount = (int) (Double.valueOf(mMoney) * 100d);
                        final WeiXinHelper helper = new WeiXinHelper(mAct, orderNum, "购买" + mOrderTitle, aount + "", "");
                        helper.setOnSuccessCallBack(new BaseServer.OnSuccessCallBack() {
                            @Override
                            public void onSuccess(int mark) {
                                hideLoadingDialog();
                                switch (mark) {
                                    case 1:
                                        helper.genPayReq();
                                        break;
                                }
                            }
                        });
                        helper.toWeiXin();
                        break;
                }
            }
        });
    }

    /**
     * 使用支付宝支付
     */
    private void payByZhiFuBao() {
        new PayToOrderServer(mAct).requestPayByZhiFuBaoOrWeiXin(lastPayType.mType, mOrderIds, new PayUpServer.OnPayUpCallBack() {
            @Override
            public void onCallBack(int mark, String orderNum) {
                hideLoadingDialog();
                switch (mark) {
                    case -1:
                        showToast("支付失败");
                        break;
                    case 0:
                        AliPayServer.toPay(mAct, orderNum, "大学生创业大赛app购买商品", mOrderTitle, mMoney, new AliPayBase.OnPayListener() {
                            @Override
                            public void onSuccess(String res) {
                                showToast("购买成功");
                                setResult(RESULT_OK, new Intent());
                                finish();
                            }

                            @Override
                            public void onFailed(String msg) {
                                showToast("购买失败");
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

    /**
     * 使用创业币支付
     */
    private void payByChuangYeBi() {
        if (CommonUtil.currentUser.isSetPaypwd()) {
            final PayDialog dialog = new PayDialog(this);
            dialog.setMessage(mMoney, lastPayType.mPayType + "");
            dialog.setListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String pass = dialog.getPass();
                    if (TextUtils.isEmpty(pass)) {
                        showToast("请输入您的密码");
                        return;
                    }
                    new PayToOrderServer(mAct).requestPayOrder(mOrderIds, pass);
                }
            });
            dialog.show();
        } else {
            final AlertDialog dialog = new AlertDialog.Builder(mAct)
                    .setTitle("提示")
                    .setMessage("您当前还没有支付密码, 是否去设置")
                    .setPositiveButton("去设置", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            PayPassActivity.startThisActivity(mAct, PayPassActivity.SETUP_PASS);
                        }
                    }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).show();
        }
    }

    public enum PayType {
        CHUANG_YE_BI("创业币支付", ""),
        ZHI_FU_BAO("支付宝支付", "bck"),
        WEI_XIN("微信支付", "wxzf"),
        XIN_SHOU_FU("兴首付支付", "");

        public final String mPayType;
        private final String mType;

        PayType(String type, String payType) {
            this.mPayType = type;
            this.mType = payType;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            new UserServer(this).requestUserInfoById(new UserServer.OnUserInfoCallBack() {
                @Override
                public void onCallBcak(UserInfo info) {
                    mMyBalance = info.getBalance();
                    tvBalance.setText("可用余额(￥" + info.getBalance() + ")");
                }
            });
        }
    }
}
