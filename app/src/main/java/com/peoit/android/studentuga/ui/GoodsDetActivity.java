package com.peoit.android.studentuga.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.andexert.library.RippleView;
import com.bumptech.glide.Glide;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.peoit.android.peoit_lib.base.BaseActivity;
import com.peoit.android.studentuga.R;
import com.peoit.android.studentuga.config.CommonUtil;
import com.peoit.android.studentuga.config.NetConstants;
import com.peoit.android.studentuga.entity.CommonInfo;
import com.peoit.android.studentuga.entity.GoodDetInfo;
import com.peoit.android.studentuga.entity.GoodsStyleInfo;
import com.peoit.android.studentuga.entity.ShopCarInfo;
import com.peoit.android.studentuga.entity.UserOnNoLoginInfo;
import com.peoit.android.studentuga.net.server.CommonServer;
import com.peoit.android.studentuga.net.server.DaoXiaoOperationServer;
import com.peoit.android.studentuga.net.server.GoodsServer;
import com.peoit.android.studentuga.net.server.ShopCarServer;
import com.peoit.android.studentuga.net.server.UserServer;
import com.peoit.android.studentuga.ui.adapter.GoodsStyleAdapter;
import com.peoit.android.studentuga.ui.adapter.ImageAdapter1;
import com.peoit.android.studentuga.ui.dialog.SelectGoodsStyleDialog;
import com.peoit.android.studentuga.ui.showUI.SimpleShowUiShow;
import com.peoit.android.studentuga.uitl.MyLogger;
import com.peoit.android.studentuga.uitl.TimeUtil;
import com.peoit.android.studentuga.view.CircleImageView;
import com.peoit.android.studentuga.view.MySliderTextView;
import com.peoit.android.studentuga.view.NoSrcollGridView;
import com.peoit.android.studentuga.view.ObservableScrollView;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class GoodsDetActivity extends BaseActivity implements BaseSliderView.OnSliderClickListener, View.OnClickListener {
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

    private int mSliderHeight;
    private String mGoodsId;
    private GoodsServer mGoodsServer;

    private ObservableScrollView svGoods;
    private SliderLayout slider;
    private PagerIndicator customIndicator;
    private TextView tvGoodsName;
    private TextView tvSellPrice;
    private TextView tvRealPrice;
    private TextView tvGoodsRemark;
    private TextView tvExpressPrice;
    private TextView tvSellCount;
    private TextView tvGoodsAddress;
    private TextView tvGoodsCommentCount;
    private LinearLayout llNoData;
    private LinearLayout llAddGoodsComments;
    private LinearLayout llMyOrder;
    private TextView tvDaiXiao;
    private CircleImageView ivAvater;
    private TextView tvName;
    private WebView webView;
    private View viewLine;
    private LinearLayout llB;
    private TextView tvAddShopCar;
    private TextView tvAtOncePay;

    private GoodDetInfo mGoodDetInfo;

    private FrameLayout fl_root;
    private SimpleShowUiShow mUIShow;
    private SelectGoodsStyleDialog mSelectGoodsStyleDialog;
    private String mSelectStyle;
    private boolean mGoodsStyleIsAll;
    private int mGoodsPosition;
    private boolean isAtOnce;
    private CommonServer mCommonServer;
    private LinearLayout llLoading;
    private UserServer mUserServer;
    private UserOnNoLoginInfo mUserInfo;
    private TextView tvToCommon;
    private boolean isDaiXiao = false;
    private String mUid;
    private boolean isCancel;

    private void assignViews1() {
        svGoods = (ObservableScrollView) findViewById(R.id.sv_goods);
        slider = (SliderLayout) findViewById(R.id.slider);
        customIndicator = (PagerIndicator) findViewById(R.id.custom_indicator);
        tvGoodsName = (TextView) findViewById(R.id.tv_goods_name);
        tvSellPrice = (TextView) findViewById(R.id.tv_sellPrice);
        tvRealPrice = (TextView) findViewById(R.id.tv_realPrice);
        tvGoodsRemark = (TextView) findViewById(R.id.tv_goodsRemark);
        tvExpressPrice = (TextView) findViewById(R.id.tv_expressPrice);
        tvSellCount = (TextView) findViewById(R.id.tv_sellCount);
        tvGoodsAddress = (TextView) findViewById(R.id.tv_goodsAddress);
        tvGoodsCommentCount = (TextView) findViewById(R.id.tv_goodsCommentCount);
        llNoData = (LinearLayout) findViewById(R.id.ll_noData);
        llAddGoodsComments = (LinearLayout) findViewById(R.id.ll_addGoodsComments);
        llLoading = (LinearLayout) findViewById(R.id.ll_loading);
        llMyOrder = (LinearLayout) findViewById(R.id.ll_myOrder);
        ivAvater = (CircleImageView) findViewById(R.id.iv_avater);
        tvName = (TextView) findViewById(R.id.tv_name);
        webView = (WebView) findViewById(R.id.webView);
        viewLine = findViewById(R.id.view_line);
        llB = (LinearLayout) findViewById(R.id.ll_b);
        tvAddShopCar = (TextView) findViewById(R.id.tv_addShopCar);
        tvAtOncePay = (TextView) findViewById(R.id.tv_atOncePay);

        tvDaiXiao = (TextView) findViewById(R.id.tv_daiXiao);
    }

    private void assignViews() {
        fl_root = (FrameLayout) findViewById(R.id.fl_root);
        mUIShow = new SimpleShowUiShow(mAct, true);
        mUIShow.setRootView(fl_root);
        mUIShow.setTvErrorMsg("数据加载失败");
        mUIShow.setIvErrorImg(R.drawable.nodeliveryaddress);
        mUIShow.setTvReloadListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadGoodsDet();
            }
        });

        assignViews1();

        tvToCommon = (TextView) findViewById(R.id.tv_toCommon);

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

    public static void startThisActivity(Activity mAc, String id, String uid, boolean isCancel) {
        Intent intent = new Intent(mAc, GoodsDetActivity.class);
        intent.putExtra("id", id);
        intent.putExtra("uid", uid);
        intent.putExtra("isCancel", isCancel);
        mAc.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBaseContentView(R.layout.act_goods_det1);
    }

    @Override
    public void initData() {
        mGoodsId = getIntent().getStringExtra("id");
        mUid = getIntent().getStringExtra("uid");
        isCancel = getIntent().getBooleanExtra("isCancel", false);
        if (TextUtils.isEmpty(mGoodsId)) {
            showToast("数据传输错误");
            finish();
            return;
        }
        isDaiXiao();
        mSliderHeight = CommonUtil.dip2px(280) - (int) getResources().getDimension(R.dimen.action_height);
        mGoodsServer = new GoodsServer(this);
        mCommonServer = new CommonServer(this);
        mUserServer = new UserServer(this);
    }

    private void isDaiXiao() {
        isDaiXiao = !TextUtils.isEmpty(mUid);
    }

    public List<GoodsStyleInfo> mGoodsStyles;

    @Override
    public void initView() {
        assignViews();
        viewLine.setAlpha(0);
        viewBg.setAlpha(0);
        viewLBg.setAlpha(1);
        viewRBg.setAlpha(1);
        tvTitle.setAlpha(0);

        slider.setPresetTransformer(SliderLayout.Transformer.Accordion);
        slider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        slider.setCustomAnimation(new DescriptionAnimation());
        slider.setDuration(4000);

        tvDaiXiao.setVisibility(isDaiXiao ? View.VISIBLE : View.GONE);
        tvAddShopCar.setVisibility(isDaiXiao ? View.GONE : View.VISIBLE);
        tvAtOncePay.setVisibility(isDaiXiao ? View.GONE : View.VISIBLE);

        mSelectGoodsStyleDialog = new SelectGoodsStyleDialog(this);
        mSelectGoodsStyleDialog.setCanceledOnTouchOutside(false);
        mSelectGoodsStyleDialog.setOkListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mGoodsStyleIsAll && mGoodsStyles != null && mGoodsStyles.size() > 0) {
                    String name = mGoodsStyles.get(mGoodsPosition).getName();
                    showToast("请选择" + name);
                    return;
                }
                if (mSelectGoodsStyleDialog.getmGoodsCount() <= 0) {
                    showToast("请选择商品数量");
                    return;
                }
                mSelectGoodsStyleDialog.dismiss();
                if (isAtOnce) {
                    ShopCarInfo info = new ShopCarInfo();
                    info.setId(mGoodDetInfo.getId());
                    info.setNumber(mSelectGoodsStyleDialog.getmGoodsCount());
                    info.setSpec(mSelectStyle);
                    info.setImgurl(mGoodDetInfo.getImgurl());
                    info.setTitle(mGoodDetInfo.getTitle());
                    info.setPrice(getMoney(mGoodDetInfo));
                    CommonUtil.setSelectGoodsGoods(info);
                    PayInfoActivity.startThisActivity(mAct, false);
                } else {
                    new ShopCarServer(mAct).requestAddGoodsToShopCar(mGoodDetInfo.getId() + "",
                            mSelectGoodsStyleDialog.getmGoodsCount() + "",
                            mSelectStyle);
                }
            }
        });
        mSelectGoodsStyleDialog.setOnSelectGoodsStyleListener(new GoodsStyleAdapter.OnGoodsStyleListener() {
            @Override
            public void onGoodsStyle(boolean isAll, int position, String style) {
                mGoodsStyleIsAll = isAll;
                mGoodsPosition = position;
                mSelectStyle = style;
            }
        });

        loadGoodsDet();

        changeUIShow(-2);
        mCommonServer.requestGoodsCommon(mGoodsId, new CommonServer.OnCommonCallBack() {
            @Override
            public void onCommon(int mark, List<CommonInfo> infos) {
                addCommon(infos);
                changeUIShow(mark);
            }
        });
    }

    private void loadGoodsDet() {
        mGoodsServer.loadGoodsDet(mGoodsId, mUid, mUIShow, new GoodsServer.OnGoodsDetCallBack() {
            @Override
            public void onDetBack(GoodDetInfo info) {
                if (info != null) {
                    mGoodDetInfo = info;
                    tvGoodsName.setText(info.getTitle());
//                    tvExpressPrice.setText(info.getSellcount() + "");
                    tvSellPrice.setText("￥" + info.getPrice());
                    tvGoodsRemark.setVisibility(View.GONE);
                    tvRealPrice.setVisibility(View.GONE);
                    tvSellCount.setText("已交易" + info.getSellcount() + "笔");
                    tvGoodsAddress.setText("库存:" + (info.getCount() - info.getSellcount()));
                    mSelectGoodsStyleDialog.mMaxCount = info.getCount() - info.getSellcount();

                    if (isCancel) {
                        tvDaiXiao.setText("取消代销");
                        tvDaiXiao.setEnabled(true);
                    } else {
                        if (info.isDx()) {
                            tvDaiXiao.setText("你已代销此商品");
                            tvDaiXiao.setEnabled(false);
                        } else {
                            tvDaiXiao.setEnabled(true);
                            tvDaiXiao.setText("申请代销");
                        }
                    }

                    webView.loadDataWithBaseURL(NetConstants.IMG_HOST, getWeb(info.getDetails()), "text/html", "utf-8", null);
                    setWebView(webView);

                    String[] imgs = TextUtils.isEmpty(info.getImgs()) ? new String[]{} : info.getImgs().split(",");
                    upDataToSlider(imgs);
                    mSelectGoodsStyleDialog.setTitle(info.getTitle());
                    mSelectGoodsStyleDialog.setImgUrl(NetConstants.IMG_HOST + info.getImgurl());

                    addUserInfo();
                }
            }

            @Override
            public void onGoodsStyle(List<GoodsStyleInfo> info) {
                mGoodsStyles = info;
                mSelectGoodsStyleDialog.setGoodsStyleList(info);
            }
        });
    }

    private String getWeb(String string) {
        String s = "<head><style>img{display: inline; height: auto; width: 100%;}</style><body leftmargin=\\\"0\\\" topmargin=\\\"0\\\"></body></head>";
        s = s + "<body>" + string + "</body>";
        com.peoit.android.peoit_lib.util.MyLogger.e(s);
        return s;
    }

    private void setWebView(WebView view) {
        WebSettings webSettings = view.getSettings();
        webSettings.setJavaScriptEnabled(true);
        // User settings
//        webSettings.setJavaScriptEnabled(true);
//        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
//        webSettings.setUseWideViewPort(true);//关键点
//        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
//        webSettings.setDisplayZoomControls(false);
//        webSettings.setJavaScriptEnabled(true); // 设置支持javascript脚本
//        webSettings.setAllowFileAccess(true); // 允许访问文件
//        webSettings.setBuiltInZoomControls(false); // 设置显示缩放按钮
//        webSettings.setSupportZoom(false); // 支持缩放
//        webSettings.setLoadWithOverviewMode(true);

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int mDensity = metrics.densityDpi;
        Log.d("maomao", "densityDpi = " + mDensity);
        if (mDensity == 240) {
            webSettings.setDefaultZoom(WebSettings.ZoomDensity.FAR);
        } else if (mDensity == 160) {
            webSettings.setDefaultZoom(WebSettings.ZoomDensity.MEDIUM);
        } else if (mDensity == 120) {
            webSettings.setDefaultZoom(WebSettings.ZoomDensity.CLOSE);
        } else if (mDensity == DisplayMetrics.DENSITY_XHIGH) {
            webSettings.setDefaultZoom(WebSettings.ZoomDensity.FAR);
        } else if (mDensity == DisplayMetrics.DENSITY_TV) {
            webSettings.setDefaultZoom(WebSettings.ZoomDensity.FAR);
        } else {
            webSettings.setDefaultZoom(WebSettings.ZoomDensity.MEDIUM);
        }

        /**
         * 用WebView显示图片，可使用这个参数 设置网页布局类型： 1、LayoutAlgorithm.NARROW_COLUMNS ：
         * 适应内容大小 2、LayoutAlgorithm.SINGLE_COLUMN:适应屏幕，内容将自动缩放
         */
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);

