package com.medical.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 管理员信息实体类
 */
@Data
public class Admin implements Serializable {

    @Schema(description = "用户ID")
    private Long id;

    @Schema(description = "用户名")
    private String username;

    @Schema(description = "密码")
    private String password;

    @Schema(description = "姓名")
    private String realName;

    @Schema(description = "手机号")
    private String phone;

    @Schema(description = "状态") // 0-禁用 1-正常
    private Integer status;

    @Schema(description = "身份证号")
    private String idCard;

    @Schema(description = "邮箱")
    private String email;

    @Schema(description = "性别")
    private Integer sex;

    @Schema(description = "年龄")
    private Integer age;

    @Schema(description = "头像")
    private String avatar;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")    // LocalDateTime格式化
    private LocalDateTime createTime;

    @Schema(description = "创建人")
    private LocalDateTime createAdmin;

    @Schema(description = "更新时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")    // LocalDateTime格式化
    private LocalDateTime updateTime;

    @Schema(description = "更新人")
    private LocalDateTime updateAdmin;

}
