package com.peoit.android.studentuga.entity;

import java.io.Serializable;

/**
 * author:libo
 * time:2015/11/10
 * E-mail:boli_android@163.com
 * last: ...
 */
public class BindInfo implements Serializable {

    /**
     * id : 15
     * phone : 18623101684
     * name : 狗二蛋同学
     * pic : null
     * type : 2
     */
    private long id;
    private String phone;
    private String name;
    private String pic;
    private String type;

    public void setId(long id) {
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

    public long getId() {
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BindInfo)) return false;

        BindInfo bindInfo = (BindInfo) o;

        return id == bindInfo.id;

    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }

    @Override
    public String toString() {
        return "BindInfo{" +
                "id=" + id +
                ", phone='" + phone + '\'' +
                ", name='" + name + '\'' +
                ", pic='" + pic + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
