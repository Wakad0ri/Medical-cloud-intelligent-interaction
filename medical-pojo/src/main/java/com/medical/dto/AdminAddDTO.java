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

    @Schema(description = "用户名")
    private String username;

    @Schema(description = "密码")
    private String password;

    @Schema(description = "姓名")
    private String realName;

    @Schema(description = "手机号")
    private String phone;

    @Schema(description = "身份证号")
    private String idCard;

    @Schema(description = "邮箱")
    private String email;

    @Schema(description = "性别")
    private Integer gender;

    @Schema(description = "年龄")
    private Integer age;

    @Schema(description = "头像")
    private String avatar;

}
