package com.medical.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 添加医生参数
 * 相较于doctor类，缺少了“doctorNo, status, createTime, updateTime, createAdmin, updateAdmin”字段
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DoctorAddDTO {

    private Long id;
    private String username;
    private String password;
    private String realName;
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
    private String avatar;
}
