package com.peoit.android.studentuga.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.peoit.android.peoit_lib.base.BaseActivity;
import com.peoit.android.peoit_lib.callback.OnFragmentCallBack;
import com.peoit.android.studentuga.R;
import com.peoit.android.studentuga.config.CommonUtil;
import com.peoit.android.studentuga.ui.fragment.MyHoldOrderAllFragment;
import com.peoit.android.studentuga.ui.fragment.MyScaleOrderFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * 我的订单界面
 * <p/>
 * author:libo
 * time:2015/8/4
 * E-mail:boli_android@163.com
 * last: ...
 */
public class MyOrderActivity extends BaseActivity implements View.OnClickListener, OnFragmentCallBack {
    private LinearLayout llTabItem1;
    private TextView tvTabItem1;
    private LinearLayout llTabItem2;
    private TextView tvTabItem2;
    private LinearLayout llTabItem3;
    private TextView tvTabItem3;
    private LinearLayout llTabItem4;
    private TextView tvTabItem4;
    private ViewPager vpMyOrder;

    private MyHoldOrderAllFragment mHoldFragment1;
    private MyHoldOrderAllFragment mHoldFragment2;
    private MyHoldOrderAllFragment mHoldFragment3;
    private MyHoldOrderAllFragment mHoldFragment4;

    private MyScaleOrderFragment mScaleFragnebt1;
    private MyScaleOrderFragment mScaleFragnebt2;
    private MyScaleOrderFragment mScaleFragnebt3;
    private MyScaleOrderFragment mScaleFragnebt4;

    private void assignViews() {
        llTabItem1 = (LinearLayout) findViewById(R.id.ll_tab_item1);
        llTabItem2 = (LinearLayout) findViewById(R.id.ll_tab_item2);
        llTabItem3 = (LinearLayout) findViewById(R.id.ll_tab_item3);
        llTabItem4 = (LinearLayout) findViewById(R.id.ll_tab_item4);

        tvTabItem1 = (TextView) findViewById(R.id.tv_tab_item1);
        tvTabItem2 = (TextView) findViewById(R.id.tv_tab_item2);
        tvTabItem3 = (TextView) findViewById(R.id.tv_tab_item3);
        tvTabItem4 = (TextView) findViewById(R.id.tv_tab_item4);

        vpMyOrder = (ViewPager) findViewById(R.id.vp_myOrder);
    }

//    public static final int MY_HOLD_ORDER = 110;
//    public static final int MY_SCALE_ORDER = 111;

//    private int currentOrderType;
    private int mTabLayoutWidth;

    private ShowItem currentShowItem;

    public List<Fragment> mFragemnts = new ArrayList<>();

    /**
     * 启动当前页
     *
     * @param mAc
     * @param
     */
    public static void startThisActivity(Activity mAc) {
        if (CommonUtil.isLoginAndToLogin(mAc, false)) {
            Intent intent = new Intent(mAc, MyOrderActivity.class);
            mAc.startActivity(intent);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_my_order);
    }

    @Override
    public void initData() {
        mHoldFragment1 = MyHoldOrderAllFragment.newInstance(MyHoldOrderAllFragment.status_XD);
        mHoldFragment2 = MyHoldOrderAllFragment.newInstance(MyHoldOrderAllFragment.status_FK);
        mHoldFragment3 = MyHoldOrderAllFragment.newInstance(MyHoldOrderAllFragment.status_JC);
        mHoldFragment4 = MyHoldOrderAllFragment.newInstance(MyHoldOrderAllFragment.status_WC);

        mFragemnts.add(mHoldFragment1);
        mFragemnts.add(mHoldFragment2);
        mFragemnts.add(mHoldFragment3);
        mFragemnts.add(mHoldFragment4);

//        if (isMyHoldOrder()) {
//        } else {
//            mScaleFragnebt1 = MyScaleOrderFragment.newInstance(MyScaleOrderFragment.status_XD);
//            mScaleFragnebt2 = MyScaleOrderFragment.newInstance(MyScaleOrderFragment.status_FK);
//            mScaleFragnebt3 = MyScaleOrderFragment.newInstance(MyScaleOrderFragment.status_JC);
//            mScaleFragnebt4 = MyScaleOrderFragment.newInstance(MyScaleOrderFragment.status_WC);
//
//            mFragemnts.add(mScaleFragnebt1);
//            mFragemnts.add(mScaleFragnebt2);
//            mFragemnts.add(mScaleFragnebt3);
//            mFragemnts.add(mScaleFragnebt4);
//        }
        mTabLayoutWidth = (CommonUtil.w_screeen - CommonUtil.dip2px(3)) / 4;
    }


//    private boolean isMyHoldOrder() {
//        return currentOrderType == MY_HOLD_ORDER;
//    }

