package com.peoit.android.studentuga.net.server;

import com.loopj.android.http.RequestParams;
import com.peoit.android.peoit_lib.ActivityBase;
import com.peoit.android.studentuga.config.CommonUtil;
import com.peoit.android.studentuga.config.NetConstants;
import com.peoit.android.studentuga.net.BaseCallBack;
import com.peoit.android.studentuga.net.BaseServer;
import com.peoit.android.studentuga.uitl.MyLogger;
import com.peoit.android.studentuga.uitl.encode.Base64;
import com.peoit.android.studentuga.uitl.encode.MD5;
import com.peoit.android.studentuga.uitl.encode.RsaUtils;

/**
 * author:libo
 * time:2015/10/9
 * E-mail:boli_android@163.com
 * last: ...
 */
public class PayPassServer extends BaseServer {
    private final String url = NetConstants.HOST + "findzfpassword.do";

    public PayPassServer(ActivityBase activityBase) {
        super(activityBase);
    }

    /**
     * 请求设置支付密码
     *
     * @param password
     */
    public void requestFindPayPass(String password, String code) {
        RequestParams params = getSignRequestParams();
        if (params == null) {
            return;
        }
        params.put("paypassword", MD5.getMD5Code(password));
        params.put("code", code);
        params.put("type", "MD5");
        mActBase.showLoadingDialog("正在保存...");
        request(url, null, params, new BaseCallBack() {
            @Override
            public void onFinish() {
                mActBase.hideLoadingDialog();
            }

            @Override
            public void onResponseSuccess(Object result) {
                mActBase.showToast("支付密码成功");
                CommonUtil.currentUser.setSetPaypwd(true);
                CommonUtil.saveCurrentUser(CommonUtil.currentUser);
                mActBase.getActivity().finish();
            }

            @Override
            protected void onResponseFailure(int statusCode, String msg) {
                mActBase.onResponseFailure(statusCode, msg);
            }
        });
    }

    /**
     * 请求设置支付密码
     *
     * @param password
     */
    public void requestSetUpPayPass(String password) {
        RequestParams params = getSignRequestParams();
        if (params == null) {
            return;
        }
        params.put("paypassword", MD5.getMD5Code(password));
        params.put("type", "MD5");
        mActBase.showLoadingDialog("正在保存...");
        request(NetConstants.NET_PAYPASS_SETUP, null, params, new BaseCallBack() {
            @Override
            public void onFinish() {
                mActBase.hideLoadingDialog();
            }

            @Override
            public void onResponseSuccess(Object result) {
                mActBase.showToast("设置支付密码成功");
                CommonUtil.currentUser.setSetPaypwd(true);
                CommonUtil.saveCurrentUser(CommonUtil.currentUser);
                mActBase.getActivity().finish();
            }

            @Override
            protected void onResponseFailure(int statusCode, String msg) {
                mActBase.onResponseFailure(statusCode, msg);
            }
        });
    }

    /**
     * 请求修改支付密码
     *
     * @param newpassword
     * @param oldpassword
     */
    public void requestModPayPass(String newpassword, String oldpassword) {
        RequestParams params = getSignRequestParams();
        if (params == null) {
            return;
        }
        params.put("newpassword", MD5.getMD5Code(newpassword));
        params.put("oldpassword", MD5.getMD5Code(oldpassword));
        params.put("type", "MD5");
        mActBase.showLoadingDialog("正在保存...");
        request(NetConstants.NET_PAYPASS_MOD, null, params, new BaseCallBack() {
            @Override
            public void onFinish() {
                mActBase.hideLoadingDialog();
            }

            @Override
            public void onResponseSuccess(Object result) {
                mActBase.showToast("修改成功");
                CommonUtil.currentUser.setSetPaypwd(true);
                CommonUtil.saveCurrentUser(CommonUtil.currentUser);
                mActBase.getActivity().finish();
            }

            @Override
            protected void onResponseFailure(int statusCode, String msg) {
                mActBase.onResponseFailure(statusCode, msg);
            }
        });
    }

    public static final String PUBLIC_SIGN = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCoMoiFupfTzOSkAnYAvwG9UB5sSa0zt1/EdL5Lkj"
            + "yLFDp2opM+bNwYkB/tW1nnZFEoP5QdxltIEQkgkTx3FkzwovAhJMfiLp+n8r3POfgaUBF2qUuRre5c4yfh7nfA9REsJcI"
            + "1pY/wRI6nUnVMkgRLK3DIyUqjM5xnJ/UVb4wvywIDAQAB";

    public static String getPass(String realPass) {
        String md5pass = MD5.getMD5Code(realPass);
        String rsaPass = null;
        try {
            byte[] bytes = RsaUtils.encryptData(md5pass.getBytes(), RsaUtils.loadPublicKey(PUBLIC_SIGN));
            rsaPass = Base64.encode(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        MyLogger.e("md5 = " + md5pass);
        MyLogger.e(rsaPass);
        return rsaPass;
    }
}
