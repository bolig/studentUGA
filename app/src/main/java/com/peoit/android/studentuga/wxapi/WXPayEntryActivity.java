package com.peoit.android.studentuga.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.peoit.android.peoit_lib.view.CustomActionBar;
import com.peoit.android.studentuga.R;
import com.peoit.android.studentuga.weixin.Constants;
import com.tencent.mm.sdk.constants.ConstantsAPI;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {
    private static final String TAG = "MicroMsg.";

    private IWXAPI api;

    private LinearLayout llGoods;
    private ImageView ivIcon;
    private TextView tvTitle1;
    private TextView tvPrice;
    private TextView tvMoney;
    private TextView tvDet;

    private View titleBar;
    private CustomActionBar actionbar;

    public static boolean isPay = false;

    private void assignViews() {
        llGoods = (LinearLayout) findViewById(R.id.ll_goods);
        ivIcon = (ImageView) findViewById(R.id.iv_icon);
        tvTitle1 = (TextView) findViewById(R.id.tv_title1);
        tvPrice = (TextView) findViewById(R.id.tv_price);
        tvMoney = (TextView) findViewById(R.id.tv_money);
        tvDet = (TextView) findViewById(R.id.tv_det);

        titleBar = findViewById(R.id.title_bar);
        actionbar = (CustomActionBar) findViewById(R.id.actionbar);

        actionbar.setTvTitle("微信支付详情").setTvR("确定", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        // 经测试在代码里直接声明透明状态栏更有效
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
            localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
        }
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_result);
        assignViews();
        api = WXAPIFactory.createWXAPI(this, Constants.APP_ID);
        api.handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq req) {

    }

    @Override
    public void onResp(BaseResp resp) {
        Log.d(TAG, "onPayFinish, errCode = " + resp.errCode);
        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            int type = resp.errCode;
            switch (type) {
                case BaseResp.ErrCode.ERR_OK:
                    isPay = true;
//                    showToast("支付成功");
                    finish();
                    break;
                case BaseResp.ErrCode.ERR_SENT_FAILED:
                    showToast("ERR_SENT_FAILED");
                    finish();
                    break;
                case BaseResp.ErrCode.ERR_USER_CANCEL:
                    showToast("取消支付");
                    finish();
                    break;
                case BaseResp.ErrCode.ERR_AUTH_DENIED:
                    showToast("ERR_AUTH_DENIED");
                    finish();
                    break;
                case BaseResp.ErrCode.ERR_UNSUPPORT:
                    showToast("ERR_UNSUPPORT");
                    finish();
                    break;
                case BaseResp.ErrCode.ERR_COMM:
                    showToast("系统异常, 支付不成功");
                    finish();
                    break;
            }
        }
    }

    @Override
    public void finish() {
        super.finish();
    }

    private void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
