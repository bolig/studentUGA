package com.peoit.android.studentuga.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;

import com.peoit.android.peoit_lib.base.BaseActivity;
import com.peoit.android.studentuga.R;
import com.peoit.android.studentuga.config.CommonUtil;
import com.peoit.android.studentuga.ui.fragment.HomeFragment;
import com.peoit.android.studentuga.ui.fragment.HomeShopCarFragment;
import com.peoit.android.studentuga.ui.fragment.HomeSortFragment1;
import com.peoit.android.studentuga.ui.fragment.HomeUserFragment2;

import java.util.ArrayList;

/**
 * 首页界面
 * <p/>
 * author:libo
 * time:2015/8/4
 * E-mail:boli_android@163.com
 * last: ...
 */
public class HomeActivity extends BaseActivity implements View.OnClickListener {

    private ViewPager vpHome;

    private LinearLayout llPage1;
    private LinearLayout llPage2;
    private LinearLayout llPage3;
    private LinearLayout llPage4;

    private ArrayList<Fragment> fragments;

    public static int currentViewPagerItem = -1;
    private boolean isExit;
    private int lastPosition = -1;

    public static void startThisActivity(Activity mAc) {
        Intent intent = new Intent(mAc, HomeActivity.class);
        mAc.startActivity(intent);
    }

    public static void startThisActivityToShopCar(Activity mAc) {
        Intent intent = new Intent(mAc, HomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        currentViewPagerItem = 2;
        mAc.startActivity(intent);
    }

    public static void startThisActivityToExit(Activity mAc) {
        Intent intent = new Intent(mAc, HomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        mAc.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setAddLayoutBase(false);
        setBaseContentView(R.layout.act_home);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (currentViewPagerItem != -1) {
            vpHome.setCurrentItem(currentViewPagerItem);
            currentViewPagerItem = -1;
        }
    }

    @Override
    public void initData() {
        fragments = new ArrayList<Fragment>();
        fragments.add(new HomeFragment());
        fragments.add(new HomeSortFragment1());
        fragments.add(new HomeShopCarFragment());
        fragments.add(new HomeUserFragment2());
    }

    @Override
    public void initView() {
        vpHome = (ViewPager) findViewById(R.id.vp_home);
        vpHome.setAdapter(new HomePagerAdapter(getSupportFragmentManager()));

        llPage1 = (LinearLayout) findViewById(R.id.ll_page1);
        llPage2 = (LinearLayout) findViewById(R.id.ll_page2);
        llPage3 = (LinearLayout) findViewById(R.id.ll_page3);
        llPage4 = (LinearLayout) findViewById(R.id.ll_page4);

        changeUIShow(0);
    }

    @Override
    public void initListener() {
        llPage1.setOnClickListener(this);
        llPage2.setOnClickListener(this);
        llPage3.setOnClickListener(this);
        llPage4.setOnClickListener(this);

        vpHome.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
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
    }

    @Override
    public void onClick(View v) {
        if (v == llPage1) {
            changeUIShow(0);
        } else if (v == llPage2) {
            changeUIShow(1);
        } else if (v == llPage3) {
            changeUIShow(2);
        } else if (v == llPage4) {
            changeUIShow(3);
        }
    }

    /**
     * 改变UI显视内容
     *
     * @param position
     */
    private void changeUIShow(int position) {
        if (lastPosition == position){
            return;
        }
        lastPosition = position;
        llPage1.setSelected(false);
        llPage2.setSelected(false);
        llPage3.setSelected(false);
        llPage4.setSelected(false);

        vpHome.setCurrentItem(position, false);

//        getToolbar().setVisibility(View.VISIBLE);


        switch (position) {
            case 0:
                llPage1.setSelected(true);
                break;
            case 1:
                llPage2.setSelected(true);
                break;
            case 2:
                llPage3.setSelected(true);
                break;
            case 3:
                llPage4.setSelected(true);
                if (!CommonUtil.isLogin()) {
                    LoginActivity.startThisActivity(mAct);
                }
                break;
        }
    }

    private class HomePagerAdapter extends FragmentPagerAdapter {

        public HomePagerAdapter(FragmentManager fm) {
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

    private boolean isExitApp = false;

    @Override
    public void onBackPressed() {
        if (!isExitApp) {
            isExitApp = true;
            showToast("再按一次将退出应用");
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    isExitApp = false;
                }
            }, 4000);
        } else {
            finish();
        }
    }
}
