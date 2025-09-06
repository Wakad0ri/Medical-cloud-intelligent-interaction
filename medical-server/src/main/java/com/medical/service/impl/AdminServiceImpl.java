package com.medical.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.medical.dto.AdminAddDTO;
import com.medical.dto.AdminLoginDTO;
import com.medical.dto.AdminPageQueryDTO;
import com.medical.entity.Admin;
import com.medical.exception.AdminStatusErrorException;
import com.medical.exception.PasswordErrorException;
import com.medical.exception.UsernameNotFoundException;
import com.medical.mapper.AdminMapper;
import com.medical.result.PageResult;
import com.medical.service.AdminService;
import com.medical.vo.AdminPageQueryVO;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

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

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        List<AdminPageQueryVO> list = page.getResult().stream().map(admin -> {
            AdminPageQueryVO adminPageQueryVO = new AdminPageQueryVO();
            BeanUtils.copyProperties(admin, adminPageQueryVO);

            // 手动处理时间格式转换
            if (admin.getUpdateTime() != null) {
                adminPageQueryVO.setUpdateTime(admin.getUpdateTime().format(formatter));
            } else if (admin.getCreateTime() != null) {
                // 如果updateTime为空，使用createTime
                adminPageQueryVO.setUpdateTime(admin.getCreateTime().format(formatter));
            }

            return adminPageQueryVO;
        }).toList();
        return new PageResult(page.getTotal(), list);

    }

}
