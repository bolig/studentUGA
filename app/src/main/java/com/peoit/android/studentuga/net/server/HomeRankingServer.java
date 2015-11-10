package com.peoit.android.studentuga.net.server;

import com.loopj.android.http.RequestParams;
import com.peoit.android.peoit_lib.ActivityBase;
import com.peoit.android.studentuga.config.NetConstants;
import com.peoit.android.studentuga.entity.UserInfo;
import com.peoit.android.studentuga.net.BaseCallBack;
import com.peoit.android.studentuga.net.BaseServer;

import java.util.List;

/**
 * author:libo
 * time:2015/10/29
 * E-mail:boli_android@163.com
 * last: ...
 */
public class HomeRankingServer extends BaseServer {

    public final String Url = NetConstants.HOST + "queryTotlalsales.do" + NetConstants.$_ADD_$ + NetConstants.RESPONSE_LIST_MODEL;

    public HomeRankingServer(ActivityBase activityBase) {
        super(activityBase);
    }

    public void requestHomeRanking(final OnRankingCallBack callBack) {
        RequestParams params = getRequestParams();
        params.put("pageNo", "1");
        params.put("pagesize", "6");
        request(Url, UserInfo.class, params, new BaseCallBack<UserInfo>() {
            @Override
            public void onResponseSuccessList(List<UserInfo> result) {
                if (result == null || result.size() == 0)
                    return;
                if (callBack != null) {
                    callBack.onCallBack(result);
                }
            }

            @Override
            protected void onResponseFailure(int statusCode, String msg) {
                mActBase.onResponseFailure(statusCode, msg);
            }
        });
    }

    public interface OnRankingCallBack {
        void onCallBack(List<UserInfo> infos);
    }
}
