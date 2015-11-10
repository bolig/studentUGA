package com.peoit.android.studentuga.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.peoit.android.studentuga.R;
import com.peoit.android.studentuga.entity.AreaInfo;
import com.peoit.android.studentuga.uitl.AreaUtil;
import com.peoit.android.studentuga.uitl.MyLogger;
import com.peoit.android.studentuga.view.wheel.OnWheelChangedListener;
import com.peoit.android.studentuga.view.wheel.WheelView;
import com.peoit.android.studentuga.view.wheel.adapter.ArrayWheelAdapter;

import java.util.List;

/**
 * author:libo
 * time:2015/10/8
 * E-mail:boli_android@163.com
 * last: ...
 */
public class SelectCityDialog extends Dialog implements View.OnClickListener {
    private View viewBg;
    private TextView tvOk;
    private WheelView wheelPro;
    private WheelView wheelCity;
    private WheelView wheelCounty;

    private boolean mCanceledOnTouchOutside;

    private Animation anim_in;
    private Animation anim_out;
    private View mContentView;

    private String mCurrentPro;
    private String mCurrentCity;
    private String mCurrentCounty;

    private OnAreaChangeListener mOnAreaChangeListener;
    private TextView tvCancel;

    private void assignViews() {
        viewBg = findViewById(R.id.view_bg);
        tvOk = (TextView) findViewById(R.id.tv_ok);
        tvCancel = (TextView) findViewById(R.id.tv_cancel);
        wheelPro = (WheelView) findViewById(R.id.wheel_pro);
        wheelCity = (WheelView) findViewById(R.id.wheel_city);
        wheelCounty = (WheelView) findViewById(R.id.wheel_county);
    }

    public SelectCityDialog(Context context) {
        this(context, null, null, null);
    }

    public SelectCityDialog(Context context, String pro, String city, String county) {
        super(context, R.style.dialog);
        this.mCurrentPro = pro;
        this.mCurrentCity = city;
        this.mCurrentCounty = county;
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
        mContentView = View.inflate(getContext(), R.layout.dialog_select_city, null);
        setContentView(mContentView);
        // 经测试在代码里直接声明透明状态栏更有效
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
            localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
        }
        initView();
        initListener();
    }

    private void initView() {
        assignViews();
        wheelPro.setVisibleItems(9);
        wheelCity.setVisibleItems(9);
        wheelCounty.setVisibleItems(9);
    }

    private void initListener() {
        viewBg.setOnClickListener(this);
        tvOk.setOnClickListener(this);
        tvCancel.setOnClickListener(this);

        wheelPro.addChangingListener(new OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                mCurrentPro = AreaUtil.nameOfIndexInPros(newValue);
                mCurrentCity = null;
                MyLogger.e("pro = " + mCurrentPro);
                updataCity();
            }
        });

        wheelCity.addChangingListener(new OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                mCurrentCity = AreaUtil.nameOfIndexInCities(newValue);
                MyLogger.e("City = " + mCurrentCity);
                updataCounty();
            }
        });

        wheelCounty.addChangingListener(new OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                mCurrentCounty = AreaUtil.nameOfIndexInCounties(newValue);
                MyLogger.e("county = " + mCurrentCounty);
            }
        });

        AreaUtil.getArea(new AreaUtil.OnAreaCallBack() {
            @Override
            public void onArea(List<AreaInfo> infos) {
                updataPro();
                updataCity();
                updataCounty();
            }
        });
    }

    /**
     * 更新省份信息
     */
    private void updataPro() {
        wheelPro.setViewAdapter(new ArrayWheelAdapter<String>(getContext(), AreaUtil.getPros()));
        wheelPro.setCurrentItem(AreaUtil.indexOfPro(mCurrentPro));
    }

    private void updataCity() {
        wheelCity.setViewAdapter(new ArrayWheelAdapter<String>(getContext(),
                TextUtils.isEmpty(mCurrentPro) ? AreaUtil.getCityies() : AreaUtil.getCityies(mCurrentPro)));
        wheelCity.setCurrentItem(AreaUtil.indexOfCity(mCurrentCity));

        updataCounty();
    }

    private void updataCounty() {
        wheelCounty.setViewAdapter(new ArrayWheelAdapter<String>(getContext(),
                TextUtils.isEmpty(mCurrentCity) ? AreaUtil.getCounties() : AreaUtil.getCounties(mCurrentCity)));
        wheelCounty.setCurrentItem(AreaUtil.indexOfCounty(mCurrentCounty));
    }

    @Override
    public void onClick(View v) {
        if (v == viewBg) {
            if (mCanceledOnTouchOutside)
                dismiss();
        } else if (v == tvOk) {
            onDataChange();
            dismiss();
        } else if (v == tvCancel) {
            dismiss();
        }
    }

    @Override
    public void setCanceledOnTouchOutside(boolean cancel) {
        super.setCanceledOnTouchOutside(cancel);
        this.mCanceledOnTouchOutside = cancel;
    }

    @Override
    public void show() {
        super.show();
        mContentView.startAnimation(anim_in);
    }

    @Override
    public void dismiss() {
        mContentView.startAnimation(anim_out);
        anim_out.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                SelectCityDialog.super.dismiss();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
    }

    private void onDataChange() {
        if (mOnAreaChangeListener != null) {
            mOnAreaChangeListener.onAreaChange(AreaUtil.nameOfIndexInPros(wheelPro.getCurrentItem()),
                    AreaUtil.nameOfIndexInCities(wheelCity.getCurrentItem()),
                    AreaUtil.nameOfIndexInCounties(wheelCounty.getCurrentItem()));
        }
    }

    public void setOnAreaChangeListener(OnAreaChangeListener listener) {
        this.mOnAreaChangeListener = listener;
    }

    public interface OnAreaChangeListener {
        void onAreaChange(String pro, String city, String county);
    }
}
