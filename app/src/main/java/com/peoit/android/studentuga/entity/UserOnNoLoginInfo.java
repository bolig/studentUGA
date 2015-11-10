package com.peoit.android.studentuga.entity;

import java.io.Serializable;

/**
 * author:libo
 * time:2015/10/29
 * E-mail:boli_android@163.com
 * last: ...
 */
public class UserOnNoLoginInfo implements Serializable {

    /**
     * qm : null
     * schoolid : 2
     * phone : 18623101684
     * stime : 1442506495000
     * areaname : 北区
     * nickname : 狗二蛋同学
     * totalsales : 1.0
     * flg : Y
     * areaid : 1
     * code : null
     * pic : null
     * studentno : 123
     * type : 2
     * password : 123
     * id : 15
     * setPaypwd : false
     * balance : 0
     * schoolname : 北京大学
     * obj1 : null
     * zfpassword : null
     */

    private String qm;
    private long schoolid;
    private String phone;
    private long stime;
    private String areaname;
    private String nickname;
    private double totalsales;
    private String flg;
    private long areaid;
    private String code;
    private String pic;
    private String studentno;
    private String type;
    private String password;
    private long id = -1;
    private boolean setPaypwd;
    private int balance;
    private String schoolname;
    private String obj1;
    private String zfpassword;

    public void setQm(String qm) {
        this.qm = qm;
    }

    public void setSchoolid(int schoolid) {
        this.schoolid = schoolid;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setStime(long stime) {
        this.stime = stime;
    }

    public void setAreaname(String areaname) {
        this.areaname = areaname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getQm() {
        return qm;
    }

    public long getSchoolid() {
        return schoolid;
    }

    public String getPhone() {
        return phone;
    }

    public long getStime() {
        return stime;
    }

    public String getAreaname() {
        return areaname;
    }

    public String getNickname() {
        return nickname;
    }

    public double getTotalsales() {
        return totalsales;
    }

    public void setTotalsales(double totalsales) {
        this.totalsales = totalsales;
    }

    public String getFlg() {
        return flg;
    }

    public void setFlg(String flg) {
        this.flg = flg;
    }

    public long getAreaid() {
        return areaid;
    }

    public void setAreaid(int areaid) {
        this.areaid = areaid;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getStudentno() {
        return studentno;
    }

    public void setStudentno(String studentno) {
        this.studentno = studentno;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isSetPaypwd() {
        return setPaypwd;
    }

    public void setSetPaypwd(boolean setPaypwd) {
        this.setPaypwd = setPaypwd;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public String getSchoolname() {
        return schoolname;
    }

    public void setSchoolname(String schoolname) {
        this.schoolname = schoolname;
    }

    public String getObj1() {
        return obj1;
    }

    public void setObj1(String obj1) {
        this.obj1 = obj1;
    }

    public String getZfpassword() {
        return zfpassword;
    }

    public void setZfpassword(String zfpassword) {
        this.zfpassword = zfpassword;
    }

    public boolean isNull() {
        return id == -1;
    }

    @Override
    public String toString() {
        return "UserOnNoLoginInfo{" +
                "qm='" + qm + '\'' +
                ", schoolid=" + schoolid +
                ", phone='" + phone + '\'' +
                ", stime=" + stime +
                ", areaname='" + areaname + '\'' +
                ", nickname='" + nickname + '\'' +
                ", totalsales=" + totalsales +
                ", flg='" + flg + '\'' +
                ", areaid=" + areaid +
                ", code='" + code + '\'' +
                ", pic='" + pic + '\'' +
                ", studentno=" + studentno +
                ", type='" + type + '\'' +
                ", password='" + password + '\'' +
                ", id=" + id +
                ", setPaypwd=" + setPaypwd +
                ", balance=" + balance +
                ", schoolname='" + schoolname + '\'' +
                ", obj1='" + obj1 + '\'' +
                ", zfpassword='" + zfpassword + '\'' +
                '}';
    }
}
