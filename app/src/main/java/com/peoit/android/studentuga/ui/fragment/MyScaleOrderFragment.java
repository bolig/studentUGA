package com.peoit.android.studentuga.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.peoit.android.peoit_lib.ActivityBase;
import com.peoit.android.peoit_lib.base.BasePagerFragment;
import com.peoit.android.studentuga.R;
import com.peoit.android.studentuga.enuml.ScaleOrderStatus;
import com.peoit.android.studentuga.net.server.OrderScaleListServer;
import com.peoit.android.studentuga.ui.showUI.SimpleShowUiShow;
import com.peoit.android.studentuga.view.list.SwipyRefreshLayout;

/**
 * author:libo
 * time:2015/9/16
 * E-mail:boli_android@163.com
 * last: ...
 */
public class MyScaleOrderFragment extends BasePagerFragment {

    private SwipyRefreshLayout srlRefresh;
    private ListView lvInfo;
    private FrameLayout flRoot;
    private int mStatus;
    private ScaleOrderStatus mOrderStatus;
    private OrderScaleListServer mSacleListServer;
    private SimpleShowUiShow mUIShow;
    private boolean isMyOrder;

    private void assignViews() {
        srlRefresh = (SwipyRefreshLayout) findViewById(R.id.srl_refresh);
        lvInfo = (ListView) findViewById(R.id.lv_info);
        flRoot = (FrameLayout) findViewById(R.id.fl_root);
    }

    public static final int status_XD = 1;
    public static final int status_FK = 2;
    public static final int status_QX = 3;
    public static final int status_JC = 4;
    public static final int status_WC = 5;

    public static MyScaleOrderFragment newInstance(int status, boolean isMy) {
        MyScaleOrderFragment fragment = new MyScaleOrderFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("status", status);
        bundle.putBoolean("isMy", isMy);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View createView(LayoutInflater inflater, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frag_myorder_all, null);
    }

    @Override
    public void initData() {
        if (getArguments() != null) {
            isMyOrder = getArguments().getBoolean("isMy", false);
            mStatus = getArguments().getInt("status", -1);
            switch (mStatus) {
                case status_XD:
                    mOrderStatus = ScaleOrderStatus.status_xiadan;
                    break;
                case status_FK:
                    mOrderStatus = ScaleOrderStatus.status_fukuan;
                    break;
                case status_QX:
                    mOrderStatus = ScaleOrderStatus.status_cancel;
                    break;
                case status_JC:
                    mOrderStatus = ScaleOrderStatus.status_jichu;
                    break;
                case status_WC:
                    mOrderStatus = ScaleOrderStatus.status_wancheng;
                    break;
            }
        }
        mSacleListServer = new OrderScaleListServer((ActivityBase) getActivity(), isMyOrder);
    }

    @Override
    public void initView() {
        assignViews();
        mUIShow = new SimpleShowUiShow(getActivity());
        mUIShow.setRootView(flRoot);
        lvInfo.setAdapter(mSacleListServer.getAdapter());
    }

    @Override
    public void initListener() {
        srlRefresh.setOnRefreshListener(mSacleListServer);
    }

    @Override
    public void onResume() {
        super.onResume();
        load();
    }

    private void load() {
        mSacleListServer.loadScaleOrder(mUIShow, mOrderStatus);
    }
}
