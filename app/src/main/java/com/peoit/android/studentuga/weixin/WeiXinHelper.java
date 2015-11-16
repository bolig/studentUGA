package com.peoit.android.studentuga.weixin;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;
import android.util.Xml;
import android.widget.Toast;

import com.peoit.android.studentuga.net.BaseServer;
import com.peoit.android.studentuga.uitl.MyLogger;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.xmlpull.v1.XmlPullParser;

import java.io.StringReader;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * author:libo
 * time:2015/11/5
 * E-mail:boli_android@163.com
 * last: ...
 */
public class WeiXinHelper {
    private final static String TAG = "-weixin-";

    private final PayReq req;
    private final StringBuffer sb;
    private final String mOrderNum;
    private final String mOrderTitle;
    private final IWXAPI msgApi;
    private final Activity mAc;
    private final String mAount;
    private final String mAttach;
    private Map<String, String> resultunifiedorder;
    private BaseServer.OnSuccessCallBack mOnSuccessCallBack;

    public WeiXinHelper(Activity mAc, String orderNum, String orderTitle, String aount, String attach) {
        this.mAc = mAc;
        req = new PayReq() {
            @Override
            public String toString() {
                return partnerId + "," +
                        prepayId + "," +
                        packageValue + "," +
                        nonceStr + "," +
                        timeStamp + "," +
                        sign + "," +
                        extData + "," +
                        appId;
            }
        };
        sb = new StringBuffer();

        msgApi = WXAPIFactory.createWXAPI(mAc, null);
        msgApi.registerApp(Constants.APP_ID);

        mOrderNum = orderNum;
        mOrderTitle = orderTitle;
        mAount = aount;
        mAttach = attach;
    }

    public void toWeiXin() {
        GetPrepayIdTask getPrepayId = new GetPrepayIdTask();
        getPrepayId.execute();
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
        Log.e("orion777777777777777", packageSign);
        return packageSign;
    }

