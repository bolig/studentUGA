package com.peoit.android.studentuga.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.peoit.android.peoit_lib.base.BaseActivity;
import com.peoit.android.studentuga.R;
import com.peoit.android.studentuga.config.CommonUtil;
import com.peoit.android.studentuga.entity.AddressInfo;
import com.peoit.android.studentuga.net.server.AddressManageServer;
import com.peoit.android.studentuga.ui.dialog.SelectCityDialog;

public class AddAddressActivity extends BaseActivity implements View.OnClickListener {
    private EditText etContact;
    private EditText etPostCode;
    private EditText etRealAddress;
    private EditText etUserName;

    private TextView tvCancel;
    private TextView tvSave;
    private TextView tvSite;

    private LinearLayout llSelectSite;

    private AddressInfo mCurrentAddressInfo;
    private SelectCityDialog mSelectSiteDialog;
    private String consigneeName;
    private String contact;
    private String mPostCode;
    private String mAddress;
    private String mRealAddress;
    private String mSelectPro;
    private String mSelectCity;
    private String mSelectCounty;
    private boolean isDefault;

    private void assignViews() {
        etUserName = (EditText) findViewById(R.id.et_userName);
        etContact = (EditText) findViewById(R.id.et_contact);
        etPostCode = (EditText) findViewById(R.id.et_postCode);
        tvSite = (TextView) findViewById(R.id.tv_site);
        etRealAddress = (EditText) findViewById(R.id.et_real_address);
        tvCancel = (TextView) findViewById(R.id.tv_cancel);
        tvSave = (TextView) findViewById(R.id.tv_save);

        llSelectSite = (LinearLayout) findViewById(R.id.ll_select_site);
    }

    public static void startThisActivity(Activity mAc, AddressInfo info, boolean isDefault) {
        if (CommonUtil.isLoginAndToLogin(mAc, false)){
            Intent intent = new Intent(mAc, AddAddressActivity.class);
            intent.putExtra("isDefault", isDefault);
            if (info != null) {
                Bundle extras = new Bundle();
                extras.putSerializable("address", info);
                intent.putExtras(extras);
            }
            mAc.startActivityForResult(intent, 1);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_add_address);
    }

    @Override
    public void initData() {
        if (getIntent().getSerializableExtra("address") != null)
            mCurrentAddressInfo = (AddressInfo) getIntent().getSerializableExtra("address");
        isDefault = getIntent().getBooleanExtra("isDefault", false);
    }

    /**
     * 半段当前是不是修改收货地址
     *
     * @return
     */
    private boolean isMod() {
        return mCurrentAddressInfo != null;
    }

    @Override
    public void initView() {
        assignViews();

        getToolbar().setTvTitle(isMod() ? "修改收货地址" : "添加收货地址").setBack();

        if (isMod()) {
            etUserName.setText(mCurrentAddressInfo.getConsignorName());
            etContact.setText(mCurrentAddressInfo.getTelephone());
            etPostCode.setText(mCurrentAddressInfo.getPostalCode());
            etRealAddress.setText(mCurrentAddressInfo.getAddress());

            tvSite.setText(mCurrentAddressInfo.getProvince()
                    + mCurrentAddressInfo.getMunicipality()
                    + mCurrentAddressInfo.getCounty());
        }
    }

    @Override
    public void initListener() {
        llSelectSite.setOnClickListener(this);
        tvCancel.setOnClickListener(this);
        tvSave.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == llSelectSite) {
            selectSite();
        } else if (v == tvCancel) {
            finish();
        } else if (v == tvSave) {
            if (isMod()) {
                if (checkModScan()) {
                    if (!TextUtils.isEmpty(mSelectPro)) {
                        mCurrentAddressInfo.setProvince(mSelectPro);
                    }
                    if (!TextUtils.isEmpty(mSelectCity)) {
                        mCurrentAddressInfo.setMunicipality(mSelectCity);
                    }
                    if (!TextUtils.isEmpty(mSelectCounty)) {
                        mCurrentAddressInfo.setCounty(mSelectCounty);
                    }
                    mCurrentAddressInfo.setAddress(mRealAddress);
                    mCurrentAddressInfo.setConsignorName(consigneeName);
                    mCurrentAddressInfo.setPostalCode(mPostCode);
                    mCurrentAddressInfo.setTelephone(contact);
                    new AddressManageServer(this).requestModAddress(mCurrentAddressInfo, true);
                }
            } else {
                if (checkUserScan()) {
                    new AddressManageServer(this).requestAddAddress(mRealAddress,
                            mSelectPro,
                            mSelectCity,
                            mSelectCounty,
                            consigneeName,
                            mPostCode,
                            contact,
                            null,
                            isDefault);
                }
            }
        }
    }

    private boolean checkModScan() {
        consigneeName = etUserName.getText().toString();
        if (TextUtils.isEmpty(consigneeName)) {
            showToast("收货人姓名不能为空");
            return false;
        }
        contact = etContact.getText().toString();
        if (TextUtils.isEmpty(contact)) {
            showToast("联系方式不能为空");
            return false;
        }
        mPostCode = etPostCode.getText().toString();
        if (TextUtils.isEmpty(mPostCode)) {
            showToast("邮政编码不能为空");
            return false;
        }
        mAddress = tvSite.getText().toString();
        if (TextUtils.isEmpty(mAddress)) {
            showToast("请选择所在位置");
            return false;
        }
        mRealAddress = etRealAddress.getText().toString();
        if (TextUtils.isEmpty(mRealAddress)) {
            showToast("详细地址不能为空");
            return false;
        }
        return true;
    }

    private boolean checkUserScan() {
        consigneeName = etUserName.getText().toString();
        if (TextUtils.isEmpty(consigneeName)) {
            showToast("请输入收货人姓名");
            return false;
        }
        contact = etContact.getText().toString();
        if (TextUtils.isEmpty(contact)) {
            showToast("请输入联系方式");
            return false;
        }
        mPostCode = etPostCode.getText().toString();
        if (TextUtils.isEmpty(mPostCode)) {
            showToast("请输入邮政编码");
            return false;
        }
        mAddress = tvSite.getText().toString();
        if (TextUtils.isEmpty(mAddress)) {
            showToast("请选择所在位置");
            return false;
        }
        mRealAddress = etRealAddress.getText().toString();
        if (TextUtils.isEmpty(mRealAddress)) {
            showToast("请输入详细地址");
            return false;
        }
        return true;
    }

    private void selectSite() {
        mSelectSiteDialog = new SelectCityDialog(mAct);
        mSelectSiteDialog.show();
        mSelectSiteDialog.setOnAreaChangeListener(new SelectCityDialog.OnAreaChangeListener() {
            @Override
            public void onAreaChange(String pro, String city, String county) {
                //showToast(pro + city + county);
                tvSite.setText(pro + city + county);
                mSelectPro = pro;
                mSelectCity = city;
                mSelectCounty = county;
            }
        });
    }
}
