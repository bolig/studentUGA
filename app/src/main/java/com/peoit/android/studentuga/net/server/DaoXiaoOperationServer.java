package com.peoit.android.studentuga.net.server;

import android.app.Activity;
import android.content.Intent;

import com.loopj.android.http.RequestParams;
import com.peoit.android.peoit_lib.ActivityBase;
import com.peoit.android.studentuga.config.CommonUtil;
import com.peoit.android.studentuga.config.NetConstants;
import com.peoit.android.studentuga.net.BaseCallBack;
import com.peoit.android.studentuga.net.BaseServer;

/**
 * author:libo
 * time:2015/11/5
 * E-mail:boli_android@163.com
 * last: ...
 */
public class DaoXiaoOperationServer extends BaseServer {

    private final String URL_ADD_DAOXIAO = NetConstants.HOST + "joinConsignmentSale.do";
    private final String URL_CANCEL_DAOXIAO = NetConstants.HOST + "delMyProduct.do";

    public DaoXiaoOperationServer(ActivityBase activityBase) {
        super(activityBase);
    }

    public void requestDaoXiao(String id, String title, String price) {
        RequestParams params = getSignRequestParams();
        params.put("id", id);
        params.put("title", title);
        params.put("price", price);
        request(URL_ADD_DAOXIAO, null, params, new BaseCallBack() {
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
                mActBase.showToast("申请成功");
                mActBase.getActivity().setResult(Activity.RESULT_OK, new Intent());
                mActBase.getActivity().finish();
            }

            @Override
            protected void onResponseFailure(int statusCode, String msg) {
//                mActBase.onResponseFailure(statusCode, msg);
                mActBase.showToast("申请失败");
            }
        });
    }

    public void requestCancelDaoXiao(String id) {
        RequestParams params = getSignRequestParams();
        params.put("id", id);
        request(URL_CANCEL_DAOXIAO, null, params, new BaseCallBack() {
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
                mActBase.showToast("取消成功");
                CommonUtil.isDaiXiaoCancel = true;
                mActBase.getActivity().finish();
            }

            @Override
            protected void onResponseFailure(int statusCode, String msg) {
//                mActBase.onResponseFailure(statusCode, msg);
                mActBase.showToast("取消失败");
            }
        });
    }
}
