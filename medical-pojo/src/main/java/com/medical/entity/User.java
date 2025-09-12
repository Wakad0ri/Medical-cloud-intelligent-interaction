package com.medical.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class User {
    // 基础信息
    private Long id;
    private String userNo;
    private String username;
    private String password;
    private String realName;
    private String phone;
    private String email;
    private String idCard;
    private Integer gender;
    private Integer age;    // 根据生日计算
    private String avatar;
    private String birthday;

    // 患者持有字段
    private String emergencyContact;
    private String emergencyPhone;
    private String bloodType;
    private String allergyHistory;
    private String address;

    // 状态字段
    private Integer status;
    private Integer isHospitalized;
    private Long departmentId;
    private LocalDateTime hospitalizedTime;
    // 出院时间,若为null则表示未出院，在update里不做更新，特写一个特殊的出院方法来更改
    private LocalDateTime dischargeTime;

    // 登录信息
    private LocalDateTime lastLoginTime;

    // 系统字段
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private Long createAdmin;
    private Long updateAdmin;

}
