package com.peoit.android.studentuga.net;

import android.content.Context;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.ResponseHandlerInterface;

/**
 * async-http 请求辅助类
 * <p/>
 * author:libo
 * time:2015/9/22
 * E-mail:boli_android@163.com
 * last: ...
 */
public class AsyncHttpUtil {

    private static AsyncHttpClient asyncHttpClient = new AsyncHttpClient();

    public static void get(String url, RequestParams params, ResponseHandlerInterface callBack) {
        asyncHttpClient.get(url, params, callBack);
    }

    public static void post(String url, RequestParams params, ResponseHandlerInterface callBack) {
        asyncHttpClient.post(url, params, callBack);
    }

    public static void post(Context context, String url, RequestParams params, ResponseHandlerInterface callBack) {
        asyncHttpClient.post(context, url, null, params, "application/json;charset=UTF-8", callBack);
    }
}
