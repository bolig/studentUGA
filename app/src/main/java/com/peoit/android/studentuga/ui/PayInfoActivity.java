package com.peoit.android.studentuga.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.peoit.android.peoit_lib.base.BaseActivity;
import com.peoit.android.studentuga.R;
import com.peoit.android.studentuga.config.CommonUtil;
import com.peoit.android.studentuga.entity.AddressInfo;
import com.peoit.android.studentuga.entity.OrderInfo;
import com.peoit.android.studentuga.entity.ShopCarInfo;
import com.peoit.android.studentuga.net.BaseCallBack;
import com.peoit.android.studentuga.net.BaseServer;
import com.peoit.android.studentuga.net.server.AddressManageServer;
import com.peoit.android.studentuga.net.server.OrderServer;
import com.peoit.android.studentuga.ui.adapter.PayInfoAdapter;

import java.math.BigDecimal;
import java.util.List;

public class PayInfoActivity extends BaseActivity {
    private ListView lvStyle;
    private LinearLayout llBottom;
    private TextView tvTotalMoney1;
    private TextView tvAddRemark;
    private TextView tvShopClose;

    private TextView tvName;
    private TextView tvPhone;
    private TextView tvAddress;
    private LinearLayout llLoading;
    private LinearLayout llError;
    private FrameLayout flUiShow;
    private TextView tvAddAddress;

    private LinearLayout llMyMsg;
    private TextView tvSendWay;
    private TextView tvGoodsCount;
    private TextView tvTatolMoney;
    private List<ShopCarInfo> mSelectGoods;
    private PayInfoAdapter mPayInfoAdapter;
    private String mTotalMoney;
    private AddressInfo mAddressInfo;
    private boolean isFromShopCar;

    private void assignViews() {
        lvStyle = (ListView) findViewById(R.id.lv_style);
        llBottom = (LinearLayout) findViewById(R.id.ll_bottom);
        tvTotalMoney1 = (TextView) findViewById(R.id.tv_total_money);
        tvAddRemark = (TextView) findViewById(R.id.tv_add_remark);
        tvShopClose = (TextView) findViewById(R.id.tv_shop_close);
    }

    public static void startThisActivity(Activity mAc, boolean isFromShopCar) {
        if (CommonUtil.isLoginAndToLogin(mAc, false)) {
            Intent intent = new Intent(mAc, PayInfoActivity.class);
            intent.putExtra("isFromShopCar", isFromShopCar);
            mAc.startActivityForResult(intent, 1);
        }
    }

