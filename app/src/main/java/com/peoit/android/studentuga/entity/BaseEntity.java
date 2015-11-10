package com.peoit.android.studentuga.entity;

import com.google.gson.annotations.Expose;

import java.io.Serializable;

/**
 * author:libo
 * time:2015/9/22
 * E-mail:boli_android@163.com
 * last: ...
 */
public class BaseEntity<T> implements Serializable {
    /**
     * message : 登陆成功
     * obj : null
     * code : 0
     * success : true
     */
    private String message;
    private int code = -1;
    private boolean success = false;

    @Expose(serialize = true)
    private T obj;

    public void setMessage(String message) {
        this.message = message;
    }

    public void setObj(T obj) {
        this.obj = obj;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public T getObj() {
        return obj;
    }

    public int getCode() {
        return code;
    }

    public boolean isSuccess() {
        return success;
    }

    @Override
    public String toString() {
        return "BaseEntity{" +
                "message='" + message + '\'' +
                ", obj=" + obj +
                ", code=" + code +
                ", success=" + success +
                '}';
    }
}
