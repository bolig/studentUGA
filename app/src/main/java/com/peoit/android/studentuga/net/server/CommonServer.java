package com.peoit.android.studentuga.net.server;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.text.TextUtils;

import com.loopj.android.http.RequestParams;
import com.peoit.android.peoit_lib.ActivityBase;
import com.peoit.android.peoit_lib.util.MyLogger;
import com.peoit.android.studentuga.config.NetConstants;
import com.peoit.android.studentuga.entity.CommonInfo;
import com.peoit.android.studentuga.net.BaseCallBack;
import com.peoit.android.studentuga.net.BaseServer;
import com.peoit.android.studentuga.uitl.BitmapUtils;
import com.peoit.android.studentuga.uitl.encode.File2Base64Util;

import java.io.File;
import java.util.List;

/**
 * author:libo
 * time:2015/11/1
 * E-mail:boli_android@163.com
 * last: ...
 */
public class CommonServer extends BaseServer {

    public final String url_addCommon = NetConstants.HOST + "addCommod.do"; // 对订单添加评论

    public final String url_queryCommon = NetConstants.HOST + "queryCommodByid.do" + NetConstants.$_ADD_$ + NetConstants.RESPONSE_LIST_MODEL;

    public CommonServer(ActivityBase activityBase) {
        super(activityBase);
    }

    /**
     * 添加评论
     *
     * @param orderid
     * @param sellid
     * @param text
     * @param star
     */
    public void requestAddCommon(String orderid,
                                 String sellid,
                                 String text,
                                 String star,
                                 String img1,
                                 String img2,
                                 String img3,
                                 String img4,
                                 String img5,
                                 String img6) {
        RequestParams params = getSignRequestParams();
        params.put("orderid", orderid);
        params.put("sellid", sellid);
        params.put("text", text);
        params.put("star", star);

        mActBase.showLoadingDialog("正在保存评论...");

        params.put("img1", getImg(img1));
        params.put("img2", getImg(img2));
        params.put("img3", getImg(img3));
        params.put("img4", getImg(img4));
        params.put("img5", getImg(img5));
        params.put("img6", getImg(img6));
        request(url_addCommon, null, params, new BaseCallBack() {
            @Override
            public void onFinish() {
                mActBase.hideLoadingDialog();
            }

            @Override
            public void onProgress(long bytesWritten, long totalSize) {
                mActBase.showLoadingDialog("上传进度: " + (bytesWritten * 100 / totalSize) + "/100");
            }

            @Override
            public void onResponseSuccess(Object result) {
                mActBase.showToast("保存成功");
                mActBase.getActivity().setResult(Activity.RESULT_OK, new Intent());
                mActBase.getActivity().finish();
            }

            @Override
            protected void onResponseFailure(int statusCode, String msg) {
                mActBase.onResponseFailure(statusCode, msg);
            }
        });
    }


    private String getImg(String img) {
        if (TextUtils.isEmpty(img)) {
            return "";
        }
        Bitmap bitmap = BitmapUtils.commpress(img, 500);
        String path = BitmapUtils.saveBitmap(bitmap, true);
        String imgBase64 = File2Base64Util.encodeBase64File(path);
        MyLogger.e("imgBase64 = " + imgBase64);
        File file = new File(path);
        if (file != null && file.exists())
            file.delete();
        return imgBase64;
    }

    /**
     * 查询商品评论
     *
     * @param id
     * @param pageNo
     * @param pageSize
     * @param callBack
     */
    public void requestGoodsCommon(String id, String pageNo, String pageSize, final OnCommonCallBack callBack) {
        RequestParams params = getRequestParams();
        params.put("id", id);
        params.put("pageNo", pageNo);
        params.put("pagesize", pageSize);
        request(url_queryCommon, CommonInfo.class, params, new BaseCallBack<CommonInfo>() {
            @Override
            public void onResponseSuccessList(List<CommonInfo> result) {
                if (callBack != null) {
                    if (result == null || result.size() == 0) {
                        callBack.onCommon(0, result);
                    } else {
                        callBack.onCommon(1, result);
                    }
                }
            }

            @Override
            protected void onResponseFailure(int statusCode, String msg) {
                mActBase.onResponseFailure(statusCode, msg);
                if (callBack != null) {
                    callBack.onCommon(-1, null);
                }
            }
        });
    }

    public void requestGoodsCommon(String id, final OnCommonCallBack callBack) {
        requestGoodsCommon(id, "1", "10", callBack);
    }

    public interface OnCommonCallBack {
        void onCommon(int mark, List<CommonInfo> infos);
    }
}
