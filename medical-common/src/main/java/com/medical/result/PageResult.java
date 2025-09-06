package com.medical.result;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageResult {

    @Schema(description = "总记录数")
    private Long total;

    @Schema(description = "记录")
    private List records;
}
