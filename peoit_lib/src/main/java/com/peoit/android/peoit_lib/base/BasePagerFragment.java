package com.peoit.android.peoit_lib.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.peoit.android.peoit_lib.FragmentBase;
import com.peoit.android.peoit_lib.callback.OnFragmentCallBack;
import com.peoit.android.peoit_lib.presenter.UIShowPresenter;
import com.peoit.android.peoit_lib.view.CustomActionBar;

/**
 * author:libo
 * time:2015/9/7
 * E-mail:boli_android@163.com
 * last: ...
 */
public abstract class BasePagerFragment extends Fragment implements FragmentBase {

    protected OnFragmentCallBack mOnFragmentCallBack;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof OnFragmentCallBack) {
            mOnFragmentCallBack = (OnFragmentCallBack) activity;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mOnFragmentCallBack = null;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
    }

    @NonNull
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return createView(inflater, savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        initView();
        initListener();
    }

    @Override
    public UIShowPresenter getUIShowPresenter() {
        return ((BaseActivity) getActivity()).getUIShowPresenter();
    }

    @Override
    public void showToast(CharSequence msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public CustomActionBar getToolbar() {
        return ((BaseActivity) getActivity()).getToolbar();
    }

    @Override
    public void onCallBackToActivity(int callBack) {
        mOnFragmentCallBack.onFragmentCallBack(callBack, null);
    }

    @Override
    public void onCallBackToActivity(int callBack, Bundle callBackData) {
        if (mOnFragmentCallBack == null)
            throw new NullPointerException(" @libo activity is not implements OnFragmentCallBack ");
        mOnFragmentCallBack.onFragmentCallBack(callBack, callBackData == null ? new Bundle() : callBackData);
    }

    @Override
    public View findViewById(int resId) {
        return getView().findViewById(resId);
    }
}
