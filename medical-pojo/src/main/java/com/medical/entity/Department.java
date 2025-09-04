package com.medical.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 科室实体
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("department")
public class Department implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    // 科室名称
    private String name;

    // 科室编码
    private String code;

    // 科室描述
    private String description;

    // 科室位置
    private String location;

    // 科室电话
    private String phone;

    // 科室主任ID
    private Long directorId;

    // 排序
    private Integer sort;

    // 状态 1-正常 0-停用
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
