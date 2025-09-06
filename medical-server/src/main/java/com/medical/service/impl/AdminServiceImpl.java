package com.medical.service.impl;

import com.medical.dto.AdminAddDTO;
import com.medical.dto.AdminLoginDTO;
import com.medical.entity.Admin;
import com.medical.exception.AdminStatusErrorException;
import com.medical.exception.PasswordErrorException;
import com.medical.exception.UsernameNotFoundException;
import com.medical.mapper.AdminMapper;
import com.medical.service.AdminService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

@Service // SpringIOC注解
@Tag(name = "管理端-管理员服务接口")
@Slf4j
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminMapper adminMapper;

    /**
     * 管理员登录
     * @param loginDTO 登录信息
     * @return Admin
     */
    @Override
    public Admin login(AdminLoginDTO loginDTO) {
        // 1. 获取登录人员的 账号，密码
        String username = loginDTO.getUsername();
        String password = loginDTO.getPassword();

        // 2. 调用mapper层，获取用户信息，处理业务异常
        Admin admin = adminMapper.getAdminByUsername(username);
        if (admin == null) {
            throw new UsernameNotFoundException("用户不存在");
        }
            // 2-1. 密码比对
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        if (!password.equals(admin.getPassword())) {
            throw new PasswordErrorException("密码错误");
        }
            // 2-2. 账号是否被禁用
        if (admin.getStatus() == 0) {
            throw new AdminStatusErrorException("账号被禁用");
        }

        // 3. 返回实体类对象，登录成功
        return admin;
    }

    /**
     * 新增管理员
     * @param adminAddDTO 管理员信息
     */
    @Override
    public void add(AdminAddDTO adminAddDTO) {
        Admin admin = new Admin();
        BeanUtils.copyProperties(adminAddDTO, admin);
        // TODO：密码需要加密，至于更新/创建 时间/人 需要用AOP或者注解实现
        String password = DigestUtils.md5DigestAsHex(adminAddDTO.getPassword().getBytes());
        BeanUtils.copyProperties(adminAddDTO, admin);
        admin.setPassword(password);
        admin.setStatus(1);
        try {
            adminMapper.insert(admin);
        } catch (Exception e) {
            log.error("添加管理员异常！", e);
        }

        log.info("添加管理员：{}", admin);
    }
}
