package com.peoit.android.studentuga.weixin;

import android.app.Activity;

import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

/**
 * author:libo
 * time:2015/11/5
 * E-mail:boli_android@163.com
 * last: ...
 */
public class WeiXinHelper1 {
    private final Activity mAc;
    private final PayReq req;
    private final IWXAPI msgApi;

    public WeiXinHelper1(Activity mAc) {
        this.mAc = mAc;
        req = new PayReq();

        msgApi = WXAPIFactory.createWXAPI(mAc, null);
        msgApi.registerApp(Constants.APP_ID);
    }

//    public void sendPay(String mOrderNum){
//        req.appId = Constants.APP_ID;
//        req.partnerId = Constants.PARENTER_ID;
//        req.prepayId = mOrderNum;
//        req.packageValue = "Sign=WXPay";
//        req.nonceStr = genNonceStr();
//        req.timeStamp = String.valueOf(genTimeStamp());
//
//        msgApi.registerApp(Constants.APP_ID);
//        msgApi.sendReq(req);
//    }
//
//    private long genTimeStamp() {
//        return System.currentTimeMillis() / 1000;
//    }
//
//
//    private String genNonceStr() {
//        Random random = new Random();
//        return MD5.getMessageDigest(String.valueOf(random.nextInt(10000)).getBytes());
//    }
}
