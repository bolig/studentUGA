package com.peoit.android.studentuga.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.andexert.library.RippleView;
import com.bumptech.glide.Glide;
import com.peoit.android.peoit_lib.base.BaseActivity;
import com.peoit.android.peoit_lib.callback.OnFragmentCallBack;
import com.peoit.android.studentuga.R;
import com.peoit.android.studentuga.config.CommonUtil;
import com.peoit.android.studentuga.config.NetConstants;
import com.peoit.android.studentuga.ui.fragment.LiuCommonCommonsFragment;
import com.peoit.android.studentuga.ui.fragment.LiuCommonGoodsFragment;
import com.peoit.android.studentuga.uitl.MyLogger;
import com.peoit.android.studentuga.view.CircleImageView;
import com.peoit.android.studentuga.view.ObservableScrollView;

import java.util.ArrayList;
import java.util.List;

public class UserLiuCommonActivity extends BaseActivity implements OnFragmentCallBack {
    private CircleImageView ivAvater;
    private TextView tvName;
    private TextView tvSignName;
    private LinearLayout llTabItem1;
    private TextView tvTabItem1;
    private LinearLayout llTabItem2;
    private TextView tvTabItem2;
    private ViewPager vpInfo;
    private View viewLine;
    private LinearLayout llTab;
    private LinearLayout llTabMianItem1;
    private LinearLayout llTabMainItem2;
    private View viewLine1;

    private View viewTitlebar;
    private View viewBg;
    private View viewLBg;
    private RippleView rippleL;
    private ImageView ivL;
    private TextView tvTitle;
    private TextView tvR;
    private View viewRBg;
    private RippleView rippleR;
    private ImageView ivR;

    private int mHeaderHeight;
    private int mLvHeight;

    private int mScrollY;
    private ObservableScrollView svCommon;
    private View viewLine2;

    private List<Fragment> fragments = new ArrayList<>();
    private int lastPosition = -1;
    private String mPhone;
    private long mUid;
    private String mSign;
    private String mName;
    private String mPic;
    private boolean isScrollToHeader = false;

    private void assignViews() {
        ivAvater = (CircleImageView) findViewById(R.id.iv_avater);
        tvName = (TextView) findViewById(R.id.tv_name);
        tvSignName = (TextView) findViewById(R.id.tv_signName);
        llTabItem1 = (LinearLayout) findViewById(R.id.ll_tab_item1);
        tvTabItem1 = (TextView) findViewById(R.id.tv_tab_item1);
        llTabItem2 = (LinearLayout) findViewById(R.id.ll_tab_item2);
        tvTabItem2 = (TextView) findViewById(R.id.tv_tab_item2);
        vpInfo = (ViewPager) findViewById(R.id.vp_info);
        viewLine = findViewById(R.id.view_line);
        llTab = (LinearLayout) findViewById(R.id.ll_tab);
        llTabMianItem1 = (LinearLayout) findViewById(R.id.ll_tab_mian_item1);
        llTabMainItem2 = (LinearLayout) findViewById(R.id.ll_tab_main_item2);
        viewLine1 = findViewById(R.id.view_line1);
        viewLine2 = findViewById(R.id.view_line2);
        svCommon = (ObservableScrollView) findViewById(R.id.sv_common);

        // actionBar相关

        viewTitlebar = findViewById(R.id.view_titlebar);
        viewBg = findViewById(R.id.view_bg);
        viewLBg = findViewById(R.id.view_l_bg);
        rippleL = (RippleView) findViewById(R.id.ripple_l);
        ivL = (ImageView) findViewById(R.id.iv_l);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        tvR = (TextView) findViewById(R.id.tv_r);
        viewRBg = findViewById(R.id.view_r_bg);
        rippleR = (RippleView) findViewById(R.id.ripple_r);
        ivR = (ImageView) findViewById(R.id.iv_r);
    }

