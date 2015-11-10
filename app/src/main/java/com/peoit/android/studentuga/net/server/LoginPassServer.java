package com.peoit.android.studentuga.net.server;

import com.loopj.android.http.RequestParams;
import com.peoit.android.peoit_lib.ActivityBase;
import com.peoit.android.studentuga.config.CommonUtil;
import com.peoit.android.studentuga.config.Constants;
import com.peoit.android.studentuga.config.NetConstants;
import com.peoit.android.studentuga.net.BaseCallBack;
import com.peoit.android.studentuga.net.BaseServer;
import com.peoit.android.studentuga.uitl.encode.MD5;

/**
 * 登录密码
 * <p/>
 * author:libo
 * time:2015/10/9
 * E-mail:boli_android@163.com
 * last: ...
 */
public class LoginPassServer extends BaseServer {

    public LoginPassServer(ActivityBase activityBase) {
        super(activityBase);
    }

    /**
     * 修改登录密码
     *
     * @param newpassword
     * @param oldpassword
     */
    public void requestModLoginPass(final String newpassword, String oldpassword) {
        RequestParams params = getSignRequestParams();
        if (params == null) {
            return;
        }
        final String md5Pass = MD5.getMD5Code(newpassword);
        params.put("newpassword", md5Pass);
        params.put("oldpassword", MD5.getMD5Code(oldpassword));
        mActBase.showLoadingDialog("正在修改登录密码...");
        request(NetConstants.NET_LOGINPASS_MOD, null, params, new BaseCallBack() {
            @Override
            public void onFinish() {
                mActBase.hideLoadingDialog();
            }

            @Override
            public void onResponseSuccess(Object result) {
                mActBase.showToast("修改成功");

                String sign = LoginServer.getSign(CommonUtil.getUserName(), md5Pass);
                mShare.put(Constants.SHARE_LOGIN_PASSWORD, newpassword);
                mShare.put(Constants.SHARE_LOGIN_SIGN, sign);

                mActBase.getActivity().finish();
            }

            @Override
            protected void onResponseFailure(int statusCode, String msg) {
                mActBase.onResponseFailure(statusCode, msg);
            }
        });
    }

    /**
     * 找回登录密码
     *
     * @param password
     */
    public void requestFindLoginPass(final String phone, String code, final String password) {
        final String md5Pass = MD5.getMD5Code(password);
        RequestParams params = getRequestParams();
        params.put("phone", phone);
        params.put("code", code);
        params.put("password", md5Pass);
        request(NetConstants.NET_LOGINPASS_FIND, null, params, new BaseCallBack() {
            @Override
            public void onFinish() {
                mActBase.hideLoadingDialog();
            }

            @Override
            public void onResponseSuccess(Object result) {
                mActBase.showToast("修改成功");

                String sign = LoginServer.getSign(phone, md5Pass);
                mShare.put(Constants.SHARE_LOGIN_PASSWORD, password);
                mShare.put(Constants.SHARE_LOGIN_SIGN, sign);

                mActBase.getActivity().finish();
            }

            @Override
            protected void onResponseFailure(int statusCode, String msg) {
                mActBase.onResponseFailure(statusCode, msg);
            }
        });
    }

    /**
     * 请求短信验证码
     *
     * @param phone
     * @param callBack
     */
    public void requestSendCode(String phone, final OnSuccessCallBack callBack) {
        RequestParams params = getRequestParams();
        params.put("phone", phone);
        params.put("type", Constants.PASSWROD_TYPE_FIND);
        mActBase.showLoadingDialog("正在请求...");
        request(NetConstants.NET_SEND_CODE, null, params, new BaseCallBack() {
            @Override
            public void onFinish() {
                mActBase.hideLoadingDialog();
            }

            @Override
            public void onResponseSuccess(Object result) {
                mActBase.showToast("发送成功");
                if (callBack != null) {
                    callBack.onSuccess(1);
                }
            }

            @Override
            protected void onResponseFailure(int statusCode, String msg) {
//                mActBase.onResponseFailure(statusCode, msg);
                mActBase.showToast("发送失败");
            }
        });
    }

    /**
     * 请求检查验证码的正确性
     *
     * @param phone
     * @param code
     */
    public void requestCheckCode(String phone, String code, final OnSuccessCallBack callBack) {
        RequestParams params = getRequestParams();
        params.put("phone", phone);
        params.put("code", code);
        params.put("type", Constants.PASSWROD_TYPE_FIND);
        mActBase.showLoadingDialog("正在验证...");
        request(NetConstants.NET_CHECK_CODE, null, params, new BaseCallBack() {

            @Override
            public void onFinish() {
                mActBase.hideLoadingDialog();
            }

            @Override
            public void onResponseSuccess(Object result) {
                mActBase.showToast("验证成功");
                if (callBack != null) {
                    callBack.onSuccess(1);
                }
            }

            @Override
            protected void onResponseFailure(int statusCode, String msg) {
                mActBase.onResponseFailure(statusCode, msg);
            }
        });
    }
}
