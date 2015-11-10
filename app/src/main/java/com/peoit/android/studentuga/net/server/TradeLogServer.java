package com.peoit.android.studentuga.net.server;

import com.loopj.android.http.RequestParams;
import com.peoit.android.peoit_lib.ActivityBase;
import com.peoit.android.peoit_lib.base.BaseUIShow;
import com.peoit.android.studentuga.R;
import com.peoit.android.studentuga.config.NetConstants;
import com.peoit.android.studentuga.entity.TradeLogInfo;
import com.peoit.android.studentuga.net.BaseCallBack;
import com.peoit.android.studentuga.net.BaseServer;
import com.peoit.android.studentuga.ui.adapter.TradeLogAdapter;
import com.peoit.android.studentuga.view.list.SwipyRefreshLayout;
import com.peoit.android.studentuga.view.list.SwipyRefreshLayoutDirection;

import java.util.List;

/**
 * author:libo
 * time:2015/10/29
 * E-mail:boli_android@163.com
 * last: ...
 */
public class TradeLogServer extends BaseServer implements SwipyRefreshLayout.OnRefreshListener {
    private TradeLogAdapter adapter;

    public TradeLogServer(ActivityBase activityBase) {
        super(activityBase);
    }

    public TradeLogAdapter getAdapter() {
        adapter = new TradeLogAdapter(mActBase.getActivity());
        return adapter;
    }

    public void requestTradeLogList() {
        RequestParams params = getSignRequestParams();
        params.put("pageNo", "1");
        mActBase.getUIShowPresenter().showError(R.drawable.nomingxi, "暂无流水信息");
        mActBase.getUIShowPresenter().setReLoad("重新加载", new BaseUIShow.OnReloadListener() {
            @Override
            public void onReload() {
                requestTradeLogList();
            }
        });
        request(NetConstants.NET_QUERY_TRADE_LOG, TradeLogInfo.class, params, new BaseCallBack<TradeLogInfo>() {
            @Override
            public void onStart() {
                mActBase.getUIShowPresenter().showLoading();
            }

            @Override
            public void onResponseSuccessList(List<TradeLogInfo> result) {
                if (result == null || result.size() == 0) {
                    mActBase.getUIShowPresenter().showError();
                    return;
                }
                mActBase.getUIShowPresenter().showData();
                adapter.upDateList(result);
                mNo ++;
            }

            @Override
            protected void onResponseFailure(int statusCode, String msg) {
                mActBase.onResponseFailure(statusCode, msg);
                mActBase.getUIShowPresenter().showError();
            }
        });
    }

    private int mNo = 1;

    /**
     * 请求分页
     *
     * @param layout
     * @param direction
     */
    public void requestTradeLogList(final SwipyRefreshLayout layout, final SwipyRefreshLayoutDirection direction) {
        RequestParams params = getSignRequestParams();
        params.put("pageNo", direction == SwipyRefreshLayoutDirection.BOTTOM ? mNo + "" : "1");
        request(NetConstants.NET_QUERY_TRADE_LOG, TradeLogInfo.class, params, new BaseCallBack<TradeLogInfo>() {
            @Override
            public void onFinish() {
                layout.setRefreshing(false);
            }

            @Override
            public void onResponseSuccessList(List<TradeLogInfo> result) {
                if (result == null || result.size() == 0) {
                    mActBase.showToast("暂无交易信息");
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
        requestTradeLogList(layout, direction);
    }
}
