package com.peoit.android.studentuga.entity;

import java.io.Serializable;

/**
 * author:libo
 * time:2015/11/10
 * E-mail:boli_android@163.com
 * last: ...
 */
public class GetMoneyInfo implements Serializable {
    /**
     * id : 10
     * approvalbz : null
     * approvaluid : 14
     * phone : 18623101684
     * approvaltime : 1446648140000
     * nickname : 狗二蛋同学
     * bz : null
     * state : Y
     * userid : 15
     * money : 3047
     * ctime : 1446648107000
     */
    private long id;
    private String approvalbz;
    private long approvaluid;
    private String phone;
    private long approvaltime;
    private String nickname;
    private String bz;
    private String state;
    private long userid;
    private int money;
    private long ctime;

    public void setId(int id) {
        this.id = id;
    }

    public void setApprovalbz(String approvalbz) {
        this.approvalbz = approvalbz;
    }

    public void setApprovaluid(int approvaluid) {
        this.approvaluid = approvaluid;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setApprovaltime(long approvaltime) {
        this.approvaltime = approvaltime;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setBz(String bz) {
        this.bz = bz;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public void setCtime(long ctime) {
        this.ctime = ctime;
    }

    public long getId() {
        return id;
    }

    public String getApprovalbz() {
        return approvalbz;
    }

    public long getApprovaluid() {
        return approvaluid;
    }

    public String getPhone() {
        return phone;
    }

    public long getApprovaltime() {
        return approvaltime;
    }

    public String getNickname() {
        return nickname;
    }

    public String getBz() {
        return bz;
    }

    public String getState() {
        return state;
    }

    public long getUserid() {
        return userid;
    }

    public int getMoney() {
        return money;
    }

    public long getCtime() {
        return ctime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GetMoneyInfo)) return false;

        GetMoneyInfo that = (GetMoneyInfo) o;

        return id == that.id;

    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }

    @Override
    public String toString() {
        return "GetMoneyInfo{" +
                "id=" + id +
                ", approvalbz='" + approvalbz + '\'' +
                ", approvaluid=" + approvaluid +
                ", phone='" + phone + '\'' +
                ", approvaltime=" + approvaltime +
                ", nickname='" + nickname + '\'' +
                ", bz='" + bz + '\'' +
                ", state='" + state + '\'' +
                ", userid=" + userid +
                ", money=" + money +
                ", ctime=" + ctime +
                '}';
    }
}
