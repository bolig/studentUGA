package com.peoit.android.studentuga.net.server;

import com.loopj.android.http.RequestParams;
import com.peoit.android.peoit_lib.ActivityBase;
import com.peoit.android.studentuga.config.Constants;
import com.peoit.android.studentuga.config.NetConstants;
import com.peoit.android.studentuga.entity.RegistorSchoolInfo;
import com.peoit.android.studentuga.net.BaseCallBack;
import com.peoit.android.studentuga.net.BaseServer;
import com.peoit.android.studentuga.ui.LoginActivity;
import com.peoit.android.studentuga.ui.RegistorActivity;
import com.peoit.android.studentuga.uitl.encode.MD5;

/**
 * author:libo
 * time:2015/9/23
 * E-mail:boli_android@163.com
 * last: ...
 */
public class RegistorServer extends BaseServer {

    public RegistorServer(ActivityBase activityBase) {
        super(activityBase);
    }

    /**
     * 用户注册的时候下载学校信息
     *
     * @param callBack
     */
    public void loadRegistorSchool(BaseCallBack<RegistorSchoolInfo> callBack) {
        request(NetConstants.NET_QUERY_SCHOOL_INFO, RegistorSchoolInfo.class, null, callBack);
    }

    public void requestSendCode(String phone) {
        RequestParams params = getRequestParams();
        params.put("phone", phone);
        params.put("type", Constants.PASSWROD_TYPE_REGISTOR);
        mActBase.showLoadingDialog("正在请求...");
        request(NetConstants.NET_SEND_CODE, null, params, new BaseCallBack() {

            @Override
            public void onFinish() {
                mActBase.hideLoadingDialog();
            }

            @Override
            public void onResponseSuccess(Object result) {
                mActBase.showToast("发送成功");
                ((RegistorActivity) mActBase.getActivity()).changeUiShow(RegistorActivity.REGISTOR_PROGRESS.SCAN_AUTH_CODE);
            }

            @Override
            protected void onResponseFailure(int statusCode, String msg) {
                mActBase.onResponseFailure(statusCode, msg);
            }
        });
    }

    /**
     * 请求检查验证码的正确性
     *
     * @param phone
     * @param code
     */
    public void requestCheckCode(String phone, String code) {
        RequestParams params = getRequestParams();
        params.put("phone", phone);
        params.put("code", code);
        params.put("type", Constants.PASSWROD_TYPE_REGISTOR);
        mActBase.showLoadingDialog("正在验证...");
        request(NetConstants.NET_CHECK_CODE, null, params, new BaseCallBack() {

            @Override
            public void onFinish() {
                mActBase.hideLoadingDialog();
            }

            @Override
            public void onResponseSuccess(Object result) {
                mActBase.showToast("验证成功");
                ((RegistorActivity) mActBase.getActivity()).changeUiShow(RegistorActivity.REGISTOR_PROGRESS.SCAN_PASSWORD);
            }

            @Override
            protected void onResponseFailure(int statusCode, String msg) {
                mActBase.onResponseFailure(statusCode, msg);
            }
        });
    }

    /**
     * 注册用户信息
     *
     * @param name             --昵称
     * @param phone            --手机号
     * @param password         --密码(注: MD5加密后传输)
     * @param type             --1:游客 2：学生
     * @param code             --手机验证码
     * @param studentno--大学生学号 (type为2时必填)
     * @param schoolid         ----大学生所在学校id (type为2时必填)
     */
    public void requestRegistor(String name, String phone, String password, String type, String code, String studentno, String schoolid) {
        RequestParams params = getRequestParams();
        params.put("name", name);
        params.put("phone", phone);
        params.put("password", MD5.getMD5Code(password));
        params.put("type", type);
        params.put("code", code);
        if ("2".equals(type)) {
            params.put("studentno", studentno);
            params.put("schoolid", schoolid);
        }
        request(NetConstants.NET_REGISTOR, null, params, new BaseCallBack() {
            @Override
            public void onResponseSuccess(Object result) {
                mActBase.showToast("注册成功");
                LoginActivity.registor_startThisActivity(mActBase.getActivity());
                mActBase.getActivity().finish();
            }

            @Override
            protected void onResponseFailure(int statusCode, String msg) {
                mActBase.onResponseFailure(statusCode, msg);
            }
        });
    }
}
