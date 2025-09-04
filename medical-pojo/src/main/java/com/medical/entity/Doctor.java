package com.medical.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 医生实体
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("doctor")
public class Doctor implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    // 用户ID
    private Long userId;

    // 医生编号
    private String doctorNo;

    // 科室ID
    private Long departmentId;

    // 职称
    private String title;

    // 专业特长
    private String specialty;

    // 简介
    private String introduction;

    // 挂号费
    private BigDecimal registrationFee;

    // 工作年限
    private Integer workYears;

    // 学历
    private String education;

    // 毕业院校
    private String graduateSchool;

    // 执业证书号
    private String licenseNumber;

    // 状态 1-在职 0-离职
    private Integer status;

    // 创建时间
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    // 更新时间
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    // 创建人
    @TableField(fill = FieldFill.INSERT)
    private Long createUser;

    // 修改人
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updateUser;
}
