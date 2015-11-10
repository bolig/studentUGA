package com.peoit.android.studentuga.net.server;

import android.support.v4.app.Fragment;
import android.view.View;

import com.loopj.android.http.RequestParams;
import com.peoit.android.peoit_lib.ActivityBase;
import com.peoit.android.studentuga.R;
import com.peoit.android.studentuga.config.NetConstants;
import com.peoit.android.studentuga.entity.OrderCommonInfo;
import com.peoit.android.studentuga.net.BaseCallBack;
import com.peoit.android.studentuga.net.BaseServer;
import com.peoit.android.studentuga.ui.adapter.OrderCommonAdapter;
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
public class OrderCommonServer extends BaseServer implements SwipyRefreshLayout.OnRefreshListener {

    private final String url = NetConstants.HOST + "queryMyCommodityComment.do" + NetConstants.$_ADD_$ + NetConstants.RESPONSE_LIST_MODEL;
    private RequestParams params;
    private OrderCommonAdapter adapter;

    public OrderCommonServer(ActivityBase activityBase) {
        super(activityBase);
    }

    public OrderCommonAdapter getAdapter(Fragment fragment){
        if (adapter == null){
            adapter = new OrderCommonAdapter(fragment);
        }
        return adapter;
    }

    public void requestOrderCommon(final SimpleShowUiShow uiShow, final boolean isCommon) {
        params = getSignRequestParams();
        params.put("pageNo", "1");
        params.put("state", isCommon ? "Y" : "N");
        request(url, OrderCommonInfo.class, params, new BaseCallBack<OrderCommonInfo>() {
            @Override
            public void onStart() {
                uiShow.setIvErrorImg(R.drawable.noproduct);
                uiShow.setTvErrorMsg(isCommon ? "暂无已评价的订单" : "暂无未评价的订单");
                uiShow.setTvReloadListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        requestOrderCommon(uiShow, isCommon);
                    }
                });
                uiShow.showLoading();
            }

            @Override
            public void onResponseSuccessList(List<OrderCommonInfo> result) {
                if (result == null || result.size() == 0){
                    uiShow.showError();
                    return;
                }
                uiShow.showData();
                adapter.upDateList(result);
                mNo ++;
            }

            @Override
            protected void onResponseFailure(int statusCode, String msg) {
                mActBase.onResponseFailure(statusCode, msg);
                uiShow.showError();
            }
        });
    }

    private int mNo = 1;

    public void requestOrderCommon(final SwipyRefreshLayout layout, final SwipyRefreshLayoutDirection direction) {
        params.put("pageNo", direction == SwipyRefreshLayoutDirection.BOTTOM ? mNo + "" : "1");
        request(url, OrderCommonInfo.class, params, new BaseCallBack<OrderCommonInfo>() {
            @Override
            public void onFinish() {
                layout.setRefreshing(false);
            }

            @Override
            public void onResponseSuccessList(List<OrderCommonInfo> result) {
                if (result == null || result.size() == 0){
                    mActBase.showToast("暂无评论了");
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
        requestOrderCommon(layout, direction);
    }
}
