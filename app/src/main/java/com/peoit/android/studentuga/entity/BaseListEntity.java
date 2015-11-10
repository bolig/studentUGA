package com.peoit.android.studentuga.entity;

import java.io.Serializable;
import java.util.List;

/**
 * author:libo
 * time:2015/7/13
 * E-mail:boli_android@163.com
 * last: ...
 */
public class BaseListEntity<T> implements Serializable {
    /**
     * message : 登陆成功
     * obj : null
     * code : 0
     * success : true
     */
    private String message;
    private int code = -1;
    private boolean success = false;

    private List<T> obj;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<T> getObj() {
        return obj;
    }

    public void setObj(List<T> obj) {
        this.obj = obj;
    }

    @Override
    public String toString() {
        return "BaseListEntity{" +
                "message='" + message + '\'' +
                ", code=" + code +
                ", success=" + success +
                ", obj=" + obj +
                '}';
    }
}
