package com.medical.controller.admin;

import com.medical.dto.AdminAddDTO;
import com.medical.dto.AdminPageQueryDTO;
import com.medical.properties.JwtProperties;
import com.medical.result.PageResult;
import com.medical.utils.JwtUtil;
import com.medical.dto.AdminLoginDTO;
import com.medical.entity.Admin;
import com.medical.result.Result;
import com.medical.service.AdminService;
import com.medical.vo.AdminLoginVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

// @Autowired       是 Spring IoC 的注解
// @RequestMapping  是 Spring MVC 的注解
// @RestController  是 Spring MVC 的注解
// @RequestBody     是 Spring MVC 的注解

/**
 * 管理端-管理员 Controller 类
 */
@RestController
@RequestMapping("/admin")
@Tag(name = "管理端-管理员控制层")
@Slf4j
public class AdminController {

    @Autowired  // SpringIoC 的注解
    private AdminService adminService;

    @Autowired  // SpringIoC 的注解
    private JwtProperties jwtProperties;

    /**
     * 管理员登录
     * @param loginDTO （json
     * @return Result<AdminLoginVO>
     */
    @PostMapping("/login")
    @Operation(summary = "管理员登录")
    public Result<AdminLoginVO> login(@RequestBody AdminLoginDTO loginDTO) {
        log.info("管理员登录：{}", loginDTO);

        // 调用业务层, 获取登录信息
        Admin admin = adminService.login(loginDTO);
        log.info("登录验证成功，用户信息：{}", admin.getUsername());

        // 登录成功，生成JWT令牌
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", admin.getId());    // 根据 管理员ID 作为JWT唯一标识符
        String token = JwtUtil.createJWT(   // 根据配置文件中的参数生成JWT令牌！,前端只有拿到这个令牌，才能访问有权限的接口！
                jwtProperties.getAdminSecretKey(),
                jwtProperties.getAdminTtl(),
                claims);
        log.info("JWT令牌生成成功");

        AdminLoginVO loginVO = AdminLoginVO.builder()
                .id(admin.getId())
                .username(admin.getUsername())
                .name(admin.getUsername())
                .token(token)
                .build();

        return Result.success(loginVO);
    }

    /**
     * 管理员登出
     * @return Result<String>
     */
    @PostMapping("/logout")
    @Operation(summary = "管路员登出")
    public Result<String> logout(){
        log.info("管理员退出登录");
        return Result.success();
    }


    @PostMapping("/add")
    @Operation(summary = "添加管理员")
    public Result<String> add(@RequestBody AdminAddDTO adminAddDTO){
        log.info("添加管理员：{}", adminAddDTO);
        adminService.add(adminAddDTO);
        return Result.success();
    }

    @GetMapping("/page")
    @Operation(summary = "分页查询管理员")
    public Result<PageResult> page(AdminPageQueryDTO adminPageQueryDTO){
        log.info("分页查询管理员：{}", adminPageQueryDTO);
        PageResult pageResult = adminService.page(adminPageQueryDTO);
        return Result.success(pageResult);
    }

    @PutMapping("/status/{status}")
    @Operation(summary = "启用禁用管理员")
    public Result<String> startOrStop(@PathVariable("status") Integer status, @RequestParam("id") Long id){
        log.info("启用禁用管理员：{}", id);
        adminService.startOrStop(status, id);
        return Result.success();
    }

    @PutMapping("/update")
    @Operation(summary = "修改管理员信息")
    public Result<String> update(@RequestBody AdminAddDTO adminAddDTO){
        log.info("修改管理员信息：{}", adminAddDTO);
        adminService.update(adminAddDTO);
        return Result.success();
    }

    @PutMapping("/password")
    @Operation(summary = "修改管理员密码")
    public Result<String> updatePassword(@RequestParam("password") String password, @RequestParam("newPassword") String newPassword){
        log.info("修改当前管理员密码");
        adminService.password(password, newPassword);
        return Result.success();
    }





}
