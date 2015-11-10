package com.peoit.android.studentuga.ui.fragment;

import android.graphics.PointF;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.peoit.android.peoit_lib.base.BasePagerFragment;
import com.peoit.android.studentuga.R;
import com.peoit.android.studentuga.config.CommonUtil;
import com.peoit.android.studentuga.config.NetConstants;
import com.peoit.android.studentuga.enuml.UserType;
import com.peoit.android.studentuga.net.BaseServer;
import com.peoit.android.studentuga.net.server.UserServer;
import com.peoit.android.studentuga.ui.DaiXiaoActivity;
import com.peoit.android.studentuga.ui.GoodsCommonActivity;
import com.peoit.android.studentuga.ui.HomeActivity;
import com.peoit.android.studentuga.ui.LoginActivity;
import com.peoit.android.studentuga.ui.MyGoodsActivity;
import com.peoit.android.studentuga.ui.MyMsgActivity;
import com.peoit.android.studentuga.ui.MyOrderOptionActivity;
import com.peoit.android.studentuga.ui.MyRankingActivity;
import com.peoit.android.studentuga.ui.MyTeacherActivity;
import com.peoit.android.studentuga.ui.UserInfoActivity;
import com.peoit.android.studentuga.ui.WealthActivity;
import com.peoit.android.studentuga.ui.showUI.SimpleShowUiShow;
import com.peoit.android.studentuga.uitl.MyLogger;
import com.peoit.android.studentuga.view.CircleImageView;
import com.peoit.android.studentuga.view.ObservableScrollView;

/**
 * author:libo
 * time:2015/9/14
 * E-mail:boli_android@163.com
 * last: ...
 */
public class HomeUserFragment2 extends BasePagerFragment implements View.OnClickListener {

    private ObservableScrollView osvScroll;
    private FrameLayout flScrollRoot;
    private FrameLayout flScrollHeader;
    private ImageView ivUserBg;
    private FrameLayout flBody;
    private LinearLayout llMyOrder;
    private LinearLayout llMyGoods;
    private LinearLayout llMyEval;
    private LinearLayout llMyMsg;
    private LinearLayout llWealth;
    private LinearLayout llMyRanking;
    private LinearLayout llMyTeacherOrStudent;
    private TextView tvMyTeacherOrStudent;
    private FrameLayout flBar;
    private CircleImageView ivAvater;
    private TextView tvUserName;
    private TextView tvSign;
    private ImageView ivEdit;

    private float imgHeightY;
    private float scaleY = 1f;
    //    private String nativeImagePath;
    private float lastScrollY = -1f;
    private boolean isScroll = true;
    private EditText et_sign;
    private String mUserSign;
    private FrameLayout flRoot;
    private SimpleShowUiShow mUIShow;
    private LinearLayout llDaiXiao;
    private LinearLayout llMyAction;

