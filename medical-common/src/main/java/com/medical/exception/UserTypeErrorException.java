package com.medical.exception;

/**
 * 用户类型错误异常
 */
public class UserTypeErrorException extends BaseException{

    public UserTypeErrorException() {
        super();
    }

    public UserTypeErrorException(String msg)
    {
        super(msg);
    }
}