//        view.getSettings().setJavaScriptEnabled(true);
//        view.getSettings().setBuiltInZoomControls(true);
//        view.getSettings().setSupportZoom(true);
//        view.setSaveEnabled(true);
    }

    private void addUserInfo() {
        String id = "-1";
        if (mGoodDetInfo.getPid() == 0) {
            id = mGoodDetInfo.getPuserid() + "";
        } else {
            id = mGoodDetInfo.getUserid() + "";
        }
        mUserServer.requestUserOnNoLogin(id, new UserServer.OnUserOnNoLoginInfoCallBack() {
            @Override
            public void onCallBcak(UserOnNoLoginInfo info) {
                if (info == null || info.isNull()) {
                    tvName.setText("用户信息加载失败");
                } else {
                    tvName.setText(info.getNickname());
                    try {
                        Glide.with(mAct)
                                .load(NetConstants.IMG_HOST + info.getPic())
                                .into(ivAvater);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    mUserInfo = info;
                }
            }
        });
    }

    private CircleImageView ivIcon;
    private TextView name;
    private TextView tvContent;
    private TextView tvTime;
    private TextView tvStar;
    private View viewLine1;

    private void addCommon(List<CommonInfo> infos) {
        llAddGoodsComments.removeAllViews();
        for (int i = 0; infos != null && i < (infos.size() >= 6 ? 6 : infos.size()); i++) {
            View view = View.inflate(mAct, R.layout.act_common_list_item, null);

            ivIcon = (CircleImageView) view.findViewById(R.id.iv_icon);
            name = (TextView) view.findViewById(R.id.name);
            tvContent = (TextView) view.findViewById(R.id.tv_content);
            tvTime = (TextView) view.findViewById(R.id.tv_time);
            tvStar = (TextView) view.findViewById(R.id.tv_star);
            viewLine1 = view.findViewById(R.id.view_line);

            NoSrcollGridView gv = (NoSrcollGridView) view.findViewById(R.id.gv_imgs);
            ImageAdapter1 imgAdapter = new ImageAdapter1(mAct);
            gv.setAdapter(imgAdapter);

            CommonInfo info = infos.get(i);

            String ings = info.getImgs();
            if (TextUtils.isEmpty(ings)) {
                gv.setVisibility(View.GONE);
            } else {
                gv.setVisibility(View.VISIBLE);
                String imgs[] = ings.split(",");
                List<String> imgss = new ArrayList<>();
                for (int j = 0; j < imgs.length; j++) {
                    imgss.add(NetConstants.IMG_HOST + imgs[j]);
                }
                imgAdapter.upDateList(imgss);
            }

            name.setText(info.getName());
            tvContent.setText(info.getText());
            tvTime.setText(TimeUtil.getTime(info.getStime()));
            Glide.with(mAct).load(NetConstants.IMG_HOST + info.getPic()).into(ivIcon);
            tvStar.setText(info.getStar() + "");

            llAddGoodsComments.addView(view);
        }
        if (infos == null || infos.size() < 6) {
            tvToCommon.setText("暂无评论了");
            tvToCommon.setEnabled(false);
        } else {
            tvToCommon.setText("查看更多评论");
            tvToCommon.setEnabled(true);
        }
    }

    private void changeUIShow(int mark) {
        llAddGoodsComments.setVisibility(View.GONE);
        llLoading.setVisibility(View.GONE);
        llNoData.setVisibility(View.GONE);
        switch (mark) {
            case -2:
                llLoading.setVisibility(View.VISIBLE);
                break;
            case -1:
            case 0:
                llNoData.setVisibility(View.VISIBLE);
                break;
            case 1:
                llAddGoodsComments.setVisibility(View.VISIBLE);
                break;
        }
    }

    private double getMoney(GoodDetInfo mGoodDetInfo) {
        BigDecimal d = new BigDecimal(mGoodDetInfo.getPrice()).multiply(new BigDecimal(mSelectGoodsStyleDialog.getmGoodsCount()));
        return d.doubleValue();
    }

    private void upDataToSlider(String[] imgs) {
        if (imgs == null)
            return;
        slider.removeAllSliders();
        for (int i = 0; i < imgs.length; i++) {
            MySliderTextView textSliderView = new MySliderTextView(getActivity());
            textSliderView
                    .image(NetConstants.IMG_HOST + imgs[i])
                    .setScaleType(BaseSliderView.ScaleType.FitCenterCrop)
                    .setOnSliderClickListener(this);
            textSliderView.bundle(new Bundle());
            textSliderView.getBundle().putString("id", "item" + i);
            slider.addSlider(textSliderView);
        }
    }

    private int mScrollY = 0;

    @Override
    public void initListener() {
        svGoods.addScrollCallBack(new ObservableScrollView.OnScrollCallBack() {
            @Override
            public void onScrollCallBack(int deltaX, int deltaY, int y) {
                mScrollY = svGoods.getScrollY();
                MyLogger.e("deltaX" + deltaY + " mScrollY = " + mScrollY);
                viewLine.setAlpha(getScrollProgress());
                viewBg.setAlpha(getScrollProgress());
                viewLBg.setAlpha(1f - getScrollProgress());
                viewRBg.setAlpha(1f - getScrollProgress());
                tvTitle.setAlpha(getScrollProgress());
            }
        });

        tvAtOncePay.setOnClickListener(this);
        tvAddShopCar.setOnClickListener(this);
        tvToCommon.setOnClickListener(this);
        ivL.setOnClickListener(this);
        ivR.setOnClickListener(this);

        llMyOrder.setOnClickListener(this);

        tvDaiXiao.setOnClickListener(this);
    }

    private float getScrollProgress() {
        return ((float) mScrollY) / ((float) mSliderHeight);
    }

    @Override
    public void onSliderClick(BaseSliderView baseSliderView) {

    }

    @Override
    public void onClick(View v) {
        if (v == tvAddShopCar) {
            if (mGoodDetInfo == null || mGoodDetInfo.getCount() - mGoodDetInfo.getSellcount() <= 0) {
                showToast("该类商品已经卖完");
                return;
            }
            if (CommonUtil.isLoginAndToLogin(mAct, false)) {
                isAtOnce = false;
                mSelectGoodsStyleDialog.show();
            }
        } else if (v == tvAtOncePay) {
            if (mGoodDetInfo == null || mGoodDetInfo.getCount() - mGoodDetInfo.getSellcount() <= 0) {
                showToast("该类商品已经卖完");
                return;
            }
            if (CommonUtil.isLoginAndToLogin(mAct, false)) {
                isAtOnce = true;
                mSelectGoodsStyleDialog.show();
            }
        } else if (v == ivL) {
            finish();
        } else if (v == ivR) {
            HomeActivity.startThisActivityToShopCar(mAct);
        } else if (v == llMyOrder) {
            if (mUserInfo == null) {
                showToast("用户为空");
            } else {
                UserGoodsAndCommonActivity.startThisActivity(mAct,
                        mUserInfo.getPhone(),
                        mUserInfo.getId(),
                        mUserInfo.getPic(),
                        mUserInfo.getNickname(),
                        mUserInfo.getQm());
            }
        } else if (v == tvToCommon) {
            MyMsgActivity.startThisActivity(mAct, mGoodsId);
        } else if (v == tvDaiXiao) {
            if (mGoodDetInfo != null) {
                if (isCancel){
                    new DaoXiaoOperationServer(this).requestCancelDaoXiao(mGoodDetInfo.getId() + "");
                } else {
                    AddDaiXiaoActivity.startThisActivity(mAct, mGoodDetInfo);
                }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            loadGoodsDet();
        }
    }
}
