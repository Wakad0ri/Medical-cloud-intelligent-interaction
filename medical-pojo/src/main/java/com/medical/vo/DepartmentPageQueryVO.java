package com.medical.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class DepartmentPageQueryVO {

    private Long id;
    private String deptCode;
    private String deptName;
    private Integer deptType;
    private Long parentId;
    private Integer level;
    private String description;
    private String location;
    private String phone;
    private Long directorId;
    // 临时字段
    private String directorName;
    private Integer bedCount;
    private Double consultationFee;
    private Integer sortOrder;
    private Integer status;
    private Integer isOutpatient;
    private Integer isInpatient;
    private Integer isEmergency;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private Long createAdmin;
    private Long updateAdmin;

}
