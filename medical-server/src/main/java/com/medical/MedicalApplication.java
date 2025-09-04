package com.medical;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 医云智能互动系统启动类
 */
@SpringBootApplication
@EnableTransactionManagement //开启注解方式的事务管理
@Slf4j
public class MedicalApplication {
    public static void main(String[] args) {
        SpringApplication.run(MedicalApplication.class, args);
        log.info("医云智能互动系统启动成功！");
    }
}
