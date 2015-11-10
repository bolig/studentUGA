package com.peoit.android.peoit_lib.base;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.peoit.android.peoit_lib.ActivityBase;
import com.peoit.android.peoit_lib.R;
import com.peoit.android.peoit_lib.presenter.UIShowPresenter;
import com.peoit.android.peoit_lib.util.MyLogger;
import com.peoit.android.peoit_lib.view.CustomActionBar;

/**
 * 所有Activity的基类，用于声明activity共用的方法
 * <p/>
 * author:libo
 * time:2015/9/6
 * E-mail:boli_android@163.com
 * last: ...
 */
public abstract class BaseActivity extends AppCompatActivity implements ActivityBase {
    private boolean isDisplayAcionbar = true;
    private boolean isAddLayoutBase = true;

    private FrameLayout root_layout;
    private UIShowPresenter mUIShowPresenter;
    private FrameLayout fl_uishow;
    protected BaseActivity mAct;
    private View toolbar_line;
    private ProgressDialog mLoadingDialog;
    private View action_title;
    private CustomActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (isAddLayoutBase) {
            super.setContentView(R.layout.layout_base);
        }
        // 经测试在代码里直接声明透明状态栏更有效
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
            localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
        }
        MyLogger.e("当前activity == " + this.getClass().getSimpleName());
    }

    public void setAddLayoutBase(boolean isAddLayoutBase) {
        this.isAddLayoutBase = isAddLayoutBase;
    }

    // 触摸其他区域，输入法界面消失
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return dispatchTouchEvent(ev, true);
    }

    protected boolean dispatchTouchEvent(MotionEvent ev, boolean isOpen) {
        if (isOpen) {
            if (ev.getAction() == MotionEvent.ACTION_DOWN) {
                View v = getCurrentFocus();
                if (isShouldHideInput(v, ev)) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (imm != null) {
                        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    }
                }
                return super.dispatchTouchEvent(ev);
            }
            // 必不可少，否则所有的组件都不会有TouchEvent了
            if (getWindow().superDispatchTouchEvent(ev)) {
                return true;
            }
            return onTouchEvent(ev);
        } else {
            // 必不可少，否则所有的组件都不会有TouchEvent了
            if (getWindow().superDispatchTouchEvent(ev)) {
                return true;
            }
            return onTouchEvent(ev);
        }
    }

    public boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = {0, 0};
            // 获取输入框当前的location位置
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击的是输入框区域，保留点击EditText的事件
                return false;
            } else {
                return true;
            }
        }
        return false;
    }

    public void setDisplayAcionbar(boolean isDisplayAcionbar) {
        this.isDisplayAcionbar = isDisplayAcionbar;
        if (isDisplayAcionbar) {
            if (actionBar != null) {
                if (!isDisplayAcionbar) {
                    action_title.setVisibility(View.GONE);
                    actionBar.setVisibility(View.GONE);
                } else {
                    action_title.setVisibility(View.VISIBLE);
                    actionBar.setVisibility(View.VISIBLE);
                }
                // 经测试在代码里直接声明透明状态栏更有效
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    if (isDisplayAcionbar)
                        action_title.setVisibility(View.VISIBLE);
                } else {
                    action_title.setVisibility(View.GONE);
                }
            }
        }
    }

    @Override
    public void initToolbar() {
        action_title = findViewById(R.id.title_bar);
        actionBar = (CustomActionBar) findViewById(R.id.actionbar);
        if (!isDisplayAcionbar) {
            action_title.setVisibility(View.GONE);
            actionBar.setVisibility(View.GONE);
        }
        // 经测试在代码里直接声明透明状态栏更有效
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (isDisplayAcionbar)
                action_title.setVisibility(View.VISIBLE);
        } else {
            action_title.setVisibility(View.GONE);
        }
    }

    @Override
    public CustomActionBar getToolbar() {
        return actionBar;
    }

    @Override
    public void doUpdataView() {

    }

    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    public void setContentView(int layoutResID) {
        setContentView(View.inflate(this, layoutResID, null));
    }

    @Override
    public void setContentView(View view) {
        root_layout = (FrameLayout) findViewById(R.id.root_layout);

        if (root_layout == null)
            return;

        root_layout.addView(view, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        fl_uishow = (FrameLayout) findViewById(R.id.fl_uishow);

        mUIShowPresenter = new UIShowPresenter(this, fl_uishow);

        init();

        initToolbar();

        initData();

        initView();

        initListener();
    }

    private void init() {
        mAct = this;
    }

    @Override
    public UIShowPresenter getUIShowPresenter() {
        return mUIShowPresenter;
    }

    @Override
    public void setBaseContentView(View contentView) {
        super.setContentView(contentView);

        init();

        initData();

        initView();

        initListener();
    }

    @Override
    public void setBaseContentView(int layoutId) {
        setBaseContentView(View.inflate(this, layoutId, null));
    }

    @Override
    public void showToast(CharSequence msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showCustomToast(CharSequence msg) {

    }

    @Override
    public void showCustomToast(CharSequence msg, int gravity) {

    }

    @Override
    public Dialog showLoadingDialog(String msg) {
        if (mLoadingDialog != null) {
            mLoadingDialog.setMessage(msg);
        } else {
            mLoadingDialog = new ProgressDialog(mAct);
            mLoadingDialog.setCanceledOnTouchOutside(false);
            mLoadingDialog.setMessage(msg);
        }
        mLoadingDialog.show();
        return mLoadingDialog;
    }

    @Override
    public void hideLoadingDialog() {
        if ((mLoadingDialog != null) && mLoadingDialog.isShowing())
            mLoadingDialog.dismiss();
    }

    @Override
    public void onResponseFailure(int errorCode, String errorMsg) {
        if (!TextUtils.isEmpty(errorMsg))
            showToast(errorMsg);
    }
}