    public static void startThisActivity(Fragment mAc, boolean isFromShopCar) {
        Intent intent = new Intent(mAc.getActivity(), PayInfoActivity.class);
        intent.putExtra("isFromShopCar", isFromShopCar);
        mAc.startActivityForResult(intent, 1);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_pay_info);
    }

    @Override
    public void initData() {
        mSelectGoods = CommonUtil.mSelectGoods;
        isFromShopCar = getIntent().getBooleanExtra("isFromShopCar", false);
        if (mSelectGoods == null || mSelectGoods.size() == 0) {
            showToast("数据传输错误");
            finish();
            return;
        }
        mTotalMoney = getTotalMoney(mSelectGoods);
    }

    @Override
    public void initView() {
        assignViews();
        getToolbar().setTvTitle("确认订单").setBack();
        addListViewHeader();
        addListViewFooter();
        mPayInfoAdapter = new PayInfoAdapter(this);
        mPayInfoAdapter.upDateList(mSelectGoods);
        lvStyle.setAdapter(mPayInfoAdapter);

        tvTatolMoney.setText("￥" + mTotalMoney);
        tvTotalMoney1.setText("￥" + mTotalMoney);
    }

    private void addListViewHeader() {
        View headerView = getLayoutInflater().inflate(R.layout.act_pay_info_list_header, null);

        tvName = (TextView) headerView.findViewById(R.id.tv_name);
        tvPhone = (TextView) headerView.findViewById(R.id.tv_phone);
        tvAddress = (TextView) headerView.findViewById(R.id.tv_address);
        llLoading = (LinearLayout) headerView.findViewById(R.id.ll_loading);
        llError = (LinearLayout) headerView.findViewById(R.id.ll_error);
        tvAddAddress = (TextView) headerView.findViewById(R.id.add_address);
        flUiShow = (FrameLayout) headerView.findViewById(R.id.fl_uiShow);

        headerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AdressManageActivity.startThisActivity(mAct, 2, true);
            }
        });

        tvAddAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddAddressActivity.startThisActivity(mAct, null, true);
            }
        });

        changeUIShow(Type.loading);

        addAddress();

        lvStyle.addHeaderView(headerView);
    }

    private void addAddress() {
        new AddressManageServer(this).requestDefaultAddress(new BaseServer.OnSuccessCallBack() {
            @Override
            public void onSuccess(int mark) {
                switch (mark) {
                    case 1:
                        mAddressInfo = CommonUtil.mDefaultAddress;
                        tvName.setText("收货人:" + mAddressInfo.getConsignorName());
                        tvPhone.setText("电话:" + mAddressInfo.getTelephone());
                        tvAddress.setText("收货地址:" + mAddressInfo.getAddress());
                        changeUIShow(Type.showData);
                        break;
                    case 2:
                        changeUIShow(Type.error);
                        break;
                }
            }
        });
    }

    private void addListViewFooter() {
        View footerView = getLayoutInflater().inflate(R.layout.act_pay_info_list_footer, null);
        llMyMsg = (LinearLayout) footerView.findViewById(R.id.ll_myMsg);
        tvSendWay = (TextView) footerView.findViewById(R.id.tv_sendWay);
        tvGoodsCount = (TextView) footerView.findViewById(R.id.tv_GoodsCount);
        tvTatolMoney = (TextView) footerView.findViewById(R.id.tv_tatolMoney);
        tvGoodsCount.setText("共" + mSelectGoods.size() + "件商品    合计:");
        lvStyle.addFooterView(footerView);
    }

    private String getTotalMoney(List<ShopCarInfo> selectShopCarGoods) {
        BigDecimal d1 = BigDecimal.valueOf(0d);
        for (int i = 0; i < selectShopCarGoods.size(); i++) {
            BigDecimal d2 = BigDecimal.valueOf(selectShopCarGoods.get(i).getPrice());
            d1 = d1.add(d2);
        }
        return d1.doubleValue() + "";
    }

    private String getOrderTotalMoney(List<OrderInfo> selectShopCarGoods) {
        BigDecimal d1 = BigDecimal.valueOf(0d);
        for (int i = 0; i < selectShopCarGoods.size(); i++) {
            BigDecimal d2 = BigDecimal.valueOf(selectShopCarGoods.get(i).getTotalPrice());
            d1 = d1.add(d2);
        }
        return d1.doubleValue() + "";
    }

    @Override
    public void initListener() {
        tvShopClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mAddressInfo != null) {
                    if (isFromShopCar) {
                        showLoadingDialog("正在生成订单...");
                        new OrderServer(mAct).requestAddShopCarOrder(getIds(), mAddressInfo.getId() + "", new BaseCallBack<OrderInfo>() {
                            @Override
                            public void onFinish() {
                                hideLoadingDialog();
                            }

                            @Override
                            public void onResponseSuccessList(List<OrderInfo> result) {
                                if (result == null || result.size() == 0) {
                                    showToast("生成订单失败");
                                    return;
                                }
                                CommonUtil.isChangeOrder = true;
                                String ids = getOrderIds(result);
                                String title = "";
                                for (int i = 0; i < result.size(); i++) {
                                    title += result.get(i).getTitle();
                                }
                                PayActivity.startThisActivity(mAct, ids, getOrderTotalMoney(result), title);
                                finish();
                            }

                            @Override
                            protected void onResponseFailure(int statusCode, String msg) {
                                onResponseFailure(statusCode, msg);
                                showToast("网络连接错误");
                            }
                        });
                    } else {
                        ShopCarInfo info = mSelectGoods.get(0);
                        new OrderServer(mAct).requestAtonceOrder(info.getNumber() + "",
                                info.getId() + "",
                                mAddressInfo.getId() + "",
                                info.getSpec(), "");
                    }
                } else {
                    showToast("请选择收货地址");
                }
            }
        });
    }

    private String getOrderIds(List<OrderInfo> result) {
        String ids = "";
        for (int i = 0; i < result.size(); i++) {
            ids += result.get(i).getId() + (i == result.size() - 1 ? "" : ",");
        }
        return ids;
    }

    private String getIds() {
        String ids = "";
        for (int i = 0; i < mSelectGoods.size(); i++) {
            ids += mSelectGoods.get(i).getId() + (i == mSelectGoods.size() - 1 ? "" : ",");
        }
        return ids;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {
                addAddress();
            } else if (requestCode == 2) {
                mAddressInfo = (AddressInfo) data.getSerializableExtra("data");
                CommonUtil.mDefaultAddress = mAddressInfo;
                tvName.setText("收货人:" + mAddressInfo.getConsignorName());
                tvPhone.setText("电话:" + mAddressInfo.getTelephone());
                tvAddress.setText("收货地址:" + mAddressInfo.getAddress());
                changeUIShow(Type.showData);
            }
        }
    }

    public void changeUIShow(Type type) {
        if (type == Type.showData) {
            flUiShow.setVisibility(View.INVISIBLE);
        } else {
            flUiShow.setVisibility(View.VISIBLE);
        }
        switch (type) {
            case loading:
                llLoading.setVisibility(View.VISIBLE);
                llError.setVisibility(View.INVISIBLE);
                break;
            case error:
                llLoading.setVisibility(View.INVISIBLE);
                llError.setVisibility(View.VISIBLE);
                break;
        }
    }

    public enum Type {
        loading,
        error,
        showData
    }
}
