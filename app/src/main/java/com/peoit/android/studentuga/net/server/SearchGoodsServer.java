package com.peoit.android.studentuga.net.server;

import android.view.View;

import com.loopj.android.http.RequestParams;
import com.peoit.android.peoit_lib.ActivityBase;
import com.peoit.android.studentuga.config.NetConstants;
import com.peoit.android.studentuga.entity.GoodsInfo;
import com.peoit.android.studentuga.net.BaseCallBack;
import com.peoit.android.studentuga.net.BaseServer;
import com.peoit.android.studentuga.ui.adapter.GoodsListAdapter;
import com.peoit.android.studentuga.ui.adapter.SortTypeAdapter;
import com.peoit.android.studentuga.ui.showUI.SimpleShowUiShow;
import com.peoit.android.studentuga.view.list.SwipyRefreshLayout;
import com.peoit.android.studentuga.view.list.SwipyRefreshLayoutDirection;

import java.util.List;

/**
 * author:libo
 * time:2015/10/10
 * E-mail:boli_android@163.com
 * last: ...
 */
public class SearchGoodsServer extends BaseServer implements SwipyRefreshLayout.OnRefreshListener {
    private SortTypeAdapter mSortTypeAdapter;
    private GoodsListAdapter mGoodsListAdapter;
    private RequestParams params;

    public SearchGoodsServer(ActivityBase activityBase) {
        super(activityBase);
    }

    public GoodsListAdapter getGoodsListAdapter() {
        mGoodsListAdapter = new GoodsListAdapter(mActBase.getActivity());
        return mGoodsListAdapter;
    }

    /**
     * 获取某个类型的商品
     *
     * @param title      商品标题(非必须,模糊查询)
     * @param queryphone 商品所属的人账户(电话号码，完全匹配查询)
     */
    public void loadGoodsList(final String title,
                              final String queryphone, final SimpleShowUiShow mUIShow) {
        params = getRequestParams();
        params.put("title", title);
        params.put("queryname", queryphone);
        params.put("pageNo", "1");
        mNo = 1;
        mUIShow.showLoading();
        request(NetConstants.NET_GOODS_LIST, GoodsInfo.class, params, new BaseCallBack<GoodsInfo>() {
            @Override
            public void onResponseSuccessList(List<GoodsInfo> result) {
                if (result == null || result.size() == 0) {
                    mUIShow.setTvErrorMsg("暂无符合条件的商品");
                    mUIShow.setTvReloadListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mNo = 1;
                            loadGoodsList(title, queryphone, mUIShow);
                        }
                    });
                    mUIShow.setReLoad(true);
                    mUIShow.showError();
                    return;
                }
                mGoodsListAdapter.upDateList(result);
                mUIShow.showData();
                mNo++;
            }

            @Override
            protected void onResponseFailure(int statusCode, String msg) {
                mActBase.onResponseFailure(statusCode, msg);
                mUIShow.setTvErrorMsg("数据加载失败");
                mUIShow.setTvReloadListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mNo = 1;
                        loadGoodsList(title, queryphone, mUIShow);
                    }
                });
                mUIShow.setReLoad(true);
                mUIShow.showError();
            }
        });
    }

    private int mNo = 1;

    /**
     * 获取某个类型的商品
     */
    public void loadGoodsList(final SwipyRefreshLayout layout, final SwipyRefreshLayoutDirection direction) {
        params.put("pageNo", direction == SwipyRefreshLayoutDirection.BOTTOM ? mNo + "" : "1");
        request(NetConstants.NET_GOODS_LIST, GoodsInfo.class, params, new BaseCallBack<GoodsInfo>() {
            @Override
            public void onFinish() {
                layout.setRefreshing(false);
            }

            @Override
            public void onResponseSuccessList(List<GoodsInfo> result) {
                if (result == null || result.size() == 0) {
                    mActBase.showToast("暂无更多商品了");
                } else {
                    switch (direction) {
                        case TOP:
                            mGoodsListAdapter.addHeadDataList(result);
                            break;
                        case BOTTOM:
                            mNo++;
                            mGoodsListAdapter.addFootDataList(result);
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
