package com.medical.controller.user;

import com.medical.common.result.Result;
import com.medical.dto.UserLoginDTO;
import com.medical.vo.UserLoginVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * 用户端用户控制器
 */
@RestController
@RequestMapping("/user/user")
@Tag(name = "用户端用户相关接口")
@Slf4j
public class UserController {

    /**
     * 登录
     *
     * @param userLoginDTO
     * @return
     */
    @PostMapping("/login")
    @Operation(summary = "用户登录", description = "用户端用户登录接口")
    public Result<UserLoginVO> login(@RequestBody UserLoginDTO userLoginDTO) {
        log.info("用户端用户登录：{}", userLoginDTO);

        // TODO: 实现登录逻辑
        UserLoginVO userLoginVO = UserLoginVO.builder()
                .id(1L)
                .username(userLoginDTO.getUsername())
                .realName("测试患者")
                .userType(userLoginDTO.getUserType())
                .token("user-test-token")
                .build();

        return Result.success(userLoginVO);
    }

    /**
     * 退出
     *
     * @return
     */
    @PostMapping("/logout")
    @Operation(summary = "退出登录", description = "用户端退出登录接口")
    public Result<String> logout() {
        return Result.success();
    }

    /**
     * 获取用户信息
     *
     * @return
     */
    @GetMapping("/info")
    @Operation(summary = "获取用户信息", description = "获取当前登录用户信息")
    public Result<UserLoginVO> getUserInfo() {
        // TODO: 实现获取用户信息逻辑
        UserLoginVO userLoginVO = UserLoginVO.builder()
                .id(1L)
                .username("patient001")
                .realName("测试患者")
                .userType(1)
                .build();

        return Result.success(userLoginVO);
    }
}
