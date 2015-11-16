package com.peoit.android.studentuga.ui.fragment;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.peoit.android.peoit_lib.ActivityBase;
import com.peoit.android.peoit_lib.adapter.BaseEntityAdapter;
import com.peoit.android.peoit_lib.adapter.BaseViewHolder;
import com.peoit.android.peoit_lib.adapter.BaseViewModelAdapter;
import com.peoit.android.peoit_lib.base.BasePagerFragment;
import com.peoit.android.peoit_lib.view.CustomActionBar;
import com.peoit.android.studentuga.R;
import com.peoit.android.studentuga.config.CommonUtil;
import com.peoit.android.studentuga.config.NetConstants;
import com.peoit.android.studentuga.entity.BannerInfo;
import com.peoit.android.studentuga.entity.GoodsInfo;
import com.peoit.android.studentuga.entity.UserInfo;
import com.peoit.android.studentuga.enuml.UserType;
import com.peoit.android.studentuga.net.BaseServer;
import com.peoit.android.studentuga.net.server.HomeBannerServer;
import com.peoit.android.studentuga.net.server.HomeChooseGoodsServer;
import com.peoit.android.studentuga.net.server.HomeRankingServer;
import com.peoit.android.studentuga.net.server.SignInServer;
import com.peoit.android.studentuga.ui.GoodsDetActivity;
import com.peoit.android.studentuga.ui.SearchActivity;
import com.peoit.android.studentuga.ui.SignInActivity;
import com.peoit.android.studentuga.ui.XiaoXiActivity;
import com.peoit.android.studentuga.ui.showUI.SimpleShowUiShow;
import com.peoit.android.studentuga.view.MySliderTextView;
import com.peoit.android.studentuga.view.list.SwipyRefreshLayout;

import java.util.List;

/**
 * author:libo
 * time:2015/9/8
 * E-mail:boli_android@163.com
 * last: ...
 */
