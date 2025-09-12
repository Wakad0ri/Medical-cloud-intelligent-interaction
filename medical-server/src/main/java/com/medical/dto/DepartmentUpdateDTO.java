package com.medical.dto;

import lombok.Data;

@Data
public class DepartmentUpdateDTO {

    private Long id;
    private String deptName;
    private String description;
    private String location;
    private String phone;
    private Long directorId;
    private Integer deptType;
    private Long parentId;
    private Integer level;
    private Integer bedCount;
    private Double consultationFee;
    private Integer sortOrder;
    private Integer status;
    private Integer isOutpatient;
    private Integer isInpatient;
    private Integer isEmergency;
}
