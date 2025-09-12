package com.medical.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * 医生信息实体类
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Doctor {

    // 基本信息
    @Schema(description = "主键id")
    private Long id;
    @Schema(description = "医生编号")
    private String doctorNo;
    @Schema(description = "用户名")
    private String username;
    @Schema(description = "密码")
    private String password;
    @Schema(description = "姓名")
    private String realName;
    @Schema(description = "性别")
    private Integer gender;
    @Schema(description = "年龄")
    private Integer age;
    @Schema(description = "手机号")
    private String phone;
    @Schema(description = "邮箱")
    private String email;
    @Schema(description = "身份证号")
    private String idCard;

    // 职业相关信息
    @Schema(description = "科室id")
    private Long departmentId;
    @Schema(description = "职称")
    private String title;
    @Schema(description = "专业特长")
    private String specialty;
    @Schema(description = "工作年限")
    private Integer workYears;
    @Schema(description = "医生简介")
    private String introduction;

    // 账号状态
    @Schema(description = "账号状态")
    private Integer status; // 0-禁用 1-正常 2-工作
    // 删除 isDirector 字段，通过查询 department 表判断

    // 系统字段
    @Schema(description = "头像")
    private String avatar;
    @Schema(description = "创建时间")
    private LocalDateTime createTime;
    @Schema(description = "更新时间")
    private LocalDateTime updateTime;
    @Schema(description = "创建人")
    private Long createAdmin;
    @Schema(description = "更新人")
    private Long updateAdmin;

}
