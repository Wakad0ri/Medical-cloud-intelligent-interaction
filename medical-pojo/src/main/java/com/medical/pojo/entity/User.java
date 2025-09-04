package com.medical.pojo.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 用户实体
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("user")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    // 用户名
    private String username;

    // 密码
    private String password;

    // 真实姓名
    private String realName;

    // 手机号
    private String phone;

    // 邮箱
    private String email;

    // 性别 1-男 2-女
    private Integer gender;

    // 年龄
    private Integer age;

    // 身份证号
    private String idCard;

    // 头像
    private String avatar;

    // 用户类型 1-患者 2-医生 3-管理员
    private Integer userType;

    // 状态 1-正常 0-禁用
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
