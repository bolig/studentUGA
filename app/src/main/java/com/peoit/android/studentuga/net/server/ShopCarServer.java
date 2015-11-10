package com.peoit.android.studentuga.net.server;

import android.view.View;

import com.loopj.android.http.RequestParams;
import com.peoit.android.peoit_lib.ActivityBase;
import com.peoit.android.studentuga.R;
import com.peoit.android.studentuga.config.CommonUtil;
import com.peoit.android.studentuga.config.NetConstants;
import com.peoit.android.studentuga.entity.ShopCarInfo;
import com.peoit.android.studentuga.net.BaseCallBack;
import com.peoit.android.studentuga.net.BaseServer;
import com.peoit.android.studentuga.ui.adapter.ShopCarAdapter1;
import com.peoit.android.studentuga.ui.showUI.SimpleShowUiShow;

import java.util.List;

/**
 * author:libo
 * time:2015/10/16
 * E-mail:boli_android@163.com
 * last: ...
 */
public class ShopCarServer extends BaseServer {
    public ShopCarServer(ActivityBase activityBase) {
        super(activityBase);
    }

    /**
     * 请求添加商品到购物车
     *
     * @param productId
     * @param count
     * @param gg
     */
    public void requestAddGoodsToShopCar(String productId, String count, String gg) {
        RequestParams params = getSignRequestParams();
        if (params == null) {
            return;
        }
        params.put("productId", productId);
        params.put("count", count);
        params.put("gg", gg);
        mActBase.showLoadingDialog("正在保存商品信息...");
        request(NetConstants.NET_GOODS_ADDSHOPCAR, ShopCarInfo.class, params, new BaseCallBack<ShopCarInfo>() {
            @Override
            public void onFinish() {
                mActBase.hideLoadingDialog();
            }

            @Override
            public void onResponseSuccess(ShopCarInfo result) {
                CommonUtil.isChangeOrder = true;
                mActBase.showToast("添加成功");
            }

            @Override
            protected void onResponseFailure(int statusCode, String msg) {
                mActBase.onResponseFailure(statusCode, msg);
            }
        });
    }

    /**
     * 请求删除购物车的商品
     *
     * @param shopid
     * @param callBack
     */
    public void requestDelGoodsAtShopCar(String shopid, final OnSuccessCallBack callBack) {
        RequestParams params = getSignRequestParams();
        if (params == null) {
            return;
        }
        params.put("shopid", shopid);
        mActBase.showLoadingDialog("正在删除...");
        request(NetConstants.NET_GOODS_DELSHOPCAR, null, params, new BaseCallBack() {
            @Override
            public void onFinish() {
                mActBase.hideLoadingDialog();
            }

            @Override
            public void onResponseSuccess(Object result) {
                mActBase.showToast("删除成功");
                if (callBack != null)
                    callBack.onSuccess(1);
            }

            @Override
            protected void onResponseFailure(int statusCode, String msg) {
                mActBase.onResponseFailure(statusCode, msg);
            }
        });
    }

    /**
     * 请求修改购物车里的商品
     *
     * @param shopid
     * @param count
     * @param gg
     * @param callBack
     */
    public void requestUpDataGoodsAtShopCar(String shopid, String count, String gg, final OnSuccessCallBack callBack) {
        RequestParams params = getSignRequestParams();
        if (params == null) {
            return;
        }
        params.put("shopid", shopid);
        params.put("count", count);
        params.put("gg", gg);
        request(NetConstants.NET_GOODS_UPDATASHOPCAR, null, params, new BaseCallBack() {
            @Override
            public void onStart() {
                if (callBack != null)
                    callBack.onSuccess(0);
            }

            @Override
            public void onFinish() {
                mActBase.hideLoadingDialog();
            }

            @Override
            public void onResponseSuccess(Object result) {
                if (callBack != null)
                    callBack.onSuccess(1);
            }

            @Override
            protected void onResponseFailure(int statusCode, String msg) {
                mActBase.onResponseFailure(statusCode, msg);
                if (callBack != null)
                    callBack.onSuccess(2);
            }
        });
    }

    public void getShopCarAdapter() {
        return;
    }


    public void loadShopCarList(final SimpleShowUiShow mUIShow, final ShopCarAdapter1 adapter) {
        if (mUIShow != null) {
            mUIShow.showLoading();
        }
        RequestParams params = getSignRequestParams();
        if (params == null) {
            return;
        }
        request(NetConstants.NET_GOODS_QUERYSHOPCAR, ShopCarInfo.class, params, new BaseCallBack<ShopCarInfo>() {
            @Override
            public void onFinish() {
                mActBase.hideLoadingDialog();
            }

            @Override
            public void onResponseSuccessList(final List<ShopCarInfo> result) {
                if (result.size() == 0) {
                    if (mUIShow != null) {
                        mUIShow.setReLoad(true);
                        mUIShow.setIvErrorImg(R.drawable.noproduct);
                        mUIShow.setTvErrorMsg("您的购物车暂无商品");
                        mUIShow.setTvReloadListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                loadShopCarList(mUIShow, adapter);
                            }
                        });
                        mUIShow.showError();
                    } else {
                        mActBase.showToast("更新失败");
                    }
                    return;
                }
                if (mUIShow != null) {
                    mUIShow.showData();
                } else {
                    mActBase.showToast("更新成功");
                }
                adapter.upDateList(result);
            }

            @Override
            protected void onResponseFailure(int statusCode, String msg) {
                mActBase.onResponseFailure(statusCode, msg);
                if (mUIShow != null) {
                    mUIShow.setReLoad(true);
                    mUIShow.setIvErrorImg(R.drawable.test_shop);
                    mUIShow.setTvErrorMsg("购物车加载失败");
                    mUIShow.showError();
                } else {
                    mActBase.showToast("更新失败");
                }
            }
        });
    }
}
