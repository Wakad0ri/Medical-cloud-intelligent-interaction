package com.medical.options;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class DoctorOptions {

    @Schema(description = "医生ID")
    private Long id;

    @Schema(description = "医生姓名")
    private String doctorName;

}
