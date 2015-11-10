package com.peoit.android.studentuga.net.server;

import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.peoit.android.peoit_lib.ActivityBase;
import com.peoit.android.studentuga.config.NetConstants;
import com.peoit.android.studentuga.entity.WeiXinInfo;
import com.peoit.android.studentuga.net.AsyncHttpUtil;
import com.peoit.android.studentuga.net.BaseCallBack;
import com.peoit.android.studentuga.net.BaseServer;
import com.peoit.android.studentuga.net.ServerMethod;
import com.peoit.android.studentuga.uitl.MyLogger;
import com.peoit.android.studentuga.weixin.Constants;
import com.peoit.android.studentuga.weixin.MD5;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.NameValuePair;
import cz.msebera.android.httpclient.client.utils.URLEncodedUtils;
import cz.msebera.android.httpclient.message.BasicNameValuePair;

/**
 * author:libo
 * time:2015/11/9
 * E-mail:boli_android@163.com
 * last: ...
 */
public class WeiXinServer extends BaseServer {
    private final String url_access_token = "https://api.weixin.qq.com/cgi-bin/token" + NetConstants.RESPONSE_NORMAL;

    private final String url_trade_no = "https://api.weixin.qq.com/pay/genprepay?access_token=";
    private String mAccessToken;

    public WeiXinServer(ActivityBase activityBase) {
        super(activityBase);
    }

    public void getAccessToken(final OnSuccessCallBack callBack) {
        RequestParams params = getRequestParams();
        params.put("grant_type", "client_credential");
        params.put("appid", Constants.APP_ID);
//        params.put("secret", Constants.APP_SUPPORT);
        request(ServerMethod.GET, url_access_token, null, params, new BaseCallBack() {
            @Override
            public void onParseCallBackData(String response) {
                if (TextUtils.isEmpty(response)) {
                    if (callBack != null) {
                        callBack.onSuccess(0);
                    }
                    return;
                }
                MyLogger.e(" 微信 " + response);
                try {
                    JSONObject json = new JSONObject(response);
                    mAccessToken = json.getString("access_token");
                    if (TextUtils.isEmpty(mAccessToken)) {
                        if (callBack != null) {
                            callBack.onSuccess(-1);
                        }
                    } else {
                        if (callBack != null) {
                            callBack.onSuccess(1);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    if (callBack != null) {
                        callBack.onSuccess(-1);
                    }
                }
            }

            @Override
            protected void onResponseFailure(int statusCode, String msg) {
                mActBase.onResponseFailure(statusCode, msg);
                if (callBack != null) {
                    callBack.onSuccess(-1);
                }
            }
        });
    }

    public void requestPrepayId(String body, String money, String tradeNo) {
        RequestParams params = getRequestParams();
        String nonceStr = genNonceStr();
        String packag = getPackAge(body, money, tradeNo);
        String timeStamp = genTimeStamp() + "";
        String sign = getAppSignature(packag, timeStamp, tradeNo);

//        params.put("appid", Constants.APP_ID);
//        params.add("noncestr", nonceStr);
//        params.put("timestamp", timeStamp);
//        params.put("package", packag);
//        params.put("app_signature", sign);
//        params.put("sign_method", "sha1");

        WeiXinInfo info = new WeiXinInfo();
        info.setAppid(Constants.APP_ID);
        info.setNoncestr(nonceStr);
        info.setApp_signature(sign);
        info.setPackag(packag);
        info.setSign_method("sha1");
        info.setTimestamp(timeStamp);

        String json = new Gson().toJson(info);
        MyLogger.e(json);

        params.setElapsedFieldInJsonStreamer(json);
        AsyncHttpUtil.post(mActBase.getActivity(), url_trade_no + mAccessToken, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String response = getResponseString(responseBody, DEFAULT_CHARSET);
                MyLogger.e("微信" + response);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                String errorMsg = getResponseString(responseBody, DEFAULT_CHARSET);
                MyLogger.e("微信" + errorMsg);
            }
        });
    }

