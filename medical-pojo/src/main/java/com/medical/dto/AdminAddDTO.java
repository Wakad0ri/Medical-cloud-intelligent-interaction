package com.medical.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * 管理员新增请求
 * 需要：用户名，用户密码，真实姓名，手机号，身份证号，邮箱，性别，年龄
 *      头像（可有可无）
 */
@Data
@Schema(description = "管理员新增请求")
public class AdminAddDTO implements Serializable {

    @Schema()
    private String username;
}
