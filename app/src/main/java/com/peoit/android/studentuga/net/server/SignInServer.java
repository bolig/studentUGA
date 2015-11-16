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
 * time:2015/10/31
 * E-mail:boli_android@163.com
 * last: ...
 */
public class SignInServer extends BaseServer {

    private final String url = NetConstants.HOST + "isUidJoinState.do";
    private final String url_signin = NetConstants.HOST + "sign.do";

    public SignInServer(ActivityBase activityBase) {
        super(activityBase);
    }

    public void requestCheckIsSignIn(final OnSuccessCallBack callBack) {
        request(url, null, getSignRequestParams(), new BaseCallBack() {

            @Override
            public void onResponseSuccess(Object result) {
                if (callBack != null) {
                    callBack.onSuccess(0);
                }
            }

            @Override
            protected void onResponseFailure(int statusCode, String msg) {
                if (callBack != null) {
                    callBack.onSuccess(statusCode);
                }
            }
        });
    }

    public void requestSignIn(String major,
                              String name, String telphone, String shen) {
        RequestParams params = getSignRequestParams();
        params.put("major", major);
        params.put("name", name);
        params.put("telphone", telphone);
        params.put("idnumber", shen);

        request(url_signin, null, params, new BaseCallBack() {
            @Override
            public void onStart() {
                mActBase.showLoadingDialog("正在提交申请...");
            }

            @Override
            public void onFinish() {
                mActBase.hideLoadingDialog();
            }

            @Override
            public void onResponseSuccess(Object result) {
                mActBase.showToast("申请提交成功");
                mActBase.getActivity().setResult(Activity.RESULT_OK, new Intent());
                mActBase.getActivity().finish();
            }

            @Override
            protected void onResponseFailure(int statusCode, String msg) {
                mActBase.showToast("申请提交失败");
            }
        });
    }
}
