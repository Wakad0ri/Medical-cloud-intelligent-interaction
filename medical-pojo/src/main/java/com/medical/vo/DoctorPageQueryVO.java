package com.medical.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 医生分页查询结果
 * 相比doctor添加了“deptName"字段
 */
@Data
public class DoctorPageQueryVO {

    @Schema(description = "医生id")
    private Long id;

    @Schema(description = "医生编号")
    private String doctorNo;

    @Schema(description = "用户名")
    private String username;

    @Schema(description = "医生名称")
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

    @Schema(description = "职称")
    private String title;

    @Schema(description = "科室id")
    private Long departmentId;

    @Schema(description = "科室名")
    private String deptName;  // 作为临时字段

    @Schema(description = "账号状态")
    private Integer status;

    @Schema(description = "特长")
    private String specialty;

    @Schema(description = "工作年限")
    private Integer workYears;

    @Schema(description = "医生简介")
    private String introduction;

    @Schema(description = "头像")
    private String avatar;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    @Schema(description = "更新时间")
    private LocalDateTime updateTime;

    @Schema(description = "创建人")
    private Long createAdmin;

    @Schema(description = "更新人")
    private Long updateAdmin;   // 若为医生自行创建则为0

}
