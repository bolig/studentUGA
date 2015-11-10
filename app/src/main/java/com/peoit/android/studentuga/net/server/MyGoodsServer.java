package com.peoit.android.studentuga.net.server;

import android.view.View;

import com.loopj.android.http.RequestParams;
import com.peoit.android.peoit_lib.ActivityBase;
import com.peoit.android.studentuga.R;
import com.peoit.android.studentuga.config.NetConstants;
import com.peoit.android.studentuga.entity.MyGoodsInfo;
import com.peoit.android.studentuga.net.BaseCallBack;
import com.peoit.android.studentuga.net.BaseServer;
import com.peoit.android.studentuga.ui.adapter.MyGoodsMineAdapter;
import com.peoit.android.studentuga.ui.showUI.SimpleShowUiShow;
import com.peoit.android.studentuga.view.list.SwipyRefreshLayout;
import com.peoit.android.studentuga.view.list.SwipyRefreshLayoutDirection;

import java.util.List;

/**
 * author:libo
 * time:2015/10/30
 * E-mail:boli_android@163.com
 * last: ...
 */
public class MyGoodsServer extends BaseServer implements SwipyRefreshLayout.OnRefreshListener {

    public final String Url = NetConstants.HOST + "queryMyProduct.do" + NetConstants.$_ADD_$ + NetConstants.RESPONSE_LIST_MODEL;
    private MyGoodsMineAdapter adapter;
    private boolean isPuAway;
    private boolean isMine;

    public MyGoodsServer(ActivityBase activityBase) {
        super(activityBase);
    }

    public MyGoodsMineAdapter getAdapter(boolean isDaiXiao) {
        adapter = new MyGoodsMineAdapter(mActBase.getActivity());
        adapter.setDaoXiao(isDaiXiao);
        return adapter;
    }

    public void requestMyGoodsList(final SimpleShowUiShow uiShow, final boolean isPutAway, final boolean isMine) {
        this.isPuAway = isPutAway;
        this.isMine = isMine;
        RequestParams params = getSignRequestParams();
        params.put("state", isPutAway ? "Y" : "N");
        params.put("type", isMine ? "zy" : "dx");
        params.put("pageNo", "1");
        request(Url, MyGoodsInfo.class, params, new BaseCallBack<MyGoodsInfo>() {

            @Override
            public void onStart() {
                uiShow.setIvErrorImg(R.drawable.noproduct);
                uiShow.setTvReloadListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        requestMyGoodsList(uiShow, isPutAway, isMine);
                    }
                });
                uiShow.setReLoad(true);
                uiShow.showLoading();
            }

            @Override
            public void onResponseSuccessList(List<MyGoodsInfo> result) {
                if (result == null || result.size() == 0) {
                    uiShow.setTvErrorMsg("暂无商品信息");
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
                uiShow.setTvErrorMsg("数据加载失败");
                uiShow.showError();
            }
        });
    }

    private int mNo = 1;

    public void requestMyGoodsList(boolean isPutAway,
                                   boolean isMine,
                                   final SwipyRefreshLayout layout,
                                   final SwipyRefreshLayoutDirection direction) {
        RequestParams params = getSignRequestParams();
        params.put("state", isPutAway ? "Y" : "N");
        params.put("type", isMine ? "zy" : "dx");
        params.put("pageNo", direction == SwipyRefreshLayoutDirection.BOTTOM ? mNo + "" : "1");
        request(Url, MyGoodsInfo.class, params, new BaseCallBack<MyGoodsInfo>() {
            @Override
            public void onFinish() {
                layout.setRefreshing(false);
            }

            @Override
            public void onResponseSuccessList(List<MyGoodsInfo> result) {
                if (result == null || result.size() == 0) {
                    mActBase.showToast("暂无商品信息");
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
            }
        });
    }

    @Override
    public void onRefresh(SwipyRefreshLayout layout, SwipyRefreshLayoutDirection direction) {
        requestMyGoodsList(isPuAway, isMine, layout, direction);
    }
}
