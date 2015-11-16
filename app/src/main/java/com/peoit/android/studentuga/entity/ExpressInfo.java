package com.peoit.android.studentuga.entity;

import java.io.Serializable;

/**
 * author:libo
 * time:2015/10/28
 * E-mail:boli_android@163.com
 * last: ...
 */
public class ExpressInfo implements Serializable {
    /**
     * name : aae
     * mark : aae全球专递
     */
    private String name;

    private String mark;

    public void setName(String name) {
        this.name = name;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public String getName() {
        return name;
    }

    public String getMark() {
        return mark;
    }



    @Override
    public String toString() {
        return "ExpressInfo{" +
                "name='" + name + '\'' +
                ", mark='" + mark + '\'' +
                '}';
    }
}
