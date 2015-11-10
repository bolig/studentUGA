package com.peoit.android.studentuga.config;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.DisplayMetrics;

import com.google.gson.Gson;
import com.peoit.android.studentuga.entity.AddressInfo;
import com.peoit.android.studentuga.entity.ShopCarInfo;
import com.peoit.android.studentuga.entity.UserInfo;
import com.peoit.android.studentuga.enuml.UserType;
import com.peoit.android.studentuga.ui.LoginActivity;
import com.peoit.android.studentuga.uitl.ShareUserHelper;

import java.util.ArrayList;
import java.util.List;


/**
 * author:libo
 * time:2015/7/9
 * E-mail:boli_android@163.com
 * last: ...
 */
public class CommonUtil {
    private static DisplayMetrics metrics;

    public static Context mContext;
    public static int w_screeen;
    public static int h_screeen;

    public static ShareUserHelper mShareHelper;
    public static UserInfo currentUser;
    public static AddressInfo mDefaultAddress;
    public static List<ShopCarInfo> mSelectGoods;

    public static boolean isChangeOrder = false;
    public static boolean isDaiXiaoCancel = false;

    public static void setSelectGoodsGoods(ShopCarInfo info) {
        mSelectGoods = new ArrayList<>();
        mSelectGoods.add(info);
    }

    public static void setSelectGoodsGoods(List<ShopCarInfo> infos) {
        if (infos == null) {
            infos = new ArrayList<>();
        }
        mSelectGoods = infos;
    }

    /**
     * 获取当前接口标示码
     *
     * @return
     */
    public static String getSign() {
        return mShareHelper.getString(Constants.SHARE_LOGIN_SIGN);
    }

    /**
     * 获取当前用户用户名
     *
     * @return
     */
    public static String getUserName() {
        return mShareHelper.getString(Constants.SHARE_LOGIN_USERNAME);
    }

    /**
     * 获取当前用户密码
     *
     * @return
     */
    public static String getPassword() {
        return mShareHelper.getString(Constants.SHARE_LOGIN_PASSWORD);
    }

    /**
     * 获取用户头像
     *
     * @return
     */
    public static String getUserAvater() {
        return mShareHelper.getString(Constants.SHARE_USER_AVATER);
    }

    public static UserType getUserType() {
        if (currentUser != null) {
            return UserType.getUserType(currentUser.getType());
        }
        return UserType.error;
    }

    /**
     * 判断是否登录
     *
     * @return
     */
    public static boolean isLogin() {
        return mShareHelper.getBoolean(Constants.SHARE_LOGIN_STAT, false);
    }

    /**
     * 判断是否登录， 并去登陆...
     *
     * @param mAc
     * @param isFinish 是否finish当前界面
     * @return
     */
    public static boolean isLoginAndToLogin(final Activity mAc, final boolean isFinish) {
        boolean isLogin = mShareHelper.getBoolean(Constants.SHARE_LOGIN_STAT, false);
        if (!isLogin) {
//            AlertDialog dialog = new AlertDialog.Builder(mAc).setPositiveButton("去登录", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//                    dialog.dismiss();
//                    Intent intent = new Intent(mAc, LoginActivity.class);
//                    mAc.startActivity(intent);
//                }
//            }).setNeutralButton("免费注册", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//                    dialog.dismiss();
//                    RegistorActivity.startThisActivity(mAc);
//                }
//            }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//                    dialog.dismiss();
//                }
//            }).setTitle("提示").setMessage("该功能需要登录个人账户, 如您尚无个人账户, 请点击\"注册\", 如您已注册个人账户, 您可以直接选择\"登录\"").show();
            LoginActivity.startThisActivity(mAc);
        }
        return isLogin;
    }

    /**
     * 保存当前用户
     *
     * @param userInfo
     */
    public synchronized static void saveCurrentUser(UserInfo userInfo) {
        Gson mGson = new Gson();
        String user = mGson.toJson(userInfo);
        mShareHelper.put(Constants.SHARE_LOGIN_USER, user);
    }

    /**
     * 配置全局Context
     *
     * @param context
     */
    public static void initContext(@NonNull Context context) {
        mContext = context;
        metrics = context.getResources().getDisplayMetrics();

        mShareHelper = ShareUserHelper.getInstance();

        w_screeen = metrics.widthPixels;
        h_screeen = metrics.heightPixels;

        String user = mShareHelper.getString(Constants.SHARE_LOGIN_USER);
        if (!TextUtils.isEmpty(user))
            currentUser = new Gson().fromJson(user, UserInfo.class);
    }

    /**
     * dp转换为px
     *
     * @param dpValue
     * @return
     */
    public static int dip2px(float dpValue) {
        final float scale = mContext.getResources().getDisplayMetrics().density;
        //MyLogger.i(scale+"");
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * px装换成dp
     */
    public static int px2dip(float pxValue) {
        final float scale = mContext.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * sp转换为px
     */
    public static int sp2px(float spValue) {
        final float scale = mContext.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * scale + 0.5f);
    }

    /**
     * px转换为sp
     */
    public static int px2sp(float pxValue) {
        final float scale = mContext.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / scale + 0.5f);
    }
}
