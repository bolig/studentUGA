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
import com.peoit.android.studentuga.net.server.LiuCommonServer;
import com.peoit.android.studentuga.ui.showUI.SimpleShowUiShow;
import com.peoit.android.studentuga.view.NoSrcollListView;
import com.peoit.android.studentuga.view.list.SwipyRefreshLayout;

/**
 * author:libo
 * time:2015/11/3
 * E-mail:boli_android@163.com
 * last: ...
 */
public class LiuCommonCommonsFragment extends BasePagerFragment {
    private SwipyRefreshLayout pullLayout;
    private NoSrcollListView lvInfo;
    private FrameLayout fl_root;
    private SimpleShowUiShow mUIShow;
    private String mPhone;
    private LiuCommonServer mLiuCommonServer;

    private void assignViews() {
        pullLayout = (SwipyRefreshLayout) findViewById(R.id.pull_layout);
        lvInfo = (NoSrcollListView) findViewById(R.id.lv_info);
        fl_root = (FrameLayout) findViewById(R.id.fl_root);
    }

    public static LiuCommonCommonsFragment newInstance(String uid) {
        LiuCommonCommonsFragment fragment = new LiuCommonCommonsFragment();
        Bundle bundle = new Bundle();
        bundle.putString("uid", uid);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View createView(LayoutInflater inflater, Bundle savedInstanceState) {
        return View.inflate(getActivity(), R.layout.frag_liu_common_goods, null);
    }

    @Override
    public void initData() {
        mLiuCommonServer = new LiuCommonServer((ActivityBase) getActivity());
        if (getArguments() != null) {
            mPhone = getArguments().getString("uid");
        }
    }

    @Override
    public void initView() {
        assignViews();
        lvInfo.setAdapter(mLiuCommonServer.getAdapter());
        mUIShow = new SimpleShowUiShow(getActivity(), true);
        mUIShow.setRootView(fl_root);
        mUIShow.setIvErrorImg(R.drawable.nomessage);
        mUIShow.setTvErrorMsg("暂无留言信息");
        mUIShow.setTvReloadListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                load();
            }
        });
        load();
    }

    private void load() {
        mLiuCommonServer.requestLiuCommon(mPhone + "", mUIShow, new BaseServer.OnSuccessCallBack() {
            @Override
            public void onSuccess(int mark) {
                mOnFragmentCallBack.onFragmentCallBack(0, null);
            }
        });
    }

    @Override
    public void initListener() {
        pullLayout.setOnRefreshListener(mLiuCommonServer);
    }

    public void setOnTouch(MotionEvent event){
        getView().dispatchTouchEvent(event);
    }
}
