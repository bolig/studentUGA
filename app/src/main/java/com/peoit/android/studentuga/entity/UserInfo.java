package com.peoit.android.studentuga.entity;

import java.io.Serializable;
import java.util.List;

/**
 * 用户信息类
 * <p/>
 * author:libo
 * time:2015/9/21
 * E-mail:boli_android@163.com
 * last: ...
 */
public class UserInfo implements Serializable {
    /**
     * qm : null
     * schoolid : null
     * phone : 15025496981
     * stime : 1442979007000
     * areaname : null
     * nickname : 蒂七天堂
     * flg : Y
     * areaid : null
     * code : null
     * pic : null
     * studentno : 1
     * type : 1
     * password : 123456
     * userVos : [{"id":14,"phone":"18623101683","name":null,"pic":null,"type":"3"}]
     * id : 18
     * setPaypwd : false
     * balance : 0
     * schoolname : null
     * zfpassword : null
     */
    private String qm;
    private String schoolid;
    private String phone;
    private long stime;
    private String areaname;
    private String nickname;
    private String flg;
    private String areaid;
    private String code;
    private String pic;
    private String studentno;
    private int type = -1;
    private String password;
    private List<UserVosEntity> userVos;
    private long id = -1;
    private boolean setPaypwd;
    private double balance;
    private String schoolname;
    private String zfpassword;

    public boolean isNull() {
        return id == -1;
    }

    public void setQm(String qm) {
        this.qm = qm;
    }

    public void setSchoolid(String schoolid) {
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

    public void setFlg(String flg) {
        this.flg = flg;
    }

    public void setAreaid(String areaid) {
        this.areaid = areaid;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public void setStudentno(String studentno) {
        this.studentno = studentno;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUserVos(List<UserVosEntity> userVos) {
        this.userVos = userVos;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setSetPaypwd(boolean setPaypwd) {
        this.setPaypwd = setPaypwd;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public void setSchoolname(String schoolname) {
        this.schoolname = schoolname;
    }

    public void setZfpassword(String zfpassword) {
        this.zfpassword = zfpassword;
    }

    public String getQm() {
        return qm;
    }

    public String getSchoolid() {
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

    public String getFlg() {
        return flg;
    }

    public String getAreaid() {
        return areaid;
    }

    public String getCode() {
        return code;
    }

    public String getPic() {
        return pic;
    }

    public String getStudentno() {
        return studentno;
    }

    public int getType() {
        return type;
    }

    public String getPassword() {
        return password;
    }

    public List<UserVosEntity> getUserVos() {
        return userVos;
    }

    public long getId() {
        return id;
    }

    public boolean isSetPaypwd() {
        return setPaypwd;
    }

    public double getBalance() {
        return balance;
    }

    public String getSchoolname() {
        return schoolname;
    }

    public String getZfpassword() {
        return zfpassword;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserInfo)) return false;

        UserInfo info = (UserInfo) o;

        return id == info.id;

    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "qm='" + qm + '\'' +
                ", schoolid='" + schoolid + '\'' +
                ", phone='" + phone + '\'' +
                ", stime=" + stime +
                ", areaname='" + areaname + '\'' +
                ", nickname='" + nickname + '\'' +
                ", flg='" + flg + '\'' +
                ", areaid='" + areaid + '\'' +
                ", code='" + code + '\'' +
                ", pic='" + pic + '\'' +
                ", studentno='" + studentno + '\'' +
                ", type='" + type + '\'' +
                ", password='" + password + '\'' +
                ", userVos=" + userVos +
                ", id=" + id +
                ", setPaypwd=" + setPaypwd +
                ", balance=" + balance +
                ", schoolname='" + schoolname + '\'' +
                ", zfpassword='" + zfpassword + '\'' +
                '}';
    }

    public static class UserVosEntity {
        /**
         * id : 14
         * phone : 18623101683
         * name : null
         * pic : null
         * type : 3
         */
        private int id;
        private String phone;
        private String name;
        private String pic;
        private String type;

        public void setId(int id) {
            this.id = id;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }

        public void setType(String type) {
            this.type = type;
        }

        public int getId() {
            return id;
        }

        public String getPhone() {
            return phone;
        }

        public String getName() {
            return name;
        }

        public String getPic() {
            return pic;
        }

        public String getType() {
            return type;
        }

        @Override
        public String toString() {
            return "UserVosEntity{" +
                    "id=" + id +
                    ", phone='" + phone + '\'' +
                    ", name='" + name + '\'' +
                    ", pic='" + pic + '\'' +
                    ", type='" + type + '\'' +
                    '}';
        }
    }
}
