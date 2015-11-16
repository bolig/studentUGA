package com.peoit.android.studentuga.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.peoit.android.peoit_lib.base.BaseActivity;
import com.peoit.android.studentuga.R;
import com.peoit.android.studentuga.config.CommonUtil;
import com.peoit.android.studentuga.net.server.TeacherOrStudentBindListServer;
import com.peoit.android.studentuga.net.server.TeacherOrStudentBindServer;
import com.peoit.android.studentuga.ui.adapter.MyTeacherAdapter;
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
public class MyTeacherBindActivity extends BaseActivity {

    private SwipyRefreshLayout pullLayout;
    private SwipeMenuListView lvmenuInfo;
    private MyTeacherAdapter adapter;
    private TeacherOrStudentBindListServer mBindListServer;
    private EditText etSearch;
    private String mSearch;


    private void assignViews() {
        pullLayout = (SwipyRefreshLayout) findViewById(R.id.pull_layout);
        lvmenuInfo = (SwipeMenuListView) findViewById(R.id.lvmenu_info);
    }

    public static void startThisActivity(Activity mAc) {
        if (CommonUtil.isLoginAndToLogin(mAc, false)) {
            Intent intent = new Intent(mAc, MyTeacherBindActivity.class);
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
        mBindListServer = new TeacherOrStudentBindListServer(mAct);
    }

    @Override
    public void initView() {
        assignViews();
        SwipeMenuCreator creator = new SwipeMenuCreator() {
            @Override
            public void create(SwipeMenu menu) {
                SwipeMenuItem menuItem = new SwipeMenuItem(mAct);
                menuItem.setBackground(mAct.getResources().getDrawable(R.drawable.draw_shop_close_sel));
                menuItem.setTitle("绑定");
                menuItem.setWidth(CommonUtil.dip2px(72));
                menuItem.setTitleColor(mAct.getResources().getColor(R.color.white_1));
                menuItem.setTitleSize(16);
                menu.addMenuItem(menuItem);
            }
        };
        lvmenuInfo.setMenuCreator(creator);
//        String title = "";
//        switch (CommonUtil.getUserType()) {
//            case daoShi:
//                title = "绑定学生";
//                break;
//            case xueSheng:
//                title = "绑定导师";
//                break;
//        }
//        getToolbar().setBack().setTvTitle(title);
        etSearch = getToolbar().setTvR("取消", new OnClick()).showSearch();
        etSearch.setHint("请输入要查询人的姓名");
        adapter = mBindListServer.getAdapter();
        lvmenuInfo.setAdapter(adapter);
        mBindListServer.requestBindList("", "");
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
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 0){
                    getToolbar().setTvR("取消", new OnClick());
                } else {
                    getToolbar().setTvR("搜索", new OnClick());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private class OnClick implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            mSearch = etSearch.getText().toString();
            if (TextUtils.isEmpty(mSearch)) {
                finish();
            } else {
                mBindListServer.requestBindList("", mSearch);
            }
        }
    }
}
