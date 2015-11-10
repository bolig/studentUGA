package com.peoit.android.studentuga.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import com.peoit.android.peoit_lib.ActivityBase;
import com.peoit.android.peoit_lib.base.BasePagerFragment;
import com.peoit.android.studentuga.R;
import com.peoit.android.studentuga.net.BaseServer;
import com.peoit.android.studentuga.net.server.UserGoodsServer;
import com.peoit.android.studentuga.ui.showUI.SimpleShowUiShow;
import com.peoit.android.studentuga.view.NoSrcollListView;
import com.peoit.android.studentuga.view.list.SwipyRefreshLayout;

/**
 * author:libo
 * time:2015/11/3
 * E-mail:boli_android@163.com
 * last: ...
 */
public class LiuCommonGoodsFragment extends BasePagerFragment {
    private SwipyRefreshLayout pullLayout;
    private NoSrcollListView lvInfo;
    private UserGoodsServer mUserGoodsServer;
    private FrameLayout fl_root;
    private SimpleShowUiShow mUIShow;
    private String mPhone;

    private void assignViews() {
        pullLayout = (SwipyRefreshLayout) findViewById(R.id.pull_layout);
        lvInfo = (NoSrcollListView) findViewById(R.id.lv_info);
        fl_root = (FrameLayout) findViewById(R.id.fl_root);
    }

    public static LiuCommonGoodsFragment newInstance(String phone) {
        LiuCommonGoodsFragment fragment = new LiuCommonGoodsFragment();
        Bundle bundle = new Bundle();
        bundle.putString("phone", phone);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View createView(LayoutInflater inflater, Bundle savedInstanceState) {
        return View.inflate(getActivity(), R.layout.frag_liu_common_goods, null);
    }

    @Override
    public void initData() {
        mUserGoodsServer = new UserGoodsServer((ActivityBase) getActivity());
        if (getArguments() != null) {
            mPhone = getArguments().getString("phone");
        }
    }

    @Override
    public void initView() {
        assignViews();
        lvInfo.setAdapter(mUserGoodsServer.getGoodsListAdapter());
        mUIShow = new SimpleShowUiShow(getActivity(), true);
        mUIShow.setRootView(fl_root);
        mUIShow.setIvErrorImg(R.drawable.noproduct);
        mUIShow.setTvErrorMsg("暂无商品信息");
        mUIShow.setTvReloadListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                load();
            }
        });
        load();
    }

    private void load() {
        mUserGoodsServer.requestUserGoodsList(mPhone, mUIShow, new BaseServer.OnSuccessCallBack() {
            @Override
            public void onSuccess(int mark) {
                mOnFragmentCallBack.onFragmentCallBack(0, null);
            }
        });
    }

    @Override
    public void initListener() {
        pullLayout.setOnRefreshListener(mUserGoodsServer);
    }

    public void setOnTouch(MotionEvent event){
        getView().dispatchTouchEvent(event);
    }
}
