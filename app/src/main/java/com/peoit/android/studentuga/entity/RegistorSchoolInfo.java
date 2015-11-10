package com.peoit.android.studentuga.entity;

import java.io.Serializable;

/**
 * 学校信息
 * <p/>
 * author:libo
 * time:2015/9/23
 * E-mail:boli_android@163.com
 * last: ...
 */
public class RegistorSchoolInfo implements Serializable {
    /**
     * id : 2
     * pId : 1
     * bz : null
     * flg : Y
     * name : 重庆邮电大学
     */
    private int id;
    private int pId;
    private String bz;
    private String flg;
    private String name;

    public RegistorSchoolInfo() {

    }

    public RegistorSchoolInfo(int id, String name) {
        this.name = name;
        this.id = id;
    }

    public RegistorSchoolInfo(int id, int pId, String name) {
        this.id = id;
        this.pId = pId;
        this.name = name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPId(int pId) {
        this.pId = pId;
    }

    public void setBz(String bz) {
        this.bz = bz;
    }

    public void setFlg(String flg) {
        this.flg = flg;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public int getPId() {
        return pId;
    }

    public String getBz() {
        return bz;
    }

    public String getFlg() {
        return flg;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "RegistorSchoolInfo{" +
                "id=" + id +
                ", pId=" + pId +
                ", bz='" + bz + '\'' +
                ", flg='" + flg + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
