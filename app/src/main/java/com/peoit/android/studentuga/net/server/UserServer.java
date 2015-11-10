package com.peoit.android.studentuga.net.server;

import com.loopj.android.http.RequestParams;
import com.peoit.android.peoit_lib.ActivityBase;
import com.peoit.android.peoit_lib.base.BaseUIShow;
import com.peoit.android.studentuga.R;
import com.peoit.android.studentuga.config.CommonUtil;
import com.peoit.android.studentuga.config.NetConstants;
import com.peoit.android.studentuga.entity.UserInfo;
import com.peoit.android.studentuga.entity.UserOnNoLoginInfo;
import com.peoit.android.studentuga.net.BaseCallBack;
import com.peoit.android.studentuga.net.BaseServer;

/**
 * author:libo
 * time:2015/9/29
 * E-mail:boli_android@163.com
 * last: ...
 */
public class UserServer extends BaseServer {

    public UserServer(ActivityBase activityBase) {
        super(activityBase);
    }

    /**
     * 修改昵称
     *
     * @param nickName
     * @param callBack
     */
    public void requestModifyUserNickName(String nickName, final OnSuccessCallBack callBack) {
        RequestParams params = getSignRequestParams();
        if (params == null) {
            return;
        }
        params.put("name", nickName);
        mActBase.showLoadingDialog("正在修改...");
        request(NetConstants.NET_MODIFY_NICKNAME, null, params, new BaseCallBack() {

            @Override
            public void onFinish() {
                mActBase.hideLoadingDialog();
            }

            @Override
            public void onResponseSuccess(Object result) {
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

    /**
     * 请求修改用户个性签名
     *
     * @param userSign
     * @param callBack
     */
    public void requestModifyUserSign(String userSign, final OnSuccessCallBack callBack) {
        RequestParams params = getSignRequestParams();
        if (params == null) {
            return;
        }
        params.put("qm", userSign);
        mActBase.showLoadingDialog("正在修改...");
        request(NetConstants.NET_MODIFY_USERSIGN, null, params, new BaseCallBack() {

            @Override
            public void onFinish() {
                mActBase.hideLoadingDialog();
            }

            @Override
            public void onResponseSuccess(Object result) {
                if (callBack != null) {
                    callBack.onSuccess(0);
                }
            }

            @Override
            protected void onResponseFailure(int statusCode, String msg) {
                mActBase.showToast("保存失败");
            }
        });
    }

    /**
     * 查询当前用户信息...
     *
     * @param callBack
     */
    public void requestUserInfoById(OnUserInfoCallBack callBack) {
        requestUserInfoById(CommonUtil.currentUser.getId() + "", callBack);
    }

    /**
     * 通过用户id 获取用户信息
     *
     * @param uid
     * @param callBack
     */
    public void requestUserInfoById(final String uid, final OnUserInfoCallBack callBack) {
        RequestParams params = getSignRequestParams();
        if (params == null) {
            return;
        }
        params.put("uid", uid);
        mActBase.getUIShowPresenter().showError(R.drawable.nomingxi, "信息加载失败");
        mActBase.getUIShowPresenter().setOnReloadListener(new BaseUIShow.OnReloadListener() {
            @Override
            public void onReload() {
                requestUserInfoById(callBack);
            }
        });
        mActBase.getUIShowPresenter().showLoading();
        request(NetConstants.NET_QUERY_USERINFO_BYID, UserInfo.class, params, new BaseCallBack<UserInfo>() {
            @Override
            public void onResponseSuccess(UserInfo result) {
                if (callBack != null) {
                    if (result == null || result.isNull()) {
                        mActBase.getUIShowPresenter().showError();
                    } else {
                        mActBase.getUIShowPresenter().showData();
                        callBack.onCallBcak(result);
                    }
                }
            }

            @Override
            protected void onResponseFailure(int statusCode, String msg) {
                mActBase.onResponseFailure(statusCode, msg);
                mActBase.getUIShowPresenter().showError();
            }
        });
    }

    /**
     * 获取没登录状态下的用户信息
     *
     * @param uid
     * @param callBack
     */
    public void requestUserOnNoLogin(String uid, final OnUserOnNoLoginInfoCallBack callBack){
        RequestParams params = getRequestParams();
        params.put("uid", uid);
        request(NetConstants.NET_QUERY_USER, UserOnNoLoginInfo.class, params, new BaseCallBack<UserOnNoLoginInfo>() {
            @Override
            public void onResponseSuccess(UserOnNoLoginInfo result) {
                if (callBack != null){
                    callBack.onCallBcak(result);
                }
            }

            @Override
            protected void onResponseFailure(int statusCode, String msg) {
                mActBase.onResponseFailure(statusCode, msg);
                if (callBack != null){
                    callBack.onCallBcak(null);
                }
            }
        });
    }

    public interface OnUserInfoCallBack {
        void onCallBcak(UserInfo info);
    }

    public interface OnUserOnNoLoginInfoCallBack {
        void onCallBcak(UserOnNoLoginInfo info);
    }
}
