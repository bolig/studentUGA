package com.peoit.android.studentuga.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.peoit.android.peoit_lib.base.BaseActivity;
import com.peoit.android.studentuga.R;
import com.peoit.android.studentuga.config.CommonUtil;
import com.peoit.android.studentuga.net.server.LookAndSayServer;
import com.peoit.android.studentuga.ui.adapter.CommonAdapter;
import com.peoit.android.studentuga.uitl.MyLogger;
import com.peoit.android.studentuga.view.list.SwipyRefreshLayout;
import com.peoit.android.studentuga.view.list.swipe.SwipeMenu;
import com.peoit.android.studentuga.view.list.swipe.SwipeMenuLayout;
import com.peoit.android.studentuga.view.list.swipe.SwipeMenuListView;

public class MyLookAndSayActivity extends BaseActivity {

    private SwipyRefreshLayout pullLayout;
    private SwipeMenuListView lvmenuInfo;
    private CommonAdapter mCommonAdapter;
    private LookAndSayServer mLookAndSayServer;

    private void assignViews() {
        pullLayout = (SwipyRefreshLayout) findViewById(R.id.pull_layout);
        lvmenuInfo = (SwipeMenuListView) findViewById(R.id.lvmenu_info);
    }

    public static void startThisActivity(Activity mAc) {
        if (CommonUtil.isLoginAndToLogin(mAc, false)) {
            mAc.startActivity(new Intent(mAc, MyLookAndSayActivity.class));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_my_msg);
    }

    @Override
    public void initData() {
        mLookAndSayServer = new LookAndSayServer(mAct);
    }

    @Override
    public void initView() {
        assignViews();
        getToolbar().setBack().setTvTitle("我的动态").setIvR(R.drawable.ic_add, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddCommonActivity.startThisActivity(mAct);
            }
        });
        mCommonAdapter = mLookAndSayServer.getAdapter();
        lvmenuInfo.setAdapter(mCommonAdapter);
        mLookAndSayServer.requestLookAndSayList(CommonUtil.currentUser.getId() + "");
    }

    @Override
    public void initListener() {
        pullLayout.setOnRefreshListener(mLookAndSayServer);
//            pullLayout.setOnRefreshListener(new SwipyRefreshLayout.OnRefreshListener() {
//                @Override
//                public void onRefresh(SwipyRefreshLayout layout, SwipyRefreshLayoutDirection direction) {
//                    switch (direction) {
//                        case TOP:
//                            mCommonServer.requestGoodsCommon(mGoodsId, "1", "20", new CommonServer.OnCommonCallBack() {
//                                @Override
//                                public void onCommon(int mark, List<CommonInfo> infos) {
//                                    pullLayout.setRefreshing(false);
//                                    switch (mark) {
//                                        case 0:
//                                            showToast("数据暂无更新");
//                                            break;
//                                        case 1:
//                                            mCommonAdapter.addHeadDataList(infos);
//                                            break;
//                                        case -1:
//                                            showToast("数据加载失败");
//                                            break;
//                                    }
//                                }
//                            });
//                            break;
//                        case BOTTOM:
//                            mCommonServer.requestGoodsCommon(mGoodsId, mNo + "", "20", new CommonServer.OnCommonCallBack() {
//                                @Override
//                                public void onCommon(int mark, List<CommonInfo> infos) {
//                                    pullLayout.setRefreshing(false);
//                                    switch (mark) {
//                                        case 0:
//                                            showToast("暂无评论了");
//                                            break;
//                                        case 1:
//                                            mCommonAdapter.addFootDataList(infos);
//                                            mCommonAdapter.addFootDataList(infos);
//                                            mNo++;
//                                            break;
//                                        case -1:
//                                            showToast("数据加载失败");
//                                            break;
//                                    }
//                                }
//                            });
//                            break;
//                    }
//                }
//            });
        lvmenuInfo.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public void onMenuItemClick(int position, SwipeMenu menu, int index) {

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
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == 3) {
            mLookAndSayServer.requestLookAndSayList(CommonUtil.currentUser.getId() + "");
        }
    }
}
