package com.medical.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Knife4j API文档配置
 */
@Configuration
@Slf4j
public class OpenApiConfig {

    /**
     * 全局OpenAPI配置
     */
    @Bean
    public OpenAPI openAPI() {
        log.info("开始生成接口文档...");
        return new OpenAPI()
                .info(new Info()
                        .title("医云智能互动系统API文档")
                        .version("1.0.0")
                        .description("医云智能互动系统接口文档")
                        .contact(new io.swagger.v3.oas.models.info.Contact()
                                .name("医云智能互动系统开发团队")
                                .email("admin@medical.com")
                                .url("http://localhost:8080"))
                );
    }

    /**
     * 管理端API分组
     */
    @Bean
    public GroupedOpenApi adminApi() {
        log.info("开始生成管理端接口文档分组...");
        return GroupedOpenApi.builder()
                .group("管理端接口")
                .pathsToMatch("/admin/**")
                .packagesToScan("com.medical.controller.admin")
                .build();
    }

    /**
     * 医生端API分组
     */
    @Bean
    public GroupedOpenApi doctorApi() {
        log.info("开始生成医生端接口文档分组...");
        return GroupedOpenApi.builder()
                .group("医生端接口")
                .pathsToMatch("/doctor/**")
                .packagesToScan("com.medical.controller.doctor")
                .build();
    }

    /**
     * 患者端API分组
     */
    @Bean
    public GroupedOpenApi patientApi() {
        log.info("开始生成患者端接口文档分组...");
        return GroupedOpenApi.builder()
                .group("患者端接口")
                .pathsToMatch("/patient/**")
                .packagesToScan("com.medical.controller.patient")
                .build();
    }

}
