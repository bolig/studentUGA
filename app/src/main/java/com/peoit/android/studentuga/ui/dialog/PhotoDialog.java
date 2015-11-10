package com.peoit.android.studentuga.ui.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.peoit.android.studentuga.R;

/**
 * author:libo
 * time:2015/8/31
 * E-mail:boli_android@163.com
 * last: ...
 */
public class PhotoDialog extends Dialog implements View.OnClickListener {

    private TextView tvNative;
    private TextView tvTake;
    private TextView tvCancel;
    private Animation anim_out;
    private Animation anim_in;
    private LinearLayout ll_tv;
    private View.OnClickListener mNativeListener;
    private View.OnClickListener mTakeListener;
    private boolean isCanceledOnTouchOutside;
    private String text1 = "本地图片";
    private String text2 = "拍照";

    private void assignViews() {
        tvNative = (TextView) findViewById(R.id.tv_native);
        tvTake = (TextView) findViewById(R.id.tv_take);
        tvCancel = (TextView) findViewById(R.id.tv_cancel);

        ll_tv = (LinearLayout) findViewById(R.id.ll_tv);

        tvNative.setText(text1);
        tvTake.setText(text2);
    }


    public PhotoDialog(Activity mAc) {
        super(mAc, R.style.dialog);
        initData();
    }

    private void initData() {
        anim_in = AnimationUtils.loadAnimation(getContext(), R.anim.abc_slide_in_bottom);
        anim_out = AnimationUtils.loadAnimation(getContext(), R.anim.abc_slide_out_bottom);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_photo);
        // 经测试在代码里直接声明透明状态栏更有效
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
            localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
        }
        assignViews();
        initListener();
    }

    private void initListener() {
        tvNative.setOnClickListener(mNativeListener);
        tvTake.setOnClickListener(mTakeListener);
        tvCancel.setOnClickListener(this);

        ll_tv.setOnClickListener(this);
    }

    public void setNativeListener(View.OnClickListener listener) {
        this.mNativeListener = listener;
        if (tvNative != null) {
            tvNative.setOnClickListener(mNativeListener);
        }
    }

    public void setTakeListener(View.OnClickListener listener) {
        this.mTakeListener = listener;
        if (tvTake != null) {
            tvTake.setOnClickListener(mTakeListener);
        }
    }

    @Override
    public void onClick(View v) {
        if (v == tvCancel) {
            dismiss();
        } else if (v == ll_tv) {
            if (isCanceledOnTouchOutside)
                dismiss();
        }
    }

    @Override
    public void setCanceledOnTouchOutside(boolean cancel) {
        this.isCanceledOnTouchOutside = cancel;
    }

    @Override
    public void show() {
        super.show();
        ll_tv.startAnimation(anim_in);
    }

    @Override
    public void dismiss() {
        ll_tv.startAnimation(anim_out);
        anim_out.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                PhotoDialog.super.dismiss();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
    }

    public void setText(String text1, String text2) {
        this.text1 = text1;
        this.text2 = text2;
        if (tvNative != null) {
            tvNative.setText(text1);
        }
        if (tvTake != null) {
            tvTake.setText(text2);
        }
    }
}
