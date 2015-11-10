package com.peoit.android.studentuga.net.server;

import android.view.View;

import com.loopj.android.http.RequestParams;
import com.peoit.android.peoit_lib.ActivityBase;
import com.peoit.android.studentuga.R;
import com.peoit.android.studentuga.config.NetConstants;
import com.peoit.android.studentuga.entity.LiuCommonInfo;
import com.peoit.android.studentuga.net.BaseCallBack;
import com.peoit.android.studentuga.net.BaseServer;
import com.peoit.android.studentuga.ui.adapter.LiuCommonAdapter;
import com.peoit.android.studentuga.ui.showUI.SimpleShowUiShow;
import com.peoit.android.studentuga.view.list.SwipyRefreshLayout;
import com.peoit.android.studentuga.view.list.SwipyRefreshLayoutDirection;

import java.util.List;

/**
 * author:libo
 * time:2015/11/1
 * E-mail:boli_android@163.com
 * last: ...
 */
public class LiuCommonServer extends BaseServer implements SwipyRefreshLayout.OnRefreshListener {
    public LiuCommonAdapter mLiuCommonAdapter;
    private RequestParams params;
    private final String url = NetConstants.HOST + "queryCommodByRenUid.do" + NetConstants.$_ADD_$ + NetConstants.RESPONSE_LIST_MODEL;

    public LiuCommonServer(ActivityBase activityBase) {
        super(activityBase);
    }

    public LiuCommonAdapter getAdapter() {
        mLiuCommonAdapter = new LiuCommonAdapter(mActBase.getActivity());
        return mLiuCommonAdapter;
    }

    public void requestLiuCommon(final String uid, final SimpleShowUiShow uiShow, final OnSuccessCallBack callBack) {
        params = getRequestParams();
        params.put("uid", uid);
        params.put("pagesize", "20");
        params.put("pageNo", "1");
        request(url, LiuCommonInfo.class, params, new BaseCallBack<LiuCommonInfo>() {
            @Override
            public void onStart() {
                uiShow.setIvErrorImg(R.drawable.nomessage);
                uiShow.setTvErrorMsg("暂无留言信息");
                uiShow.setTvReloadListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        requestLiuCommon(uid, uiShow, callBack);
                    }
                });
                uiShow.setReLoad(true);
                uiShow.showLoading();
            }

            @Override
            public void onResponseSuccessList(List<LiuCommonInfo> result) {
                if (result == null || result.size() == 0) {
                    uiShow.showError();
                    return;
                }
                uiShow.showData();
                mLiuCommonAdapter.upDateList(result);
                mNo ++;
                if (callBack != null){
                    callBack.onSuccess(1);
                }
            }

            @Override
            protected void onResponseFailure(int statusCode, String msg) {
                mActBase.onResponseFailure(statusCode, msg);
                uiShow.showError();
            }
        });
    }

    private int mNo = 1;

    public void requestLiuCommon(final SwipyRefreshLayout layout, final SwipyRefreshLayoutDirection direction) {
        params.put("pageNo", mNo + "");
        request(url, LiuCommonInfo.class, params, new BaseCallBack<LiuCommonInfo>() {
            @Override
            public void onFinish() {
                layout.setRefreshing(false);
            }

            @Override
            public void onResponseSuccessList(List<LiuCommonInfo> result) {
                if (result == null || result.size() == 0) {
                    mActBase.showToast("赞无留言了");
                    return;
                }
                switch (direction) {
                    case TOP:
                        mLiuCommonAdapter.addHeadDataList(result);
                        break;
                    case BOTTOM:
                        mLiuCommonAdapter.addFootDataList(result);
                        mNo ++;
                        break;
                }
            }

            @Override
            protected void onResponseFailure(int statusCode, String msg) {
                mActBase.onResponseFailure(statusCode, msg);
                mActBase.showToast("数据加载失败");
            }
        });
    }

    @Override
    public void onRefresh(SwipyRefreshLayout layout, SwipyRefreshLayoutDirection direction) {
        requestLiuCommon(layout, direction);
    }
}
