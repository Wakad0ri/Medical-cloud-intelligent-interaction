package com.medical.dto;

import lombok.Data;

@Data
public class DepartmentPageQueryDTO {

    // 条件查询
    private String deptName;
    private Integer status;
    private Integer deptType;
    private Long directId;
    private Integer isOutpatient;
    private Integer isInpatient;
    private Integer isEmergency;

    // 分页参数
    private Integer page;
    private Integer pageSize;
}
