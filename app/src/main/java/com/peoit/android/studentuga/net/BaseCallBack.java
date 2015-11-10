package com.peoit.android.studentuga.net;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.peoit.android.studentuga.config.NetConstants;
import com.peoit.android.studentuga.entity.BaseEntity;
import com.peoit.android.studentuga.entity.BaseListEntity;
import com.peoit.android.studentuga.error.NoCarryException;
import com.peoit.android.studentuga.uitl.MyLogger;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import cz.msebera.android.httpclient.Header;

/**
 * 数据请求数据返回操作类
 * <p/>
 * author:libo
 * time:2015/9/23
 * E-mail:boli_android@163.com
 * last: ...
 */
public abstract class BaseCallBack<T> extends AsyncHttpResponseHandler {

    private static final String TAG_ = " --- baseCallBack --- ";

    private static String LOG_TAG = " baseCallback ";

    private Class<T> mClazz;
    private NetConstants.Response_ mResponse_;

    public void setmClazz(Class<T> mClazz) {
        this.mClazz = mClazz;
    }

    public void setResponse_(NetConstants.Response_ response_) {
        this.mResponse_ = response_;
    }


    @Override
    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
        onSuccess(statusCode, headers, getResponseString(responseBody, getCharset()));
    }

    @Override
    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
        onFailure(statusCode, headers, getResponseString(responseBody, getCharset()), error);
    }

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
            AsyncHttpClient.log.e(LOG_TAG, "Encoding response into string failed", e);
            return "";
        }
    }

    /**
     * Called when request fails
     *
     * @param statusCode     http response status line
     * @param headers        response headers if any
     * @param responseString string response of given charset
     * @param throwable      throwable returned when processing request
     */
    @Deprecated
    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
        MyLogger.e(" statusCode = " + statusCode);
        MyLogger.e(responseString);
        onResponseFailure(statusCode, "网络连接异常, 请稍后再试");
    }

    /**
     * 请求成功的回调
     *
     * @param statusCode http response status line
     * @param headers    response headers if any
     * @param response   string response of given charset
     */
    @Deprecated
    public void onSuccess(int statusCode, Header[] headers, String response) {
        switch (mResponse_) {
            case NORMAL:
                onParseCallBackData(response);
                break;
            case MODEL:
                onParseCallBackData(response);
                onParseCallBackDataToModel(response);
                onParseCallBackDataToModelString(response);
                break;
            case LIST_MODEL:
                onParseCallBackDataToListModel(response);
                onParseCallBackDataToModelString(response);
                break;
            case LIST_MODEL_PAGE:
                onParseCallBackDataToListModelPage(response);
                onParseCallBackDataToModelString(response);
                break;
            case NORMAL_STRING:
                onParseCallBackDataToModelString(response);
                break;
        }
    }

    /**
     * 解析返回数据结合， 并带有分页
     *
     * @param response
     */
    private void onParseCallBackDataToListModelPage(String response) {
        MyLogger.e(" ----------------- ListModelPage parse ------------------ ");
    }

    /**
     * 解析返回数据集合
     *
     * @param response
     */
    private void onParseCallBackDataToListModel(String response) {
        MyLogger.e(" ----------------- ListModel parse ------------------ ");
        if (mClazz == null)
            throw new NullPointerException(" @libo mClazz is null");
        BaseListEntity<T> parseJson = new Gson().fromJson(response, getType(BaseListEntity.class, mClazz));
        if (parseJson != null) {
            if (parseJson.isSuccess()) {
                onResponseSuccessList(parseJson.getObj());
            } else {
                onResponseFailure(parseJson.getCode(), parseJson.getMessage());
            }
        } else {
            MyLogger.e(" statusCode = " + Error.GSON_ERROR);
            onResponseFailure(Error.GSON_ERROR, "错误数据, 请稍后再试...");
        }
    }

    /**
     * 解析单个实体
     *
     * @param response
     */
    private void onParseCallBackDataToModel(String response) {
        MyLogger.e(" ----------------- Model parse ------------------ ");
        Type type = (mClazz == null ? BaseEntity.class : getType(BaseEntity.class, mClazz));
        BaseEntity<T> parseJson = new Gson().fromJson(response, type);
        if (parseJson != null) {
            if (parseJson.isSuccess()) {
                onResponseSuccess(parseJson.getObj());
            } else {
                onResponseFailure(parseJson.getCode(), parseJson.getMessage());
            }
        } else {
            onResponseFailure(Error.GSON_ERROR, "错误数据, 请稍后再试...");
        }
    }

    /**
     * 解析单个实体
     *
     * @param response
     */
    private void onParseCallBackDataToModelString(String response) {
        MyLogger.e(" ----------------- Model parse ------------------ ");
        try {
            JSONObject json = new JSONObject(response);
            String obj = json.getString("obj");
            int code = json.getInt("code");
            String msg = json.getString("message");
            boolean success = json.getBoolean("success");
            if (code == 0 && success)
                onResponseSuccessModelString(obj);
            else
                onResponseFailure(code, msg);
        } catch (Exception e) {
            e.printStackTrace();
            onResponseFailure(Error.GSON_ERROR, "错误数据, 请稍后再试...");
        }
    }

    /**
     * 自定义解析
     *
     * @param response
     */
    public void onParseCallBackData(String response) {
        MyLogger.e(" ----------------- custom parse ------------------ ");
    }

    /**
     * 获取数据解析类型
     *
     * @param mBaseClass
     * @param mClazz
     * @return
     */
    private Type getType(final Class<?> mBaseClass, final Class... mClazz) {
        return new ParameterizedType() {
            public Type getRawType() {
                return mBaseClass;
            }

            public Type[] getActualTypeArguments() {
                return mClazz;
            }

            public Type getOwnerType() {
                return null;
            }
        };
    }

    /**
     * 数据解析成功后，集合的回调
     *
     * @param result
     */
    @Deprecated
    public void onResponseSuccessList(List<T> result) {
        throw new NoCarryException();
    }

    /**
     * 数据解析成功，单个实体的回调
     *
     * @param result
     */
    @Deprecated
    public void onResponseSuccess(T result) {
        throw new NoCarryException();
    }

    /**
     * obj只是一个String
     *
     * @param result
     */
    public void onResponseSuccessModelString(String result) {

    }

    /**
     * 自定义错误回调
     *
     * @param statusCode
     * @param msg
     */
    protected abstract void onResponseFailure(int statusCode, String msg);
}
