package com.peoit.android.studentuga.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.peoit.android.peoit_lib.base.BasePagerFragment;
import com.peoit.android.peoit_lib.view.CustomActionBar;
import com.peoit.android.studentuga.R;
import com.peoit.android.studentuga.net.server.GoodsServer;
import com.peoit.android.studentuga.ui.HomeActivity;
import com.peoit.android.studentuga.ui.showUI.SimpleShowUiShow;

/**
 * author:libo
 * time:2015/10/10
 * E-mail:boli_android@163.com
 * last: ...
 */
public class HomeSortFragment1 extends BasePagerFragment {
    private EditText etSreach;
    private ListView lvGoodsSort;
    private CustomActionBar actionBar;
    private FrameLayout fl_root;

    private SimpleShowUiShow mShowUIShow;
    private GoodsServer mGoodsServer;

    private void assignViews() {
        actionBar = (CustomActionBar) findViewById(R.id.actionbar);
        etSreach = (EditText) findViewById(R.id.et_sreach);
        lvGoodsSort = (ListView) findViewById(R.id.lv_goods_sort);
        fl_root = (FrameLayout) findViewById(R.id.fl_root);
    }

    @Override
    public View createView(LayoutInflater inflater, Bundle savedInstanceState) {
        return View.inflate(getActivity(), R.layout.frag_sort1, null);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initView() {
        assignViews();
        actionBar.setTvTitle("商品分类");

        mShowUIShow = new SimpleShowUiShow(getActivity());
        mShowUIShow.setRootView(fl_root);
        mShowUIShow.setIvErrorImg(R.drawable.nodeliveryaddress);
        mShowUIShow.setTvErrorMsg("加载失败");
        mShowUIShow.setTvReload("重新加载", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mGoodsServer.loadGoodsType(mShowUIShow);
            }
        });
        mGoodsServer = new GoodsServer((HomeActivity) getActivity());
        lvGoodsSort.setAdapter(mGoodsServer.getSortTypeAdapter());
        mGoodsServer.loadGoodsType(mShowUIShow);
    }

    @Override
    public void initListener() {

    }
}
