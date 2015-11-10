package com.peoit.android.studentuga.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.peoit.android.peoit_lib.base.BaseActivity;
import com.peoit.android.studentuga.R;
import com.peoit.android.studentuga.config.ExpressConstants;
import com.peoit.android.studentuga.entity.ExpressInfo;
import com.peoit.android.studentuga.ui.adapter.ExpressAdapter;

import java.lang.reflect.Type;
import java.util.List;

public class ExpressActivity extends BaseActivity {
    private ListView lvExpress;
    private List<ExpressInfo> mExpresses;
    private Handler mHanlder = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            getUIShowPresenter().showData();
            adapter.upDateList(mExpresses);
        }
    };
    private ExpressAdapter adapter;

    private void assignViews() {
        lvExpress = (ListView) findViewById(R.id.lv_express);
    }

    public static void startThisActivity(Activity mAc) {
        Intent intent = new Intent(mAc, ExpressActivity.class);
        mAc.startActivityForResult(intent, 1);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_express);
    }

    @Override
    public void initData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Gson gson = new Gson();
                Type type = new TypeToken<List<ExpressInfo>>() {
                }.getType();
                mExpresses = gson.fromJson(ExpressConstants.jsonForExpress, type);
                mHanlder.sendEmptyMessage(0);
            }
        }).start();
        adapter = new ExpressAdapter(mAct);
    }

    @Override
    public void initView() {
        assignViews();
        getToolbar().setBack().setTvTitle("选择快递");
        lvExpress.setAdapter(adapter);
        getUIShowPresenter().showLoading();
    }

    @Override
    public void initListener() {

    }
}
