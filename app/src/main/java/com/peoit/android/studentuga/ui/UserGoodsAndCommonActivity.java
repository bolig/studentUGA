package com.peoit.android.studentuga.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.andexert.library.RippleView;
import com.bumptech.glide.Glide;
import com.peoit.android.peoit_lib.base.BaseActivity;
import com.peoit.android.studentuga.R;
import com.peoit.android.studentuga.config.CommonUtil;
import com.peoit.android.studentuga.config.NetConstants;
import com.peoit.android.studentuga.net.BaseServer;
import com.peoit.android.studentuga.net.server.LiuCommonServer;
import com.peoit.android.studentuga.net.server.UserGoodsServer;
import com.peoit.android.studentuga.ui.showUI.SimpleShowUiShow;
import com.peoit.android.studentuga.uitl.MyLogger;
import com.peoit.android.studentuga.view.CircleImageView;
import com.peoit.android.studentuga.view.NoSrcollListView;
import com.peoit.android.studentuga.view.ObservableScrollView;
import com.peoit.android.studentuga.view.list.SwipyRefreshLayout;
import com.peoit.android.studentuga.view.list.SwipyRefreshLayoutDirection;

public class UserGoodsAndCommonActivity extends BaseActivity {
    private SwipyRefreshLayout pullLayout;
    private NoSrcollListView lvInfo;
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

    private int mScrollY;
    private int mHeaderHeight;
    private FrameLayout flRoot;
    private SimpleShowUiShow mUIShow;
    private UserGoodsServer mUserGoodsServer;
    private Type lastType;
    private String mPhone;
    private LiuCommonServer mLiuCommonServer;
    private long mUid;
    private String mSign;
    private String mName;
    private String mPic;
    private ObservableScrollView sv;
    private int mLvHeight;


    private void assignViews() {
        pullLayout = (SwipyRefreshLayout) findViewById(R.id.pull_layout);
        lvInfo = (NoSrcollListView) findViewById(R.id.lv_info);
        sv = (ObservableScrollView) findViewById(R.id.sv);

        viewLine = findViewById(R.id.view_line);
        viewLine1 = findViewById(R.id.view_line1);

        llTab = (LinearLayout) findViewById(R.id.ll_tab);
        llTabMianItem1 = (LinearLayout) findViewById(R.id.ll_tab_mian_item1);
        llTabMainItem2 = (LinearLayout) findViewById(R.id.ll_tab_main_item2);

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
        Intent intent = new Intent(mAc, UserGoodsAndCommonActivity.class);
        intent.putExtra("uid", uid);
        intent.putExtra("phone", phone);
        intent.putExtra("pic", pic);
        intent.putExtra("name", name);
        intent.putExtra("sign", sign);
        mAc.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBaseContentView(R.layout.act_user_goods_and_common1);
    }

    @Override
    public void initData() {
        mPhone = getIntent().getStringExtra("phone");
        mUid = getIntent().getLongExtra("uid", -1);
        mSign = getIntent().getStringExtra("sign");
        mName = getIntent().getStringExtra("name");
        mPic = getIntent().getStringExtra("pic");

        mHeaderHeight = CommonUtil.dip2px(200) - (int) getResources().getDimension(R.dimen.action_height);
        mLvHeight = CommonUtil.h_screeen - mHeaderHeight;

        mUserGoodsServer = new UserGoodsServer(this);
        mLiuCommonServer = new LiuCommonServer(this);
    }

    @Override
    public void initView() {
        assignViews();
        addHeaderView();

        tvTitle.setText("用户中心");
        ivR.setImageResource(R.drawable.ic_liu);

        tvName.setText(mName);
        tvSignName.setText(mSign);
        Glide.with(mAct).load(NetConstants.IMG_HOST + mPic).error(R.drawable.user_avater).into(ivAvater);

        viewLine.setAlpha(0);
        viewBg.setAlpha(0);
        viewLBg.setAlpha(1);
        viewRBg.setAlpha(1);
        tvTitle.setAlpha(0);

        changeUIShow(Type.goods);
        ScrollView.LayoutParams layoutParams = (ScrollView.LayoutParams) lvInfo.getLayoutParams();
        layoutParams.height = mLvHeight;
    }

    private CircleImageView ivAvater;
    private TextView tvName;
    private TextView tvSignName;
    private LinearLayout llTabItem1;
    private TextView tvTabItem1;
    private LinearLayout llTabItem2;
    private TextView tvTabItem2;

