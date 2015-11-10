package com.peoit.android.studentuga.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.peoit.android.peoit_lib.base.BaseActivity;
import com.peoit.android.studentuga.R;
import com.peoit.android.studentuga.config.CommonUtil;
import com.peoit.android.studentuga.entity.CommonInfo;
import com.peoit.android.studentuga.net.server.CommonServer;
import com.peoit.android.studentuga.net.server.MyLiuCommonServer;
import com.peoit.android.studentuga.ui.adapter.CommonAdapter;
import com.peoit.android.studentuga.uitl.MyLogger;
import com.peoit.android.studentuga.view.list.SwipyRefreshLayout;
import com.peoit.android.studentuga.view.list.SwipyRefreshLayoutDirection;
import com.peoit.android.studentuga.view.list.swipe.SwipeMenu;
import com.peoit.android.studentuga.view.list.swipe.SwipeMenuLayout;
import com.peoit.android.studentuga.view.list.swipe.SwipeMenuListView;

import java.util.List;

/**
 * 我的提问界面
 * <p/>
 * author:libo
 * time:2015/8/4
 * E-mail:boli_android@163.com
 * last: ...
 */
public class MyMsgActivity extends BaseActivity {
    private SwipyRefreshLayout pullLayout;
    private SwipeMenuListView lvmenuInfo;
    private MyLiuCommonServer mMyLiuCommonServer;
    private String mGoodsId;
    private CommonServer mCommonServer;
    private CommonAdapter mCommonAdapter;

    private void assignViews() {
        pullLayout = (SwipyRefreshLayout) findViewById(R.id.pull_layout);
        lvmenuInfo = (SwipeMenuListView) findViewById(R.id.lvmenu_info);
    }

    public static void startThisActivity(Activity mAc) {
        if (CommonUtil.isLoginAndToLogin(mAc, false)) {
            mAc.startActivity(new Intent(mAc, MyMsgActivity.class));
        }
    }

    public static void startThisActivity(Activity mAc, String goodsId) {
        mAc.startActivity(new Intent(mAc, MyMsgActivity.class).putExtra("id", goodsId));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_my_msg);
    }

    @Override
    public void initData() {
        mGoodsId = getIntent().getStringExtra("id");
        mMyLiuCommonServer = new MyLiuCommonServer(this);
        mCommonServer = new CommonServer(this);
    }

    private boolean isLiu() {
        return TextUtils.isEmpty(mGoodsId);
    }

    @Override
    public void initView() {
        assignViews();
        getToolbar().setBack().setTvTitle(isLiu() ? "我的留言" : "商品评论");
//        SwipeMenuCreator creator = new SwipeMenuCreator() {
//            @Override
//            public void create(SwipeMenu menu) {
//                SwipeMenuItem menuItem = new SwipeMenuItem(mAct);
//                menuItem.setBackground(mAct.getResources().getDrawable(R.drawable.draw_shop_close_sel));
//                menuItem.setTitle("删除");
//                menuItem.setWidth(CommonUtil.dip2px(80));
//                menuItem.setTitleColor(mAct.getResources().getColor(R.color.white_1));
//                menuItem.setTitleSize(16);
//                menu.addMenuItem(menuItem);
//            }
//        };
//        lvmenuInfo.setMenuCreator(creator);
        if (isLiu()) {
            lvmenuInfo.setAdapter(mMyLiuCommonServer.getAdapter());
            mMyLiuCommonServer.requestMyLiuCommon();
        } else {
            mCommonAdapter = new CommonAdapter(mAct);
            lvmenuInfo.setAdapter(mCommonAdapter);
            mCommonServer.requestGoodsCommon(mGoodsId, "1", "20", new CommonServer.OnCommonCallBack() {
                @Override
                public void onCommon(int mark, List<CommonInfo> infos) {
                    pullLayout.setRefreshing(false);
                    switch (mark) {
                        case 0:
                            showToast("暂无更多评论了");
                            break;
                        case 1:
                            mCommonAdapter.upDateList(infos);
                            mNo ++;
                            break;
                        case -1:
                            showToast("数据加载失败");
                            break;
                    }
                }
            });
        }
    }

    private int mNo = 1;

    @Override
    public void initListener() {
        if (isLiu()) {
            pullLayout.setOnRefreshListener(mMyLiuCommonServer);
        } else {
            pullLayout.setOnRefreshListener(new SwipyRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh(SwipyRefreshLayout layout, SwipyRefreshLayoutDirection direction) {
                    switch (direction) {
                        case TOP:
                            mCommonServer.requestGoodsCommon(mGoodsId, "1", "20", new CommonServer.OnCommonCallBack() {
                                @Override
                                public void onCommon(int mark, List<CommonInfo> infos) {
                                    pullLayout.setRefreshing(false);
                                    switch (mark) {
                                        case 0:
                                            showToast("数据暂无更新");
                                            break;
                                        case 1:
                                            mCommonAdapter.addHeadDataList(infos);
                                            break;
                                        case -1:
                                            showToast("数据加载失败");
                                            break;
                                    }
                                }
                            });
                            break;
                        case BOTTOM:
                            mCommonServer.requestGoodsCommon(mGoodsId, mNo + "", "20", new CommonServer.OnCommonCallBack() {
                                @Override
                                public void onCommon(int mark, List<CommonInfo> infos) {
                                    pullLayout.setRefreshing(false);
                                    switch (mark) {
                                        case 0:
                                            showToast("暂无评论了");
                                            break;
                                        case 1:
                                            mCommonAdapter.addFootDataList(infos);                                            mCommonAdapter.addFootDataList(infos);
                                            mNo ++;
                                            break;
                                        case -1:
                                            showToast("数据加载失败");
                                            break;
                                    }
                                }
                            });
                            break;
                    }
                }
            });
        }
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
}