    @Override
    public View createView(LayoutInflater inflater, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frag_user_center1, null);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initView() {
        osvScroll = (ObservableScrollView) findViewById(R.id.osv_scroll);

        flScrollRoot = (FrameLayout) findViewById(R.id.fl_scroll_root);
        flScrollHeader = (FrameLayout) findViewById(R.id.fl_scroll_header);
        flBody = (FrameLayout) findViewById(R.id.fl_body);
        flBar = (FrameLayout) findViewById(R.id.fl_bar);

        ivUserBg = (ImageView) findViewById(R.id.iv_user_bg);
        ivAvater = (CircleImageView) findViewById(R.id.iv_avater);
        ivEdit = (ImageView) findViewById(R.id.iv_edit);

        llMyOrder = (LinearLayout) findViewById(R.id.ll_myOrder);
        llMyGoods = (LinearLayout) findViewById(R.id.ll_myGoods);
        llMyEval = (LinearLayout) findViewById(R.id.ll_myEval);
        llMyMsg = (LinearLayout) findViewById(R.id.ll_myMsg);
        llWealth = (LinearLayout) findViewById(R.id.ll_wealth);
        llMyRanking = (LinearLayout) findViewById(R.id.ll_myRanking);
        llMyTeacherOrStudent = (LinearLayout) findViewById(R.id.ll_myTeacherOrStudent);

        llDaiXiao = (LinearLayout) findViewById(R.id.ll_daixiao);
        llMyAction = (LinearLayout) findViewById(R.id.ll_myAction);

        tvMyTeacherOrStudent = (TextView) findViewById(R.id.tv_myTeacherOrStudent);
        tvUserName = (TextView) findViewById(R.id.tv_userName);
        tvSign = (TextView) findViewById(R.id.tv_sign);

        et_sign = (EditText) findViewById(R.id.et_userSgin);

        flRoot = (FrameLayout) findViewById(R.id.fl_root);
        mUIShow = new SimpleShowUiShow(getActivity(), true);
        mUIShow.setRootView(flRoot);
        mUIShow.setTvReload("去登录", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginActivity.startThisActivity(getActivity());
            }
        });
        mUIShow.setIvErrorImg(R.drawable.user_avater);
        mUIShow.setTvErrorMsg("当前你还没有登录, 不能查看个人信息");

        osvScroll.scrollBy(0, 2);
    }

    PointF startPoint = new PointF();
    private static final int ACTION_UP = 0x00000100;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case ACTION_UP:
                    if (scaleY > 1f) {
                        ivUserBg.animate().scaleX(1f).scaleY(1f).setDuration(200).setInterpolator(new DecelerateInterpolator(1.5f)).start();
                        flBar.animate().translationY(0).setDuration(200).setInterpolator(new DecelerateInterpolator(1.5f)).start();
                        flBody.animate().translationY(0).setDuration(200).setInterpolator(new DecelerateInterpolator(1.5f)).start();
                        osvScroll.scrollBy(0, 5);
                    }
                    scaleY = 1f;
                    isScroll = true;
                    break;
            }
        }
    };

    @Override
    public void initListener() {
        ivAvater.setOnClickListener(this);
        ivEdit.setOnClickListener(this);

        llMyOrder.setOnClickListener(this);
        llMyGoods.setOnClickListener(this);
        llMyEval.setOnClickListener(this);
        llMyMsg.setOnClickListener(this);
        llWealth.setOnClickListener(this);
        llMyRanking.setOnClickListener(this);
        llMyTeacherOrStudent.setOnClickListener(this);
        llDaiXiao.setOnClickListener(this);
        llMyAction.setOnClickListener(this);

        llMyOrder.setOnTouchListener(new MyOnTouchListener());
        llMyGoods.setOnTouchListener(new MyOnTouchListener());
        llMyEval.setOnTouchListener(new MyOnTouchListener());
        llMyMsg.setOnTouchListener(new MyOnTouchListener());
        llWealth.setOnTouchListener(new MyOnTouchListener());
        llMyRanking.setOnTouchListener(new MyOnTouchListener());
        llMyTeacherOrStudent.setOnTouchListener(new MyOnTouchListener());
        llMyAction.setOnTouchListener(new MyOnTouchListener());
        llDaiXiao.setOnTouchListener(new MyOnTouchListener());

        ivAvater.setOnTouchListener(new MyOnTouchListener());
        ivEdit.setOnTouchListener(new MyOnTouchListener());

        osvScroll.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                imgHeightY = ivUserBg.getLayoutParams().height;

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        startPoint.set(event.getX(), event.getY());
                        return true;
                    case MotionEvent.ACTION_MOVE:
                        if (osvScroll.getScaleY() == 0 && isScroll) {
                            startPoint.set(event.getX(), event.getY());
                            isScroll = false;
                            MyLogger.e(" ---------------startpoint-------------- ");
                        }
                        float dY = event.getY() - startPoint.y;
                        if (dY >= 10f && osvScroll.getScrollY() == 0) {
                            scaleY = (dY / 3f + imgHeightY) / imgHeightY;

                            MyLogger.e("dy = " + dY + " --------------------- " + imgHeightY + ", Event = " + event.getY() + " , start = " + startPoint.y);
                            MyLogger.e("scaleY = " + scaleY);

                            ivUserBg.setPivotY(0.5f);
                            ivUserBg.setScaleY(scaleY);
                            ivUserBg.setScaleX(scaleY);

                            flBar.setTranslationY((scaleY - 1.0f) * imgHeightY);
                            flBody.setTranslationY((scaleY - 1.0f) * imgHeightY);
                            return true;
                        }
                        lastScrollY = osvScroll.getScaleY();
                        break;
                    case MotionEvent.ACTION_UP:
                        mHandler.sendEmptyMessage(ACTION_UP);
                        break;
                }
                return false;
            }
        });

        flRoot.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v == llMyOrder) {
//            showToast("我的订单");
            MyOrderOptionActivity.startThisActivity(getActivity());
        } else if (v == llMyGoods) {
//            showToast("我的商品");
            MyGoodsActivity.startThisActivity(getActivity());
        } else if (v == llMyEval) {
//            showToast("商品评论");
            GoodsCommonActivity.startThisActivity(getActivity());
        } else if (v == llMyMsg) {
//            showToast("我的留言");
            MyMsgActivity.startThisActivity(getActivity());
        } else if (v == llWealth) {
//            showToast("财富中心");
            WealthActivity.startThisActivity(getActivity());
        } else if (v == llMyRanking) {
//            showToast("我的排名");
            MyRankingActivity.startThisActivity(getActivity());
        } else if (v == llMyTeacherOrStudent) {
//            showToast("我的导师 or 学生");
            UserType type = CommonUtil.getUserType();
            switch (type) {
                case xueSheng:
                case daoShi:
                    MyTeacherActivity.startThisActivity(getActivity());
                    break;
            }
        } else if (v == ivAvater) {
            UserInfoActivity.startThisActivityForResult(getActivity());
        } else if (v == ivEdit) {
            if (CommonUtil.isLogin()) {
                ivEdit.setSelected(!ivEdit.isSelected());
                tvSign.setVisibility(ivEdit.isSelected() ? View.INVISIBLE : View.VISIBLE);
                et_sign.setVisibility(ivEdit.isSelected() ? View.VISIBLE : View.INVISIBLE);
                if (ivEdit.isSelected()) {
                    et_sign.setText("");
                } else {
                    if (checkSignScan()) {
                        new UserServer((HomeActivity) getActivity()).requestModifyUserSign(mUserSign, new BaseServer.OnSuccessCallBack() {
                            @Override
                            public void onSuccess(int mark) {
                                tvSign.setText(mUserSign);
                                CommonUtil.currentUser.setQm(mUserSign);
                                CommonUtil.saveCurrentUser(CommonUtil.currentUser);
                            }
                        });
                    }
                }
            }
        } else if (v == llDaiXiao) {
            DaiXiaoActivity.startThisActivity(getActivity());
        } else if (v == llMyAction) {

        }
    }

    /**
     * @return
     */
    private boolean checkSignScan() {
        mUserSign = et_sign.getText().toString();
        return !TextUtils.isEmpty(mUserSign);
    }

    private class MyOnTouchListener implements View.OnTouchListener {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    int[] point = new int[2];
                    v.getLocationInWindow(point);
                    startPoint.set(event.getX(), point[1] + CommonUtil.dip2px(24));
                    break;
                case MotionEvent.ACTION_MOVE:

                    break;
            }
            return false;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        MyLogger.e("onResume");
        upDataView();
    }

    public void upDataView() {
        if (CommonUtil.isLogin()) {
            mUIShow.showData();
            final String avater = CommonUtil.getUserAvater();
            if (!TextUtils.isEmpty(avater)) {
                Glide.with(CommonUtil.mContext)
                        .load(NetConstants.IMG_HOST + avater)
                        .error(R.drawable.user_avater)
                        .into(ivAvater);
            }
            tvUserName.setText(CommonUtil.currentUser.getNickname());
            String sign = CommonUtil.currentUser.getQm();
            if (TextUtils.isEmpty(sign)) {
                tvSign.setHint("说点什么吧!");
            } else {
                tvSign.setText(sign);
            }
        } else {
            mUIShow.showError();
        }

        llWealth.setVisibility(View.VISIBLE);
        llMyAction.setVisibility(View.GONE);
        llMyTeacherOrStudent.setVisibility(View.VISIBLE);
        llDaiXiao.setVisibility(View.VISIBLE);
        llMyEval.setVisibility(View.VISIBLE);
        llMyGoods.setVisibility(View.VISIBLE);
        llMyMsg.setVisibility(View.VISIBLE);
        llMyOrder.setVisibility(View.VISIBLE);

        switch (CommonUtil.getUserType()) {
            case xueSheng:

                break;
            case xueShengHui:
                llMyGoods.setVisibility(View.GONE);
                llMyRanking.setVisibility(View.GONE);
                llMyTeacherOrStudent.setVisibility(View.GONE);
                llDaiXiao.setVisibility(View.GONE);
                break;
            case daoShi:
                llMyGoods.setVisibility(View.GONE);
                llMyRanking.setVisibility(View.GONE);
                llDaiXiao.setVisibility(View.GONE);
                break;
            case youKe:
                llMyGoods.setVisibility(View.GONE);
                llMyRanking.setVisibility(View.GONE);
                llMyTeacherOrStudent.setVisibility(View.GONE);
                llDaiXiao.setVisibility(View.GONE);
                break;
            case shangJia:
                llMyRanking.setVisibility(View.GONE);
                llMyTeacherOrStudent.setVisibility(View.GONE);
                llDaiXiao.setVisibility(View.GONE);
                break;
        }
    }
}

