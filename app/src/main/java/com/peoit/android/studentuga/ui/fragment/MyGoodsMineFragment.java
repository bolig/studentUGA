package com.peoit.android.studentuga.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.peoit.android.peoit_lib.ActivityBase;
import com.peoit.android.peoit_lib.base.BasePagerFragment;
import com.peoit.android.studentuga.R;
import com.peoit.android.studentuga.config.CommonUtil;
import com.peoit.android.studentuga.net.server.MyGoodsServer;
import com.peoit.android.studentuga.ui.showUI.SimpleShowUiShow;
import com.peoit.android.studentuga.uitl.MyLogger;
import com.peoit.android.studentuga.view.list.SwipyRefreshLayout;
import com.peoit.android.studentuga.view.list.swipe.SwipeMenu;
import com.peoit.android.studentuga.view.list.swipe.SwipeMenuLayout;
import com.peoit.android.studentuga.view.list.swipe.SwipeMenuListView;

/**
 * author:libo
 * time:2015/10/30
 * E-mail:boli_android@163.com
 * last: ...
 */
public class MyGoodsMineFragment extends BasePagerFragment {
    private SwipyRefreshLayout pullLayout;
    private SwipeMenuListView lvmenuInfo;
    private MyGoodsServer mMyGoodsServer;
    private boolean isMine;
    private SimpleShowUiShow mUiShow;
    private FrameLayout fl_root;

    private void assignViews() {
        pullLayout = (SwipyRefreshLayout) findViewById(R.id.pull_layout);
        lvmenuInfo = (SwipeMenuListView) findViewById(R.id.lvmenu_info);
        fl_root = (FrameLayout) findViewById(R.id.fl_root);
    }

    public static MyGoodsMineFragment newInstance(boolean isMine) {
        MyGoodsMineFragment fragment = new MyGoodsMineFragment();
        Bundle bundle = new Bundle();
        bundle.putBoolean("isMine", isMine);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View createView(LayoutInflater inflater, Bundle savedInstanceState) {
        return View.inflate(getActivity(), R.layout.act_my_msg, null);
    }

    @Override
    public void initData() {
        isMine = getArguments().getBoolean("isMine");
        mMyGoodsServer = new MyGoodsServer((ActivityBase) getActivity());
    }

    @Override
    public void initView() {
        assignViews();
//        SwipeMenuCreator creator = new SwipeMenuCreator() {
//            @Override
//            public void create(SwipeMenu menu) {
//                SwipeMenuItem menuItem = new SwipeMenuItem(getActivity());
//                menuItem.setBackground(getActivity().getResources().getDrawable(R.drawable.draw_pay_cancel_sel));
//                menuItem.setTitle("编辑");
//                menuItem.setWidth(CommonUtil.dip2px(88));
//                menuItem.setTitleColor(getActivity().getResources().getColor(R.color.white_1));
//                menuItem.setTitleSize(16);
//                menu.addMenuItem(menuItem);
//
//                SwipeMenuItem menuItem1 = new SwipeMenuItem(getActivity());
//                menuItem1.setBackground(getActivity().getResources().getDrawable(R.drawable.draw_close_sel));
//                menuItem1.setTitle("删除");
//                menuItem1.setWidth(CommonUtil.dip2px(88));
//                menuItem1.setTitleColor(getActivity().getResources().getColor(R.color.white_1));
//                menuItem1.setTitleSize(16);
//                menu.addMenuItem(menuItem1);
//            }
//        };
//        lvmenuInfo.setMenuCreator(creator);
        lvmenuInfo.setAdapter(mMyGoodsServer.getAdapter(!isMine));

        mUiShow = new SimpleShowUiShow(getActivity());
        mUiShow.setRootView(fl_root);

        mMyGoodsServer.requestMyGoodsList(mUiShow, true, isMine);
    }

    @Override
    public void initListener() {
        lvmenuInfo.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public void onMenuItemClick(int position, SwipeMenu menu, int index) {
                switch (index) {
                    case 0:
                        showToast("编辑");
                        return;
                    case 1:
                        showToast("删除");
                        return;
                }
            }
        });

        lvmenuInfo.setOnSwipeListener(new SwipeMenuListView.OnSwipeListener() {
            @Override
            public void onSwipeStart(int position) {
                pullLayout.setScroll(false);
            }

            @Override
            public void onSwipeEnd(int position) {

            }
        });

        lvmenuInfo.setOnMenuChangeListener(new SwipeMenuLayout.OnMenuStatChangeListener() {
            @Override
            public void onStatChange(boolean isOpen) {
                MyLogger.e("isOpen = " + isOpen);
                pullLayout.setScroll(!isOpen);
            }
        });

        pullLayout.setOnRefreshListener(mMyGoodsServer);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!isMine && CommonUtil.isDaiXiaoCancel) {
            mMyGoodsServer.requestMyGoodsList(mUiShow, true, isMine);
            CommonUtil.isDaiXiaoCancel = false;
        }
    }
}
