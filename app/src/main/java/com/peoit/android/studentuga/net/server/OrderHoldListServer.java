package com.peoit.android.studentuga.net.server;

import android.view.View;

import com.loopj.android.http.RequestParams;
import com.peoit.android.peoit_lib.ActivityBase;
import com.peoit.android.studentuga.R;
import com.peoit.android.studentuga.config.NetConstants;
import com.peoit.android.studentuga.entity.OrderInfo;
import com.peoit.android.studentuga.enuml.OrderStatus;
import com.peoit.android.studentuga.net.BaseCallBack;
import com.peoit.android.studentuga.net.BaseServer;
import com.peoit.android.studentuga.ui.adapter.OrderAdapter;
import com.peoit.android.studentuga.ui.showUI.SimpleShowUiShow;
import com.peoit.android.studentuga.view.list.SwipyRefreshLayout;
import com.peoit.android.studentuga.view.list.SwipyRefreshLayoutDirection;

import java.util.List;

/**
 * author:libo
 * time:2015/10/27
 * E-mail:boli_android@163.com
 * last: ...
 */
public class OrderHoldListServer extends BaseServer implements SwipyRefreshLayout.OnRefreshListener {

    private OrderAdapter adapter;
    private int mSkip = 1;
    private OrderStatus mOrderStatus;
    private SimpleShowUiShow mUiShow;

    public OrderHoldListServer(ActivityBase activityBase) {
        super(activityBase);
    }

    public OrderAdapter getAdapter() {
        adapter = new OrderAdapter(mActBase.getActivity(), true);
        adapter.setSuccessCallBack(new OnSuccessCallBack() {
            @Override
            public void onSuccess(int mark) {
                loadHoldOrder(mUiShow, mOrderStatus);
            }
        });
        return adapter;
    }

    /**
     * 获取用户购买商品的订单列表
     *
     * @param uiShow
     * @param status
     */
    public void loadHoldOrder(final SimpleShowUiShow uiShow, final OrderStatus status) {
        mUiShow = uiShow;
        mOrderStatus = status;
        RequestParams params = getSignRequestParams();
        if (params == null) {
            return;
        }
        params.put("status", status.mOrderStatus);
        params.put("pageNo", "1");
        request(NetConstants.NET_QUERY_HOLD_ORDER, OrderInfo.class, params, new BaseCallBack<OrderInfo>() {
            @Override
            public void onStart() {
                uiShow.showLoading();
            }

            @Override
            public void onResponseSuccessList(List<OrderInfo> result) {
                if (result == null || result.size() == 0) {
                    uiShow.setTvErrorMsg("您当前还没有订单信息");
                    uiShow.setIvErrorImg(R.drawable.test_goods);
                    uiShow.setTvReloadListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            loadHoldOrder(uiShow, status);
                        }
                    });
                    uiShow.setReLoad(true);
                    uiShow.showError();
                    return;
                }
                uiShow.showData();
                adapter.upDateList(result);
                mSkip++;
            }

            @Override
            protected void onResponseFailure(int statusCode, String msg) {
                mActBase.onResponseFailure(statusCode, msg);
                uiShow.setTvErrorMsg("订单信息加载失败");
                uiShow.setIvErrorImg(R.drawable.test_goods);
                uiShow.setTvReloadListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        loadHoldOrder(uiShow, status);
                    }
                });
                uiShow.setReLoad(true);
                uiShow.showError();
            }
        });
    }


    /**
     * 获取用户购买商品的订单列表
     *
     * @param
     * @param status
     */
    public void loadHoldOrder(final SwipyRefreshLayout layout, final boolean isMore, final OrderStatus status) {
        mOrderStatus = status;
        RequestParams params = getSignRequestParams();
        if (params == null) {
            return;
        }
        params.put("status", status.mOrderStatus);
        params.put("pageNo", isMore ? mSkip + "" : "1");
        request(NetConstants.NET_QUERY_HOLD_ORDER, OrderInfo.class, params, new BaseCallBack<OrderInfo>() {
            @Override
            public void onFinish() {
                layout.setRefreshing(false);
            }

            @Override
            public void onResponseSuccessList(List<OrderInfo> result) {
                if (result.size() == 0) {
                    mActBase.showToast("当前没有数据了");
                    return;
                }
                if (isMore) {
                    mSkip ++;
                    boolean isChange = adapter.addFootDataList(result);
                    if (!isChange) {
                        mActBase.showToast("当前没有数据了");
                    }
                } else {
                    boolean isChange = adapter.addHeadDataList(result);
                    if (!isChange) {
                        mActBase.showToast("当前没有数据了");
                    }
                }
            }

            @Override
            protected void onResponseFailure(int statusCode, String msg) {
                mActBase.onResponseFailure(statusCode, msg);
                mActBase.showToast("数据获取失败");
            }
        });
    }

    @Override
    public void onRefresh(SwipyRefreshLayout layout, SwipyRefreshLayoutDirection direction) {
        switch (direction) {
            case TOP:
                loadHoldOrder(layout, false, mOrderStatus);
                break;
            case BOTTOM:
                loadHoldOrder(layout, true, mOrderStatus);
                break;
        }
    }
}
