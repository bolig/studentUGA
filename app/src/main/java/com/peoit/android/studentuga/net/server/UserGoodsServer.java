package com.peoit.android.studentuga.net.server;

import android.view.View;

import com.loopj.android.http.RequestParams;
import com.peoit.android.peoit_lib.ActivityBase;
import com.peoit.android.studentuga.R;
import com.peoit.android.studentuga.config.NetConstants;
import com.peoit.android.studentuga.entity.GoodsInfo;
import com.peoit.android.studentuga.net.BaseCallBack;
import com.peoit.android.studentuga.net.BaseServer;
import com.peoit.android.studentuga.ui.adapter.GoodsListAdapter;
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
public class UserGoodsServer extends BaseServer implements SwipyRefreshLayout.OnRefreshListener {

    private RequestParams params;
    public GoodsListAdapter mGoodsListAdapter;

    public UserGoodsServer(ActivityBase activityBase) {
        super(activityBase);
    }

    public GoodsListAdapter getGoodsListAdapter() {
        mGoodsListAdapter = new GoodsListAdapter(mActBase.getActivity());
        return mGoodsListAdapter;
    }

    public void requestUserGoodsList(final String queryphone, final SimpleShowUiShow uiShow, final OnSuccessCallBack callBack) {
        params = getRequestParams();
        params.put("queryphone", queryphone);
        params.put("pageNo", "1");
        request(NetConstants.NET_GOODS_LIST, GoodsInfo.class, params, new BaseCallBack<GoodsInfo>() {
            @Override
            public void onStart() {
                uiShow.setIvErrorImg(R.drawable.noproduct);
                uiShow.setTvErrorMsg("暂无商品信息");
                uiShow.setTvReloadListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        requestUserGoodsList(queryphone, uiShow, callBack);
                    }
                });
                uiShow.showLoading();
            }

            @Override
            public void onResponseSuccessList(List<GoodsInfo> result) {
                if (result == null || result.size() == 0) {
                    uiShow.showError();
                    return;
                }
                uiShow.showData();
                mGoodsListAdapter.upDateList(result);
                mNo ++;
                if (callBack != null) {
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

    public void requestUserGoodsList(final SwipyRefreshLayout layout, final SwipyRefreshLayoutDirection direction) {
        params.put("pageNo", direction == SwipyRefreshLayoutDirection.BOTTOM ? "" + mNo : "1");
        request(NetConstants.NET_GOODS_LIST, GoodsInfo.class, params, new BaseCallBack<GoodsInfo>() {
            @Override
            public void onFinish() {
                layout.setRefreshing(false);
            }

            @Override
            public void onResponseSuccessList(List<GoodsInfo> result) {
                if (result == null || result.size() == 0) {
                    mActBase.showToast("暂无更多商品了");
                    return;
                }
                switch (direction) {
                    case TOP:
                        mGoodsListAdapter.addHeadDataList(result);
                        break;
                    case BOTTOM:
                        mGoodsListAdapter.addFootDataList(result);
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
        requestUserGoodsList(layout, direction);
    }
}
