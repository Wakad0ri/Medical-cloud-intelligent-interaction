package com.medical.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * OpenAPI 3.0 配置 - 临时禁用
 */
//@Configuration
@Slf4j
public class OpenApiConfig {

    /**
     * 全局OpenAPI配置
     */
    @Bean
    public OpenAPI customOpenAPI() {
        log.info("初始化OpenAPI配置...");
        return new OpenAPI()
                .info(new Info()
                        .title("医云智能互动系统API文档")
                        .version("1.0.0")
                        .description("医云智能互动系统接口文档，包含管理端和用户端所有接口")
                        .contact(new Contact()
                                .name("医云智能互动系统开发团队")
                                .email("admin@medical.com")
                                .url("http://localhost:8080"))
                        .license(new License()
                                .name("MIT License")
                                .url("https://opensource.org/licenses/MIT")))
                .servers(List.of(
                        new Server().url("http://localhost:8080").description("开发环境"),
                        new Server().url("https://api.medical.com").description("生产环境")
                ));
    }

    /**
     * 管理端API分组
     */
    @Bean
    public GroupedOpenApi adminApi() {
        log.info("配置管理端API分组...");
        return GroupedOpenApi.builder()
                .group("管理端接口")
                .pathsToMatch("/admin/**")
                .packagesToScan("com.medical.controller.admin")
                .build();
    }

    /**
     * 用户端API分组
     */
    @Bean
    public GroupedOpenApi userApi() {
        log.info("配置用户端API分组...");
        return GroupedOpenApi.builder()
                .group("用户端接口")
                .pathsToMatch("/user/**")
                .packagesToScan("com.medical.controller.user")
                .build();
    }

    /**
     * 公共API分组
     */
    @Bean
    public GroupedOpenApi publicApi() {
        log.info("配置公共API分组...");
        return GroupedOpenApi.builder()
                .group("公共接口")
                .pathsToMatch("/api/**", "/common/**")
                .packagesToScan("com.medical.controller.common")
                .build();
    }
}
