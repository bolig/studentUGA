package com.peoit.android.studentuga.error;

/**
 * author:libo
 * time:2015/9/23
 * E-mail:boli_android@163.com
 * last: ...
 */
public class NoCarryException extends RuntimeException {

    public NoCarryException() {
        this(" this method is no carry ");
    }

    public NoCarryException(String detailMessage) {
        super(detailMessage);
    }

    public NoCarryException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    public NoCarryException(Throwable throwable) {
        super(throwable);
    }

}
