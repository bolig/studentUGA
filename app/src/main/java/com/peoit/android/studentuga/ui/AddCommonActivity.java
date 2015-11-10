package com.peoit.android.studentuga.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.peoit.android.peoit_lib.base.BaseActivity;
import com.peoit.android.studentuga.R;
import com.peoit.android.studentuga.config.CommonUtil;
import com.peoit.android.studentuga.config.NetConstants;
import com.peoit.android.studentuga.entity.ShopCarInfo;
import com.peoit.android.studentuga.net.server.AddLiuCommonServer;
import com.peoit.android.studentuga.net.server.CommonServer;
import com.peoit.android.studentuga.ui.adapter.ImageAdapter;

import java.util.ArrayList;
import java.util.List;

import me.iwf.photopicker.PhotoPickerActivity;

public class AddCommonActivity extends BaseActivity implements View.OnClickListener {
    private ImageView ivIcon;
    private TextView tvTitle;
    private TextView tvPrice;
    private EditText etCommon;
    private ImageView ivStar1;
    private ImageView ivStar2;
    private ImageView ivStar3;
    private ImageView ivStar4;
    private ImageView ivStar5;
    private TextView tvCommon;
    private StarType lastStarType;
    private String mLiuCommon;

    private GridView gvImgs;
    private TextView tvImgCount;

    private List<String> imgs = new ArrayList<>();
    private ImageAdapter imgAdapter;
    private TextView tvEdit;
    private String mOrderId;
    private String mSellId;
    private ShopCarInfo mGoods;
    private LinearLayout llGoods;
    private View viewLine;

    private LinearLayout llStar;
    private int mStar;
    private TextView tvTitle1;
    private String mUid;
    private LinearLayout ll_img;

    private void assignViews() {
        ivIcon = (ImageView) findViewById(R.id.iv_icon);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        tvTitle1 = (TextView) findViewById(R.id.tv_title1);
        tvPrice = (TextView) findViewById(R.id.tv_price);
        etCommon = (EditText) findViewById(R.id.et_common);
        ivStar1 = (ImageView) findViewById(R.id.iv_star1);
        ivStar2 = (ImageView) findViewById(R.id.iv_star2);
        ivStar3 = (ImageView) findViewById(R.id.iv_star3);
        ivStar4 = (ImageView) findViewById(R.id.iv_star4);
        ivStar5 = (ImageView) findViewById(R.id.iv_star5);
        tvCommon = (TextView) findViewById(R.id.tv_common);

        gvImgs = (GridView) findViewById(R.id.gv_imgs);
        tvImgCount = (TextView) findViewById(R.id.tv_imgCount);
        tvImgCount.setText("(0/6)");
        tvEdit = (TextView) findViewById(R.id.tv_edit);

        llGoods = (LinearLayout) findViewById(R.id.ll_goods);
        llStar = (LinearLayout) findViewById(R.id.ll_star);

        ll_img = (LinearLayout) findViewById(R.id.ll_img);
        viewLine = findViewById(R.id.view_line);
    }

    public static void startThisActivity(Activity mAc, String uid) {
        if (CommonUtil.isLoginAndToLogin(mAc, false)) {
            Intent intent = new Intent(mAc, AddCommonActivity.class);
            intent.putExtra("uid", uid);
            mAc.startActivityForResult(intent, 1);
        }
    }

    public static void startThisActivity(Fragment fragment, @NonNull String orderid, @NonNull String sellid, ShopCarInfo shopCarInfo) {
        if (CommonUtil.isLoginAndToLogin(fragment.getActivity(), false)) {
            Intent intent = new Intent(fragment.getActivity(), AddCommonActivity.class);
            intent.putExtra("oid", orderid);
            intent.putExtra("sid", sellid);
            Bundle bundle = new Bundle();
            bundle.putSerializable("goods", shopCarInfo);
            intent.putExtras(bundle);
            fragment.startActivityForResult(intent, 1);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.act_add_common1);
    }

    @Override
    public void initData() {
        mOrderId = getIntent().getStringExtra("oid");
        mSellId = getIntent().getStringExtra("sid");
        mUid = getIntent().getStringExtra("uid");
        mGoods = (ShopCarInfo) getIntent().getSerializableExtra("goods");
        imgs.add("");
    }

    public boolean isLiu() {
        return TextUtils.isEmpty(mOrderId) && TextUtils.isEmpty(mSellId);
    }

    @Override
    public void initView() {
        assignViews();
        getToolbar().setBack().setTvTitle(isLiu() ? "添加留言" : "添加评论");
        tvCommon.setText(isLiu() ? "发表留言" : "发表评论");
        imgAdapter = new ImageAdapter(mAct, tvEdit);
        imgAdapter.upDateList(imgs);
        gvImgs.setAdapter(imgAdapter);
        tvEdit.setEnabled(false);
        if (mGoods != null) {
            llGoods.setVisibility(View.VISIBLE);
            viewLine.setVisibility(View.VISIBLE);
            llStar.setVisibility(View.VISIBLE);
            changeUIshow(StarType.star5);
            tvTitle1.setText(mGoods.getTitle());
            tvPrice.setText("￥" + mGoods.getPrice());
            Glide.with(mAct).load(NetConstants.IMG_HOST + mGoods.getImgurl()).error(R.drawable.noproduct).into(ivIcon);
        } else {
            ll_img.setVisibility(View.GONE);
        }
    }

