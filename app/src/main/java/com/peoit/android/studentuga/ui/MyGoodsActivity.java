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

import com.peoit.android.peoit_lib.base.BaseActivity;
import com.peoit.android.studentuga.R;
import com.peoit.android.studentuga.config.CommonUtil;
import com.peoit.android.studentuga.ui.fragment.MyGoodsMineFragment;

import java.util.ArrayList;
import java.util.List;

public class MyGoodsActivity extends BaseActivity {
    private LinearLayout llTabItem1;
    private LinearLayout llTabItem2;
    private ViewPager vpMyGoods;

    private List<Fragment> fragments = new ArrayList<>();
    private int lastPosition = -1;

    private void assignViews() {
        llTabItem1 = (LinearLayout) findViewById(R.id.ll_tab_item1);
        llTabItem2 = (LinearLayout) findViewById(R.id.ll_tab_item2);
        vpMyGoods = (ViewPager) findViewById(R.id.vp_myGoods);
    }

    public static void startThisActivity(Activity mAc) {
        if (CommonUtil.isLoginAndToLogin(mAc, false)){
            Intent intent = new Intent(mAc, MyGoodsActivity.class);
            mAc.startActivity(intent);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_my_goods);
    }

    @Override
    public void initData() {
        fragments.add(MyGoodsMineFragment.newInstance(true));
        fragments.add(MyGoodsMineFragment.newInstance(false));
    }

    @Override
    public void initView() {
        assignViews();
        getToolbar().setBack().setTvTitle("我的商品");
        vpMyGoods.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
        changUIShow(0);
    }

    @Override
    public void initListener() {
        vpMyGoods.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                changUIShow(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        llTabItem1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changUIShow(0);
            }
        });

        llTabItem2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changUIShow(1);
            }
        });
    }

    private void changUIShow(int position) {
        if (lastPosition == position) {
            return;
        }

        llTabItem1.setSelected(false);
        llTabItem2.setSelected(false);

        lastPosition = position;
        vpMyGoods.setCurrentItem(position, false);
        switch (position) {
            case 0:
                llTabItem1.setSelected(true);
                break;
            case 1:
                llTabItem2.setSelected(true);
                break;
        }
    }

    private class MyPagerAdapter extends FragmentPagerAdapter {

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }
    }
}
