package com.peoit.android.studentuga.net.server;

import android.text.TextUtils;

import com.loopj.android.http.RequestParams;
import com.peoit.android.peoit_lib.ActivityBase;
import com.peoit.android.studentuga.config.NetConstants;
import com.peoit.android.studentuga.net.BaseCallBack;
import com.peoit.android.studentuga.net.BaseServer;
import com.peoit.android.studentuga.uitl.encode.File2Base64Util;

/**
 * 上传文件
 * <p/>
 * author:libo
 * time:2015/9/25
 * E-mail:boli_android@163.com
 * last: ...
 */
public class FlieServer extends BaseServer {

    public FlieServer(ActivityBase activityBase) {
        super(activityBase);
    }

    public void upload(String filePath, final OnFileUploadSuccessListener listener) {
        mActBase.showLoadingDialog("正在准备上传...");
        RequestParams params = getSignRequestParams();
        if (params == null) {
            return;
        }
        String fileEnCode = File2Base64Util.encodeBase64File(filePath);
        params.put("img", fileEnCode);
        request(NetConstants.NET_UPLOAD_FILE, null, params, new BaseCallBack() {

            @Override
            public void onStart() {
                mActBase.showLoadingDialog("上传进度");
            }

            @Override
            public void onProgress(long bytesWritten, long totalSize) {
                mActBase.showLoadingDialog("上传进度: " + getProgress(bytesWritten, totalSize));
            }

            @Override
            public void onFinish() {
                mActBase.hideLoadingDialog();
            }

            @Override
            public void onResponseSuccessModelString(String result) {
                if (TextUtils.isEmpty(result)) {
                    mActBase.showToast("上传失败");
                } else {
                    if (listener != null) {
                        listener.onSuccess(result);
                    }
                }
            }

            @Override
            protected void onResponseFailure(int statusCode, String msg) {
                mActBase.onResponseFailure(statusCode, msg);
                if (listener != null) {
                    listener.onFailure();
                }
            }
        });
    }

    private String getProgress(long bytesWritten, long totalSize) {
        int pregress = (int) ((bytesWritten * 100f) / totalSize);
        return pregress + "/100";
    }

    public interface OnFileUploadSuccessListener {
        void onSuccess(String imgUrl);

        void onFailure();
    }
}
