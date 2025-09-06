package com.medical.service;

import com.medical.dto.AdminAddDTO;
import com.medical.dto.AdminLoginDTO;
import com.medical.dto.AdminPageQueryDTO;
import com.medical.entity.Admin;
import com.medical.result.PageResult;


public interface AdminService {

    Admin login(AdminLoginDTO loginDTO);

    void add(AdminAddDTO adminAddDTO);

    PageResult page(AdminPageQueryDTO adminPageQueryDTO);
}
