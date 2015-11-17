package com.peoit.android.studentuga.entity;

import java.io.Serializable;

/**
 * Created by ling on 2015/11/17.
 * description:
 */
public class UserStu implements Serializable {
    /**
     * id : 134
     * phone : 15084387696
     * nickname : 15084387696
     * pic : /upImg/201511161520329155598.jpg
     * type : 2
     * password : null
     * zfpassword : null
     * balance : null
     * schoolid : 85
     * schoolname : 石榴红赛区
     * areaid : 1
     * areaname : 石榴红赛区
     * studentno : 1510310036
     * code : null
     * stime : 1447653577000
     * qm : 大众创业万众创新，今天很残酷，明天很残酷，后天更残酷，所以大家只要有明确的目标，坚定的信念，坚持下去你一定可以成功。相信下一个奇迹就是你。
     * flg : Y
     * isjoin : Y
     * joinid : 51
     * totalsales : 0
     * userVos : []
     * setPaypwd : false
     * totalVo : {"shcoolTop":null,"areaTop":null,"totlTop":null}
     */

    private int id;
    private String phone;
    private String nickname;
    private String pic;
    private String type;
    private Object password;
    private Object zfpassword;
    private Object balance;
    private int schoolid;
    private String schoolname;
    private int areaid;
    private String areaname;
    private String studentno;
    private Object code;
    private long stime;
    private String qm;
    private String flg;
    private String isjoin;
    private int joinid;
    private int totalsales;
    private boolean setPaypwd;
    /**
     * shcoolTop : null
     * areaTop : null
     * totlTop : null
     */

    private TotalVoEntity totalVo;
    //private List<?> userVos;

    public void setId(int id) {
        this.id = id;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setPassword(Object password) {
        this.password = password;
    }

    public void setZfpassword(Object zfpassword) {
        this.zfpassword = zfpassword;
    }

    public void setBalance(Object balance) {
        this.balance = balance;
    }

    public void setSchoolid(int schoolid) {
        this.schoolid = schoolid;
    }

    public void setSchoolname(String schoolname) {
        this.schoolname = schoolname;
    }

    public void setAreaid(int areaid) {
        this.areaid = areaid;
    }

    public void setAreaname(String areaname) {
        this.areaname = areaname;
    }

    public void setStudentno(String studentno) {
        this.studentno = studentno;
    }

    public void setCode(Object code) {
        this.code = code;
    }

    public void setStime(long stime) {
        this.stime = stime;
    }

    public void setQm(String qm) {
        this.qm = qm;
    }

    public void setFlg(String flg) {
        this.flg = flg;
    }

    public void setIsjoin(String isjoin) {
        this.isjoin = isjoin;
    }

    public void setJoinid(int joinid) {
        this.joinid = joinid;
    }

    public void setTotalsales(int totalsales) {
        this.totalsales = totalsales;
    }

    public void setSetPaypwd(boolean setPaypwd) {
        this.setPaypwd = setPaypwd;
    }

    public void setTotalVo(TotalVoEntity totalVo) {
        this.totalVo = totalVo;
    }


    public int getId() {
        return id;
    }

    public String getPhone() {
        return phone;
    }

    public String getNickname() {
        return nickname;
    }

    public String getPic() {
        return pic;
    }

    public String getType() {
        return type;
    }

    public Object getPassword() {
        return password;
    }

    public Object getZfpassword() {
        return zfpassword;
    }

    public Object getBalance() {
        return balance;
    }

    public int getSchoolid() {
        return schoolid;
    }

    public String getSchoolname() {
        return schoolname;
    }

    public int getAreaid() {
        return areaid;
    }

    public String getAreaname() {
        return areaname;
    }

    public String getStudentno() {
        return studentno;
    }

    public Object getCode() {
        return code;
    }

    public long getStime() {
        return stime;
    }

    public String getQm() {
        return qm;
    }

    public String getFlg() {
        return flg;
    }

    public String getIsjoin() {
        return isjoin;
    }

    public int getJoinid() {
        return joinid;
    }

    public int getTotalsales() {
        return totalsales;
    }

    public boolean isSetPaypwd() {
        return setPaypwd;
    }

    public TotalVoEntity getTotalVo() {
        return totalVo;
    }


    public static class TotalVoEntity implements Serializable{
        private Object shcoolTop;
        private Object areaTop;
        private Object totlTop;

        public void setShcoolTop(Object shcoolTop) {
            this.shcoolTop = shcoolTop;
        }

        public void setAreaTop(Object areaTop) {
            this.areaTop = areaTop;
        }

        public void setTotlTop(Object totlTop) {
            this.totlTop = totlTop;
        }

        public Object getShcoolTop() {
            return shcoolTop;
        }

        public Object getAreaTop() {
            return areaTop;
        }

        public Object getTotlTop() {
            return totlTop;
        }
    }
}
