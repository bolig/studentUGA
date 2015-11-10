package com.peoit.android.studentuga.uitl;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.peoit.android.studentuga.config.CommonUtil;

/**
 * 偏好设置辅助类
 * <p/>
 * <p/>
 * Title: LocalUserInfo<／p>
 * <p/>
 * Description: <／p>
 * <p/>
 * Company: shangxian<／p>
 *
 * @author libo
 * @version 1.0
 * @time 2015年6月16日
 */
public class ShareUserHelper {
    public static final String PREFERENCE_NAME = "pSchool_preferences";// 保存Preference的name
    private static SharedPreferences mSharedPreferences;
    private static ShareUserHelper mPreferenceUtils;
    private static Editor editor;

    private ShareUserHelper() {
        MyLogger.e("mContext >>>>>>>>>>>>>>>>." + (CommonUtil.mContext == null ? "null" : CommonUtil.mContext.getPackageName()));
        mSharedPreferences = CommonUtil.mContext.getSharedPreferences(PREFERENCE_NAME,
                Context.MODE_PRIVATE);
    }

    /**
     * 单例模式，获取instance实例
     *
     * @return
     */
    public static ShareUserHelper getInstance() {
        if (mPreferenceUtils == null) {
            mPreferenceUtils = new ShareUserHelper();
        }
        editor = mSharedPreferences.edit();
        return mPreferenceUtils;
    }

    public boolean contains(String key) {
        return mSharedPreferences.contains(key);
    }

    /**
     * 保存键为key的值为vlaue
     *
     * @param key
     * @param vlaue
     */
    public void put(String key, int vlaue) {
        Editor editor = mSharedPreferences.edit();
        editor.putInt(key, vlaue);
        editor.commit();
    }

    /**
     * 保存键为key的值为vlaue
     *
     * @param key
     * @param vlaue
     */
    public void put(String key, String vlaue) {
        Editor editor = mSharedPreferences.edit();
        editor.putString(key, vlaue);
        editor.commit();
    }

    /**
     * 保存键为key的值为vlaue
     *
     * @param key
     * @param vlaue
     */
    public void put(String key, boolean vlaue) {
        Editor editor = mSharedPreferences.edit();
        editor.putBoolean(key, vlaue);
        editor.commit();
    }

    /**
     * 保存键为key的值为vlaue
     *
     * @param key
     * @param vlaue
     */
    public void put(String key, long vlaue) {
        Editor editor = mSharedPreferences.edit();
        editor.putLong(key, vlaue);
        editor.commit();
    }

    /**
     * 保存键为key的值为vlaue
     *
     * @param key
     * @param vlaue
     */
    public void put(String key, float vlaue) {
        Editor editor = mSharedPreferences.edit();
        editor.putFloat(key, vlaue);
        editor.commit();
    }

    /**
     * key对应的整型值叠加1
     *
     * @param key
     */
    public void superposition(String key) {
        int vlaue = mSharedPreferences.getInt(key, 0);
        Editor editor = mSharedPreferences.edit();
        vlaue++;
        editor.putFloat(key, vlaue);
        editor.commit();
    }

    /**
     * 获取保存的int类型值(有默认值)
     *
     * @param key
     * @param defult 如果没有返回的默认值
     * @return
     */
    public int getInt(String key, int defult) {
        return mSharedPreferences.getInt(key, defult);
    }

    /**
     * 获取保存的int类型值(无默认值)
     *
     * @param key
     * @return
     */
    public int getInt(String key) {
        return mSharedPreferences.getInt(key, 0);
    }

    public boolean getBoolean(String key) {
        return mSharedPreferences.getBoolean(key, true);
    }

    public boolean getBoolean(String key, boolean isTrue) {
        return mSharedPreferences.getBoolean(key, isTrue);
    }

    public String getString(String key) {
        return mSharedPreferences.getString(key, null);
    }

    public String getString(String key, String defult) {
        return mSharedPreferences.getString(key, defult);
    }

    public long getLong(String key, long defult) {
        return mSharedPreferences.getLong(key, defult);
    }

    public long getLong(String key) {
        return mSharedPreferences.getLong(key, 0);
    }

    /**
     * 移除多个单元by key
     *
     * @param keys
     */
    public void remove(String... keys) {
        for (String key : keys) {
            editor.remove(key);
        }
        editor.commit();
    }

    /**
     * 清空偏好设置
     */
    public synchronized void clear() {
        editor.clear();
        editor.commit();
    }
}
