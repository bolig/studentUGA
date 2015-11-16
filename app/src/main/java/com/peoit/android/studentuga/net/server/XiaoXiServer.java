package com.peoit.android.studentuga.net.server;

import com.loopj.android.http.RequestParams;
import com.peoit.android.peoit_lib.ActivityBase;
import com.peoit.android.peoit_lib.base.BaseUIShow;
import com.peoit.android.studentuga.R;
import com.peoit.android.studentuga.config.NetConstants;
import com.peoit.android.studentuga.entity.XiaoXiInfo;
import com.peoit.android.studentuga.net.BaseCallBack;
import com.peoit.android.studentuga.net.BaseServer;
import com.peoit.android.studentuga.ui.adapter.XiaoXiAdapter;
import com.peoit.android.studentuga.view.list.SwipyRefreshLayout;
import com.peoit.android.studentuga.view.list.SwipyRefreshLayoutDirection;

import java.util.List;

/**
 * author:libo
 * time:2015/11/13
 * E-mail:boli_android@163.com
 * last: ...
 */
public class XiaoXiServer extends BaseServer implements SwipyRefreshLayout.OnRefreshListener {

    private final String url = NetConstants.HOST + "queryNote.do" + NetConstants.$_ADD_$ + NetConstants.RESPONSE_LIST_MODEL;
    private XiaoXiAdapter adapter;

    public XiaoXiServer(ActivityBase activityBase) {
        super(activityBase);
    }

    public XiaoXiAdapter getAdapter() {
        adapter = new XiaoXiAdapter(mActBase.getActivity());
        return adapter;
    }

    private int mNo = 1;

    public void requestXiaoXi() {
        RequestParams params = getRequestParams();
        params.put("pageNo", "1");
        mNo = 1;
        request(url, XiaoXiInfo.class, params, new BaseCallBack<XiaoXiInfo>() {
            @Override
            public void onStart() {
                mActBase.getUIShowPresenter().showLoading();
            }

            @Override
            public void onResponseSuccessList(List<XiaoXiInfo> result) {
                if (result == null || result.size() == 0) {
                    mActBase.getUIShowPresenter().showError(R.drawable.nomessage, "暂无通知消息");
                    mActBase.getUIShowPresenter().setOnReloadListener(new BaseUIShow.OnReloadListener() {
                        @Override
                        public void onReload() {
                            requestXiaoXi();
                        }
                    });
                    return;
                }
                mActBase.getUIShowPresenter().showData();
                adapter.upDateList(result);
                mNo ++;
            }

            @Override
            protected void onResponseFailure(int statusCode, String msg) {
                mActBase.onResponseFailure(statusCode, msg);
                mActBase.getUIShowPresenter().showError(R.drawable.nomessage, "数据加载失败");
                mActBase.getUIShowPresenter().setOnReloadListener(new BaseUIShow.OnReloadListener() {
                    @Override
                    public void onReload() {
                        requestXiaoXi();
                    }
                });
            }
        });
    }

    public void requestXiaoXi(final SwipyRefreshLayout layout, final SwipyRefreshLayoutDirection direction) {
        RequestParams params = getRequestParams();
        params.put("pageNo", direction == SwipyRefreshLayoutDirection.BOTTOM ? mNo + "" : "1");
        request(url, XiaoXiInfo.class, params, new BaseCallBack<XiaoXiInfo>() {

            @Override
            public void onFinish() {
                layout.setRefreshing(false);
            }

            @Override
            public void onResponseSuccessList(List<XiaoXiInfo> result) {
                if (result == null || result.size() == 0) {
                    mActBase.showToast("暂无更多数据了");
                    return;
                }
                switch (direction) {
                    case BOTTOM:
                        adapter.addFootDataList(result);
                        mNo++;
                        break;
                    case TOP:
                        adapter.addHeadDataList(result);
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
        requestXiaoXi(layout, direction);
    }
}
