package com.medical.service;

import com.medical.dto.DepartmentAddDTO;
import com.medical.dto.DepartmentPageQueryDTO;
import com.medical.dto.DepartmentUpdateDTO;
import com.medical.entity.Department;
import com.medical.options.DepartmentOptions;
import com.medical.result.PageResult;

import java.util.List;

public interface DepartmentService {

    List<DepartmentOptions> options();

    Department getById(Long id);

    void update(DepartmentUpdateDTO departmentUpdateDTO);

    PageResult page(DepartmentPageQueryDTO departmentPageQueryDTO);

    void add(DepartmentAddDTO departmentAddDTO);

    void updateStatus(Integer status, List<Long> ids);

    void delete(List<Long> ids);
}
