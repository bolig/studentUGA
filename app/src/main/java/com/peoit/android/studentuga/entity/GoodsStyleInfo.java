package com.peoit.android.studentuga.entity;

import android.text.TextUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * author:libo
 * time:2015/10/15
 * E-mail:boli_android@163.com
 * last: ...
 */
public class GoodsStyleInfo implements Serializable {
    /**
     * specVal : 红色,蓝色,白色
     * name : 颜色
     */
    private String specVal;
    private String name;
    private List<String> strs = new ArrayList<>();

    public List<String> getSpecVals() {
        strs.clear();
        if (!TextUtils.isEmpty(this.specVal)) {
            String[] styles = this.specVal.split(",");
            for (int i = 0; i < styles.length; i++) {
                strs.add(styles[i]);
            }
        }
        return strs;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpecVal() {
        return specVal;
    }

    public String getName() {
        return name;
    }

    public List<String> getStrs() {
        return strs;
    }

    public void setStrs(List<String> strs) {
        this.strs = strs;
    }

    @Override
    public String toString() {
        return "GoodsStyleInfo{" +
                "specVal='" + specVal + '\'' +
                ", name='" + name + '\'' +
                ", strs=" + strs +
                '}';
    }
}
