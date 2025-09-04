package com.medical.controller.admin;

import com.medical.result.Result;
import com.medical.dto.UserLoginDTO;
import com.medical.vo.UserLoginVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * 管理端用户控制器
 */
@RestController
@RequestMapping("/admin/user")
@Tag(name = "管理端用户相关接口")
@Slf4j
public class UserController {

    /**
     * 登录
     *
     * @param userLoginDTO
     * @return
     */
    @PostMapping("/login")
    @Operation(summary = "用户登录")
    public Result<UserLoginVO> login(@RequestBody UserLoginDTO userLoginDTO) {
        log.info("用户登录：{}", userLoginDTO);

        // TODO: 实现登录逻辑
        UserLoginVO userLoginVO = UserLoginVO.builder()
                .id(1L)
                .username(userLoginDTO.getUsername())
                .realName("测试用户")
                .userType(userLoginDTO.getUserType())
                .token("test-token")
                .build();

        return Result.success(userLoginVO);
    }

    /**
     * 退出
     *
     * @return
     */
    @PostMapping("/logout")
    @Operation(summary = "退出登录")
    public Result<String> logout() {
        return Result.success();
    }
}
