package com.peoit.android.studentuga.net.server;

import android.text.TextUtils;

import com.loopj.android.http.RequestParams;
import com.peoit.android.peoit_lib.ActivityBase;
import com.peoit.android.studentuga.config.NetConstants;
import com.peoit.android.studentuga.net.BaseCallBack;
import com.peoit.android.studentuga.net.BaseServer;

/**
 * author:libo
 * time:2015/11/1
 * E-mail:boli_android@163.com
 * last: ...
 */
public class PayUpServer extends BaseServer {

    public final String url = NetConstants.HOST + "recharge2.do";

    public PayUpServer(ActivityBase activityBase) {
        super(activityBase);
    }

    public void requestPayUp(String paytype, String amount, final OnPayUpCallBack callBack) {
        RequestParams params = getSignRequestParams();
        params.put("paytype", paytype);
        params.put("amount", amount);
        request(url, null, params, new BaseCallBack() {
            @Override
            public void onStart() {
                mActBase.showLoadingDialog("正在生成支付订单...");
            }

            @Override
            public void onFinish() {

            }

            @Override
            public void onResponseSuccess(Object result) {

            }

            @Override
            public void onResponseSuccessModelString(String result) {
                if (callBack != null){
                    callBack.onCallBack(TextUtils.isEmpty(result) ? -1 : 0, result);
                }
            }

            @Override
            protected void onResponseFailure(int statusCode, String msg) {
                if (callBack != null){
                    callBack.onCallBack(-1, null);
                }
            }
        });
    }

    public interface OnPayUpCallBack {
        void onCallBack(int mark, String orderNum);
    }
}
