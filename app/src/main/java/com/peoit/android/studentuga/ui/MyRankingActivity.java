package com.peoit.android.studentuga.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.peoit.android.peoit_lib.base.BaseActivity;
import com.peoit.android.studentuga.R;
import com.peoit.android.studentuga.config.CommonUtil;
import com.peoit.android.studentuga.entity.RankingInfo;
import com.peoit.android.studentuga.net.server.MyRankingServer;
import com.peoit.android.studentuga.net.server.RankingServer;
import com.peoit.android.studentuga.ui.showUI.SimpleShowUiShow;
import com.peoit.android.studentuga.view.list.SwipyRefreshLayout;

/**
 * 我的排名界面
 * <p/>
 * author:libo
 * time:2015/8/4
 * E-mail:boli_android@163.com
 * last: ...
 */
public class MyRankingActivity extends BaseActivity implements View.OnClickListener {

    private EditText etSreach;
    private LinearLayout llRankingItem1;
    private LinearLayout llRankingItem2;
    private LinearLayout llRankingItem3;
    private LinearLayout llTabItem1;
    private TextView tvTabItem1;
    private LinearLayout llTabItem2;
    private TextView tvTabItem2;
    private LinearLayout llTabItem3;
    private TextView tvTabItem3;
    private SwipyRefreshLayout pullLayout;
    private ListView lvRanking;
    private FrameLayout flLoadRoot;

    private TextView tvItem1;
    private TextView tvItem2;
    private TextView tvItem3;
    private TextView tvItem4;

    private SimpleShowUiShow mShowUI;

    private int mTabWidth;
    private int mRankingLayoutWidth;
    private Item lastItem;
    private View headerView;
    private int mTextViewWidth;
    private MyRankingServer mRankingServer;

    private TextView tvMyRankingAtAll;
    private TextView tvMyRankingAtArea;
    private TextView tvMyRankingAtSchool;
    private RankingServer mRankingsServer;

    private void assignViews() {

        etSreach = (EditText) findViewById(R.id.et_sreach);

        llRankingItem1 = (LinearLayout) findViewById(R.id.ll_ranking_item1);
        llRankingItem2 = (LinearLayout) findViewById(R.id.ll_ranking_item2);
        llRankingItem3 = (LinearLayout) findViewById(R.id.ll_ranking_item3);

        llTabItem1 = (LinearLayout) findViewById(R.id.ll_tab_item1);
        llTabItem2 = (LinearLayout) findViewById(R.id.ll_tab_item2);
        llTabItem3 = (LinearLayout) findViewById(R.id.ll_tab_item3);

        tvTabItem1 = (TextView) findViewById(R.id.tv_tab_item1);
        tvTabItem2 = (TextView) findViewById(R.id.tv_tab_item2);
        tvTabItem3 = (TextView) findViewById(R.id.tv_tab_item3);

        tvMyRankingAtAll = (TextView) findViewById(R.id.tv_myRankingAtAll);
        tvMyRankingAtArea = (TextView) findViewById(R.id.tv_myRankingAtArea);
        tvMyRankingAtSchool = (TextView) findViewById(R.id.tv_myRankingAtSchool);

        pullLayout = (SwipyRefreshLayout) findViewById(R.id.pull_layout);
        lvRanking = (ListView) findViewById(R.id.lv_ranking);

        flLoadRoot = (FrameLayout) findViewById(R.id.fl_load_root);
    }

    public static void startThisActivity(Activity mAc) {
        if (CommonUtil.isLoginAndToLogin(mAc, false)) {
            Intent intent = new Intent(mAc, MyRankingActivity.class);
            mAc.startActivity(intent);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_my_ranking);
    }

    @Override
    public void initData() {
        mTabWidth = (CommonUtil.w_screeen - CommonUtil.dip2px(2)) / 3;
        mRankingLayoutWidth = (CommonUtil.w_screeen - CommonUtil.dip2px(16)) / 3;
        mTextViewWidth = CommonUtil.w_screeen / 4;

        mRankingServer = new MyRankingServer(this);
        mRankingsServer = new RankingServer(this);
    }