    private String genAppSign(List<NameValuePair> params) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < params.size(); i++) {
            sb.append(params.get(i).getName());
            sb.append('=');
            sb.append(params.get(i).getValue());
            sb.append('&');
        }
        sb.append("key=");
        sb.append(Constants.API_KEY);

        this.sb.append("sign str\n" + sb.toString() + "\n\n");
        String appSign = MD5.getMessageDigest(sb.toString().getBytes()).toUpperCase();
        Log.e("orion", appSign);
        return appSign;
    }

    private String toXml(List<NameValuePair> params) {
        StringBuilder sb = new StringBuilder();
        sb.append("<xml>");
        for (int i = 0; i < params.size(); i++) {
            sb.append("<" + params.get(i).getName() + ">");
            sb.append(params.get(i).getValue());
            sb.append("</" + params.get(i).getName() + ">");
        }
        sb.append("</xml>");

        Log.e("orion1", sb.toString());
        return sb.toString();
    }

    private class GetPrepayIdTask extends AsyncTask<Void, Void, Map<String, String>> {

        private ProgressDialog dialog;

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected void onPostExecute(Map<String, String> result) {
            if (dialog != null) {
                dialog.dismiss();
            }
            sb.append("prepay_id\n" + result.get("prepay_id") + "\n\n");
            resultunifiedorder = result == null ? new HashMap<String, String>() : result;
            if (mOnSuccessCallBack != null) {
                mOnSuccessCallBack.onSuccess(ORDER_CALLBACK);
            }
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }

        @Override
        protected Map<String, String> doInBackground(Void... params) {
            String url = String.format("https://api.mch.weixin.qq.com/pay/unifiedorder");
            String entity = genProductArgs();
            Log.e("orion2", entity);
            try {
                byte[] buf = Util.httpPost(url, entity);
                String content = new String(buf);
                Log.e("orion3", content);
                Map<String, String> xml = decodeXml(content);
                return xml;
            } catch (Exception e) {
                e.printStackTrace();
                return new HashMap<>();
            }
        }
    }

    public final static int ORDER_CALLBACK = 1;

    public Map<String, String> decodeXml(String content) {
        try {
            Map<String, String> xml = new HashMap<String, String>();
            XmlPullParser parser = Xml.newPullParser();
            parser.setInput(new StringReader(content));
            int event = parser.getEventType();
            while (event != XmlPullParser.END_DOCUMENT) {
                String nodeName = parser.getName();
                switch (event) {
                    case XmlPullParser.START_DOCUMENT:

                        break;
                    case XmlPullParser.START_TAG:
                        if ("xml".equals(nodeName) == false) {
                            //实例化student对象
                            xml.put(nodeName, parser.nextText());
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        break;
                }
                event = parser.next();
            }
            return xml;
        } catch (Exception e) {
            Log.e("orion4", e.toString());
        }
        return null;
    }

    private String genNonceStr() {
        Random random = new Random();
        return MD5.getMessageDigest(String.valueOf(random.nextInt(10000)).getBytes());
    }

    private long genTimeStamp() {
        return System.currentTimeMillis() / 1000;
    }

    private String genOutTradNo(String str) {
        if (TextUtils.isEmpty(str)) {
            return "";
        }
        return MD5.getMessageDigest(str.getBytes());
    }

    private String genProductArgs() {
        StringBuffer xml = new StringBuffer();
        try {
            String nonceStr = genNonceStr();
            xml.append("</xml>");
            List<NameValuePair> packageParams = new LinkedList<NameValuePair>();
            packageParams.add(new BasicNameValuePair("appid", Constants.APP_ID));
            packageParams.add(new BasicNameValuePair("body", mOrderTitle));
            packageParams.add(new BasicNameValuePair("mch_id", Constants.MCH_ID));
            packageParams.add(new BasicNameValuePair("nonce_str", nonceStr));
            packageParams.add(new BasicNameValuePair("notify_url", "http://120.24.165.95:8081/cyshop/api/wxverify.do"));
            MyLogger.e("trade_no = " + mOrderNum);
            packageParams.add(new BasicNameValuePair("out_trade_no", mOrderNum));
//            packageParams.add(new BasicNameValuePair("out_trade_no", genOutTradNo(mOrderNum)));
            packageParams.add(new BasicNameValuePair("spbill_create_ip", "127.0.0.1"));
            packageParams.add(new BasicNameValuePair("total_fee", mAount));
            packageParams.add(new BasicNameValuePair("trade_type", "APP"));
            String sign = genPackageSign(packageParams);
            packageParams.add(new BasicNameValuePair("sign", sign));
//            packageParams.add(new BasicNameValuePair("attach", mAttach));
            String xmlstring = toXml(packageParams);
//            return xmlstring;
            return new String(xmlstring.toString().getBytes(), "ISO8859-1");
        } catch (Exception e) {
            Log.e(TAG, "genProductArgs fail, ex = " + e.getMessage());
            return null;
        }
    }

    public void genPayReq() {
        req.appId = Constants.APP_ID;
//        req.partnerId = Constants.MCH_ID;
        req.partnerId = Constants.MCH_ID;
        req.prepayId = resultunifiedorder.get("prepay_id");
        if (TextUtils.isEmpty(req.partnerId)) {
            Toast.makeText(mAc, "支付失败", Toast.LENGTH_SHORT).show();
            return;
        }
        MyLogger.e("  微信ID  " + req.prepayId);
        req.packageValue = "Sign=WXPay";
        req.nonceStr = genNonceStr();
        req.timeStamp = String.valueOf(genTimeStamp());

        List<NameValuePair> signParams = new LinkedList<NameValuePair>();
        signParams.add(new BasicNameValuePair("appid", req.appId));
        signParams.add(new BasicNameValuePair("noncestr", req.nonceStr));
        signParams.add(new BasicNameValuePair("package", req.packageValue));
        signParams.add(new BasicNameValuePair("partnerid", req.partnerId));
        signParams.add(new BasicNameValuePair("prepayid", req.prepayId));
        signParams.add(new BasicNameValuePair("timestamp", req.timeStamp));

        req.sign = genAppSign(signParams);

        sb.append("sign\n" + req.sign + "\n\n");

        Log.e("orion = ", signParams.toString());
        Log.e("sign = ", sb.toString());

        MyLogger.e("req = " + req);

        msgApi.sendReq(req);
    }

    public void setOnSuccessCallBack(BaseServer.OnSuccessCallBack callBack) {
        this.mOnSuccessCallBack = callBack;
    }
}
