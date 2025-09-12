package com.medical.vo;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 医生信息VO
 * 相比doctor缺少了“密码”属性
 */
@Data
public class DoctorInfoVO {

    private Long id;
    private String doctorNo;
    private String username;
    private String realName;
    private Integer gender;
    private Integer age;
    private String phone;
    private String email;
    private String idCard;
    private Long departmentId;
    private String title;
    private String specialty;
    private Integer workTime;
    private String introduction;
    private String avatar;
    private Integer status;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private Long createAdmin;
    private Long updateAdmin;

}
