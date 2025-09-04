package com.medical.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 用户登录DTO
 */
@Data
@Schema(description = "用户登录请求")
public class UserLoginDTO implements Serializable {

    @Schema(description = "用户名")
    @NotBlank(message = "用户名不能为空")
    private String username;

    @Schema(description = "密码")
    @NotBlank(message = "密码不能为空")
    private String password;

    @Schema(description = "用户类型 1-患者 2-医生 3-管理员")
    private Integer userType;
}
