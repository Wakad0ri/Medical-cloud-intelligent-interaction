package com.medical.controller.admin;

import com.medical.dto.DoctorAddDTO;
import com.medical.dto.DoctorPageQueryDTO;
import com.medical.dto.DoctorUpdateDTO;
import com.medical.entity.Doctor;
import com.medical.options.DoctorOptions;
import com.medical.result.PageResult;
import com.medical.result.Result;
import com.medical.service.DoctorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("adminDoctorController")
@Slf4j
@RequestMapping("/admin/doctor")
@Tag(name = "医生管理")
public class DoctorController {

    @Autowired
    private DoctorService doctorService;

    /**
     * 医生分页查询：GET
     * @param doctorPageQueryDTO json
     * @return Result<PageResult>
     */
    @GetMapping("/page")
    @Operation(summary = "分页查询医生")
    public Result<PageResult> page(DoctorPageQueryDTO doctorPageQueryDTO){
        log.info("分页查询医生：{}", doctorPageQueryDTO);
        PageResult pageResult = doctorService.page(doctorPageQueryDTO);
        return Result.success(pageResult);
    }

    /**
     * 获取医生选项
     */
    @GetMapping("/options")
    @Operation(summary = "获取医生选项")
    public Result<List<DoctorOptions>> options() {
        log.info("获取医生选项");
        List<DoctorOptions> options = doctorService.options();
        return Result.success(options);
    }

    /**
     * 新增医生
     */
    @PostMapping
    @Operation(summary = "新增医生")
    public Result<String> add(@RequestBody DoctorAddDTO doctorAddDTO) {
        log.info("新增医生：{}", doctorAddDTO);
        doctorService.add(doctorAddDTO);
        return Result.success();
    }

    /**
     * 更新医生信息
     */
    @PutMapping
    @Operation(summary = "更新医生信息")
    public Result<String> update(@RequestBody DoctorUpdateDTO doctorUpdateDTO) {
        log.info("更新医生信息：{}", doctorUpdateDTO);
        doctorService.update(doctorUpdateDTO);
        return Result.success();
    }

    /**
     * 批量更新医生状态
     */
    @PostMapping("/status/{status}")
    @Operation(summary = "批量更新医生状态")
    public Result<String> updateStatus(@PathVariable("status") Integer status, @RequestBody List<Long> ids) {
        log.info("批量更新医生状态：status={}, ids={}", status, ids);
        doctorService.updateStatus(status, ids);
        return Result.success();
    }
    
    /**
     * 批量删除医生
     */
    @DeleteMapping
    @Operation(summary = "批量删除医生")
    public Result<String> deleteByIds(@RequestParam("ids") List<Long> ids) {
        log.info("批量删除医生：{}", ids);
        doctorService.deleteByIds(ids);
        return Result.success();
    }

    /**
     * 根据ID查询医生
     */
    @GetMapping("/{id}")
    @Operation(summary = "根据ID查询医生")
    public Result<Doctor> getById(@PathVariable("id") Long id) {
        log.info("根据ID查询医生：{}", id);
        Doctor doctor = doctorService.getById(id);
        return Result.success(doctor);
    }
}
