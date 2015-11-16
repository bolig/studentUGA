package com.peoit.android.studentuga.net.server;

import com.loopj.android.http.RequestParams;
import com.peoit.android.peoit_lib.ActivityBase;
import com.peoit.android.peoit_lib.base.BaseUIShow;
import com.peoit.android.studentuga.R;
import com.peoit.android.studentuga.config.NetConstants;
import com.peoit.android.studentuga.entity.GetMoneyInfo;
import com.peoit.android.studentuga.net.BaseCallBack;
import com.peoit.android.studentuga.net.BaseServer;
import com.peoit.android.studentuga.ui.adapter.GetMoneyAdapter;
import com.peoit.android.studentuga.view.list.SwipyRefreshLayout;
import com.peoit.android.studentuga.view.list.SwipyRefreshLayoutDirection;

import java.util.List;

/**
 * author:libo
 * time:2015/11/10
 * E-mail:boli_android@163.com
 * last: ...
 */
public class GetMoneyListServer extends BaseServer implements SwipyRefreshLayout.OnRefreshListener {
    private final String url = NetConstants.HOST + "queryWithdraws.do" + NetConstants.$_ADD_$ + NetConstants.RESPONSE_LIST_MODEL;
    private GetMoneyAdapter adapter;
    private RequestParams params;

    public GetMoneyListServer(ActivityBase activityBase) {
        super(activityBase);
    }

    public GetMoneyAdapter getAdapter() {
        adapter = new GetMoneyAdapter(mActBase.getActivity());
        return adapter;
    }

    public void requestGetMoneyList(final String type) {
        params = getSignRequestParams();
        params.put("type", type);
        params.put("pageNo", "1");
        request(url, GetMoneyInfo.class, params, new BaseCallBack<GetMoneyInfo>() {
            @Override
            public void onStart() {
                mActBase.getUIShowPresenter().showLoading();
            }

            @Override
            public void onResponseSuccessList(List<GetMoneyInfo> result) {
                if (result == null || result.size() == 0) {
                    mActBase.getUIShowPresenter().showError(R.drawable.nomingxi, "暂无提现信息");
                    mActBase.getUIShowPresenter().setOnReloadListener(new BaseUIShow.OnReloadListener() {
                        @Override
                        public void onReload() {
                            requestGetMoneyList(type);
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
//                mActBase.onResponseFailure(statusCode, msg);
                mActBase.getUIShowPresenter().showError(R.drawable.nomingxi, "数据加载失败");
                mActBase.getUIShowPresenter().setOnReloadListener(new BaseUIShow.OnReloadListener() {
                    @Override
                    public void onReload() {
                        requestGetMoneyList(type);
                    }
                });
            }
        });
    }

    private int mNo = 1;

    public void requestGetMoneyList(final SwipyRefreshLayout layout, final SwipyRefreshLayoutDirection direction) {
        params.put("pageNo", direction == SwipyRefreshLayoutDirection.BOTTOM ? mNo + "" : "1");
        request(url, GetMoneyInfo.class, params, new BaseCallBack<GetMoneyInfo>() {

            @Override
            public void onFinish() {
                layout.setRefreshing(false);
            }

            @Override
            public void onResponseSuccessList(List<GetMoneyInfo> result) {
                if (result == null || result.size() == 0) {
                    mActBase.showToast("暂无更多数据了");
                    return;
                }
                mActBase.getUIShowPresenter().showData();
                switch (direction) {
                    case TOP:
                        adapter.addHeadDataList(result);
                        break;
                    case BOTTOM:
                        adapter.addFootDataList(result);
                        mNo++;
                        break;
                }
            }

            @Override
            protected void onResponseFailure(int statusCode, String msg) {
//                mActBase.onResponseFailure(statusCode, msg);
                mActBase.showToast("数据加载失败");
            }
        });
    }

    @Override
    public void onRefresh(SwipyRefreshLayout layout, SwipyRefreshLayoutDirection direction) {
        requestGetMoneyList(layout, direction);
    }
}
