package com.medical.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Department {

    // 基本信息
    @Schema(description = "科室ID")
    private Long id;
    @Schema(description = "科室编码")
    private String deptCode;
    @Schema(description = "科室名称")
    private String deptName;
    @Schema(description = "科室类型")   // 0-临床科室 1-医技科室 2-管理科室
    private Integer deptType;
    @Schema(description = "上级科室")
    private Long parentId;
    @Schema(description = "科室层级")
    private Integer level;

    // 科室详细信息
    @Schema(description = "科室描述")
    private String description;
    @Schema(description = "科室地址")
    private String location;
    @Schema(description = "科室电话")
    private String phone;
    @Schema(description = "科室主任ID")
    private Long directorId;

    // 业务信息
    @Schema(description = "科室床位")
    private Integer bedCount;
    @Schema(description = "挂号费用")
    private Double consultationFee;
    @Schema(description = "排序")
    private Integer sortOrder;

    // 状态信息
    @Schema(description = "状态") // 0-禁用 1-正常
    private Integer status;
    @Schema(description = "是否支持门诊")
    private Integer isOutpatient;
    @Schema(description = "是否支持住院")
    private Integer isInpatient;
    @Schema(description = "是否支持急诊科室")
    private Integer isEmergency;

    // 系统字段
    @Schema(description = "创建时间")
    private LocalDateTime createTime;
    @Schema(description = "更新时间")
    private LocalDateTime updateTime;
    @Schema(description = "创建人")
    private Long createAdmin;
    @Schema(description = "更新人")
    private Long updateAdmin;

}
