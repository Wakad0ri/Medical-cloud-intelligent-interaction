package com.medical.options;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class DepartmentOptions {

    @Schema(description = "科室id")
    private Long id;

    @Schema(description = "科室名称")
    private String deptName;
}
