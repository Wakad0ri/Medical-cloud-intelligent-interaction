package com.medical.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class AdminUpdateDTO {

    @Schema(description = "用户ID")
    private Long id;

    @Schema(description = "用户名")
    private String username;

    @Schema(description = "姓名")
    private String realName;

    @Schema(description = "手机号")
    private String phone;

    @Schema(description = "身份证号")
    private String idCard;

    @Schema(description = "邮箱")
    private String email;

    @Schema(description = "性别")
    private Integer gender;

    @Schema(description = "年龄")
    private Integer age;

    @Schema(description = "头像")
    private String avatar;

    @Schema(description = "状态")
    private Integer status;

}
