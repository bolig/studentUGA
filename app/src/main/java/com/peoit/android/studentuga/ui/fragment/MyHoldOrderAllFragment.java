package com.peoit.android.studentuga.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.peoit.android.peoit_lib.ActivityBase;
import com.peoit.android.peoit_lib.base.BasePagerFragment;
import com.peoit.android.peoit_lib.util.MyLogger;
import com.peoit.android.studentuga.R;
import com.peoit.android.studentuga.enuml.OrderStatus;
import com.peoit.android.studentuga.net.server.OrderHoldListServer;
import com.peoit.android.studentuga.ui.showUI.SimpleShowUiShow;
import com.peoit.android.studentuga.view.list.SwipyRefreshLayout;

/**
 * author:libo
 * time:2015/9/16
 * E-mail:boli_android@163.com
 * last: ...
 */
public class MyHoldOrderAllFragment extends BasePagerFragment {
    private OrderStatus mOrderStatus;
    private SwipyRefreshLayout srlRefresh;
    private ListView lvInfo;
    private FrameLayout flRoot;
    private SimpleShowUiShow mUIShow;
    private int mStatus;
    private OrderHoldListServer mOrderListServer;
    private View mView;

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

    public static MyHoldOrderAllFragment newInstance(int status) {
        MyHoldOrderAllFragment fragment = new MyHoldOrderAllFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("status", status);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View createView(LayoutInflater inflater, Bundle savedInstanceState) {
        if (mView == null) {
            MyLogger.e(mStatus + "xxxxx1");
            mView = inflater.inflate(R.layout.frag_myorder_all, null);
        }
        return mView;
    }

    @Override
    public void initData() {
        if (getArguments() != null) {
            mStatus = getArguments().getInt("status", -1);
            switch (mStatus) {
                case status_XD:
                    mOrderStatus = OrderStatus.status_xiadan;
                    break;
                case status_FK:
                    mOrderStatus = OrderStatus.status_fukuan;
                    break;
                case status_QX:
                    mOrderStatus = OrderStatus.status_cancel;
                    break;
                case status_JC:
                    mOrderStatus = OrderStatus.status_jichu;
                    break;
                case status_WC:
                    mOrderStatus = OrderStatus.status_wancheng;
                    break;
            }
        }
        mOrderListServer = new OrderHoldListServer((ActivityBase) getActivity());
    }

    private boolean isFirst = true;

    @Override
    public void initView() {
        assignViews();
        MyLogger.e(mStatus + "initView");
        mUIShow = new SimpleShowUiShow(getActivity());
        mUIShow.setRootView(flRoot);
        lvInfo.setAdapter(mOrderListServer.getAdapter());
    }

    @Override
    public void initListener() {
        srlRefresh.setOnRefreshListener(mOrderListServer);
    }

    @Override
    public void onResume() {
        super.onResume();
        load();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyLogger.e(mStatus + "onCreate");
    }

    @Override
    public void onPause() {
        super.onPause();
        MyLogger.e(mStatus + "onPause");
    }

    public void load() {
        mOrderListServer.loadHoldOrder(mUIShow, mOrderStatus);
    }
}
