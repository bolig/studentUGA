package com.peoit.android.studentuga.net.server;

import com.loopj.android.http.RequestParams;
import com.peoit.android.peoit_lib.ActivityBase;
import com.peoit.android.peoit_lib.base.BaseUIShow;
import com.peoit.android.studentuga.R;
import com.peoit.android.studentuga.config.NetConstants;
import com.peoit.android.studentuga.entity.LiuCommonInfo;
import com.peoit.android.studentuga.net.BaseCallBack;
import com.peoit.android.studentuga.net.BaseServer;
import com.peoit.android.studentuga.ui.adapter.LiuCommonAdapter;
import com.peoit.android.studentuga.view.list.SwipyRefreshLayout;
import com.peoit.android.studentuga.view.list.SwipyRefreshLayoutDirection;

import java.util.List;

/**
 * author:libo
 * time:2015/11/2
 * E-mail:boli_android@163.com
 * last: ...
 */
public class MyLiuCommonServer extends BaseServer implements SwipyRefreshLayout.OnRefreshListener {
    private final String url = NetConstants.HOST + "queryMyCommodByRen.do" + NetConstants.$_ADD_$ + NetConstants.RESPONSE_LIST_MODEL;
    private LiuCommonAdapter adapter;

    public MyLiuCommonServer(ActivityBase activityBase) {
        super(activityBase);
    }

    public LiuCommonAdapter getAdapter() {
        adapter = new LiuCommonAdapter(mActBase.getActivity());
        return adapter;
    }

    public void requestMyLiuCommon() {
        RequestParams params = getSignRequestParams();
        params.put("pagesize", "20");
        params.put("pageNo", "1");
        mActBase.getUIShowPresenter().showLoading();
        request(url, LiuCommonInfo.class, params, new BaseCallBack<LiuCommonInfo>() {
            @Override
            public void onResponseSuccessList(List<LiuCommonInfo> result) {
                if (result == null || result.size() == 0) {
                    mActBase.getUIShowPresenter().showError(R.drawable.nomessage, "暂无留言信息");
                    mActBase.getUIShowPresenter().setOnReloadListener(new BaseUIShow.OnReloadListener() {
                        @Override
                        public void onReload() {
                            requestMyLiuCommon();
                        }
                    });
                    return;
                }
                mActBase.getUIShowPresenter().showData();
                adapter.upDateList(result);
                mNo ++;
            }

            @Override
            protected void onResponseFailure(int statusCode, String msg) {
                mActBase.onResponseFailure(statusCode, msg);
                mActBase.getUIShowPresenter().showError(R.drawable.nomessage, "数据加载失败");
                mActBase.getUIShowPresenter().setOnReloadListener(new BaseUIShow.OnReloadListener() {
                    @Override
                    public void onReload() {
                        requestMyLiuCommon();
                    }
                });
            }
        });
    }
    private int mNo = 1;

    public void requestMyLiuCommon(final SwipyRefreshLayout layout, final SwipyRefreshLayoutDirection direction) {
        RequestParams params = getSignRequestParams();
        params.put("pagesize", "20");
        params.put("pageNo", direction == SwipyRefreshLayoutDirection.BOTTOM ? mNo + "" : "1");
        request(url, LiuCommonInfo.class, params, new BaseCallBack<LiuCommonInfo>() {

            @Override
            public void onFinish() {
                layout.setRefreshing(false);
            }

            @Override
            public void onResponseSuccessList(List<LiuCommonInfo> result) {
                if (result == null || result.size() == 0) {
                    mActBase.showToast("暂无留言信息");
                    return;
                }
                switch (direction) {
                    case TOP:
                        adapter.addHeadDataList(result);
                        break;
                    case BOTTOM:
                        adapter.addFootDataList(result);
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

    }
}
