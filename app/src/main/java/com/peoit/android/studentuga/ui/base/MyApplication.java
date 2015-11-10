package com.peoit.android.studentuga.ui.base;

import android.app.Application;

import com.peoit.android.studentuga.config.CommonUtil;
import com.peoit.android.studentuga.uitl.ShareUserHelper;

import java.util.ArrayList;

/**
 * author:libo
 * time:2015/9/7
 * E-mail:boli_android@163.com
 * last: ...
 */
public class MyApplication extends Application {

    private static MyApplication mInstance;

    public static MyApplication getInstance() {
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        CommonUtil.initContext(this);
    }

    public void logout() {
        ShareUserHelper mShare = ShareUserHelper.getInstance();
        mShare.clear();
        CommonUtil.currentUser = null;
        CommonUtil.mDefaultAddress = null;
        CommonUtil.mSelectGoods = new ArrayList<>();
    }
}
