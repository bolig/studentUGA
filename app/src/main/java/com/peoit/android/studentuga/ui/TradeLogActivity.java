package com.peoit.android.studentuga.ui;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.ListView;

import com.peoit.android.peoit_lib.base.BaseActivity;
import com.peoit.android.studentuga.R;
import com.peoit.android.studentuga.config.CommonUtil;
import com.peoit.android.studentuga.net.server.TradeLogServer;
import com.peoit.android.studentuga.view.list.SwipyRefreshLayout;

/**
 * 交易流水
 * <p/>
 * author:libo
 * time:2015/8/4
 * E-mail:boli_android@163.com
 * last: ...
 */
public class TradeLogActivity extends BaseActivity {
    private SwipyRefreshLayout pullLayout;
    private ListView lvInfo;
    private TradeLogServer mTradeLogServer;

    private void assignViews() {
        pullLayout = (SwipyRefreshLayout) findViewById(R.id.pull_layout);
        lvInfo = (ListView) findViewById(R.id.lv_info);
    }

    public static void startThisActivity(Activity mAc) {
        if (CommonUtil.isLoginAndToLogin(mAc, false)) {
            Intent intent = new Intent(mAc, TradeLogActivity.class);
            mAc.startActivity(intent);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_pull_list);
    }

    @Override
    public void initData() {
        mTradeLogServer = new TradeLogServer(this);
    }

    @Override
    public void initView() {
        assignViews();
        getToolbar().setBack().setTvTitle("交易流水");
        lvInfo.setDivider(new ColorDrawable(getResources().getColor(R.color.md_blue_grey_300)));
        lvInfo.setDividerHeight(1);
        lvInfo.setAdapter(mTradeLogServer.getAdapter());
        mTradeLogServer.requestTradeLogList();
    }

    @Override
    public void initListener() {
        pullLayout.setOnRefreshListener(mTradeLogServer);
    }
}
