package com.peoit.android.studentuga.net.server;

import com.loopj.android.http.RequestParams;
import com.peoit.android.peoit_lib.ActivityBase;
import com.peoit.android.peoit_lib.base.BaseUIShow;
import com.peoit.android.studentuga.R;
import com.peoit.android.studentuga.config.NetConstants;
import com.peoit.android.studentuga.entity.BindInfo;
import com.peoit.android.studentuga.net.BaseCallBack;
import com.peoit.android.studentuga.net.BaseServer;
import com.peoit.android.studentuga.ui.adapter.MyTeacherAdapter1;
import com.peoit.android.studentuga.view.list.SwipyRefreshLayout;
import com.peoit.android.studentuga.view.list.SwipyRefreshLayoutDirection;

import java.util.List;

/**
 * author:libo
 * time:2015/11/10
 * E-mail:boli_android@163.com
 * last: ...
 */
public class TeacherOrStudentBindedListServer extends BaseServer implements SwipyRefreshLayout.OnRefreshListener {
    private final String url = NetConstants.HOST + "queryTutorStuList.do" + NetConstants.$_ADD_$ + NetConstants.RESPONSE_LIST_MODEL;
    private MyTeacherAdapter1 adapter;

    public TeacherOrStudentBindedListServer(ActivityBase activityBase) {
        super(activityBase);
    }

    public MyTeacherAdapter1 getAdapter() {
        adapter = new MyTeacherAdapter1(mActBase.getActivity());
        return adapter;
    }

    private int mNo = 1;

    public void requestBindList() {
        RequestParams params = getSignRequestParams();
        params.put("pageNo", "1");
        mActBase.getUIShowPresenter().showLoading();
        request(url, BindInfo.class, params, new BaseCallBack<BindInfo>() {
            @Override
            public void onResponseSuccessList(List<BindInfo> result) {
                if (result == null || result.size() == 0) {
                    mActBase.getUIShowPresenter().showError(R.drawable.user_avater, "暂无可绑定的用户信息");
                    mActBase.getUIShowPresenter().setOnReloadListener(new BaseUIShow.OnReloadListener() {
                        @Override
                        public void onReload() {
                            requestBindList();
                        }
                    });
                    return;
                }
                mActBase.getUIShowPresenter().showData();
                adapter.upDateList(result);
                mNo++;
            }

            @Override
            protected void onResponseFailure(int statusCode, String msg) {
                mActBase.onResponseFailure(statusCode, msg);
                mActBase.getUIShowPresenter().showError(R.drawable.user_avater, "数据加载失败");
                mActBase.getUIShowPresenter().setOnReloadListener(new BaseUIShow.OnReloadListener() {
                    @Override
                    public void onReload() {
                        requestBindList();
                    }
                });
            }
        });
    }

    public void requestBindList(final SwipyRefreshLayout layout, final SwipyRefreshLayoutDirection direction) {
        RequestParams params = getSignRequestParams();
        params.put("pageNo", direction == SwipyRefreshLayoutDirection.BOTTOM ? mNo + "" : "1");
        request(url, BindInfo.class, params, new BaseCallBack<BindInfo>() {

            @Override
            public void onFinish() {
                layout.setRefreshing(false);
            }

            @Override
            public void onResponseSuccessList(List<BindInfo> result) {
                if (result == null || result.size() == 0) {
                    mActBase.showToast("暂无更多信息了");
                    return;
                }
                switch (direction) {
                    case TOP:
                        adapter.addHeadDataList(result);
                        break;
                    case BOTTOM:
                        adapter.addFootDataList(result);
                        mNo++;
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
        requestBindList(layout, direction);
    }
}
