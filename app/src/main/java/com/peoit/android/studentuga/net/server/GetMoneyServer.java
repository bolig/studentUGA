package com.peoit.android.studentuga.net.server;

import android.app.Activity;
import android.content.Intent;

import com.loopj.android.http.RequestParams;
import com.peoit.android.peoit_lib.ActivityBase;
import com.peoit.android.studentuga.config.NetConstants;
import com.peoit.android.studentuga.net.BaseCallBack;
import com.peoit.android.studentuga.net.BaseServer;

/**
 * author:libo
 * time:2015/11/10
 * E-mail:boli_android@163.com
 * last: ...
 */
public class GetMoneyServer extends BaseServer {
    private final String url_getMoney = NetConstants.HOST + "AddWithdraws.do";
    private final String url_cancel = NetConstants.HOST + "delWithdraws.do";

    public GetMoneyServer(ActivityBase activityBase) {
        super(activityBase);
    }

    public void requestGetMoney(String money) {
        RequestParams params = getSignRequestParams();
        params.put("money", money);
        request(url_getMoney, null, params, new BaseCallBack() {
            @Override
            public void onStart() {
                mActBase.showLoadingDialog("正在申请提现...");
            }

            @Override
            public void onFinish() {
                mActBase.hideLoadingDialog();
            }

            @Override
            public void onResponseSuccess(Object result) {
                mActBase.showToast("申请成功");
                mActBase.getActivity().setResult(Activity.RESULT_OK, new Intent());
                mActBase.getActivity().finish();
            }

            @Override
            protected void onResponseFailure(int statusCode, String msg) {
                mActBase.onResponseFailure(statusCode, msg);
            }
        });
    }

    public void requestCancel(String id, final OnSuccessCallBack callBack) {
        RequestParams params = getSignRequestParams();
        params.put("id", id);
        request(url_cancel, null, params, new BaseCallBack() {
            @Override
            public void onStart() {
                mActBase.showLoadingDialog("正在取消提现...");
            }

            @Override
            public void onFinish() {
                mActBase.hideLoadingDialog();
            }

            @Override
            public void onResponseSuccess(Object result) {
                mActBase.showToast("取消成功");
                if (callBack != null) {
                    callBack.onSuccess(0);
                }
            }

            @Override
            protected void onResponseFailure(int statusCode, String msg) {
                mActBase.onResponseFailure(statusCode, msg);
            }
        });
    }
}