public class HomeFragment extends BasePagerFragment implements BaseSliderView.OnSliderClickListener,
        BaseViewModelAdapter.ViewModelAdapterBase<GoodsInfo> {
    private SliderLayout mSlider;
    private PagerIndicator mIndicator;

    private SwipyRefreshLayout srlRefresh;
    private ListView lvInfo;
    private View mHeaderView;

    private TextView tvItem1;
    private TextView tvItem2;
    private TextView tvItem3;
    private TextView tvItem4;
    private View mRankingView;
    private View action_title;
    private CustomActionBar actionBar;
    private HomeChooseGoodsServer mHomeGoodsChooseServer;
    private HomeRankingServer mHomeRankingServer;
    private FrameLayout flRoot;
    private BaseViewModelAdapter<GoodsInfo> mGoodsAdapter;
    private TextView tvNoRanking;
    private HomeBannerServer mHomeBannerServer;
    private SignInServer mSignInServer;

    @Override
    public View createView(LayoutInflater inflater, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frag_home, null);
    }

    @Override
    public void initData() {
        mHomeGoodsChooseServer = new HomeChooseGoodsServer((ActivityBase) getActivity());
        mHomeRankingServer = new HomeRankingServer((ActivityBase) getActivity());
        mHomeBannerServer = new HomeBannerServer((ActivityBase) getActivity());
        mSignInServer = new SignInServer((ActivityBase) getActivity());
    }


    @Override
    public void initView() {
        srlRefresh = (SwipyRefreshLayout) findViewById(R.id.srl_refresh);

        lvInfo = (ListView) findViewById(R.id.lv_info);

        flRoot = (FrameLayout) findViewById(R.id.fl_root);

        action_title = findViewById(com.peoit.android.peoit_lib.R.id.title_bar);
        actionBar = (CustomActionBar) findViewById(com.peoit.android.peoit_lib.R.id.actionbar);
        // 经测试在代码里直接声明透明状态栏更有效
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            action_title.setVisibility(View.VISIBLE);
        } else {
            action_title.setVisibility(View.GONE);
        }
        addHeader();
        actionBar.showSearch(true, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchActivity.startThisActivity(getActivity());
            }
        }).showR2(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                XiaoXiActivity.startThisActivity(getActivity());
            }
        });
    }

    private LinearLayout llRanking;
    private TextView tvSignUp;

    private void addHeader() {
        mHeaderView = LayoutInflater.from(getActivity()).inflate(R.layout.frag_home_banner, null);

        mSlider = (SliderLayout) mHeaderView.findViewById(R.id.slider);
        mIndicator = (PagerIndicator) mHeaderView.findViewById(R.id.custom_indicator);
        tvNoRanking = (TextView) mHeaderView.findViewById(R.id.tv_noRanking);

        mSlider.setPresetTransformer(SliderLayout.Transformer.Accordion);
        mSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mSlider.setCustomAnimation(new DescriptionAnimation());
        mSlider.setDuration(4000);

        llRanking = (LinearLayout) mHeaderView.findViewById(R.id.ll_ranking);
        llRanking.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                getActivity().dispatchTouchEvent(event);
                return false;
            }
        });
        tvSignUp = (TextView) mHeaderView.findViewById(R.id.tv_sign_up);

        lvInfo.addHeaderView(mHeaderView);

        SimpleShowUiShow uiShow = new SimpleShowUiShow(getActivity());
        uiShow.setRootView(flRoot);

        mGoodsAdapter = mHomeGoodsChooseServer.getAdapter(this);
        lvInfo.setAdapter(mGoodsAdapter);
        mHomeGoodsChooseServer.RequestHoneChooseGoodsList(uiShow);
        mHomeRankingServer.requestHomeRanking(new HomeRankingServer.OnRankingCallBack() {
            @Override
            public void onCallBack(List<UserInfo> infos) {
                addViewToRanking(infos);
            }
        });
        mHomeBannerServer.requestHomebanner(new HomeBannerServer.OnBannerCallBack() {
            @Override
            public void onCallBack(List<BannerInfo> infos) {
                updataView(infos);
            }
        });
    }

    public void addViewToRanking(List<UserInfo> infos) {
        llRanking.removeAllViews();
        if (infos.size() > 0) {
            tvNoRanking.setVisibility(View.GONE);
            infos.add(0, new UserInfo());
        }
        for (int i = 0; i < infos.size(); i++) {
            addRanking(i, infos.get(i));
        }
    }

    private void addRanking(int i, UserInfo info) {
        mRankingView = getActivity().getLayoutInflater().inflate(R.layout.layout_ranking_item, null);
        mRankingView.setEnabled(false);

        tvItem1 = (TextView) mRankingView.findViewById(R.id.tv_item1);
        tvItem2 = (TextView) mRankingView.findViewById(R.id.tv_item2);
        tvItem3 = (TextView) mRankingView.findViewById(R.id.tv_item3);
        tvItem4 = (TextView) mRankingView.findViewById(R.id.tv_item4);

        int width = CommonUtil.w_screeen / 7;

        setTextLayoutParams(tvItem1, 2 * width);
        setTextLayoutParams(tvItem2, width);
        setTextLayoutParams(tvItem3, 2 * width);
        setTextLayoutParams(tvItem4, 2 * width);

        if (i > 0) {
            tvItem1.setTextSize(12);
            tvItem2.setTextSize(12);
            tvItem3.setTextSize(12);
            tvItem4.setTextSize(12);

            tvItem1.setText(info.getNickname());
            tvItem2.setText(i + "");
            tvItem3.setText(info.getAreaname());
            tvItem4.setText(info.getSchoolname());
        }
        switch (i) {
            case 0:
                break;
            case 1:
                mRankingView.setBackgroundResource(R.color.col_ranking_1);
                mRankingView.setSelected(true);
                break;
            case 2:
                mRankingView.setBackgroundResource(R.color.col_ranking_2);
                mRankingView.setSelected(true);
                break;
            case 3:
                mRankingView.setBackgroundResource(R.color.col_ranking_3);
                mRankingView.setSelected(true);
                break;
        }
        llRanking.addView(mRankingView);
        llRanking.addView(getLineView());
    }

    private void setTextLayoutParams(TextView tvItem, int width) {
        ViewGroup.LayoutParams layoutParams = tvItem.getLayoutParams();
        layoutParams.width = width;
        layoutParams.height = CommonUtil.dip2px(24);
        tvItem.setLayoutParams(layoutParams);
    }

    private View getLineView() {
        TextView tv = new TextView(getActivity());
        tv.setBackgroundResource(R.color.md_grey_300);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, CommonUtil.dip2px(1));
        tv.setLayoutParams(params);
        return tv;
    }

    public void updataView(List<BannerInfo> infos) {
        mSlider.removeAllSliders();
        if (infos != null) {
            for (int i = 0; i < infos.size(); i++) {
                BannerInfo info = infos.get(i);

                MySliderTextView textSliderView = new MySliderTextView(getActivity());
                textSliderView
                        .image(NetConstants.IMG_HOST + info.getUrl())
                        .description(info.getTitle())
                        .setScaleType(BaseSliderView.ScaleType.FitCenterCrop)
                        .setOnSliderClickListener(this);
                textSliderView.bundle(new Bundle());
                textSliderView.getBundle().putString("id", info.getId() + "");
                mSlider.addSlider(textSliderView);
            }
        }
    }

    @Override
    public void initListener() {
        srlRefresh.setOnRefreshListener(mHomeGoodsChooseServer);

        lvInfo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                GoodsDetActivity.startThisActivity(getActivity(), mGoodsAdapter.getItem(position + 1).getId() + "", null, false);
            }
        });

        llRanking.setEnabled(false);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!CommonUtil.isLogin()) {
            tvSignUp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CommonUtil.isLoginAndToLogin(getActivity(), false);
                }
            });
        } else {
            if (CommonUtil.getUserType() == UserType.xueSheng) {
                mSignInServer.requestCheckIsSignIn(new BaseServer.OnSuccessCallBack() {
                    @Override
                    public void onSuccess(int mark) {
                        switch (mark) {
                            case 0:
                                tvSignUp.setVisibility(View.VISIBLE);
                                tvSignUp.setText("你已报名");
                                tvSignUp.setEnabled(false);
                                tvSignUp.setOnClickListener(null);
                                break;
                            case -1:
                                tvSignUp.setVisibility(View.VISIBLE);
                                tvSignUp.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        SignInActivity.startThisActivity(HomeFragment.this);
                                    }
                                });
                                break;
                            case -2:
                                tvSignUp.setVisibility(View.VISIBLE);
                                tvSignUp.setSelected(true);
                                tvSignUp.setText("已报名, 等待审核...");
                                tvSignUp.setOnClickListener(null);
                                break;
                        }
                    }
                });
            } else {
                tvSignUp.setVisibility(View.VISIBLE);
                tvSignUp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showToast("只有用户类型为大学生才能报名");
                    }
                });
            }
        }
    }

    @Override
    public void onSliderClick(BaseSliderView var1) {
//        String msg = var1.getBundle().getString("id");
//        GoodsDetActivity.startThisActivity(getActivity(), msg, null, false);
    }

    @Override
    public void initModelView(int position, final GoodsInfo data, BaseEntityAdapter.ViewHolderBase holderBase, View convertView) {
        Glide.with(getActivity())
                .load(NetConstants.IMG_HOST + data.getImgurl())
                .error(R.drawable.noproduct)
                .into(((ViewHolder) holderBase).iv_icon);
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GoodsDetActivity.startThisActivity(getActivity(), data.getId() + "", null, false);
            }
        });
    }

    @Override
    public BaseViewHolder getViewModelHolder() {
        return new ViewHolder();
    }

    private class ViewHolder extends BaseViewHolder {

        private ImageView iv_icon;

        @Override
        protected int getConvertViewByLayoutId() {
            return R.layout.layout_test_item;
        }

        @Override
        public void inflaer(View convertView) {
            iv_icon = (ImageView) convertView.findViewById(R.id.iv_icon);
        }
    }
}
