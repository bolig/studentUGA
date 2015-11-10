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
import com.peoit.android.studentuga.ui.fragment.GoodsCommonFragment;

import java.util.ArrayList;
import java.util.List;

public class GoodsCommonActivity extends BaseActivity {
    private LinearLayout llTabItem1;
    private LinearLayout llTabItem2;
    private ViewPager vpCommon;

    private List<GoodsCommonFragment> fragments = new ArrayList<>();
    private int lastType = -1;

    private void assignViews() {
        llTabItem1 = (LinearLayout) findViewById(R.id.ll_tab_item1);
        llTabItem2 = (LinearLayout) findViewById(R.id.ll_tab_item2);
        vpCommon = (ViewPager) findViewById(R.id.vp_common);
    }

    public static void startThisActivity(Activity mAc) {
        Intent intent = new Intent(mAc, GoodsCommonActivity.class);
        mAc.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_goods_common);
    }

    @Override
    public void initData() {
        fragments.add(GoodsCommonFragment.newInstance(false));
        fragments.add(GoodsCommonFragment.newInstance(true));
    }

    @Override
    public void initView() {
        assignViews();
        getToolbar().setBack().setTvTitle("商品评论");
        vpCommon.setAdapter(new PagerAdapter(getSupportFragmentManager()));
        changeUIShow(0);
    }

    @Override
    public void initListener() {
        vpCommon.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
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
        llTabItem2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeUIShow(1);
            }
        });
    }

    private void changeUIShow(int type) {
        if (lastType == type) {
            return;
        }
        lastType = type;

        llTabItem1.setSelected(false);
        llTabItem2.setSelected(false);

        vpCommon.setCurrentItem(type);
        switch (type) {
            case 0:
                llTabItem1.setSelected(true);
                break;
            case 1:
                llTabItem2.setSelected(true);
                break;
        }

    }

    public class PagerAdapter extends FragmentPagerAdapter {

        public PagerAdapter(FragmentManager fm) {
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
