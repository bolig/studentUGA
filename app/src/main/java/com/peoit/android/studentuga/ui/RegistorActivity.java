package com.peoit.android.studentuga.ui;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.peoit.android.peoit_lib.base.BaseActivity;
import com.peoit.android.studentuga.R;
import com.peoit.android.studentuga.config.CommonUtil;
import com.peoit.android.studentuga.entity.RegistorSchoolInfo;
import com.peoit.android.studentuga.net.BaseCallBack;
import com.peoit.android.studentuga.net.BaseServer;
import com.peoit.android.studentuga.net.server.RegistorServer;
import com.peoit.android.studentuga.ui.adapter.RegistorSelectSchoolAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 注册界面
 * <p/>
 * author:libo
 * time:2015/8/4
 * E-mail:boli_android@163.com
 * last: ...
 */
public class RegistorActivity extends BaseActivity implements View.OnClickListener {
    private LinearLayout llScanPhoneNum;
    private LinearLayout llScanPhoneNumStudentInfo;
    private LinearLayout llScanPhoneNumSelectSchool;
    private LinearLayout llScanPhoneNumSelectType;
    private LinearLayout llScanAuthCode;
    private LinearLayout llScanPassword;

    private ImageView ivScanPhoneNumSelectUserType;
    private ImageView ivScanPhoneNumSelectSchool;

    private TextView tvScanPhoneNumUserType;
    private TextView tvScanPhoneNumSchoolName;
    private TextView tvScanPhoneNumNext;
    private TextView tvScanPhoneNumRegistorDeal;
    private TextView tvScanAuthCodeNewAuthCode;
    private TextView tvScanAuthCodeNext;
    private TextView tvScanPasswordRegistor;

    private EditText etScanPhoneNumStudentNum;
    private EditText etScanPhoneNumPhoneNum;
    private EditText etScanAuthCodeAuthCode;
    private EditText etScanPasswordPass;
    private EditText etScanPasswordRePass;

    private PopupWindow mSelectSchoolPupop;
    private PopupWindow mSelectTypePupop;

    private RegistorServer mRegistorServer;

    private RegistorSelectSchoolAdapter mSelectSchoolAdapter;
    private RegistorSelectSchoolAdapter mSelectTypeAdapter;

    private RegistorSchoolInfo mCurrentSelectSchoolInfo;
    private RegistorSchoolInfo mCurrentTypeInfo;

    private List<RegistorSchoolInfo> typeInfos = new ArrayList<>();
    private List<RegistorSchoolInfo> mSelectRegistorSchoolInfos = new ArrayList<>();

    private String studentNo;
    private String mUserPhone;
    private String mAuthCode;
    private String mUserPassword;
    private String mUserRePassword;

    private REGISTOR_PROGRESS mLateProgress;


    private void assignViews() {
        llScanPhoneNum = (LinearLayout) findViewById(R.id.ll_scan_phoneNum);
        llScanPhoneNumStudentInfo = (LinearLayout) findViewById(R.id.ll_scanPhoneNum_student_info);
        llScanPhoneNumSelectSchool = (LinearLayout) findViewById(R.id.ll_scanPhoneNum_selectSchool);
        llScanPhoneNumSelectType = (LinearLayout) findViewById(R.id.ll_scanPhoneNum_selectType);
        llScanAuthCode = (LinearLayout) findViewById(R.id.ll_scan_authCode);
        llScanPassword = (LinearLayout) findViewById(R.id.ll_scan_password);

        ivScanPhoneNumSelectSchool = (ImageView) findViewById(R.id.iv_scanPhoneNum_selectSchool);
        ivScanPhoneNumSelectUserType = (ImageView) findViewById(R.id.iv_scanPhoneNum_selectUserType);

        tvScanPhoneNumUserType = (TextView) findViewById(R.id.tv_scanPhoneNum_userType);
        tvScanPhoneNumSchoolName = (TextView) findViewById(R.id.tv_scanPhoneNum_schoolName);
        tvScanPhoneNumNext = (TextView) findViewById(R.id.tv_scanPhoneNum_next);
        tvScanPhoneNumRegistorDeal = (TextView) findViewById(R.id.tv_scanPhoneNum_registor_deal);
        tvScanAuthCodeNewAuthCode = (TextView) findViewById(R.id.tv_scanAuthCode_newAuthCode);
        tvScanAuthCodeNext = (TextView) findViewById(R.id.tv_scanAuthCode_next);
        tvScanPasswordRegistor = (TextView) findViewById(R.id.tv_scanPassword_registor);

        etScanPhoneNumStudentNum = (EditText) findViewById(R.id.et_scanPhoneNum_studentNum);
        etScanPhoneNumPhoneNum = (EditText) findViewById(R.id.et_scanPhoneNum_phoneNum);
        etScanAuthCodeAuthCode = (EditText) findViewById(R.id.et_scanAuthCode_authCode);
        etScanPasswordPass = (EditText) findViewById(R.id.et_scanPassword_pass);
        etScanPasswordRePass = (EditText) findViewById(R.id.et_scanPassword_rePass);
    }


