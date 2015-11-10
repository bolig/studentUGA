package com.peoit.android.studentuga.net.server;

import com.loopj.android.http.RequestParams;
import com.peoit.android.peoit_lib.ActivityBase;
import com.peoit.android.studentuga.config.CommonUtil;
import com.peoit.android.studentuga.config.Constants;
import com.peoit.android.studentuga.config.NetConstants;
import com.peoit.android.studentuga.entity.UserInfo;
import com.peoit.android.studentuga.net.BaseCallBack;
import com.peoit.android.studentuga.net.BaseServer;
import com.peoit.android.studentuga.uitl.MyLogger;
import com.peoit.android.studentuga.uitl.encode.MD5;

/**
 * author:libo
 * time:2015/9/23
 * E-mail:boli_android@163.com
 * last: ...
 */
public class LoginServer extends BaseServer {


    public LoginServer(ActivityBase activityBase) {
        super(activityBase);
    }

    /**
     * 请求登录
     *
     * @param username 手机号
     * @param password 密码
     */
    public void requestLogin(final String username, final String password) {
        RequestParams params = getRequestParams();
        params.put("phone", username);
        params.put("password", MD5.getMD5Code(password));
        mActBase.showLoadingDialog("正在登录...");
        request(NetConstants.NET_LOGIN, UserInfo.class, params, new BaseCallBack<UserInfo>() {
            @Override
            public void onFinish() {
                mActBase.hideLoadingDialog();
            }

            @Override
            public void onResponseSuccess(UserInfo result) {
                CommonUtil.currentUser = result;
                mActBase.showToast("登录成功");
                MyLogger.e("user = " + result.toString());
                String sign = getSign(username, result.getPassword());
                mShare.put(Constants.SHARE_LOGIN_STAT, true);
                mShare.put(Constants.SHARE_LOGIN_SIGN, sign);
                mShare.put(Constants.SHARE_LOGIN_USERNAME, username);
                mShare.put(Constants.SHARE_LOGIN_PASSWORD, password);
                mShare.put(Constants.SHARE_USER_AVATER, result.getPic());
//                HomeActivity.startThisActivity(mActBase.getActivity());
                mActBase.getActivity().finish();
            }

            @Override
            protected void onResponseFailure(int statusCode, String msg) {
                mActBase.onResponseFailure(statusCode, msg);
            }

            @Override
            public void onResponseSuccessModelString(String result) {
                mShare.put(Constants.SHARE_LOGIN_USER, result);
            }
        });
    }

    public static String getSign(String username, String password) {
        String sign = username + "|" + password + "|" + "cydsxxxx";
        sign = MD5.getMD5Code(sign);
        return sign;
    }
}

