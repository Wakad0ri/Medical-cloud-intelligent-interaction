package com.medical.controller.admin;

import com.medical.dto.DepartmentAddDTO;
import com.medical.dto.DepartmentPageQueryDTO;
import com.medical.dto.DepartmentUpdateDTO;
import com.medical.entity.Department;
import com.medical.options.DepartmentOptions;
import com.medical.result.PageResult;
import com.medical.result.Result;
import com.medical.service.DepartmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("adminDepartmentController")
@Slf4j
@RequestMapping("/admin/department")
@Tag(name = "科室管理")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    /**
     * 科室分页条件查询
     * @param departmentPageQueryDTO json
     * @return Result<PageResult>
     */
    @GetMapping("/page")
    @Operation(summary = "科室分页条件查询")
    public Result<PageResult> page(DepartmentPageQueryDTO departmentPageQueryDTO){
        log.info("科室分页条件查询：{}", departmentPageQueryDTO);
        PageResult pageResult = departmentService.page(departmentPageQueryDTO);
        return Result.success(pageResult);
    }

    /**
     * 新增科室
     * @param departmentAddDTO json
     */
    @PostMapping("/add")
    public Result<String> add(@RequestBody DepartmentAddDTO departmentAddDTO){
        log.info("新增科室：{}", departmentAddDTO);
        departmentService.add(departmentAddDTO);
        return Result.success();
    }

    /**
     * 下拉框获取科室信息
     * @return Result<List<DepartmentOptions>>
     */
    @GetMapping("/department-options")
    public Result<List<DepartmentOptions>> getDepartmentOptions(){
        log.info("下拉框获取科室信息");
        List<DepartmentOptions> departmentOptions = departmentService.options();
        return Result.success(departmentOptions);
    }

    /**
     * 更新部门信息
     * @param departmentUpdateDTO json
     * @return Result<String>
     */
    @PutMapping("/update")
    @Operation(summary = "更新部门信息")
    public Result<String> update(@RequestBody DepartmentUpdateDTO departmentUpdateDTO){
        log.info("更新部门信息：{}", departmentUpdateDTO);
        departmentService.update(departmentUpdateDTO);
        return Result.success();
    }

    /**
     * 批量更新部门状态
     * @param status Integer
     * @param ids List<Long>
     * @return Result<String>
     */
    @PutMapping("/status/{status}")
    @Operation(summary = "批量更新部门状态")
    public Result<String> updateStatus(@PathVariable("status") Integer status, @RequestParam("ids") List<Long> ids){
        log.info("批量更新部门状态：{}", ids);
        departmentService.updateStatus(status, ids);
        return Result.success();
    }

    /**
     * 删除部门
     * @param ids List<Long>
     * @return Result<String>
     */
    @DeleteMapping
    @Operation(summary = "删除部门")
    public Result<String> delete(@RequestParam("ids") List<Long> ids){
        log.info("删除部门：{}", ids);
        departmentService.delete(ids);
        return Result.success();
    }


    /**
     * 获取部门信息
     * @param id Long
     * @return Result<Department>
     */
    @GetMapping("/detail/{id}")
    public Result<Department> getById(@PathVariable("id") Long id){
        log.info("获取部门信息：{}", id);
        Department department = departmentService.getById(id);
        return Result.success(department);
    }

}
