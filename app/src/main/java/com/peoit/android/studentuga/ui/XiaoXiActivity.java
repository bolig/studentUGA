package com.peoit.android.studentuga.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import com.peoit.android.peoit_lib.base.BaseActivity;
import com.peoit.android.studentuga.R;
import com.peoit.android.studentuga.net.server.XiaoXiServer;
import com.peoit.android.studentuga.view.list.SwipyRefreshLayout;

public class XiaoXiActivity extends BaseActivity {
    private SwipyRefreshLayout pullLayout;
    private ListView lvInfo;
    private XiaoXiServer mXiaoXiServer;

    private void assignViews() {
        pullLayout = (SwipyRefreshLayout) findViewById(R.id.pull_layout);
        lvInfo = (ListView) findViewById(R.id.lv_info);
    }

    public static void startThisActivity(Activity mAc) {
        Intent intent = new Intent(mAc, XiaoXiActivity.class);
        mAc.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_xiao_xi);
    }

    @Override
    public void initData() {
        mXiaoXiServer = new XiaoXiServer(mAct);
    }

    @Override
    public void initView() {
        assignViews();
        getToolbar().setBack().setTvTitle("通知消息");
        lvInfo.setAdapter(mXiaoXiServer.getAdapter());
        mXiaoXiServer.requestXiaoXi();
    }

    @Override
    public void initListener() {
        pullLayout.setOnRefreshListener(mXiaoXiServer);
    }
}
