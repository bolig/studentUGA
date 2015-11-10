package com.peoit.android.studentuga.net.server;

import com.peoit.android.peoit_lib.ActivityBase;
import com.peoit.android.studentuga.config.NetConstants;
import com.peoit.android.studentuga.entity.BannerInfo;
import com.peoit.android.studentuga.net.BaseCallBack;
import com.peoit.android.studentuga.net.BaseServer;

import java.util.List;

/**
 * author:libo
 * time:2015/10/29
 * E-mail:boli_android@163.com
 * last: ...
 */
public class HomeBannerServer extends BaseServer {

    private final String Url = NetConstants.HOST + "queryAds.do" + NetConstants.$_ADD_$ + NetConstants.RESPONSE_LIST_MODEL;

    public HomeBannerServer(ActivityBase activityBase) {
        super(activityBase);
    }

    public void requestHomebanner(final OnBannerCallBack callBack) {
        request(Url, BannerInfo.class, getRequestParams(), new BaseCallBack<BannerInfo>() {
            @Override
            public void onResponseSuccessList(List<BannerInfo> result) {
                if (result == null || result.size() == 0)
                    return;
                if (callBack != null)
                    callBack.onCallBack(result);
            }

            @Override
            protected void onResponseFailure(int statusCode, String msg) {
                mActBase.onResponseFailure(statusCode, msg);
            }
        });
    }

    public interface OnBannerCallBack {
        void onCallBack(List<BannerInfo> infos);
    }
}
