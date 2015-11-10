package com.peoit.android.studentuga.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.peoit.android.peoit_lib.base.BaseActivity;
import com.peoit.android.studentuga.R;
import com.peoit.android.studentuga.net.server.GoodsServer;
import com.peoit.android.studentuga.ui.showUI.SimpleShowUiShow;
import com.peoit.android.studentuga.view.list.SwipyRefreshLayout;
import com.peoit.android.studentuga.view.list.swipe.SwipeMenuListView;

public class GoodsSortActivity extends BaseActivity implements View.OnClickListener {
    private EditText etSreach;
    private LinearLayout llTabItem1;
    private LinearLayout llTabItem2;
    private LinearLayout llTabItem3;
    private LinearLayout llTabItem4;
    private SwipyRefreshLayout pullLayout;
    private SwipeMenuListView lvmenuInfo;
    private FrameLayout flRoot;
    private SimpleShowUiShow mShowUIShow;
    private GoodsServer mGoodsServer;
    private TabType lastTabType;
    private String id;

    private void assignViews() {
        etSreach = (EditText) findViewById(R.id.et_sreach);
        llTabItem1 = (LinearLayout) findViewById(R.id.ll_tab_item1);
        llTabItem2 = (LinearLayout) findViewById(R.id.ll_tab_item2);
        llTabItem3 = (LinearLayout) findViewById(R.id.ll_tab_item3);
        llTabItem4 = (LinearLayout) findViewById(R.id.ll_tab_item4);
        pullLayout = (SwipyRefreshLayout) findViewById(R.id.pull_layout);
        lvmenuInfo = (SwipeMenuListView) findViewById(R.id.lvmenu_info);
        flRoot = (FrameLayout) findViewById(R.id.fl_root);
    }

    public static void startThisActivity(Activity mAc, String id) {
        Intent intent = new Intent(mAc, GoodsSortActivity.class);
        Bundle extras = new Bundle();
        extras.putSerializable("id", id);
        intent.putExtras(extras);
        mAc.startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_goods_sort);
    }

    @Override
    public void initData() {
        id = getIntent().getStringExtra("id");
        if (TextUtils.isEmpty(id)) {
            showToast("数据传输错误");
            finish();
            return;
        }
    }

    @Override
    public void initView() {
        assignViews();
        getToolbar().setTvTitle("商品").setBack();
        mShowUIShow = new SimpleShowUiShow(getActivity());
        mShowUIShow.setRootView(flRoot);
        mShowUIShow.setIvErrorImg(R.drawable.noproduct);
        mShowUIShow.setTvErrorMsg("加载失败");
        mShowUIShow.setTvReload("重新加载", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mGoodsServer.loadGoodsList(id, null, null, "0", "1", mShowUIShow);
            }
        });
        mGoodsServer = new GoodsServer(mAct);
        lvmenuInfo.setAdapter(mGoodsServer.getGoodsListAdapter());
        changeUIShow(TabType.tab_item1);
    }

    @Override
    public void initListener() {
        llTabItem1.setOnClickListener(this);
        llTabItem2.setOnClickListener(this);
        llTabItem3.setOnClickListener(this);
        llTabItem4.setOnClickListener(this);

        pullLayout.setOnRefreshListener(mGoodsServer);
    }

    @Override
    public void onClick(View v) {
        if (v == llTabItem1) {
            changeUIShow(TabType.tab_item1);
        } else if (v == llTabItem2) {
            changeUIShow(TabType.tab_item2);
        } else if (v == llTabItem3) {
            changeUIShow(TabType.tab_item3);
        } else if (v == llTabItem4) {
            changeUIShow(TabType.tab_item4);
        }
    }

    public void changeUIShow(TabType tabType) {
        if (tabType == lastTabType) {
            return;
        }
        llTabItem1.setSelected(false);
        llTabItem2.setSelected(false);
        llTabItem3.setSelected(false);
        llTabItem4.setSelected(false);
        switch (tabType) {
            case tab_item1:
                llTabItem1.setSelected(true);
                mGoodsServer.loadGoodsList(id, null, null, "0", "1", mShowUIShow);
                break;
            case tab_item2:
                llTabItem2.setSelected(true);
                mGoodsServer.loadGoodsList(id, null, null, "1", "1", mShowUIShow);
                break;
            case tab_item3:
                llTabItem3.setSelected(true);
                mGoodsServer.loadGoodsList(id + "", null, null, "3", "1", mShowUIShow);
                break;
            case tab_item4:
                llTabItem4.setSelected(true);
                mGoodsServer.loadGoodsList(id + "", null, null, "4", "1", mShowUIShow);
                break;
        }
        lastTabType = tabType;
    }

    public enum TabType {
        tab_item1,
        tab_item2,
        tab_item3,
        tab_item4
    }
}