    public static void startThisActivity(Activity mAc, String phone, long uid, String pic, String name, String sign) {
        Intent intent = new Intent(mAc, UserLiuCommonActivity.class);
        intent.putExtra("uid", uid);
        intent.putExtra("phone", phone);
        intent.putExtra("pic", pic);
        intent.putExtra("name", name);
        intent.putExtra("sign", sign);
        mAc.startActivity(intent);
    }

//    public static void startThisActivity(Context context) {
//        Intent intent = new Intent(context, UserLiuCommonActivity.class);
//        context.startActivity(intent);
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBaseContentView(R.layout.act_user_liu_common);
    }

    @Override
    public void initData() {
        mPhone = getIntent().getStringExtra("phone");
        mUid = getIntent().getLongExtra("uid", -1);
        mSign = getIntent().getStringExtra("sign");
        mName = getIntent().getStringExtra("name");
        mPic = getIntent().getStringExtra("pic");

        mHeaderHeight = CommonUtil.dip2px(200) - (int) getResources().getDimension(R.dimen.action_height);
        mLvHeight = CommonUtil.h_screeen - ((int) getResources().getDimension(R.dimen.action_height)) - CommonUtil.dip2px(49);

        fragments.add(LiuCommonGoodsFragment.newInstance(mPhone));
        fragments.add(LiuCommonCommonsFragment.newInstance(mUid + ""));
    }

    @Override
    public void initView() {
        assignViews();
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) vpInfo.getLayoutParams();
        params.height = mLvHeight;
        vpInfo.setLayoutParams(params);

        viewLine.setAlpha(0);
        viewBg.setAlpha(0);
        viewLBg.setAlpha(1);
        viewRBg.setAlpha(1);
        tvTitle.setAlpha(0);

        tvTitle.setText("用户中心");
        ivR.setImageResource(R.drawable.ic_liu);

        tvName.setText(mName);
        tvSignName.setText(mSign);
        Glide.with(mAct).load(NetConstants.IMG_HOST + mPic).into(ivAvater);

        vpInfo.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }
        });
        changeUIShow(0);
    }

    @Override
    public void initListener() {
        svCommon.addScrollCallBack(new ObservableScrollView.OnScrollCallBack() {
            @Override
            public void onScrollCallBack(int deltaX, int deltaY, int y) {
                mScrollY = y;
                MyLogger.e(" mScrollY = " + deltaX + ", " + deltaY + ", " + mScrollY);
                viewBg.setAlpha(getScrollProgress());
                viewLBg.setAlpha(1f - getScrollProgress());
                viewRBg.setAlpha(1f - getScrollProgress());
                tvTitle.setAlpha(getScrollProgress());
                if (mHeaderHeight <= mScrollY) {
                    if (llTab.getVisibility() != View.VISIBLE)
                        llTab.setVisibility(View.VISIBLE);
                    if (viewLine1.getVisibility() != View.VISIBLE)
                        viewLine1.setVisibility(View.VISIBLE);
                    if (!viewLine2.isSelected()) {
                        viewLine2.setSelected(true);
                    }
                    isScrollToHeader = true;
                    svCommon.setScrollHeader(isScrollToHeader);
                } else {
                    if (llTab.getVisibility() != View.GONE)
                        llTab.setVisibility(View.GONE);
                    if (viewLine1.getVisibility() != View.GONE)
                        viewLine1.setVisibility(View.GONE);
                    if (viewLine2.isSelected()) {
                        viewLine2.setSelected(false);
                    }
                    isScrollToHeader = false;
                    svCommon.setScrollHeader(isScrollToHeader);
                }
            }
        });

        vpInfo.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                changeUIShow(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        llTabItem1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeUIShow(0);
            }
        });
        llTabMianItem1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeUIShow(0);
            }
        });

        llTabItem2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeUIShow(1);
            }
        });
        llTabMainItem2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeUIShow(1);
            }
        });
        ivL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        ivR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                AddCommonActivity.startThisActivity(mAct);
            }
        });
        svCommon.setOnTouchCallBack(new ObservableScrollView.OnTouchCallBack() {
            @Override
            public boolean onCallBack(MotionEvent event) {
//                switch (lastPosition) {
//                    case 0:
//                        ((LiuCommonGoodsFragment) fragments.get(lastPosition)).setOnTouch(event);
//                        break;
//                    case 1:
//                        ((LiuCommonCommonsFragment) fragments.get(lastPosition)).setOnTouch(event);
//                        break;
//                }
                return vpInfo.dispatchTouchEvent(event);
            }
        });
    }

    private void changeUIShow(int position) {
        if (lastPosition == position) {
            return;
        }
        lastPosition = position;

        llTabItem1.setSelected(false);
        llTabItem2.setSelected(false);

        llTabMianItem1.setSelected(false);
        llTabMainItem2.setSelected(false);

        vpInfo.setCurrentItem(position);
        switch (position) {
            case 0:
                llTabItem1.setSelected(true);
                llTabMianItem1.setSelected(true);
                break;
            case 1:
                llTabItem2.setSelected(true);
                llTabMainItem2.setSelected(true);
                break;
        }
    }

    private float getScrollProgress() {
        return ((float) mScrollY) / ((float) mHeaderHeight);
    }

    @Override
    public void onFragmentCallBack(int callBack, Bundle callBackData) {
        svCommon.scrollTo(0, 0);
    }
}