    @Override
    public void initView() {
        assignViews();
        getToolbar().setBack().setTvTitle("订单中心");
        setTabLayoutWidth(llTabItem1);
        setTabLayoutWidth(llTabItem2);
        setTabLayoutWidth(llTabItem3);
        setTabLayoutWidth(llTabItem4);
        vpMyOrder.setAdapter(new MyOrderPagerAdapter(getSupportFragmentManager(), mFragemnts));
        changeUIShow(ShowItem.ITEM1);
    }

    private void setTabLayoutWidth(LinearLayout llTabItem1) {
        ViewGroup.LayoutParams params = llTabItem1.getLayoutParams();
        params.width = mTabLayoutWidth;
    }

    /**
     * 改变界面显示
     *
     * @param item
     */
    private void changeUIShow(ShowItem item) {
        if (item.equals(currentShowItem)) {
            return;
        }
        llTabItem1.setSelected(false);
        llTabItem2.setSelected(false);
        llTabItem3.setSelected(false);
        llTabItem4.setSelected(false);
        vpMyOrder.setCurrentItem(item.getCurrentItem(), false);
        switch (item) {
            case ITEM1:
                llTabItem1.setSelected(true);
                break;
            case ITEM2:
                llTabItem2.setSelected(true);
                break;
            case ITEM3:
                llTabItem3.setSelected(true);
                break;
            case ITEM4:
                llTabItem4.setSelected(true);
                break;
        }
        currentShowItem = item;
    }

    @Override
    public void initListener() {
        llTabItem1.setOnClickListener(this);
        llTabItem2.setOnClickListener(this);
        llTabItem3.setOnClickListener(this);
        llTabItem4.setOnClickListener(this);

        vpMyOrder.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                ShowItem item = ShowItem.ITEM1.getItem(position);
                if (item != null) {
                    changeUIShow(item);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v == llTabItem1) {
            changeUIShow(ShowItem.ITEM1);
        } else if (v == llTabItem2) {
            changeUIShow(ShowItem.ITEM2);
        } else if (v == llTabItem3) {
            changeUIShow(ShowItem.ITEM3);
        } else if (v == llTabItem4) {
            changeUIShow(ShowItem.ITEM4);
        }
    }

    @Override
    public void onFragmentCallBack(int callBack, Bundle callBackData) {

    }

    private enum ShowItem {
        ITEM1(0),
        ITEM2(1),
        ITEM3(2),
        ITEM4(3);

        private final int currentItem;

        ShowItem(int currentItem) {
            this.currentItem = currentItem;
        }

        private int getCurrentItem() {
            return currentItem;
        }

        public boolean equals(ShowItem item) {
            if (item == null)
                return false;
            return currentItem == item.getCurrentItem();
        }

        public ShowItem getItem(int index) {
            switch (index) {
                case 0:
                    return ITEM1;
                case 1:
                    return ITEM2;
                case 2:
                    return ITEM3;
                case 3:
                    return ITEM4;
            }
            return null;
        }
    }

    private class MyOrderPagerAdapter extends FragmentPagerAdapter {

        private final List<Fragment> mFragments;

        public MyOrderPagerAdapter(FragmentManager fm, List<Fragment> fragments) {
            super(fm);
            this.mFragments = fragments;
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

    }
}