    public static void startThisActivity(Activity mAc) {
        Intent intent = new Intent(mAc, RegistorActivity.class);
        mAc.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_registor);
    }

    @Override
    public void initData() {
        mSelectRegistorSchoolInfos.add(new RegistorSchoolInfo(-1, "正在加载..."));

        typeInfos.add(new RegistorSchoolInfo(1, "游客"));
        typeInfos.add(new RegistorSchoolInfo(2, "大学生"));

        mCurrentTypeInfo = typeInfos.get(1);

        mRegistorServer = new RegistorServer(this);

        mSelectSchoolAdapter = new RegistorSelectSchoolAdapter(mAct);
        mSelectSchoolAdapter.upDateList(mSelectRegistorSchoolInfos);
        mSelectSchoolAdapter.setOnItemListener(new RegistorSelectSchoolAdapter.OnGetItemListener() {
            @Override
            public void onItem(RegistorSchoolInfo info) {
                mCurrentSelectSchoolInfo = info.getId() == -1 ? null : info;
                if (mCurrentSelectSchoolInfo != null)
                    tvScanPhoneNumSchoolName.setText(mCurrentSelectSchoolInfo.getName());
                mSelectSchoolPupop.dismiss();
            }
        });

        mSelectTypeAdapter = new RegistorSelectSchoolAdapter(mAct);
        mSelectTypeAdapter.upDateList(typeInfos);
        mSelectTypeAdapter.setOnItemListener(new RegistorSelectSchoolAdapter.OnGetItemListener() {
            @Override
            public void onItem(RegistorSchoolInfo info) {
                mCurrentTypeInfo = info;
                if (isCurrentTypeIsStudent()) {
                    llScanPhoneNumStudentInfo.setVisibility(View.VISIBLE);
                } else {
                    llScanPhoneNumStudentInfo.setVisibility(View.GONE);
                }
                tvScanPhoneNumUserType.setText(mCurrentTypeInfo.getName());
                mSelectTypePupop.dismiss();
            }
        });

        showLoadingDialog("正在初始化数据");
        mRegistorServer.loadRegistorSchool(new BaseCallBack<RegistorSchoolInfo>() {
            @Override
            public void onFinish() {
                hideLoadingDialog();
            }

            @Override
            public void onResponseSuccessList(List<RegistorSchoolInfo> result) {
                mSelectRegistorSchoolInfos = result;
                mSelectSchoolAdapter.upDateList(result);
            }

            @Override
            protected void onResponseFailure(int statusCode, String msg) {
                showToast("初始化数据失败");
                finish();
            }
        });

        mThread.start();
    }

    @Override
    public void initView() {
        assignViews();
        getToolbar().setTvTitle("注册").setBack();

        tvScanPhoneNumUserType.setText(mCurrentTypeInfo.getName());

        changeUiShow(REGISTOR_PROGRESS.SCAN_PHONE_NUM);
    }

    @Override
    public void initListener() {
        tvScanAuthCodeNewAuthCode.setOnClickListener(this);
        tvScanAuthCodeNext.setOnClickListener(this);
        tvScanPasswordRegistor.setOnClickListener(this);
        tvScanPhoneNumRegistorDeal.setOnClickListener(this);
        tvScanPhoneNumNext.setOnClickListener(this);

        llScanPhoneNumSelectSchool.setOnClickListener(this);
        llScanPhoneNumSelectType.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == tvScanAuthCodeNewAuthCode) {
            mRegistorServer.requestSendCode(mUserPhone, new BaseServer.OnSuccessCallBack() {
                @Override
                public void onSuccess(int mark) {
                    mHandler.sendEmptyMessage(-1);
                }
            });
        } else if (v == tvScanAuthCodeNext) {
            if (checkAuthCode()) {
                mRegistorServer.requestCheckCode(mUserPhone, mAuthCode);
            }
        } else if (v == tvScanPasswordRegistor) {
            if (checkScanPassword()) {
                mRegistorServer.requestRegistor(mUserPhone,
                        mUserPhone,
                        mUserPassword,
                        mCurrentTypeInfo.getId() + "",
                        mAuthCode,
                        studentNo,
                        mCurrentSelectSchoolInfo == null ? null : mCurrentSelectSchoolInfo.getId() + "");
            }
        } else if (v == tvScanPhoneNumRegistorDeal) {
            ProtocolActivity.startThisActivity(this);
            //showToast("查看注册协议");
        } else if (v == tvScanPhoneNumNext) {
            if (checkScanPhoneNum()) {
                mRegistorServer.requestSendCode(mUserPhone, new BaseServer.OnSuccessCallBack() {
                    @Override
                    public void onSuccess(int mark) {
                        mHandler.sendEmptyMessage(-1);
                    }
                });
            }
        } else if (v == llScanPhoneNumSelectSchool) {
            llScanPhoneNumSelectSchool.setSelected(true);
            showSelectSchoolWindow();
        } else if (v == llScanPhoneNumSelectType) {
            llScanPhoneNumSelectType.setSelected(true);
            showSelectTypeWindow();
        }
    }

    /**
     * 验证密码的有效性
     *
     * @return
     */
    private boolean checkScanPassword() {
        mUserPassword = etScanPasswordPass.getText().toString();
        if (TextUtils.isEmpty(mUserPassword)) {
            showToast("请输入您的密码");
            return false;
        }
        mUserRePassword = etScanPasswordRePass.getText().toString();
        if (!mUserPassword.equals(mUserRePassword)) {
            showToast("两次密码不一致");
            return false;
        }
        return true;
    }

    /**
     * 检查验证码的正确性
     *
     * @return
     */
    private boolean checkAuthCode() {
        mAuthCode = etScanAuthCodeAuthCode.getText().toString();
        if (TextUtils.isEmpty(mAuthCode)) {
            showToast("请输入您的验证码");
            return false;
        }
        return true;
    }

    /**
     * 判断当前类型是不是大学生
     *
     * @return
     */
    private boolean isCurrentTypeIsStudent() {
        return mCurrentTypeInfo.getId() == 2;
    }

    /**
     * 判断输入手机号时, 输入内容的正确性
     */
    private boolean checkScanPhoneNum() {
        studentNo = etScanPhoneNumStudentNum.getText().toString();
        if (isCurrentTypeIsStudent() && TextUtils.isEmpty(studentNo)) {
            showToast("请输入您的工号");
            return false;
        }
        if (isCurrentTypeIsStudent() && (mCurrentSelectSchoolInfo == null || TextUtils.isEmpty(mCurrentSelectSchoolInfo.getName()))) {
            showToast("请选择您的学校");
            return false;
        }
        mUserPhone = etScanPhoneNumPhoneNum.getText().toString();
        if (TextUtils.isEmpty(mUserPhone)) {
            showToast("请输入您的手机号");
            return false;
        }
        return true;
    }

    /**
     * 显示选择用户类型的popupWindow
     */
    private void showSelectTypeWindow() {
        if (mSelectTypePupop == null) {
            mSelectTypePupop = new PopupWindow(mAct);
            mSelectTypePupop.setBackgroundDrawable(new BitmapDrawable());
            mSelectTypePupop.setOutsideTouchable(true);
            mSelectTypePupop.setFocusable(true);
            mSelectTypePupop.setWidth(llScanPhoneNumSelectType.getWidth());
            mSelectTypePupop.setHeight(CommonUtil.dip2px(48) * 2 + CommonUtil.dip2px(3) + CommonUtil.dip2px(4));

            View mSelectView = getSelectView(false);

            mSelectTypePupop.setContentView(mSelectView);
            mSelectTypePupop.showAsDropDown(llScanPhoneNumSelectType, 0, -CommonUtil.dip2px(8));

            mSelectTypePupop.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    llScanPhoneNumSelectType.setSelected(false);
                }
            });
        } else {
            mSelectTypePupop.showAsDropDown(llScanPhoneNumSelectType, 0, -CommonUtil.dip2px(8));
        }
    }

    /**
     * 显示选择学校的popupWindow
     */
    private void showSelectSchoolWindow() {
        if (mSelectSchoolPupop == null) {
            mSelectSchoolPupop = new PopupWindow(mAct);
            mSelectSchoolPupop.setBackgroundDrawable(new BitmapDrawable());
            mSelectSchoolPupop.setOutsideTouchable(true);
            mSelectSchoolPupop.setFocusable(true);
            mSelectSchoolPupop.setWidth(llScanPhoneNumSelectSchool.getWidth());
            mSelectSchoolPupop.setHeight(getSelectSchoolHeight());

            View mSelectView = getSelectView(true);

            mSelectSchoolPupop.setContentView(mSelectView);
            mSelectSchoolPupop.showAsDropDown(llScanPhoneNumSelectSchool, 0, -CommonUtil.dip2px(8));

            mSelectSchoolPupop.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    llScanPhoneNumSelectSchool.setSelected(false);
                }
            });
        } else {
            mSelectSchoolPupop.showAsDropDown(llScanPhoneNumSelectSchool, 0, -CommonUtil.dip2px(8));
        }
    }

    /**
     * 根据school结合获取popupwindow 的高度
     *
     * @return
     */
    private int getSelectSchoolHeight() {
        if (mSelectRegistorSchoolInfos != null && mSelectRegistorSchoolInfos.size() < 4 && mSelectRegistorSchoolInfos.size() > 0) {
            return CommonUtil.dip2px(48) * mSelectRegistorSchoolInfos.size() + CommonUtil.dip2px(1) * (mSelectRegistorSchoolInfos.size() - 1);
        }
        return CommonUtil.dip2px(48) * 4 + CommonUtil.dip2px(1) * 3 + CommonUtil.dip2px(4);
    }

    private View getSelectView(boolean b) {
        View mView = getLayoutInflater().inflate(R.layout.act_registor_select_school_pupop_layout, null);
        ListView lvSchool = (ListView) mView.findViewById(R.id.lv_school);
        lvSchool.setAdapter(b ? mSelectSchoolAdapter : mSelectTypeAdapter);
        return mView;
    }

    /**
     * 改变界面显示进度
     * <p/>
     * SCAN_PHONE_NUM 输入手机号
     * SCAN_AUTH_CODE 输入验证码
     * SCAN_PASSWORD  输入密码
     *
     * @param type
     */
    public void changeUiShow(REGISTOR_PROGRESS type) {
        if (type == mLateProgress)
            return;
        llScanPhoneNum.setVisibility(View.INVISIBLE);
        llScanAuthCode.setVisibility(View.INVISIBLE);
        llScanPassword.setVisibility(View.INVISIBLE);
        switch (type) {
            case SCAN_PHONE_NUM:
                llScanPhoneNum.setVisibility(View.VISIBLE);
                break;
            case SCAN_AUTH_CODE:
                llScanAuthCode.setVisibility(View.VISIBLE);
                break;
            case SCAN_PASSWORD:
                llScanPassword.setVisibility(View.VISIBLE);
                mHandler.sendEmptyMessage(-2);
                break;
        }
        this.mLateProgress = type;
    }

    public enum REGISTOR_PROGRESS {
        SCAN_PHONE_NUM,
        SCAN_AUTH_CODE,
        SCAN_PASSWORD
    }

    private int timer = 60;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            int what = msg.what;
            switch (what) {
                case 0:
                    if (!tvScanAuthCodeNewAuthCode.isEnabled()) {
                        tvScanAuthCodeNewAuthCode.setEnabled(true);
                        tvScanAuthCodeNewAuthCode.setText("重新获取");
                        isStart = false;
                        timer = 60;
                    }
                    break;
                case 1:
                    tvScanAuthCodeNewAuthCode.setEnabled(false);
                    tvScanAuthCodeNewAuthCode.setText(timer + "秒重新获取");
                    break;
                case -1:
                    isStart = true;
                    timer = 60;
                    break;
                case -2:
                    isStart = false;
                    timer = 60;
                    break;
            }
        }
    };

    private boolean isStart = false;

    private Thread mThread = new Thread(new Runnable() {
        @Override
        public void run() {
            while (true) {
                if (isStart) {
                    if (timer-- <= 1) {
                        mHandler.sendEmptyMessage(0);
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    } else {
                        mHandler.sendEmptyMessage(1);
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    });
}

