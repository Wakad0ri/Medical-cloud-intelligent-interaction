package com.medical.dto;

import lombok.Data;

@Data
public class DoctorPageQueryDTO {

    // 搜索条件
    private String realName;
    private Long departmentId;
    private Integer status;

    // 分页条件
    private Integer page;
    private Integer pageSize;
}