    private void addHeaderView() {
        View view = View.inflate(mAct, R.layout.act_user_goods_and_common_header, null);

        ivAvater = (CircleImageView) view.findViewById(R.id.iv_avater);

        tvName = (TextView) view.findViewById(R.id.tv_name);
        tvSignName = (TextView) view.findViewById(R.id.tv_signName);

        llTabItem1 = (LinearLayout) view.findViewById(R.id.ll_tab_item1);
        llTabItem2 = (LinearLayout) view.findViewById(R.id.ll_tab_item2);

        tvTabItem1 = (TextView) view.findViewById(R.id.tv_tab_item1);
        tvTabItem2 = (TextView) view.findViewById(R.id.tv_tab_item2);

        flRoot = (FrameLayout) view.findViewById(R.id.fl_root);
        mUIShow = new SimpleShowUiShow(this);
        mUIShow.setRootView(flRoot);

//        lvInfo.addHeaderView(view);
        lvInfo.addHeaderView(view, null, true);
    }

    @Override
    public void initListener() {
        sv.addScrollCallBack(new ObservableScrollView.OnScrollCallBack() {
            @Override
            public void onScrollCallBack(int deltaX, int deltaY, int y) {
                mScrollY = y;
                MyLogger.e(" mScrollY = " + deltaX + ", " + deltaY + ", " + mScrollY);
                viewLine.setAlpha(getScrollProgress());
                viewBg.setAlpha(getScrollProgress());
                viewLBg.setAlpha(1f - getScrollProgress());
                viewRBg.setAlpha(1f - getScrollProgress());
                tvTitle.setAlpha(getScrollProgress());
                if (mHeaderHeight <= mScrollY) {
                    if (llTab.getVisibility() != View.VISIBLE)
                        llTab.setVisibility(View.VISIBLE);
                    if (viewLine1.getVisibility() != View.VISIBLE)
                        viewLine1.setVisibility(View.VISIBLE);
                } else {
                    if (llTab.getVisibility() != View.GONE)
                        llTab.setVisibility(View.GONE);
                    if (viewLine1.getVisibility() != View.GONE)
                        viewLine1.setVisibility(View.GONE);
                }
            }
        });
        pullLayout.setOnRefreshListener(new SwipyRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh(SwipyRefreshLayout layout, SwipyRefreshLayoutDirection direction) {
                switch (lastType) {
                    case goods:
                        mUserGoodsServer.requestUserGoodsList(layout, direction);
                        break;
                    case common:
                        mLiuCommonServer.requestLiuCommon(layout, direction);
                        break;
                }
            }
        });
        llTabItem1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeUIShow(Type.goods);
            }
        });
        llTabMianItem1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeUIShow(Type.goods);
            }
        });

        llTabItem2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeUIShow(Type.common);
            }
        });
        llTabMainItem2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeUIShow(Type.common);
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
                AddCommonActivity.startThisActivity(mAct, mUid + "");
            }
        });
    }

    private float getScrollProgress() {
        return ((float) mScrollY) / ((float) mHeaderHeight);
    }

    private void changeUIShow(Type type) {
        if (lastType == type) {
            return;
        }
        lastType = type;

        llTabItem1.setSelected(false);
        llTabItem2.setSelected(false);

        llTabMianItem1.setSelected(false);
        llTabMainItem2.setSelected(false);

        switch (type) {
            case goods:
                if (mUserGoodsServer.mGoodsListAdapter == null)
                    lvInfo.setAdapter(mUserGoodsServer.getGoodsListAdapter());
                lvInfo.setAdapter(mUserGoodsServer.mGoodsListAdapter);
                if (mUserGoodsServer.mGoodsListAdapter.getCount() == 0) {
                    mUserGoodsServer.requestUserGoodsList(mPhone, mUIShow, new BaseServer.OnSuccessCallBack() {
                        @Override
                        public void onSuccess(int mark) {
                            flRoot.setVisibility(View.GONE);
                        }
                    });
                } else {
                    if (mUserGoodsServer.mGoodsListAdapter.getCount() > 0) {
                        flRoot.setVisibility(View.GONE);
                    } else {
                        flRoot.setVisibility(View.VISIBLE);
                    }
                }
                llTabItem1.setSelected(true);
                llTabMianItem1.setSelected(true);
                break;
            case common:
                if (mLiuCommonServer.mLiuCommonAdapter == null)
                    lvInfo.setAdapter(mLiuCommonServer.getAdapter());
                lvInfo.setAdapter(mLiuCommonServer.mLiuCommonAdapter);
                if (mLiuCommonServer.mLiuCommonAdapter.getCount() == 0) {
                    mLiuCommonServer.requestLiuCommon(mUid + "", mUIShow, new BaseServer.OnSuccessCallBack() {
                        @Override
                        public void onSuccess(int mark) {
                            flRoot.setVisibility(View.GONE);
                        }
                    });
                } else {
                    if (mLiuCommonServer.mLiuCommonAdapter.getCount() > 0) {
                        flRoot.setVisibility(View.GONE);
                    } else {
                        flRoot.setVisibility(View.VISIBLE);
                    }
                }
                llTabItem2.setSelected(true);
                llTabMainItem2.setSelected(true);
                break;
        }
    }

    private enum Type {
        goods,
        common
    }
}
