package com.medical.aspect;

import com.medical.annotation.AutoFill;
import com.medical.context.BaseContext;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.time.LocalDateTime;

/**
 * 日志切面 - 用于测试AOP配置
 */
@Aspect
@Component
@Slf4j
public class AutoFillAspect {

    // @PointCut 注解的作用是：定义一个切点，可以被其他通知引用
    @Pointcut("execution(* com.medical.mapper.*.*(..)) &&  @annotation(com.medical.annotation.AutoFill)")
    public void pc1(){}

    @Before("pc1()")
    public void before(JoinPoint joinPoint) throws Exception {
        log.info("开始进行自动填充");
        // 获取方法签名，获取方法上的参数
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Object[] args = joinPoint.getArgs();
        if (args.length == 0)  return;
        Object aimArg = args[0];

        // 业务逻辑：
        LocalDateTime now = LocalDateTime.now();
        Long currentId = BaseContext.getCurrentId();
        Method method = signature.getMethod();
        AutoFill autoFill = method.getAnnotation(AutoFill.class);
        AutoFill.OperationType value = autoFill.value();
        switch (value){
            case INSERT:
                aimArg.getClass().getDeclaredMethod("setUpdateTime", LocalDateTime.class).invoke(aimArg, now);
                aimArg.getClass().getDeclaredMethod("setUpdateAdmin", Long.class).invoke(aimArg, currentId);
                aimArg.getClass().getDeclaredMethod("setCreateTime", LocalDateTime.class).invoke(aimArg, now);
                aimArg.getClass().getDeclaredMethod("setCreateAdmin", Long.class).invoke(aimArg, currentId);
                break;
            case UPDATE:
                aimArg.getClass().getDeclaredMethod("setUpdateTime", LocalDateTime.class).invoke(aimArg, now);
                aimArg.getClass().getDeclaredMethod("setUpdateAdmin", Long.class).invoke(aimArg, currentId);
                break;
        }
    }
}
