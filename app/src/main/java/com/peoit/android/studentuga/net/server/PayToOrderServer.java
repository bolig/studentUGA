package com.peoit.android.studentuga.net.server;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;

import com.loopj.android.http.RequestParams;
import com.peoit.android.peoit_lib.ActivityBase;
import com.peoit.android.studentuga.config.NetConstants;
import com.peoit.android.studentuga.net.BaseCallBack;
import com.peoit.android.studentuga.net.BaseServer;
import com.peoit.android.studentuga.uitl.encode.MD5;

/**
 * author:libo
 * time:2015/10/26
 * E-mail:boli_android@163.com
 * last: ...
 */
public class PayToOrderServer extends BaseServer {

    private final String url_pay_zfb_wx = NetConstants.HOST + "orderPay.do";

    public PayToOrderServer(ActivityBase activityBase) {
        super(activityBase);
    }

    /**
     * 请求支付订单...
     *
     * @param orderids
     * @param paypassword
     */
    public void requestPayOrder(String orderids, String paypassword) {
        RequestParams params = getSignRequestParams();
        if (params == null) {
            return;
        }
        params.put("orderids", orderids);
        params.put("paypassword", MD5.getMD5Code(paypassword));
        params.put("type", "MD5");
        mActBase.showLoadingDialog("正在支付...");
        request(NetConstants.NET_PAY_ORDER, null, params, new BaseCallBack() {
            @Override
            public void onFinish() {
                mActBase.hideLoadingDialog();
            }

            @Override
            public void onResponseSuccess(Object result) {
                mActBase.showToast("支付成功");
                mActBase.getActivity().setResult(Activity.RESULT_OK, new Intent());
                mActBase.getActivity().finish();
            }

            @Override
            protected void onResponseFailure(int statusCode, String msg) {
                mActBase.onResponseFailure(statusCode, msg);
            }
        });
    }

    /**
     * 请求第三方支付
     *
     * @param paytype
     * @param orderids
     * @param callBack
     */
    public void requestPayByZhiFuBaoOrWeiXin(String paytype, String orderids, final PayUpServer.OnPayUpCallBack callBack){
        RequestParams params = getSignRequestParams();
        params.put("paytype", paytype);
        params.put("orderids", orderids);
        request(url_pay_zfb_wx, null, params, new BaseCallBack() {
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
}
