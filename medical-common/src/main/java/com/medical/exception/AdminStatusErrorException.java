package com.medical.exception;

/**
 * 管理员状态异常
 */
public class AdminStatusErrorException extends BaseException {

    public AdminStatusErrorException() {
        super();
    }

    public AdminStatusErrorException(String msg) {
        super(msg);
    }
}
