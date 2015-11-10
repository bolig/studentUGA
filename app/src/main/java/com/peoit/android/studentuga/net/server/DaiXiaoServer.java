package com.peoit.android.studentuga.net.server;

import com.loopj.android.http.RequestParams;
import com.peoit.android.peoit_lib.ActivityBase;
import com.peoit.android.peoit_lib.base.BaseUIShow;
import com.peoit.android.studentuga.R;
import com.peoit.android.studentuga.config.NetConstants;
import com.peoit.android.studentuga.entity.GoodsInfo;
import com.peoit.android.studentuga.net.BaseCallBack;
import com.peoit.android.studentuga.net.BaseServer;
import com.peoit.android.studentuga.ui.adapter.GoodsListAdapter;
import com.peoit.android.studentuga.view.list.SwipyRefreshLayout;
import com.peoit.android.studentuga.view.list.SwipyRefreshLayoutDirection;

import java.util.List;

/**
 * author:libo
 * time:2015/11/4
 * E-mail:boli_android@163.com
 * last: ...
 */
public class DaiXiaoServer extends BaseServer implements SwipyRefreshLayout.OnRefreshListener {
    private GoodsListAdapter mGoodsListAdapter;
    private RequestParams params;

    public DaiXiaoServer(ActivityBase activityBase) {
        super(activityBase);
    }

    public GoodsListAdapter getGoodsListAdapter() {
        mGoodsListAdapter = new GoodsListAdapter(mActBase.getActivity());
        mGoodsListAdapter.setDaoXiao(true);
        return mGoodsListAdapter;
    }

    private int mSkip = 1;

    public void loadGoodsList() {
        params = getRequestParams();
//        params.put("typeid", typeid);
//        params.put("title", title);
//        params.put("queryphone", queryphone);
//        params.put("oderType", oderType);
        params.put("pageNo", "1");
        params.put("dx", "2");
//        params.put("usertype", 2 + "");
        mActBase.getUIShowPresenter().setOnReloadListener(new BaseUIShow.OnReloadListener() {
            @Override
            public void onReload() {
                loadGoodsList();
            }
        });
        mActBase.getUIShowPresenter().showLoading();
        request(NetConstants.NET_GOODS_LIST, GoodsInfo.class, params, new BaseCallBack<GoodsInfo>() {
            @Override
            public void onResponseSuccessList(List<GoodsInfo> result) {
                if (result == null || result.size() == 0) {
                    mActBase.getUIShowPresenter().showError(R.drawable.noproduct, "暂无代销商品");
                    return;
                }
                mActBase.getUIShowPresenter().showData();
                mGoodsListAdapter.addHeadDataList(result);
                mSkip ++;
            }

            @Override
            protected void onResponseFailure(int statusCode, String msg) {
                mActBase.onResponseFailure(statusCode, msg);
                mActBase.getUIShowPresenter().showError(R.drawable.noproduct, "数据加载失败");
            }
        });
    }

    /**
     * 获取某个类型的商品
     */
    public void loadGoodsList(final SwipyRefreshLayout layout, final SwipyRefreshLayoutDirection direction) {
        params.put("pageNo", direction == SwipyRefreshLayoutDirection.BOTTOM ? mSkip + "" : "1");
        request(NetConstants.NET_GOODS_LIST, GoodsInfo.class, params, new BaseCallBack<GoodsInfo>() {
            @Override
            public void onFinish() {
                layout.setRefreshing(false);
            }

            @Override
            public void onResponseSuccessList(List<GoodsInfo> result) {
                if (result == null || result.size() == 0) {
                    mActBase.showToast("暂无商品了");
                } else {
                    switch (direction) {
                        case TOP:
                            mGoodsListAdapter.addHeadDataList(result);
                            break;
                        case BOTTOM:
                            mGoodsListAdapter.addFootDataList(result);
                            mSkip ++;
                            break;
                    }
                }
            }

            @Override
            protected void onResponseFailure(int statusCode, String msg) {
                mActBase.onResponseFailure(statusCode, msg);
                mActBase.showToast("商品加载失败");
            }
        });
    }

    @Override
    public void onRefresh(SwipyRefreshLayout layout, SwipyRefreshLayoutDirection direction) {
        loadGoodsList(layout, direction);
    }
}
