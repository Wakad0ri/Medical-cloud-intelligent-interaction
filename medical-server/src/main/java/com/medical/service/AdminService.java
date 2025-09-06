package com.medical.service;

import com.medical.dto.AdminAddDTO;
import com.medical.dto.AdminLoginDTO;
import com.medical.entity.Admin;


public interface AdminService {

    Admin login(AdminLoginDTO loginDTO);

    void add(AdminAddDTO adminAddDTO);
}
