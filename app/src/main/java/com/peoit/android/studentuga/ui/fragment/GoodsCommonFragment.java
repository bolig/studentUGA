package com.peoit.android.studentuga.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.peoit.android.peoit_lib.ActivityBase;
import com.peoit.android.peoit_lib.base.BasePagerFragment;
import com.peoit.android.studentuga.R;
import com.peoit.android.studentuga.net.server.OrderCommonServer;
import com.peoit.android.studentuga.ui.showUI.SimpleShowUiShow;
import com.peoit.android.studentuga.view.list.SwipyRefreshLayout;

/**
 * author:libo
 * time:2015/11/2
 * E-mail:boli_android@163.com
 * last: ...
 */
public class GoodsCommonFragment extends BasePagerFragment {
    private SwipyRefreshLayout srlRefresh;
    private ListView lvInfo;
    private FrameLayout flRoot;
    private boolean isCommon;
    private OrderCommonServer mGoodsCommonServer;
    private SimpleShowUiShow uiShow;

    private void assignViews() {
        srlRefresh = (SwipyRefreshLayout) findViewById(R.id.srl_refresh);
        lvInfo = (ListView) findViewById(R.id.lv_info);
        flRoot = (FrameLayout) findViewById(R.id.fl_root);
    }

    public static GoodsCommonFragment newInstance(boolean isCommon) {
        GoodsCommonFragment fragment = new GoodsCommonFragment();
        Bundle bundle = new Bundle();
        bundle.putBoolean("isCommon", isCommon);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View createView(LayoutInflater inflater, Bundle savedInstanceState) {
        return View.inflate(getActivity(), R.layout.act_goods_common_frag, null);
    }

    @Override
    public void initData() {
        isCommon = getArguments().getBoolean("isCommon", false);
        mGoodsCommonServer = new OrderCommonServer((ActivityBase) getActivity());
    }

    @Override
    public void initView() {
        assignViews();
        uiShow = new SimpleShowUiShow(getActivity());
        uiShow.setRootView(flRoot);
        uiShow.setTvReloadListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mGoodsCommonServer.requestOrderCommon(uiShow, isCommon);
            }
        });
        uiShow.setReLoad(true);
        lvInfo.setAdapter(mGoodsCommonServer.getAdapter(this));
        mGoodsCommonServer.requestOrderCommon(uiShow, isCommon);
    }

    @Override
    public void initListener() {
        srlRefresh.setOnRefreshListener(mGoodsCommonServer);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            mGoodsCommonServer.requestOrderCommon(uiShow, isCommon);
        }
    }
}
