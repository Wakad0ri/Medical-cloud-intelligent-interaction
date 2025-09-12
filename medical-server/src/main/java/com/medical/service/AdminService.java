package com.medical.service;

import com.medical.dto.AdminAddDTO;
import com.medical.dto.AdminLoginDTO;
import com.medical.dto.AdminPageQueryDTO;
import com.medical.dto.AdminUpdateDTO;
import com.medical.entity.Admin;
import com.medical.options.AdminOptions;
import com.medical.result.PageResult;

import java.util.List;


public interface AdminService {

    Admin login(AdminLoginDTO loginDTO);

    void add(AdminAddDTO adminAddDTO);

    PageResult page(AdminPageQueryDTO adminPageQueryDTO);

    void startOrStop(Integer status, List<Long> ids);

    void update(AdminUpdateDTO adminLoginDTO);

    void password(String password, String newPassword);

    void delete(List<Long> ids);

    List<AdminOptions> options();
}
