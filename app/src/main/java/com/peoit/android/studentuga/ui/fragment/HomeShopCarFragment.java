package com.peoit.android.studentuga.ui.fragment;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.peoit.android.peoit_lib.base.BasePagerFragment;
import com.peoit.android.peoit_lib.view.CustomActionBar;
import com.peoit.android.studentuga.R;
import com.peoit.android.studentuga.config.CommonUtil;
import com.peoit.android.studentuga.entity.ShopCarInfo;
import com.peoit.android.studentuga.net.BaseServer;
import com.peoit.android.studentuga.net.server.ShopCarServer;
import com.peoit.android.studentuga.ui.HomeActivity;
import com.peoit.android.studentuga.ui.LoginActivity;
import com.peoit.android.studentuga.ui.PayInfoActivity;
import com.peoit.android.studentuga.ui.adapter.ShopCarAdapter1;
import com.peoit.android.studentuga.ui.showUI.SimpleShowUiShow;

import java.math.BigDecimal;
import java.util.List;

/**
 * author:libo
 * time:2015/9/8
 * E-mail:boli_android@163.com
 * last: ...
 */
public class HomeShopCarFragment extends BasePagerFragment implements View.OnClickListener {
    private ListView lvShop;
    private LinearLayout llBottom;
    private LinearLayout llSelectAll;
    private TextView tvTotalMoney;
    private TextView tvAddRemark;
    private TextView tvShopClose;
    private Toolbar toolbar;

    private View action_title;
    private CustomActionBar actionBar;
    private FrameLayout fl_root;
    private SimpleShowUiShow mUiShow;
    private ShopCarAdapter1 statAdapter;
    private ShopCarServer mShopCarServer;
    private List<ShopCarInfo> mSelectGoods;

    @Override
    public View createView(LayoutInflater inflater, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frag_shop_car, null);
    }

    @Override
    public void initData() {
        mShopCarServer = new ShopCarServer((HomeActivity) getActivity());
    }

    @Override
    public void initView() {
        lvShop = (ListView) findViewById(R.id.lv_shop);

        llBottom = (LinearLayout) findViewById(R.id.ll_bottom);
        llSelectAll = (LinearLayout) findViewById(R.id.ll_select_all);

        tvTotalMoney = (TextView) findViewById(R.id.tv_total_money);
        tvAddRemark = (TextView) findViewById(R.id.tv_add_remark);
        tvShopClose = (TextView) findViewById(R.id.tv_shop_close);

        statAdapter = new ShopCarAdapter1(getActivity());

        lvShop.setAdapter(statAdapter);

        action_title = findViewById(com.peoit.android.peoit_lib.R.id.title_bar);
        actionBar = (CustomActionBar) findViewById(com.peoit.android.peoit_lib.R.id.actionbar);
        actionBar.setTvTitle("购物车");
        // 经测试在代码里直接声明透明状态栏更有效
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            action_title.setVisibility(View.VISIBLE);
        } else {
            action_title.setVisibility(View.GONE);
        }

        fl_root = (FrameLayout) findViewById(R.id.fl_root);
        mUiShow = new SimpleShowUiShow(getActivity());
        mUiShow.setRootView(fl_root);
    }

    @Override
    public void initListener() {
        llSelectAll.setOnClickListener(this);
        tvShopClose.setOnClickListener(this);

        statAdapter.setOnShopCarGoodsChangeListener(new ShopCarAdapter1.OnShopCarGoodsChangeListener() {
            @Override
            public void onGoodsChange(List<ShopCarInfo> selectShopCarGoods, List<ShopCarInfo> allGoods, boolean isSelectAllGoods) {
//                isSelectAllGoodsAtLate = isSelectAllGoods;
                llSelectAll.setSelected(isSelectAllGoods);
                if (allGoods == null || allGoods.size() == 0) {
                    mShopCarServer.loadShopCarList(mUiShow, statAdapter);
                    tvShopClose.setText("结算(" + 0 + ")");
                    tvTotalMoney.setText("￥0.00");
                    return;
                }
                if (selectShopCarGoods == null)
                    return;
                mSelectGoods = selectShopCarGoods;
                tvShopClose.setText("结算(" + selectShopCarGoods.size() + ")");
                tvTotalMoney.setText(getTotalMoney(selectShopCarGoods));
            }
        });
        statAdapter.setUpdataSuccessCallBack(new BaseServer.OnSuccessCallBack() {
            @Override
            public void onSuccess(int mark) {
                switch (mark) {
                    case 0:
                        ((HomeActivity) getActivity()).showLoadingDialog("正在保存...");
                        break;
                    case 1:
                        llSelectAll.setSelected(false);
                        tvTotalMoney.setText("￥0.0");
                        mShopCarServer.loadShopCarList(null, statAdapter);
                        break;
                    case 2:

                        break;
                }
            }
        });
    }

    private String getTotalMoney(List<ShopCarInfo> selectShopCarGoods) {
        BigDecimal d1 = BigDecimal.valueOf(0d);
//        double money = 0d;
        for (int i = 0; i < selectShopCarGoods.size(); i++) {
//            ShopCarInfo info = selectShopCarGoods.get(i);
//            money +=
            BigDecimal d2 = BigDecimal.valueOf(selectShopCarGoods.get(i).getPrice());
            d1 = d1.add(d2);
        }
        return "￥" + d1.doubleValue();
    }

    @Override
    public void onClick(View v) {
        if (v == llSelectAll) {
            llSelectAll.setSelected(!llSelectAll.isSelected());
            selectAllGoods(llSelectAll.isSelected());
            // isSelectAllGoodsAtLate = llSelectAll.isSelected();
        } else if (v == tvShopClose) {
            if (mSelectGoods == null || mSelectGoods.size() == 0) {
                showToast("请至少选择一样商品");
                return;
            }
            CommonUtil.setSelectGoodsGoods(mSelectGoods);
            PayInfoActivity.startThisActivity(getActivity(), true);
        }
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            statAdapter.notifyDataSetChanged();
            statAdapter.changeShopGoods();
        }
    };

    private void selectAllGoods(final boolean isSlect) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (ShopCarInfo info :
                        statAdapter.getDatas()) {
                    info.setSelect(isSlect);
                }
                mHandler.sendEmptyMessage(0);
            }
        }).start();
    }

    @Override
    public void onResume() {
        super.onResume();
        //llSelectAll.setSelected(isSelectAllGoodsAtLate);
        if (CommonUtil.isLogin()) {
//            if (CommonUtil.isChangeOrder) {
//                mShopCarServer.loadShopCarList(mUiShow, statAdapter);
//            }
            // TODO:
            mUiShow.setTvReload("重新加载", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mShopCarServer.loadShopCarList(mUiShow, statAdapter);
                }
            });
            mShopCarServer.loadShopCarList(mUiShow, statAdapter);
        } else {
            mUiShow.setTvReload("去登录", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LoginActivity.startThisActivity(getActivity());
                }
            });
            mUiShow.setIvErrorImg(R.drawable.noproduct);
            mUiShow.setTvErrorMsg("您当前还没有登录个人账户, 无法获取您的购物车信息");
            mUiShow.showError();
        }
    }
}
