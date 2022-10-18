package com.neutron.login_backend.controller;

import com.neutron.login_backend.common.Result;
import com.neutron.login_backend.entity.User;
import com.neutron.login_backend.service.JwtTokenService;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.jwk.RSAKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;

@RestController
@RequestMapping("/out")
public class LogoutController {

    @Resource
    private RedisTemplate<String, User> redisTemplate;

    @Autowired
    private JwtTokenService jwtTokenService;

    @PostMapping
    public Result<String> logout(HttpServletRequest request) throws ParseException, JOSEException {

        RSAKey rsaKey = jwtTokenService.generateRsaKey();
        String authorization = request.getHeader("Authorization");
        String payload = jwtTokenService.verifyToken(authorization, rsaKey);

        redisTemplate.delete(payload);
        SecurityContextHolder.clearContext();

        return Result.success("用户登出成功");
    }

}
