package com.medical.dto;

import lombok.Data;

/**
 * 科室更新数据传输对象
 * 相较于department类，缺少了“id, deptCode, createTime, updateTime, createAdmin, updateAdmin”字段
 */
@Data
public class DepartmentUpdateDTO {

    private String deptName;
    private Integer deptType;
    private Long parentId;
    private Integer level;
    private String description;
    private String location;
    private String phone;
    private Long directorId;
    private Integer bedCount;
    private Double consultationFee;
    private Integer sortOrder;
    private Integer status;
    private Integer isOutpatient;
    private Integer isInpatient;
    private Integer isEmergency;
}
