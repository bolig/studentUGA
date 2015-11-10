package com.peoit.android.studentuga.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import com.peoit.android.peoit_lib.base.BaseActivity;
import com.peoit.android.studentuga.R;
import com.peoit.android.studentuga.net.server.DaiXiaoServer;
import com.peoit.android.studentuga.view.list.SwipyRefreshLayout;

public class DaiXiaoActivity extends BaseActivity {

    private SwipyRefreshLayout pullLayout;
    private ListView lvmenuInfo;
    private DaiXiaoServer mDaiXiaoServer;

    public static void startThisActivity(Activity mAc) {
        Intent intent = new Intent(mAc, DaiXiaoActivity.class);
        mAc.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_dai_xiao);
    }

    @Override
    public void initData() {
        mDaiXiaoServer = new DaiXiaoServer(this);
    }

    @Override
    public void initView() {
        getToolbar().setBack().setTvTitle("代销商品");
        pullLayout = (SwipyRefreshLayout) findViewById(R.id.pull_layout);
        lvmenuInfo = (ListView) findViewById(R.id.lv_info);
        lvmenuInfo.setAdapter(mDaiXiaoServer.getGoodsListAdapter());
        mDaiXiaoServer.loadGoodsList();
    }

    @Override
    public void initListener() {
        pullLayout.setOnRefreshListener(mDaiXiaoServer);
    }
}
