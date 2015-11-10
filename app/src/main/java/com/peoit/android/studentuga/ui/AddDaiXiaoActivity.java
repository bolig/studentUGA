package com.peoit.android.studentuga.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.peoit.android.peoit_lib.base.BaseActivity;
import com.peoit.android.studentuga.R;
import com.peoit.android.studentuga.config.NetConstants;
import com.peoit.android.studentuga.entity.GoodDetInfo;
import com.peoit.android.studentuga.net.server.DaoXiaoOperationServer;

public class AddDaiXiaoActivity extends BaseActivity {
    private LinearLayout llGoods;
    private ImageView ivIcon;
    private TextView tvTitle;
    private TextView tvPrice;
    private View viewLine;
    private EditText etPassword;
    private EditText etPrice;
    private TextView tvDaixiao;
    private GoodDetInfo mGoodInfo;
    private String mTitle;

    private String mPrice;

    private void assignViews() {
        llGoods = (LinearLayout) findViewById(R.id.ll_goods);
        ivIcon = (ImageView) findViewById(R.id.iv_icon);
        tvTitle = (TextView) findViewById(R.id.tv_title1);
        tvPrice = (TextView) findViewById(R.id.tv_price);
        viewLine = findViewById(R.id.view_line);
        etPassword = (EditText) findViewById(R.id.et_password);
        etPrice = (EditText) findViewById(R.id.et_price);
        tvDaixiao = (TextView) findViewById(R.id.tv_daixiao);
    }

    public static void startThisActivity(Activity mAc, GoodDetInfo info) {
        Intent intent = new Intent(mAc, AddDaiXiaoActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("goods", info);
        intent.putExtras(bundle);
        mAc.startActivityForResult(intent, 0);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_add_dai_xiao);
    }

    @Override
    public void initData() {
        mGoodInfo = (GoodDetInfo) getIntent().getSerializableExtra("goods");
    }

    @Override
    public void initView() {
        assignViews();
        getToolbar().setBack().setTvTitle("申请代销");
        Glide.with(mAct).load(NetConstants.IMG_HOST + mGoodInfo.getImgurl()).error(R.drawable.noproduct).into(ivIcon);
        tvTitle.setText(mGoodInfo.getTitle());
        tvPrice.setText("￥" + mGoodInfo.getPrice());

        etPrice.setHint("原价￥" + mGoodInfo.getPrice() + ", 建议零售价￥" + mGoodInfo.getGuideprice());
    }

    @Override
    public void initListener() {
        tvDaixiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (match()) {
                    new DaoXiaoOperationServer(mAct).requestDaoXiao(mGoodInfo.getId() + "", mTitle, mPrice);
                }
            }
        });
    }

    private boolean match() {
        mTitle = etPassword.getText().toString();
        if (TextUtils.isEmpty(mTitle)) {
            showToast("请输入您修改的商品名称");
            return false;
        }
        mPrice = etPrice.getText().toString();
        if (TextUtils.isEmpty(mPrice)) {
            showToast("请输入您修改的商品价格");
            return false;
        }
        double d = Double.valueOf(mPrice);
        if (d < mGoodInfo.getPrice()) {
            showToast("您修改价格必须大于原有价格");
            return false;
        }
        return true;
    }
}
