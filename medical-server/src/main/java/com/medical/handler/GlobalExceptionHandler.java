package com.medical.handler;

import com.medical.exception.AdminStatusErrorException;
import com.medical.exception.PasswordErrorException;
import com.medical.exception.UsernameNotFoundException;
import com.medical.exception.BaseException;
import com.medical.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


/**
 * 全局异常处理器，处理项目中抛出的业务异常
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 捕获业务异常
     * @param ex BaseException
     * @return Result
     */
    @ExceptionHandler(BaseException.class)
    public Result<String> exceptionHandler(BaseException ex){
        log.error("异常信息：{}", ex.getMessage());
        return Result.error(ex.getMessage());
    }

    /**
     * 捕获管理员状态异常
     * @param ex AdminStatusErrorException
     * @return Result
     */
    @ExceptionHandler(AdminStatusErrorException.class)
    public Result<String> exceptionHandler(AdminStatusErrorException ex){
        log.error("管理员状态异常：{}", ex.getMessage());
        return Result.error(ex.getMessage());
    }

    /**
     * 捕获用户名不存在异常
     * @param ex UsernameNotFoundException
     * @return Result
     */
    @ExceptionHandler(UsernameNotFoundException.class)
    public Result<String> exceptionHandler(UsernameNotFoundException ex){
        log.error("用户名不存在异常：{}", ex.getMessage());
        return Result.error(ex.getMessage());
    }

    /**
     * 捕获密码错误异常
     * @param ex PasswordErrorException
     * @return Result
     */
    @ExceptionHandler(PasswordErrorException.class)
    public Result<String> exceptionHandler(PasswordErrorException ex){
        log.error("密码错误异常：{}", ex.getMessage());
        return Result.error(ex.getMessage());
    }

    /**
     * 捕获其他异常
     * @param ex Exception
     * @return Result
     */
    @ExceptionHandler(Exception.class)
    public Result<String> exceptionHandler(Exception ex){
        log.error("未知异常：{}", ex.getMessage(), ex);
        return Result.error("服务器内部错误");
    }
}
