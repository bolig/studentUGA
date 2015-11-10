package com.peoit.android.studentuga.net.server;

import android.view.View;

import com.loopj.android.http.RequestParams;
import com.peoit.android.peoit_lib.ActivityBase;
import com.peoit.android.studentuga.R;
import com.peoit.android.studentuga.config.NetConstants;
import com.peoit.android.studentuga.entity.UserInfo;
import com.peoit.android.studentuga.net.BaseCallBack;
import com.peoit.android.studentuga.net.BaseServer;
import com.peoit.android.studentuga.ui.adapter.MyRankingAdapter;
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
public class RankingServer extends BaseServer implements SwipyRefreshLayout.OnRefreshListener {

    public final String Url = NetConstants.HOST + "queryTotlalsales.do" + NetConstants.$_ADD_$ + NetConstants.RESPONSE_LIST_MODEL;
    private MyRankingAdapter adapter;
    private String areaid;

    public RankingServer(ActivityBase activityBase) {
        super(activityBase);
    }

    public MyRankingAdapter getAdapter() {
        adapter = new MyRankingAdapter(mActBase.getActivity());
        return adapter;
    }

    public void requestHomeRanking(String areaid, SimpleShowUiShow uiShow) {
//        RequestParams params = getRequestParams();
//        params.put("pageNo", "1");
//        params.put("pagesize", "6");
//        request(Url, UserInfo.class, params, new BaseCallBack<UserInfo>() {
//            @Override
//            public void onResponseSuccessList(List<UserInfo> result) {
//                if (result == null || result.size() == 0)
//                    return;
//            }
//
//            @Override
//            protected void onResponseFailure(int statusCode, String msg) {
//                mActBase.onResponseFailure(statusCode, msg);
//            }
//        });
        requestHomeRanking(areaid, uiShow, null, null);
    }

    private int mNo = 1;

    public void requestHomeRanking(final String areaid, final SimpleShowUiShow uiShow, final SwipyRefreshLayout layout, final SwipyRefreshLayoutDirection direction) {
        final boolean isNull = (layout == null);
        RequestParams params = getRequestParams();
        this.areaid = areaid;
        params.put("areaid", areaid);
        params.put("pageNo", direction == SwipyRefreshLayoutDirection.BOTTOM ? mNo + "" : "1");
        params.put("pagesize", "10");
        if (isNull) {
            uiShow.setIvErrorImg(R.drawable.nomingxi);
            uiShow.setTvErrorMsg("暂无排名信息");
            uiShow.setTvReloadListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    requestHomeRanking(areaid, uiShow, layout, direction);
                }
            });
            uiShow.setReLoad(true);
            uiShow.showLoading();
        }
        request(Url, UserInfo.class, params, new BaseCallBack<UserInfo>() {
            @Override
            public void onFinish() {
                if (!isNull) {
                    layout.setRefreshing(false);
                }
            }

            @Override
            public void onResponseSuccessList(List<UserInfo> result) {
                if (result == null || result.size() == 0) {
                    if (isNull) {
                        uiShow.showError();
                    } else {
                        mActBase.showToast("数据加载完成");
                    }
                    return;
                }
                if (isNull) {
                    uiShow.showData();
                    adapter.upDateList(result);
                    mNo ++;
                } else {
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
            }

            @Override
            protected void onResponseFailure(int statusCode, String msg) {
                mActBase.onResponseFailure(statusCode, msg);
                if (isNull) {
                    uiShow.showError();
                } else {
                    mActBase.showToast("数据加载失败");
                }
            }
        });
    }


    @Override
    public void onRefresh(SwipyRefreshLayout layout, SwipyRefreshLayoutDirection direction) {
        requestHomeRanking(areaid, null, layout, direction);
    }
}
