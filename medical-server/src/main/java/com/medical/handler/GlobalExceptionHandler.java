package com.medical.handler;

import com.medical.exception.AdminStatusErrorException;
import com.medical.exception.DoctorStatusErrorException;
import com.medical.exception.PasswordErrorException;
import com.medical.exception.UsernameNotFoundException;
import com.medical.exception.BaseException;
import com.medical.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.sql.SQLException;


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
     * 捕获医生状态异常
     * @param ex DoctorStatusErrorException
     * @return Result
     */
    @ExceptionHandler(DoctorStatusErrorException.class)
    public Result<String> exceptionHandler(DoctorStatusErrorException ex){
        log.error("医生状态异常：{}", ex.getMessage());
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
     * 捕获SQL异常
     * @param ex SQLException
     * @return Result
     */
    @ExceptionHandler(SQLException.class)
    public Result<String> exceptionHandler(SQLException ex){
        log.error("SQL异常：{}", ex.getMessage(), ex);
        return Result.error("数据库操作异常");
    }

    /**
     * 捕获数据访问异常
     * @param ex DataAccessException
     * @return Result
     */
    @ExceptionHandler(DataAccessException.class)
    public Result<String> exceptionHandler(DataAccessException ex){
        log.error("数据访问异常：{}", ex.getMessage(), ex);
        return Result.error("数据访问异常");
    }

    /**
     * 捕获其他异常
     * @param ex Exception
     * @return Result
     */
    @ExceptionHandler(Exception.class)
    public Result<String> exceptionHandler(Exception ex){
        String errorMessage = ex.getMessage();
        if (errorMessage == null || errorMessage.trim().isEmpty()) {
            errorMessage = "未知错误：" + ex.getClass().getSimpleName();
        }
        log.error("未知异常：{}", errorMessage, ex);
        return Result.error("服务器内部错误");
    }
}
