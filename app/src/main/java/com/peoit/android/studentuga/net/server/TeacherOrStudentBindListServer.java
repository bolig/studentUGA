package com.peoit.android.studentuga.net.server;

import com.loopj.android.http.RequestParams;
import com.peoit.android.peoit_lib.ActivityBase;
import com.peoit.android.peoit_lib.base.BaseUIShow;
import com.peoit.android.studentuga.R;
import com.peoit.android.studentuga.config.NetConstants;
import com.peoit.android.studentuga.entity.BindUserInfo;
import com.peoit.android.studentuga.net.BaseCallBack;
import com.peoit.android.studentuga.net.BaseServer;
import com.peoit.android.studentuga.ui.adapter.MyTeacherAdapter;
import com.peoit.android.studentuga.view.list.SwipyRefreshLayout;
import com.peoit.android.studentuga.view.list.SwipyRefreshLayoutDirection;

import java.util.List;

/**
 * author:libo
 * time:2015/11/10
 * E-mail:boli_android@163.com
 * last: ...
 */
public class TeacherOrStudentBindListServer extends BaseServer implements SwipyRefreshLayout.OnRefreshListener {
    private final String url = NetConstants.HOST + "queryBingUserTutor.do" + NetConstants.$_ADD_$ + NetConstants.RESPONSE_LIST_MODEL;
    private MyTeacherAdapter adapter;
    private RequestParams params;

    public TeacherOrStudentBindListServer(ActivityBase activityBase) {
        super(activityBase);
    }

    public MyTeacherAdapter getAdapter() {
        adapter = new MyTeacherAdapter(mActBase.getActivity());
        return adapter;
    }

    private int mNo = 1;

    public void requestBindList(final String phone, final String name) {
        params = getSignRequestParams();
        params.put("pageNo", "1");
        params.put("keyphone", phone);
        params.put("name", name);
        mActBase.getUIShowPresenter().showLoading();
        request(url, BindUserInfo.class, params, new BaseCallBack<BindUserInfo>() {
            @Override
            public void onResponseSuccessList(List<BindUserInfo> result) {
                if (result == null || result.size() == 0) {
                    mActBase.getUIShowPresenter().showError(R.drawable.user_avater, "暂无绑定的用户信息");
                    mActBase.getUIShowPresenter().setOnReloadListener(new BaseUIShow.OnReloadListener() {
                        @Override
                        public void onReload() {
                            requestBindList(phone, name);
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
                        requestBindList(phone, name);
                    }
                });
            }
        });
    }

    public void requestBindList(final SwipyRefreshLayout layout, final SwipyRefreshLayoutDirection direction) {
        params.put("pageNo", direction == SwipyRefreshLayoutDirection.BOTTOM ? mNo + "" : "1");
        request(url, BindUserInfo.class, params, new BaseCallBack<BindUserInfo>() {
            @Override
            public void onFinish() {
                layout.setRefreshing(false);
            }

            @Override
            public void onResponseSuccessList(List<BindUserInfo> result) {
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
