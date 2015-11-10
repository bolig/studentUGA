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
 * time:2015/11/5
 * E-mail:boli_android@163.com
 * last: ...
 */
public class ExpressServer extends BaseServer {
    private final String Url = NetConstants.HOST + "jcOrder.do";

    public ExpressServer(ActivityBase activityBase) {
        super(activityBase);
    }

    public void requestAddExpress(String orderid, String kdno, String kdtype) {
        RequestParams params = getSignRequestParams();
        params.put("orderid", orderid);
        params.put("kdno", kdno);
        params.put("kdtype", kdtype);
        request(Url, null, params, new BaseCallBack() {
            @Override
            public void onStart() {
                mActBase.showLoadingDialog("正在发货...");
            }

            @Override
            public void onFinish() {
                mActBase.hideLoadingDialog();
            }

            @Override
            public void onResponseSuccess(Object result) {
                mActBase.showToast("发货成功");
                mActBase.getActivity().setResult(Activity.RESULT_OK, new Intent());
                mActBase.getActivity().finish();
            }

            @Override
            protected void onResponseFailure(int statusCode, String msg) {
//                mActBase.onResponseFailure(statusCode, msg);
                mActBase.showToast("发货失败");
            }
        });
    }
}
