package com.peoit.android.studentuga.net.server;

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
public class TeacherOrStudentBindServer extends BaseServer {
    private final String url = NetConstants.HOST + "BingTutor.do";

    public TeacherOrStudentBindServer(ActivityBase activityBase) {
        super(activityBase);
    }

    public void requestBind(String uid) {
        RequestParams params = getSignRequestParams();
        params.put("uid", uid);
        request(url, null, params, new BaseCallBack() {
            @Override
            public void onStart() {
                mActBase.showLoadingDialog("正在绑定信息...");
            }

            @Override
            public void onFinish() {
                mActBase.hideLoadingDialog();
            }

            @Override
            public void onResponseSuccess(Object result) {
                mActBase.showToast("绑定成功");
            }

            @Override
            protected void onResponseFailure(int statusCode, String msg) {
                mActBase.onResponseFailure(statusCode, msg);
            }
        });
    }
}
