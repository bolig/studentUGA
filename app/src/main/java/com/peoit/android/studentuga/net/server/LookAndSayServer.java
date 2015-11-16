package com.peoit.android.studentuga.net.server;

import android.view.View;

import com.loopj.android.http.RequestParams;
import com.peoit.android.peoit_lib.ActivityBase;
import com.peoit.android.peoit_lib.base.BaseUIShow;
import com.peoit.android.studentuga.R;
import com.peoit.android.studentuga.config.NetConstants;
import com.peoit.android.studentuga.entity.CommonInfo;
import com.peoit.android.studentuga.net.BaseCallBack;
import com.peoit.android.studentuga.net.BaseServer;
import com.peoit.android.studentuga.ui.adapter.CommonAdapter;
import com.peoit.android.studentuga.ui.showUI.SimpleShowUiShow;
import com.peoit.android.studentuga.view.list.SwipyRefreshLayout;
import com.peoit.android.studentuga.view.list.SwipyRefreshLayoutDirection;

import java.util.List;

/**
 * author:libo
 * time:2015/11/11
 * E-mail:boli_android@163.com
 * last: ...
 */
public class LookAndSayServer extends BaseServer implements SwipyRefreshLayout.OnRefreshListener {
    private final String url = NetConstants.HOST + "queryCommodBySsUid.do" + NetConstants.$_ADD_$ + NetConstants.RESPONSE_LIST_MODEL;
    public CommonAdapter adapter;
    private RequestParams params;

    public LookAndSayServer(ActivityBase activityBase) {
        super(activityBase);
    }

    public CommonAdapter getAdapter() {
        adapter = new CommonAdapter(mActBase.getActivity());
        return adapter;
    }

    private int mNo = 1;

    public void requestLookAndSayList(final String uid) {
        params = getRequestParams();
        params.put("uid", uid);
        params.put("pagesize", "20");
        params.put("pageNo", "1");
        mNo = 1;
        request(url, CommonInfo.class, params, new BaseCallBack<CommonInfo>() {
            @Override
            public void onStart() {
                mActBase.getUIShowPresenter().showLoading();
            }

            @Override
            public void onResponseSuccessList(List<CommonInfo> result) {
                if (result == null || result.size() == 0) {
                    mActBase.getUIShowPresenter().showError(R.drawable.nomessage, "暂无动态");
                    mActBase.getUIShowPresenter().setOnReloadListener(new BaseUIShow.OnReloadListener() {
                        @Override
                        public void onReload() {
                            requestLookAndSayList(uid);
                        }
                    });
                    return;
                }
                mActBase.getUIShowPresenter().showData();
                adapter.upDateList(result);
                mNo++;
            }

            @Override
            protected void onResponseFailure(int statusCode, String msg) {
                mActBase.onResponseFailure(statusCode, msg);
                mActBase.getUIShowPresenter().showError(R.drawable.nomessage, "数据加载失败");
                mActBase.getUIShowPresenter().setOnReloadListener(new BaseUIShow.OnReloadListener() {
                    @Override
                    public void onReload() {
                        requestLookAndSayList(uid);
                    }
                });
            }
        });
    }

    public void requestLookAndSayList(final String uid, final SimpleShowUiShow uiShow, final OnSuccessCallBack callBack) {
        params = getRequestParams();
        params.put("uid", uid);
        params.put("pagesize", "20");
        params.put("pageNo", "1");
        mNo = 1;
        request(url, CommonInfo.class, params, new BaseCallBack<CommonInfo>() {
            @Override
            public void onStart() {
                uiShow.setReLoad(true);
                uiShow.setIvErrorImg(R.drawable.nomessage);
                uiShow.setTvReloadListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        requestLookAndSayList(uid, uiShow, callBack);
                    }
                });
                uiShow.showLoading();
            }

            @Override
            public void onResponseSuccessList(List<CommonInfo> result) {
                if (result == null || result.size() == 0) {
                    uiShow.setTvErrorMsg("暂无动态");
                    uiShow.showError();
                    return;
                }
                uiShow.showData();
                adapter.upDateList(result);
                mNo++;
                if (callBack != null){
                    callBack.onSuccess(1);
                }
            }

            @Override
            protected void onResponseFailure(int statusCode, String msg) {
                mActBase.onResponseFailure(statusCode, msg);
                uiShow.setTvErrorMsg("数据加载失败");
                uiShow.showError();
            }
        });
    }

    public void requestLookAndSayList(final SwipyRefreshLayout layout, final SwipyRefreshLayoutDirection direction) {
        params.put("pageNo", direction == SwipyRefreshLayoutDirection.BOTTOM ? mNo + "" : "1");
        request(url, CommonInfo.class, params, new BaseCallBack<CommonInfo>() {

            @Override
            public void onFinish() {
                layout.setRefreshing(false);
            }

            @Override
            public void onResponseSuccessList(List<CommonInfo> result) {
                if (result == null || result.size() == 0) {
                    mActBase.showToast("暂无动态了");
                    return;
                }
                switch (direction) {
                    case TOP:
                        if (!adapter.addHeadDataList(result)) {
                            mActBase.showToast("暂无动态了");
                        }
                        break;
                    case BOTTOM:
                        adapter.addFootDataList(result);
                        mNo++;
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
        requestLookAndSayList(layout, direction);
    }
}
