package com.peoit.android.studentuga.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.peoit.android.peoit_lib.base.BaseActivity;
import com.peoit.android.peoit_lib.callback.OnFragmentCallBack;
import com.peoit.android.studentuga.R;
import com.peoit.android.studentuga.config.CommonUtil;
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
public class MyScaleOrderActivity extends BaseActivity implements View.OnClickListener, OnFragmentCallBack {
    private LinearLayout llTabItem1;
    private TextView tvTabItem1;
    private LinearLayout llTabItem2;
    private TextView tvTabItem2;
    private LinearLayout llTabItem3;
    private TextView tvTabItem3;
    private ViewPager vpMyOrder;

    private MyScaleOrderFragment mScaleFragnebt2;
    private MyScaleOrderFragment mScaleFragnebt3;
    private MyScaleOrderFragment mScaleFragnebt4;

    private void assignViews() {
        llTabItem1 = (LinearLayout) findViewById(R.id.ll_tab_item1);
        llTabItem2 = (LinearLayout) findViewById(R.id.ll_tab_item2);
        llTabItem3 = (LinearLayout) findViewById(R.id.ll_tab_item3);

        tvTabItem1 = (TextView) findViewById(R.id.tv_tab_item1);
        tvTabItem2 = (TextView) findViewById(R.id.tv_tab_item2);
        tvTabItem3 = (TextView) findViewById(R.id.tv_tab_item3);

        vpMyOrder = (ViewPager) findViewById(R.id.vp_myOrder);
    }

    public static final int MY_My_ORDER = 110;
    public static final int MY_He_ORDER = 111;

    private int currentOrderType;

    private ShowItem currentShowItem;

    public List<Fragment> mFragemnts = new ArrayList<>();

    /**
     * 启动当前页
     *
     * @param mAc
     * @param orderType 用于标示显示的order类型( 本页自定义属性 1. )
     */
    public static void startThisActivity(Activity mAc, int orderType) {
        if (CommonUtil.isLoginAndToLogin(mAc, false)) {
            Intent intent = new Intent(mAc, MyScaleOrderActivity.class);
            intent.putExtra("orderType", orderType);
            mAc.startActivity(intent);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_my_order1);
    }

    @Override
    public void initData() {
        currentOrderType = getIntent().getIntExtra("orderType", -1);
        if (currentOrderType == -1) {
            showToast("数据传输错误");
            finish();
            return;
        }
        mScaleFragnebt2 = MyScaleOrderFragment.newInstance(MyScaleOrderFragment.status_FK, isMyOrder());
        mScaleFragnebt3 = MyScaleOrderFragment.newInstance(MyScaleOrderFragment.status_JC, isMyOrder());
        mScaleFragnebt4 = MyScaleOrderFragment.newInstance(MyScaleOrderFragment.status_WC, isMyOrder());

        mFragemnts.add(mScaleFragnebt2);
        mFragemnts.add(mScaleFragnebt3);
        mFragemnts.add(mScaleFragnebt4);
    }

    /**
     * 判断当前类型是否是我的拿货点, 反之为我的提货单...
     *
     * @return
     */
    private boolean isMyOrder() {
        return currentOrderType == MY_My_ORDER;
    }

    @Override
    public void initView() {
        assignViews();
        getToolbar().setBack().setTvTitle(isMyOrder() ? "售卖商品订单" : "代销商品订单");
        vpMyOrder.setAdapter(new MyOrderPagerAdapter(getSupportFragmentManager(), mFragemnts));
        changeUIShow(ShowItem.ITEM1);
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
        }
        currentShowItem = item;
    }

    @Override
    public void initListener() {
        llTabItem1.setOnClickListener(this);
        llTabItem2.setOnClickListener(this);
        llTabItem3.setOnClickListener(this);

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
        }
    }

    @Override
    public void onFragmentCallBack(int callBack, Bundle callBackData) {

    }

    private enum ShowItem {
        ITEM1(0),
        ITEM2(1),
        ITEM3(2);

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
