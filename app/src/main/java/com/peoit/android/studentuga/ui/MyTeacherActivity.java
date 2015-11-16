package com.peoit.android.studentuga.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import com.peoit.android.peoit_lib.base.BaseActivity;
import com.peoit.android.studentuga.R;
import com.peoit.android.studentuga.config.CommonUtil;
import com.peoit.android.studentuga.net.server.TeacherOrStudentBindServer;
import com.peoit.android.studentuga.net.server.TeacherOrStudentBindedListServer;
import com.peoit.android.studentuga.ui.adapter.MyTeacherAdapter1;
import com.peoit.android.studentuga.uitl.MyLogger;
import com.peoit.android.studentuga.view.list.SwipyRefreshLayout;
import com.peoit.android.studentuga.view.list.swipe.SwipeMenu;
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
public class MyTeacherActivity extends BaseActivity {

    private SwipyRefreshLayout pullLayout;
    private SwipeMenuListView lvmenuInfo;
    private MyTeacherAdapter1 adapter;
    private TeacherOrStudentBindedListServer mBindListServer;

    private void assignViews() {
        pullLayout = (SwipyRefreshLayout) findViewById(R.id.pull_layout);
        lvmenuInfo = (SwipeMenuListView) findViewById(R.id.lvmenu_info);
    }

    public static void startThisActivity(Activity mAc) {
        if (CommonUtil.isLoginAndToLogin(mAc, false)) {
            Intent intent = new Intent(mAc, MyTeacherActivity.class);
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
        mBindListServer = new TeacherOrStudentBindedListServer(mAct);
    }

    @Override
    public void initView() {
        assignViews();
//        SwipeMenuCreator creator = new SwipeMenuCreator() {
//            @Override
//            public void create(SwipeMenu menu) {
//                SwipeMenuItem menuItem = new SwipeMenuItem(mAct);
//                menuItem.setBackground(mAct.getResources().getDrawable(R.drawable.draw_shop_close_sel));
//                menuItem.setTitle("绑定");
//                menuItem.setWidth(CommonUtil.dip2px(72));
//                menuItem.setTitleColor(mAct.getResources().getColor(R.color.white_1));
//                menuItem.setTitleSize(16);
//                menu.addMenuItem(menuItem);
//            }
//        };
//        lvmenuInfo.setMenuCreator(creator);
        String title = "";
        String tvr = "";
        switch (CommonUtil.getUserType()) {
            case daoShi:
                title = "我的学生";
                tvr = "绑定学生";
                break;
            case xueSheng:
                title = "我的导师";
                tvr = "绑定导师";
                break;
        }
        getToolbar().setBack().setTvTitle(title).setTvR(tvr, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyTeacherBindActivity.startThisActivity(mAct);
            }
        });
        adapter = mBindListServer.getAdapter();
        lvmenuInfo.setAdapter(adapter);
        mBindListServer.requestBindList();
    }

    @Override
    public void initListener() {
        pullLayout.setOnRefreshListener(mBindListServer);
        lvmenuInfo.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public void onMenuItemClick(int position, SwipeMenu menu, int index) {
                new TeacherOrStudentBindServer(mAct).requestBind(adapter.getItem(position).getId() + "");
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
                View iv_next = ((ViewGroup) root).getChildAt(2);
                iv_next.setAlpha(((float) totalProgress - (float) currentProgess) / (float) totalProgress);
            }
        });
    }
}
