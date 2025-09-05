package com.medical.exception;

/**
 * 密码错误异常
 */
public class PasswordErrorException extends BaseException{

    public PasswordErrorException()
    {
        super();
    }

    public PasswordErrorException(String msg)
    {
        super(msg);
    }
}
