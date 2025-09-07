package com.medical.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.medical.context.BaseContext;
import com.medical.dto.AdminAddDTO;
import com.medical.dto.AdminLoginDTO;
import com.medical.dto.AdminPageQueryDTO;
import com.medical.dto.AdminUpdateDTO;
import com.medical.entity.Admin;
import com.medical.exception.AdminStatusErrorException;
import com.medical.exception.PasswordErrorException;
import com.medical.exception.UsernameNotFoundException;
import com.medical.mapper.AdminMapper;
import com.medical.result.PageResult;
import com.medical.service.AdminService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.util.List;
import java.util.Objects;

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
        // 密码需要加密，至于更新/创建 时间/人 需要用AOP或者注解实现
        String password = DigestUtils.md5DigestAsHex(adminAddDTO.getPassword().getBytes());
        BeanUtils.copyProperties(adminAddDTO, admin);
        admin.setPassword(password);
        admin.setStatus(1);

        adminMapper.insert(admin);
    }

    /**
     * 分页查询管理员
     * @param adminPageQueryDTO 分页查询参数
     * @return PageResult
     */
    @Override
    public PageResult page(AdminPageQueryDTO adminPageQueryDTO) {
        PageHelper.startPage(adminPageQueryDTO.getPage(), adminPageQueryDTO.getPageSize());
        Page<Admin> page = adminMapper.page(adminPageQueryDTO);

        // 通过WebMvcConfiguration处理时间格式处理

        return new PageResult(page.getTotal(), page.getResult());
    }

    /**
     * 启用禁用管理员
     * @param status 状态
     * @param id 管理员ID
     */
    @Override
    public void startOrStop(Integer status, Long id) {
        Long nowId = BaseContext.getCurrentId();
        if (Objects.equals(id, nowId)){
            throw new AdminStatusErrorException("不能禁用自己");
        }
        Admin admin = Admin.builder()
                .id(id)
                .status(status)
                .build();
        adminMapper.update(admin);
    }

    /**
     * 修改管理员信息
     * @param adminUpdateDTO 管理员信息
     */
    @Override
    public void update(AdminUpdateDTO adminUpdateDTO) {
        Admin adminDB = adminMapper.getAdminByUsername(adminUpdateDTO.getUsername());
        if(adminDB.getStatus() == 1){
            throw new AdminStatusErrorException("只有在禁用的情况下才能修改！");
        }
        Admin admin = new Admin();
        BeanUtils.copyProperties(adminUpdateDTO, admin);
        adminMapper.update(admin);
    }

    /**
     * 修改管理员密码
     * @param password 旧密码
     * @param newPassword 新密码
     */
    @Override
    public void password(String password, String newPassword){
        Long id = BaseContext.getCurrentId();
        Admin admin = adminMapper.getById(id);
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        if(!password.equals(admin.getPassword())){
            throw new PasswordErrorException("旧密码错误！");
        }
        newPassword = DigestUtils.md5DigestAsHex(newPassword.getBytes());
        admin.setPassword(newPassword);
        adminMapper.update(admin);
    }

    /**
     * 删除管理员
     * @param ids List<Long>
     */
    @Override
    @Transactional
    public void delete(List<Long> ids){
        for (Long id : ids){
            Admin admin = adminMapper.getById(id);
            if(admin.getStatus() == 1){
                throw new AdminStatusErrorException("只有禁用的账号才能删除！");
            }
        }
        adminMapper.deleteBatch(ids);
    }
}
