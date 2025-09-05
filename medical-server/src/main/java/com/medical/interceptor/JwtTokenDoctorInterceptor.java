package com.medical.interceptor;

import com.medical.context.BaseContext;
import com.medical.properties.JwtProperties;
import com.medical.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;

/**
 * 医生JWT拦截器
 */
@Component
@Slf4j
public class JwtTokenDoctorInterceptor {

    @Autowired
    private JwtProperties jwtProperties;

    /**
     * 拦截器方法
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param handler Object（实际为HandlerMethod）
     * @return boolean
     */
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod)){
            return true;
        }
        String token = request.getHeader(jwtProperties.getDoctorTokenName());
        try {
            log.info("jwt校验:{}", token);
            Claims claims = JwtUtil.parseJWT(jwtProperties.getDoctorSecretKey(), token);
            Long doctorId = Long.valueOf(claims.get("doctorId").toString());
            log.info("当前用户id：{}", doctorId);
            BaseContext.setCurrentId(doctorId);
            return true;
        } catch (Exception ex) {
            response.setStatus(401);
            return false;
        }
    }
}
