package com.medical.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class AdminPageQueryVO {

    @Schema(description = "姓名")
    private String realName;

    @Schema(description = "账号")
    private String username;

    @Schema(description = "手机号")
    private String phone;

    @Schema(description = "账号状态")
    private Integer status;

    @Schema(description = "最后操作时间")
    private String updateTime;
}
