package com.peoit.android.studentuga.net.server;

import android.view.View;

import com.loopj.android.http.RequestParams;
import com.peoit.android.peoit_lib.ActivityBase;
import com.peoit.android.peoit_lib.adapter.BaseViewModelAdapter;
import com.peoit.android.studentuga.R;
import com.peoit.android.studentuga.config.NetConstants;
import com.peoit.android.studentuga.entity.GoodsInfo;
import com.peoit.android.studentuga.net.BaseCallBack;
import com.peoit.android.studentuga.net.BaseServer;
import com.peoit.android.studentuga.ui.fragment.HomeFragment;
import com.peoit.android.studentuga.ui.showUI.SimpleShowUiShow;
import com.peoit.android.studentuga.view.list.SwipyRefreshLayout;
import com.peoit.android.studentuga.view.list.SwipyRefreshLayoutDirection;

import java.util.List;

/**
 * author:libo
 * time:2015/10/29
 * E-mail:boli_android@163.com
 * last: ...
 */
public class HomeChooseGoodsServer extends BaseServer implements SwipyRefreshLayout.OnRefreshListener {
    private final String Url = NetConstants.HOST + "queryProductTop.do" + NetConstants.$_ADD_$ + NetConstants.RESPONSE_LIST_MODEL;
    private BaseViewModelAdapter<GoodsInfo> adapter;

    public HomeChooseGoodsServer(ActivityBase activityBase) {
        super(activityBase);
    }

    public BaseViewModelAdapter<GoodsInfo> getAdapter(HomeFragment fragment) {
        adapter = new BaseViewModelAdapter<GoodsInfo>(mActBase.getActivity(), fragment);
        return adapter;
    }

    private int mNo = 1;

    public void RequestHoneChooseGoodsList(final OnBannerCallBack callBack) {
        RequestParams params = getRequestParams();
        params.put("pageNo", "1");
        params.put("pagesize", "5");
        request(Url, GoodsInfo.class, params, new BaseCallBack<GoodsInfo>() {
            @Override
            public void onStart() {
            }

            @Override
            public void onResponseSuccessList(List<GoodsInfo> result) {
                if (result == null || result.size() == 0)
                    return;
                if (callBack != null) {
                    callBack.onCallBack(result);
                }
            }

            @Override
            protected void onResponseFailure(int statusCode, String msg) {
                mActBase.onResponseFailure(statusCode, msg);
            }
        });
    }

    public void RequestHoneChooseGoodsList(final SimpleShowUiShow uiShow) {
        RequestParams params = getRequestParams();
        params.put("pageNo", "1");
        params.put("pagesize", "10");
        request(Url, GoodsInfo.class, params, new BaseCallBack<GoodsInfo>() {
            @Override
            public void onStart() {
                uiShow.setIvErrorImg(R.drawable.test_shop);
                uiShow.setTvErrorMsg("数据加载失败");
                uiShow.setTvReloadListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        RequestHoneChooseGoodsList(uiShow);
                    }
                });
                uiShow.setReLoad(true);
                uiShow.showLoading();
            }

            @Override
            public void onResponseSuccessList(List<GoodsInfo> result) {
                if (result == null || result.size() == 0) {
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

    public void RequestHoneChooseGoodsList(final SwipyRefreshLayout layout, final SwipyRefreshLayoutDirection direction) {
        RequestParams params = getRequestParams();
        params.put("pageNo", direction == SwipyRefreshLayoutDirection.TOP ? "1" : mNo+ "");
        params.put("pagesize", "10");
        request(Url, GoodsInfo.class, params, new BaseCallBack<GoodsInfo>() {
            @Override
            public void onFinish() {
                layout.setRefreshing(false);
            }

            @Override
            public void onResponseSuccessList(List<GoodsInfo> result) {
                if (result == null || result.size() == 0) {
                    mActBase.showToast("暂时没有推荐商品了");
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
        RequestHoneChooseGoodsList(layout, direction);
    }

    public interface OnBannerCallBack {
        void onCallBack(List<GoodsInfo> infos);
    }
}
