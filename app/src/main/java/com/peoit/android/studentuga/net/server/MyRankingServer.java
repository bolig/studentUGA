package com.peoit.android.studentuga.net.server;

import com.loopj.android.http.RequestParams;
import com.peoit.android.peoit_lib.ActivityBase;
import com.peoit.android.studentuga.config.NetConstants;
import com.peoit.android.studentuga.entity.RankingInfo;
import com.peoit.android.studentuga.net.BaseCallBack;
import com.peoit.android.studentuga.net.BaseServer;

/**
 * author:libo
 * time:2015/10/29
 * E-mail:boli_android@163.com
 * last: ...
 */
public class MyRankingServer extends BaseServer {

    public final String url = NetConstants.HOST + "queryUserTop.do";

    public MyRankingServer(ActivityBase activityBase) {
        super(activityBase);
    }

    public void requestRanking(String uid, final OnRankingCallBack callBack) {
        RequestParams params = getRequestParams();
        params.put("uid", uid);
        request(url, RankingInfo.class, params, new BaseCallBack<RankingInfo>() {
            @Override
            public void onResponseSuccess(RankingInfo result) {
                if (callBack != null) {
                    callBack.onCallBack(result);
                }
            }

            @Override
            protected void onResponseFailure(int statusCode, String msg) {
                mActBase.onResponseFailure(statusCode, msg);
                if (callBack != null) {
                    callBack.onCallBack(null);
                }
            }
        });
    }

    public interface OnRankingCallBack {
        void onCallBack(RankingInfo info);
    }
}
