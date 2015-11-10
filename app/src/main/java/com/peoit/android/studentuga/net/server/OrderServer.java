package com.peoit.android.studentuga.net.server;

import android.text.TextUtils;

import com.loopj.android.http.RequestParams;
import com.peoit.android.peoit_lib.ActivityBase;
import com.peoit.android.studentuga.config.NetConstants;
import com.peoit.android.studentuga.entity.OrderInfo;
import com.peoit.android.studentuga.net.BaseCallBack;
import com.peoit.android.studentuga.net.BaseServer;
import com.peoit.android.studentuga.ui.PayActivity;

/**
 * author:libo
 * time:2015/10/23
 * E-mail:boli_android@163.com
 * last: ...
 */
public class OrderServer extends BaseServer {
    public OrderServer(ActivityBase activityBase) {
        super(activityBase);
    }

    /**
     * 生成购物车的商品的订单
     *
     * @param shopids
     * @param addressid
     * @param callBack
     */
    public void requestAddShopCarOrder(String shopids, String addressid, BaseCallBack<OrderInfo> callBack) {
        RequestParams params = getSignRequestParams();
        if (params == null) {
            return;
        }
        params.put("shopids", shopids);
        params.put("addressid", addressid);
        request(NetConstants.NET_ADD_SHOPCAR_ORDER, OrderInfo.class, params, callBack);
    }

    /**
     * 直接购买商品
     *
     * @param count
     * @param productId
     * @param addressid
     * @param gg
     * @param remarks   (可选)
     */
    public void requestAtonceOrder(String count, String productId, String addressid, String gg, String remarks) {
        RequestParams params = getSignRequestParams();
        if (params == null) {
            return;
        }
        params.put("count", count);
        params.put("productId", productId);
        params.put("addressid", addressid);
        params.put("gg", gg);
        if (!TextUtils.isEmpty(remarks)) {
            params.put("remarks", remarks);
        }
        mActBase.showLoadingDialog("正在生成订单...");
        request(NetConstants.NET_AT_ONCE_ORDER, OrderInfo.class, params, new BaseCallBack<OrderInfo>() {
            @Override
            public void onFinish() {
                mActBase.hideLoadingDialog();
            }

            @Override
            public void onResponseSuccess(OrderInfo result) {
                if (result.isNull()) {
                    mActBase.showToast("生成订单失败");
                    return;
                }
                PayActivity.startThisActivity(mActBase.getActivity(), result.getId() + "", result.getTotalPrice() + "", result.getTitle());
                mActBase.getActivity().finish();
            }

            @Override
            protected void onResponseFailure(int statusCode, String msg) {
                onResponseFailure(statusCode, msg);
                mActBase.showToast("网络连接错误");
            }
        });
    }

    /**
     * 取消订单
     *
     * @param orderid
     */
    public void requestCancelOrder(String orderid, final OnSuccessCallBack callback) {
        RequestParams params = getSignRequestParams();
        if (params == null) {
            return;
        }
        params.put("orderid", orderid);
        request(NetConstants.NET_ORDER_CANCEL, null, params, new BaseCallBack() {
            @Override
            public void onStart() {
                mActBase.showLoadingDialog("正在取消订单...");
            }

            @Override
            public void onFinish() {
                mActBase.hideLoadingDialog();
            }

            @Override
            public void onResponseSuccess(Object result) {
                mActBase.showToast("取消成功");
                if (callback != null) {
                    callback.onSuccess(0);
                }
            }

            @Override
            protected void onResponseFailure(int statusCode, String msg) {
                mActBase.onResponseFailure(statusCode, msg);
            }
        });
    }

    /**
     * 完成订单
     *
     * @param orderid
     */
    public void requestFinishOrder(String orderid, final OnSuccessCallBack callback) {
        RequestParams params = getSignRequestParams();
        if (params == null) {
            return;
        }
        params.put("orderid", orderid);
        request(NetConstants.NET_ORDER_FINISH, null, params, new BaseCallBack() {
            @Override
            public void onStart() {
                mActBase.showLoadingDialog("正在完成订单...");
            }

            @Override
            public void onFinish() {
                mActBase.hideLoadingDialog();
            }

            @Override
            public void onResponseSuccess(Object result) {
                mActBase.showToast("交易成功");
                if (callback != null) {
                    callback.onSuccess(0);
                }
            }

            @Override
            protected void onResponseFailure(int statusCode, String msg) {
                mActBase.onResponseFailure(statusCode, msg);
            }
        });

    }
}