    @Override
    public void initListener() {
        ivStar1.setOnClickListener(this);
        ivStar2.setOnClickListener(this);
        ivStar3.setOnClickListener(this);
        ivStar4.setOnClickListener(this);
        ivStar5.setOnClickListener(this);

        tvCommon.setOnClickListener(this);

        tvEdit.setOnClickListener(this);

        imgAdapter.setOnSelectCountCallBack(new ImageAdapter.OnSelectCountCallBack() {
            @Override
            public void onCallBack(int count) {
                tvImgCount.setText("(" + count + "/6)");
            }
        });
    }

    private void changeUIshow(StarType type) {
        if (lastStarType == type) {
            return;
        }
        lastStarType = type;
        switch (type) {
            case star0:
                ivStar1.setSelected(false);
                ivStar2.setSelected(false);
                ivStar3.setSelected(false);
                ivStar4.setSelected(false);
                ivStar5.setSelected(false);
                break;
            case star1:
                ivStar1.setSelected(true);
                ivStar2.setSelected(false);
                ivStar3.setSelected(false);
                ivStar4.setSelected(false);
                ivStar5.setSelected(false);
                break;
            case star2:
                ivStar1.setSelected(true);
                ivStar2.setSelected(true);
                ivStar3.setSelected(false);
                ivStar4.setSelected(false);
                ivStar5.setSelected(false);
                break;
            case star3:
                ivStar1.setSelected(true);
                ivStar2.setSelected(true);
                ivStar3.setSelected(true);
                ivStar4.setSelected(false);
                ivStar5.setSelected(false);
                break;
            case star4:
                ivStar1.setSelected(true);
                ivStar2.setSelected(true);
                ivStar3.setSelected(true);
                ivStar4.setSelected(true);
                ivStar5.setSelected(false);
                break;
            case star5:
                ivStar1.setSelected(true);
                ivStar2.setSelected(true);
                ivStar3.setSelected(true);
                ivStar4.setSelected(true);
                ivStar5.setSelected(true);
                break;
        }
    }

    @Override
    public void onClick(View v) {
        if (v == ivStar1) {
            changeUIshow(StarType.star1);
        } else if (v == ivStar2) {
            changeUIshow(StarType.star2);
        } else if (v == ivStar3) {
            changeUIshow(StarType.star3);
        } else if (v == ivStar4) {
            changeUIshow(StarType.star4);
        } else if (v == ivStar5) {
            changeUIshow(StarType.star5);
        } else if (v == tvCommon) {
            if (isLiu()) {
                if (match()) {
                    new AddLiuCommonServer(this).requestAddLiuCommon(mUid,
                            mLiuCommon,
                            imgs.size() >= 1 ? imgs.get(0) : "",
                            imgs.size() >= 2 ? imgs.get(1) : "",
                            imgs.size() >= 3 ? imgs.get(2) : "",
                            imgs.size() >= 4 ? imgs.get(3) : "",
                            imgs.size() >= 5 ? imgs.get(4) : "",
                            imgs.size() >= 6 ? imgs.get(5) : "");
                }
            } else {
                if (matchCommon()) {
                    new CommonServer(this).requestAddCommon(mOrderId,
                            mSellId,
                            mLiuCommon,
                            mStar + "",
                            imgs.size() >= 1 ? imgs.get(0) : "",
                            imgs.size() >= 2 ? imgs.get(1) : "",
                            imgs.size() >= 3 ? imgs.get(2) : "",
                            imgs.size() >= 4 ? imgs.get(3) : "",
                            imgs.size() >= 5 ? imgs.get(4) : "",
                            imgs.size() >= 6 ? imgs.get(5) : "");
                }
            }
        } else if (v == tvEdit) {
            String tv = tvEdit.getText().toString();
            boolean isEdit = "编辑".equals(tv);
            imgAdapter.setEdit(isEdit);
            if (isEdit) {
                tvEdit.setText("保存");
            } else {
                tvEdit.setText("编辑");
            }
        }
    }

    private boolean matchCommon() {
        mLiuCommon = etCommon.getText().toString();
        if (TextUtils.isEmpty(mLiuCommon)) {
            showToast("说点什么吧");
            return false;
        }
        mStar = lastStarType.mStarType;
        return true;
    }

    private boolean match() {
        mLiuCommon = etCommon.getText().toString();
        if (TextUtils.isEmpty(mLiuCommon)) {
            showToast("说点什么吧");
            return false;
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (data != null) {
                if (requestCode == 1) {
                    List<String> paths = data.getStringArrayListExtra(PhotoPickerActivity.KEY_SELECTED_PHOTOS);
                    if (paths == null || paths.size() == 0)
                        return;
                    imgs.remove(imgs.size() - 1);
                    for (int i = 0; i < paths.size(); i++) {
                        if (imgs != null && imgs.size() < 6) {
                            imgs.add(paths.get(i));
                        } else {
                            break;
                        }
                    }
                    if (imgs.size() < 6) {
                        tvImgCount.setText("(" + ((imgs == null || imgs.size() == 0) ? 0 : imgs.size()) + "/6)");
                        imgs.add("");
                    } else {
                        tvImgCount.setText("6/6");
                    }
                    imgAdapter.upDateList(imgs);

                    if (imgs != null || imgs.size() > 0) {
                        tvEdit.setEnabled(true);
                    }
                } else if (requestCode == 2) {

                }
            }
        }
    }

    private enum StarType {
        star0(0),
        star1(1),
        star2(2),
        star3(3),
        star4(4),
        star5(5);

        public final int mStarType;

        StarType(int type) {
            this.mStarType = type;
        }
    }
}
