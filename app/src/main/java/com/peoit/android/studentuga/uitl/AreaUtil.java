package com.peoit.android.studentuga.uitl;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.peoit.android.studentuga.config.AreaConstants;
import com.peoit.android.studentuga.entity.AreaInfo;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * author:libo
 * time:2015/10/8
 * E-mail:boli_android@163.com
 * last: ...
 */
public class AreaUtil {
    private static List<AreaInfo> mAreaInfos = new ArrayList<>();
    private static List<AreaInfo.CityListEntity> mCityLists = new ArrayList<>();
    private static List<String> mCounties = new ArrayList<>();

    private static String[] pros;
    private static String[] cities;
    private static String[] counties;

    /**
     * 获取区域信息， 如果有直接回调， 否则解析后回调...
     *
     * @param callBack
     */
    public static void getArea(final OnAreaCallBack callBack) {
        if (mAreaInfos != null && !mAreaInfos.isEmpty()) {
            if (callBack != null) {
                callBack.onArea(mAreaInfos);
            }
        } else {
            toParseArea(callBack);
        }
    }

    /**
     * 解析区域信息
     *
     * @param callBack
     */
    private static void toParseArea(final OnAreaCallBack callBack) {
        Type type = new TypeToken<List<AreaInfo>>() {
        }.getType();
        mAreaInfos = new Gson().fromJson(AreaConstants.NEW_AREA_INFO, type);
        if (callBack != null) {
            callBack.onArea(mAreaInfos);
        }
    }

    public static String[] getPros() {
        if (mAreaInfos == null || mAreaInfos.isEmpty()) {
            return new String[]{""};
        }
        pros = new String[mAreaInfos.size()];
        for (int i = 0; i < mAreaInfos.size(); i++) {
            pros[i] = mAreaInfos.get(i).getName();
        }
        return pros;
    }

    /**
     * 通过名字获取省份的位置
     *
     * @param proName
     * @return
     */
    public static int indexOfPro(String proName) {
        if (TextUtils.isEmpty(proName))
            return 0;
        for (int i = 0; i < pros.length; i++) {
            if (proName.equals(pros[i])) {
                return i;
            }
        }
        return 0;
    }

    public static String nameOfIndexInPros(int index) {
        return (pros != null && index >= 0 && index <= pros.length - 1) ? pros[index] : null;
    }

    public static String[] getCityies() {
        if (mAreaInfos == null || mAreaInfos.isEmpty())
            return new String[]{""};
        mCityLists = mAreaInfos.get(0).getCityList();
        if (mCityLists == null) {
            return new String[]{""};
        }
        cities = new String[mCityLists.size()];
        for (int i = 0; i < mCityLists.size(); i++) {
            cities[i] = mCityLists.get(i).getName();
        }
        return cities;
    }

    /**
     * @param proName
     * @return
     */
    public static String[] getCityies(String proName) {
        if (mAreaInfos == null || mAreaInfos.isEmpty())
            return new String[]{""};
        mCityLists = getCity(proName);
        if (mCityLists == null) {
            return new String[]{""};
        }
        cities = new String[mCityLists.size()];
        for (int i = 0; i < mCityLists.size(); i++) {
            cities[i] = mCityLists.get(i).getName();
        }
        return cities;
    }

    /**
     * 获取城市名获取它在结合中的位置
     *
     * @param cityName
     * @return
     */
    public static int indexOfCity(String cityName) {
        if (TextUtils.isEmpty(cityName))
            return 0;
        for (int i = 0; i < cities.length; i++) {
            if (cityName.equals(cities[i])) {
                return i;
            }
        }
        return 0;
    }

    /**
     * 通过下标获取城市名称
     *
     * @param index
     * @return
     */
    public static String nameOfIndexInCities(int index) {
        return (cities != null && index >= 0 && index <= cities.length - 1) ? cities[index] : null;
    }

    public static String[] getCounties() {
        if (mCityLists == null || mCityLists.isEmpty()) {
            MyLogger.e("getCounties = null11");
            return new String[]{""};
        }
        mCounties = mCityLists.get(0).getAreaList();
        if (mCounties == null || mCounties.isEmpty()) {
            MyLogger.e("getCounties = null12");
            return new String[]{""};
        }
        counties = new String[mCounties.size()];
        for (int i = 0; i < mCounties.size(); i++) {
            counties[i] = mCounties.get(i);
        }
        return counties;
    }

    /**
     * 获取对应城市下的区县
     *
     * @param cityName
     * @return
     */
    public static String[] getCounties(String cityName) {
        if (mCityLists == null || mCityLists.isEmpty()) {
            MyLogger.e("getCounties = null1");
            return new String[]{""};
        }
        mCounties = getCounty(cityName);
        if (mCounties == null || mCounties.isEmpty()) {
            MyLogger.e("getCounties = null2");
            return new String[]{""};
        }
        counties = new String[mCounties.size()];
        for (int i = 0; i < mCounties.size(); i++) {
            counties[i] = mCounties.get(i);
        }
        return counties;
    }

    public static int indexOfCounty(String countyName) {
        if (TextUtils.isEmpty(countyName)) {
            return 0;
        }
        for (int i = 0; i < counties.length; i++) {
            if (countyName.equals(counties[i])) {
                return i;
            }
        }
        return 0;
    }

    public static String nameOfIndexInCounties(int index) {
        return (counties != null && index >= 0 && index <= counties.length - 1) ? counties[index] : null;
    }

    private static List<String> getCounty(String cityName) {
        for (AreaInfo.CityListEntity info :
                mCityLists) {
            if (info.isCity(cityName)) {
                return info.getAreaList();
            }
        }
        return null;
    }

    private static List<AreaInfo.CityListEntity> getCity(String proName) {
        for (AreaInfo info : mAreaInfos) {
            if (info.isPro(proName))
                return info.getCityList();
        }
        return null;
    }

    public interface OnAreaCallBack {
        void onArea(List<AreaInfo> infos);
    }
}