    public static final String DEFAULT_CHARSET = "UTF-8";
    public static final String UTF8_BOM = "\uFEFF";

    /**
     * Attempts to encode response bytes as string of set encoding
     *
     * @param charset     charset to create string with
     * @param stringBytes response bytes
     * @return String of set encoding or null
     */
    public static String getResponseString(byte[] stringBytes, String charset) {
        try {
            String toReturn = (stringBytes == null) ? null : new String(stringBytes, charset);
            if (toReturn != null && toReturn.startsWith(UTF8_BOM)) {
                return toReturn.substring(1);
            }
            MyLogger.e(" --- response string --- " + toReturn);
            return toReturn;
        } catch (UnsupportedEncodingException e) {
            AsyncHttpClient.log.e(" 微信 ", "Encoding response into string failed", e);
            return "";
        }
    }

    private String getAppSignature(String packag, String time, String traceid) {
        List<NameValuePair> signParams = new LinkedList<NameValuePair>();
        signParams.add(new BasicNameValuePair("appid", Constants.APP_ID));
        signParams.add(new BasicNameValuePair("noncestr", Constants.APP_ID));
        signParams.add(new BasicNameValuePair("appkey", Constants.API_KEY));
        signParams.add(new BasicNameValuePair("package", packag));
        signParams.add(new BasicNameValuePair("timestamp", time));
        signParams.add(new BasicNameValuePair("traceid", traceid));

        String sign = genPackageSign(signParams);
        return sign;
    }

    /**
     * 生成签名
     */
    private String genPackageSign(List<NameValuePair> params) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < params.size(); i++) {
            sb.append(params.get(i).getName());
            sb.append('=');
            sb.append(params.get(i).getValue());
            sb.append('&');
        }
        sb.append("key=");
        sb.append(Constants.API_KEY);

        String packageSign = MD5.getMessageDigest(sb.toString().getBytes()).toUpperCase();
        Log.e("orion", packageSign);
        return packageSign;
    }

    private long genTimeStamp() {
        return System.currentTimeMillis() / 1000;
    }

    private String genNonceStr() {
        Random random = new Random();
        return MD5.getMessageDigest(String.valueOf(random.nextInt(10000)).getBytes());
    }

    private String getPackAge(String body, String money, String tradeNo) {
        List<NameValuePair> packageParams = new LinkedList<>();
        packageParams.add(new BasicNameValuePair("appid", Constants.APP_ID));
        packageParams.add(new BasicNameValuePair("body", body));
        packageParams.add(new BasicNameValuePair("mch_id", Constants.MCH_ID));
        packageParams.add(new BasicNameValuePair("nonce_str", genNonceStr()));
        packageParams.add(new BasicNameValuePair("notify_url", "http://120.24.165.95:8081/cyshop/api/wxverify.do"));
        packageParams.add(new BasicNameValuePair("out_trade_no", genTradeNo(tradeNo)));
        packageParams.add(new BasicNameValuePair("spbill_create_ip", "192.168.1.1"));
        packageParams.add(new BasicNameValuePair("total_fee", money));

        packageParams.add(new BasicNameValuePair("trade_type", "APP"));
        packageParams.add(new BasicNameValuePair("input_charset", "UTF-8"));

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < packageParams.size(); i++) {
            sb.append(packageParams.get(i).getName());
            sb.append("=");
            sb.append(packageParams.get(i).getValue());
            sb.append("&");
        }
        sb.append("key=");
        sb.append(Constants.API_KEY);
        String packAgeSign = MD5.getMessageDigest(sb.toString().getBytes()).toUpperCase();
        return URLEncodedUtils.format(packageParams, "UTF-8") + "&sign=" + packAgeSign;
    }

    private String genTradeNo(String tradeNo) {
        if (TextUtils.isEmpty(tradeNo)) {
            return "";
        }
        return MD5.getMessageDigest(tradeNo.getBytes());
    }
}
