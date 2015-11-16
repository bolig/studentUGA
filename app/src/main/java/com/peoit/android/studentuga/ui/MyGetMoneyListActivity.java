package com.peoit.android.studentuga.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.peoit.android.peoit_lib.base.BaseActivity;
import com.peoit.android.peoit_lib.base.BaseUIShow;
import com.peoit.android.studentuga.R;
import com.peoit.android.studentuga.config.CommonUtil;
import com.peoit.android.studentuga.net.BaseServer;
import com.peoit.android.studentuga.net.server.GetMoneyListServer;
import com.peoit.android.studentuga.net.server.GetMoneyServer;
import com.peoit.android.studentuga.ui.adapter.GetMoneyAdapter;
import com.peoit.android.studentuga.uitl.MyLogger;
import com.peoit.android.studentuga.view.list.SwipyRefreshLayout;
import com.peoit.android.studentuga.view.list.swipe.SwipeMenu;
import com.peoit.android.studentuga.view.list.swipe.SwipeMenuCreator;
import com.peoit.android.studentuga.view.list.swipe.SwipeMenuItem;
import com.peoit.android.studentuga.view.list.swipe.SwipeMenuLayout;
import com.peoit.android.studentuga.view.list.swipe.SwipeMenuListView;

/**
 * 我的导师or学生界面
 * <p>
 * author:libo
 * time:2015/8/4
 * E-mail:boli_android@163.com
 * last: ...
 */
public class MyGetMoneyListActivity extends BaseActivity {

    private SwipyRefreshLayout pullLayout;
    private SwipeMenuListView lvmenuInfo;
    private GetMoneyListServer mGetMoneyServer;
    private GetMoneyAdapter adapter;

    private void assignViews() {
        pullLayout = (SwipyRefreshLayout) findViewById(R.id.pull_layout);
        lvmenuInfo = (SwipeMenuListView) findViewById(R.id.lvmenu_info);
    }

    public static void startThisActivity(Activity mAc) {
        if (CommonUtil.isLoginAndToLogin(mAc, false)) {
            Intent intent = new Intent(mAc, MyGetMoneyListActivity.class);
            mAc.startActivity(intent);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_my_msg);
    }

    @Override
    public void initData() {
        mGetMoneyServer = new GetMoneyListServer(mAct);
    }

    @Override
    public void initView() {
        assignViews();
        SwipeMenuCreator creator = new SwipeMenuCreator() {
            @Override
            public void create(SwipeMenu menu) {
                SwipeMenuItem menuItem = new SwipeMenuItem(mAct);
                menuItem.setBackground(mAct.getResources().getDrawable(R.drawable.draw_shop_close_sel));
                menuItem.setTitle("取消");
                menuItem.setWidth(CommonUtil.dip2px(96));
                menuItem.setTitleColor(mAct.getResources().getColor(R.color.white_1));
                menuItem.setTitleSize(16);
                menu.addMenuItem(menuItem);
            }
        };
        lvmenuInfo.setMenuCreator(creator);
        getToolbar().setBack().setTvTitle("提现记录");
        adapter = mGetMoneyServer.getAdapter();
        lvmenuInfo.setAdapter(adapter);
        mGetMoneyServer.requestGetMoneyList("");
    }

    @Override
    public void initListener() {
        pullLayout.setOnRefreshListener(mGetMoneyServer);
        lvmenuInfo.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public void onMenuItemClick(final int position, SwipeMenu menu, int index) {
                new GetMoneyServer(mAct).requestCancel(adapter.getItem(position).getId() + "", new BaseServer.OnSuccessCallBack() {
                    @Override
                    public void onSuccess(int mark) {
                        if (adapter.getCount() == 1) {
                            getUIShowPresenter().showError(R.drawable.nomingxi, "暂无提现信息");
                            getUIShowPresenter().setOnReloadListener(new BaseUIShow.OnReloadListener() {
                                @Override
                                public void onReload() {
                                    mGetMoneyServer.requestGetMoneyList("");
                                }
                            });
                        }
                        adapter.removeDataItem(position);
                    }
                });
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
        lvmenuInfo.setOnSrcollListener(new SwipeMenuLayout.OnSrcollChangeListener() {
            @Override
            public void onSrcollChange(View root, int currentProgess, int totalProgress) {

            }
        });
    }
}
