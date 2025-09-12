package com.medical.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class AdminPageQueryDTO {

    // 搜索条件
    @Schema(description = "姓名")
    private String name;
    @Schema(description = "状态")
    private Integer status;




    // 固定分页条件
    @Schema(description = "当前页码")
    private Integer page;

    @Schema(description = "每页记录数")
    private Integer pageSize;
}
