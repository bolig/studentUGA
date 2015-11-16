package com.peoit.android.studentuga.entity;

import java.io.Serializable;

/**
 * author:libo
 * time:2015/11/10
 * E-mail:boli_android@163.com
 * last: ...
 */
public class BindUserInfo implements Serializable {

    /**
     * id : 15
     * qm : null
     * schoolid : 2
     * phone : 18623101684
     * schoolname : 北京大学
     * areaname : 北区
     * nickname : 狗二蛋同学
     * flg : Y
     * areaid : 1
     * code : null
     * pic : null
     * studentno : 123
     * type : 2
     */
    private int id;
    private String qm;
    private int schoolid;
    private String phone;
    private String schoolname;
    private String areaname;
    private String nickname;
    private String flg;
    private int areaid;
    private String code;
    private String pic;
    private int studentno;
    private String type;

    public void setId(int id) {
        this.id = id;
    }

    public void setQm(String qm) {
        this.qm = qm;
    }

    public void setSchoolid(int schoolid) {
        this.schoolid = schoolid;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setSchoolname(String schoolname) {
        this.schoolname = schoolname;
    }

    public void setAreaname(String areaname) {
        this.areaname = areaname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setFlg(String flg) {
        this.flg = flg;
    }

    public void setAreaid(int areaid) {
        this.areaid = areaid;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public void setStudentno(int studentno) {
        this.studentno = studentno;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public String getQm() {
        return qm;
    }

    public int getSchoolid() {
        return schoolid;
    }

    public String getPhone() {
        return phone;
    }

    public String getSchoolname() {
        return schoolname;
    }

    public String getAreaname() {
        return areaname;
    }

    public String getNickname() {
        return nickname;
    }

    public String getFlg() {
        return flg;
    }

    public int getAreaid() {
        return areaid;
    }

    public String getCode() {
        return code;
    }

    public String getPic() {
        return pic;
    }

    public int getStudentno() {
        return studentno;
    }

    public String getType() {
        return type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BindUserInfo)) return false;

        BindUserInfo that = (BindUserInfo) o;

        return id == that.id;

    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public String toString() {
        return "BindUserInfo{" +
                "id=" + id +
                ", qm='" + qm + '\'' +
                ", schoolid=" + schoolid +
                ", phone='" + phone + '\'' +
                ", schoolname='" + schoolname + '\'' +
                ", areaname='" + areaname + '\'' +
                ", nickname='" + nickname + '\'' +
                ", flg='" + flg + '\'' +
                ", areaid=" + areaid +
                ", code='" + code + '\'' +
                ", pic='" + pic + '\'' +
                ", studentno=" + studentno +
                ", type='" + type + '\'' +
                '}';
    }
}
