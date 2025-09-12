package com.medical.dto;

import lombok.Data;

/**
 * 医生更新数据传输对象
 * 相较于doctor类，缺少了“doctorNo, createTime, updateTime, createAdmin, updateAdmin”字段
 */
@Data
public class DoctorUpdateDTO {

    private Long id;
    private String realName;
    private String username;
    private String password;
    private Integer gender;
    private Integer age;
    private String phone;
    private String email;
    private String idCard;
    private Long departmentId;
    private String title;
    private String specialty;
    private Integer workYears;
    private String introduction;
    private Integer status;
    private String avatar;
}
