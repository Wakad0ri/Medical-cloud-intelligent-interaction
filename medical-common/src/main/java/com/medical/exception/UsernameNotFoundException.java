package com.medical.exception;

/**
 * 用户名不存在异常
 */
public class UsernameNotFoundException extends BaseException {

    public UsernameNotFoundException(String msg) {
        super(msg);
    }
}
