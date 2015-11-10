package com.peoit.android.studentuga.entity;

/**
 * author:libo
 * time:2015/9/22
 * E-mail:boli_android@163.com
 * last: ...
 */
public class RankingInfo {
    /**
     * totlTop : 123
     * shcoolTop : 1
     * areaTop : 16
     */
    private int totlTop;
    private int shcoolTop;
    private int areaTop;

    public void setTotlTop(int totlTop) {
        this.totlTop = totlTop;
    }

    public void setShcoolTop(int shcoolTop) {
        this.shcoolTop = shcoolTop;
    }

    public void setAreaTop(int areaTop) {
        this.areaTop = areaTop;
    }

    public int getTotlTop() {
        return totlTop;
    }

    public int getShcoolTop() {
        return shcoolTop;
    }

    public int getAreaTop() {
        return areaTop;
    }

    @Override
    public String toString() {
        return "RankingInfo{" +
                "totlTop=" + totlTop +
                ", shcoolTop=" + shcoolTop +
                ", areaTop=" + areaTop +
                '}';
    }
}
