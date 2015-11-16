package com.peoit.android.studentuga.net;

import com.loopj.android.http.RequestParams;
import com.peoit.android.peoit_lib.ActivityBase;
import com.peoit.android.studentuga.config.CommonUtil;
import com.peoit.android.studentuga.config.NetConstants;
import com.peoit.android.studentuga.uitl.MyLogger;
import com.peoit.android.studentuga.uitl.ShareUserHelper;

/**
 * author:libo
 * time:2015/9/22
 * E-mail:boli_android@163.com
 * last: ...
 */
public class BaseServer {

    protected final ActivityBase mActBase;
    private String mRequestUrl;
    private NetConstants.Response_ mResponse_;
    protected ShareUserHelper mShare;

    public BaseServer(ActivityBase activityBase) {
        this.mActBase = activityBase;
        this.mShare = ShareUserHelper.getInstance();
    }

    /**
     * 发起联网请求数据， 默认为POST
     *
     * @param url
     * @param clazz
     * @param params
     * @param callBack
     */
    protected <T> void request(String url, Class<T> clazz, RequestParams params, BaseCallBack<T> callBack) {
        request(ServerMethod.POST, url, clazz, params, callBack);
    }

    /**
     * 发起联网请求数据
     *
     * @param method
     * @param url
     * @param clazz
     * @param params
     * @param callBack
     */
    protected <T> void request(ServerMethod method, String url, Class<T> clazz, RequestParams params, BaseCallBack<T> callBack) {
        MyLogger.e("requestParams = " + (params != null ? params.toString() : " null "));
        if (callBack != null) {
            getResponse(url);
            MyLogger.e("url = " + mRequestUrl);
            callBack.setmClazz(clazz);
            callBack.setResponse_(mResponse_);
            switch (method) {
                case POST:
                    AsyncHttpUtil.post(mRequestUrl, params, callBack);
                    break;
                case GET:
                    AsyncHttpUtil.get(mRequestUrl, params, callBack);
                    break;
                case JSON_POST:
                    AsyncHttpUtil.post(mActBase.getActivity(), mRequestUrl, params, callBack);
                    break;
            }
        }
    }

    /**
     * 解析自定义url
     *
     * @param url
     */
    private void getResponse(String url) {
        if (url.contains(NetConstants.$_ADD_$)) {
            String[] strUrl = url.split(NetConstants.$_ADD_$);
            mRequestUrl = strUrl[0];
            mResponse_ = NetConstants.Response_.LIST_MODEL.getResponse_(strUrl[1]);
        } else {
            mRequestUrl = url;
            mResponse_ = NetConstants.Response_.MODEL;
        }
    }

    /**
     * 获取空的请求数据封装类
     *
     * @return
     */
    protected RequestParams getRequestParams() {
        return new RequestParams();
    }

    /**
     * 获取有默认参数的数据封装类
     *
     * @return
     */
    protected RequestParams getSignRequestParams() {
        RequestParams params = new RequestParams();

        MyLogger.e("sign = " + CommonUtil.getSign());
        MyLogger.e("Phone = " + CommonUtil.getUserName());

        params.put("sign", CommonUtil.getSign());
        params.put("phone", CommonUtil.getUserName());
        return params;
    }

    public interface OnSuccessCallBack {
        void onSuccess(int mark);
    }
}
