package com.peoit.android.studentuga.net.server;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.text.TextUtils;

import com.loopj.android.http.RequestParams;
import com.peoit.android.peoit_lib.ActivityBase;
import com.peoit.android.peoit_lib.util.MyLogger;
import com.peoit.android.studentuga.config.NetConstants;
import com.peoit.android.studentuga.net.BaseCallBack;
import com.peoit.android.studentuga.net.BaseServer;
import com.peoit.android.studentuga.uitl.BitmapUtils;
import com.peoit.android.studentuga.uitl.encode.File2Base64Util;

import java.io.File;

/**
 * author:libo
 * time:2015/11/1
 * E-mail:boli_android@163.com
 * last: ...
 */
public class AddLiuCommonServer extends BaseServer {

    private final String url = NetConstants.HOST + "addCommodRen.do";

    public AddLiuCommonServer(ActivityBase activityBase) {
        super(activityBase);
    }

    public void requestAddLiuCommon(String uid,
                                    String text,
                                    String img1,
                                    String img2,
                                    String img3,
                                    String img4,
                                    String img5,
                                    String img6) {
        RequestParams params = getSignRequestParams();
        params.put("userid", uid);
        params.put("text", text);
        mActBase.showLoadingDialog("正在保存留言...");
        params.put("img1", getImg(img1));
        params.put("img2", getImg(img2));
        params.put("img3", getImg(img3));
        params.put("img4", getImg(img4));
        params.put("img5", getImg(img5));
        params.put("img6", getImg(img6));
        request(url, null, params, new BaseCallBack() {
            @Override
            public void onStart() {
                mActBase.showLoadingDialog("正在保存留言...");
            }

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
                mActBase.showToast("保存失败");
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
}
