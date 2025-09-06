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
    public void before(JoinPoint joinPoint){
        log.info("开始进行自动填充");
        // 获取方法签名，获取方法上的参数
        MethodSignature sign = (MethodSignature) joinPoint.getSignature();
        AutoFill autoFill = sign.getMethod().getAnnotation(AutoFill.class);

        Object[] args = joinPoint.getArgs();
        if (args.length == 0){
            return;
        }
        Object entity = args[0];

        // 业务逻辑：
        LocalDateTime now = LocalDateTime.now();
        Long currentId = BaseContext.getCurrentId();
        if (autoFill.value() == AutoFill.OperationType.INSERT){
            // entity.getClass(): 获取实体对象的Class对象
            // getDeclaredMethod("方法名", 方法参数类型): 获取指定的方法（包括私有方法）
            try {
                entity.getClass()
                        .getDeclaredMethod("setUpdateTime", LocalDateTime.class)
                        .invoke(entity, now);
                entity.getClass()
                        .getDeclaredMethod("setUpdateAdmin", Long.class)
                        .invoke(entity, currentId);
                entity.getClass()
                        .getDeclaredMethod("setCreateTime", LocalDateTime.class)
                        .invoke(entity, now);
                entity.getClass()
                        .getDeclaredMethod("setCreateAdmin", Long.class)
                        .invoke(entity, currentId);

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        if (autoFill.value() == AutoFill.OperationType.UPDATE){
            try {
                entity.getClass()
                        .getDeclaredMethod("setUpdateTime", LocalDateTime.class)
                        .invoke(entity, now);
                entity.getClass()
                        .getDeclaredMethod("setUpdateAdmin", Long.class)
                        .invoke(entity, currentId);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }



}
