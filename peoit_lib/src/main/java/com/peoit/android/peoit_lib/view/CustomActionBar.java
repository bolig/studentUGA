package com.peoit.android.peoit_lib.view;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.andexert.library.RippleView;
import com.peoit.android.peoit_lib.R;

/**
 * author:libo
 * time:2015/9/28
 * E-mail:boli_android@163.com
 * last: ...
 */
public class CustomActionBar extends RelativeLayout {
    private RippleView rippleL;
    private ImageView ivL;
    private TextView tvTitle;
    private TextView tvR;
    private RippleView rippleR;
    private ImageView ivR;
    private View rootView;
    private TextView tv_search;
    private EditText et_search;
    private RippleView rippleR2;

    private void assignViews() {
        rippleL = (RippleView) rootView.findViewById(R.id.ripple_l);
        ivL = (ImageView) rootView.findViewById(R.id.iv_l);
        tvTitle = (TextView) rootView.findViewById(R.id.tv_title);
        tvR = (TextView) rootView.findViewById(R.id.tv_r);
        rippleR = (RippleView) rootView.findViewById(R.id.ripple_r);
        rippleR2 = (RippleView) rootView.findViewById(R.id.ripple_r2);
        ivR = (ImageView) rootView.findViewById(R.id.iv_r);
        tv_search = (TextView) rootView.findViewById(R.id.tv_search);
        et_search = (EditText) rootView.findViewById(R.id.et_search);
    }


    public CustomActionBar(Context context) {
        super(context);
        init();
    }

    public CustomActionBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomActionBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        if (isInEditMode()) {
            return;
        }
        rootView = LayoutInflater.from(getContext()).inflate(R.layout.layout_action_abr, null);
        assignViews();
        addView(rootView);

        rippleL.setVisibility(GONE);
        rippleR.setVisibility(GONE);

        tvTitle.setVisibility(GONE);
        tvR.setVisibility(GONE);

        et_search.setVisibility(GONE);
        tv_search.setVisibility(GONE);

        rootView.post(new Runnable() {
            @Override
            public void run() {
                LayoutParams params = (LayoutParams) rootView.getLayoutParams();
                params.height = dip2px(52);
                params.width = LayoutParams.MATCH_PARENT;
                rootView.setLayoutParams(params);
            }
        });
    }

    public CustomActionBar setBack() {
        ivL.setImageResource(R.drawable.ic_back);
        ivL.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    ((Activity) getContext()).finish();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        rippleL.setVisibility(VISIBLE);
        return this;
    }

    /**
     * 设置左边ImageView的属性
     *
     * @param resId
     * @param listener
     */
    public CustomActionBar setIvL(int resId, OnClickListener listener) {
        ivL.setImageResource(resId);
        ivL.setOnClickListener(listener);
        rippleL.setVisibility(VISIBLE);
        return this;
    }

    /**
     * 设置标题
     *
     * @param title
     */
    public CustomActionBar setTvTitle(String title) {
        tvTitle.setText(title);
        tvTitle.setVisibility(VISIBLE);
        return this;
    }

    /**
     * 设置右边文本控件文字和单击事件
     *
     * @param rTvText
     * @param listener
     */
    public CustomActionBar setTvR(String rTvText, OnClickListener listener) {
        tvR.setText(rTvText);
        tvR.setOnClickListener(listener);
        tvR.setVisibility(VISIBLE);
        rippleR.setVisibility(GONE);
        return this;
    }

    /**
     * 设置右边控件图片与单击事件
     *
     * @param resId
     * @param listener
     * @return
     */
    public CustomActionBar setIvR(int resId, OnClickListener listener) {
        ivR.setImageResource(resId);
        ivR.setOnClickListener(listener);
        rippleR.setVisibility(VISIBLE);
        tvR.setVisibility(GONE);
        return this;
    }

    /**
     * dp转换为px
     *
     * @param dpValue
     * @return
     */
    public int dip2px(float dpValue) {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        //MyLogger.i(scale+"");
        return (int) (dpValue * scale + 0.5f);
    }

    public CustomActionBar showSearch(boolean isShow, OnClickListener listener) {
        tv_search.setVisibility(isShow ? VISIBLE : GONE);
        tv_search.setOnClickListener(listener);
        return this;
    }

    public EditText showSearch() {
        et_search.setVisibility(VISIBLE);
        return et_search;
    }

    public CustomActionBar showR2(OnClickListener listener) {
        rippleR2.setVisibility(VISIBLE);
        rippleR2.setOnClickListener(listener);
        return this;
    }

}