    @Override
    public void initView() {
        assignViews();
        addHeaderView();
        getToolbar().setBack().setTvTitle("我的排名");
        mShowUI = new SimpleShowUiShow(mAct);
        mShowUI.setRootView(flLoadRoot);

        setLayoutWidth(llTabItem1, mTabWidth);
        setLayoutWidth(llTabItem2, mTabWidth);
        setLayoutWidth(llTabItem3, mTabWidth);

        setLayoutWidth(llRankingItem1, mRankingLayoutWidth);
        setLayoutWidth(llRankingItem2, mRankingLayoutWidth);
        setLayoutWidth(llRankingItem3, mRankingLayoutWidth);

        lvRanking.setAdapter(mRankingsServer.getAdapter());

        changeUIShow(Item.item1);

        mRankingServer.requestRanking(CommonUtil.currentUser.getId() + "", new MyRankingServer.OnRankingCallBack() {
            @Override
            public void onCallBack(RankingInfo info) {
                if (info != null) {
                    tvMyRankingAtAll.setText(info.getTotlTop() + "");
                    tvMyRankingAtArea.setText(info.getAreaTop() + "");
                    tvMyRankingAtSchool.setText(info.getShcoolTop() + "");
                } else {
                    tvMyRankingAtAll.setText("暂无排名");
                    tvMyRankingAtArea.setText("暂无排名");
                    tvMyRankingAtSchool.setText("暂无排名");
                }
            }
        });
    }


    private void addHeaderView() {
        headerView = getLayoutInflater().inflate(R.layout.act_my_ranking_list_header, null);

        tvItem1 = (TextView) headerView.findViewById(R.id.tv_item1);
        tvItem2 = (TextView) headerView.findViewById(R.id.tv_item2);
        tvItem3 = (TextView) headerView.findViewById(R.id.tv_item3);
        tvItem4 = (TextView) headerView.findViewById(R.id.tv_item4);

        setTextViewWidth(tvItem1);
        setTextViewWidth(tvItem2);
        setTextViewWidth(tvItem3);
        setTextViewWidth(tvItem4);

        lvRanking.addHeaderView(headerView);
    }

    public void setTextViewWidth(TextView tv) {
        ViewGroup.LayoutParams params = tv.getLayoutParams();
        params.width = mTextViewWidth;
    }

    private void setLayoutWidth(LinearLayout ll, int width) {
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) ll.getLayoutParams();
        params.width = width;
    }

    @Override
    public void initListener() {
        llTabItem1.setOnClickListener(this);
        llTabItem2.setOnClickListener(this);
        llTabItem3.setOnClickListener(this);

        pullLayout.setOnRefreshListener(mRankingsServer);
    }

    /**
     * 根据当前选项， 改变界面显示
     *
     * @param item
     */
    private void changeUIShow(Item item) {
        if (lastItem == item)
            return;

        llTabItem1.setSelected(false);
        llTabItem2.setSelected(false);
        llTabItem3.setSelected(false);

        switch (item) {
            case item1:
                mRankingsServer.requestHomeRanking("", mShowUI);
                llTabItem1.setSelected(true);
                break;
            case item2:
                mRankingsServer.requestHomeRanking(CommonUtil.currentUser.getAreaid(), mShowUI);
                llTabItem2.setSelected(true);
                break;
            case item3:
                mRankingsServer.requestHomeRanking(CommonUtil.currentUser.getSchoolid(), mShowUI);
                llTabItem3.setSelected(true);
                break;
        }
        lastItem = item;
    }

    @Override
    public void onClick(View v) {
        if (v == llTabItem1) {
            changeUIShow(Item.item1);
        } else if (v == llTabItem2) {
            changeUIShow(Item.item2);
        } else if (v == llTabItem3) {
            changeUIShow(Item.item3);
        }
    }

    private enum Item {
        item1,
        item2,
        item3
    }
}
