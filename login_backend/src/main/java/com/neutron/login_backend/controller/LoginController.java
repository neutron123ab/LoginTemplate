package com.neutron.login_backend.controller;

import com.neutron.login_backend.common.Result;
import com.neutron.login_backend.entity.User;
import com.neutron.login_backend.mapper.UserMapper;
import com.neutron.login_backend.service.JwtTokenService;
import com.neutron.login_backend.service.LoginService;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.jwk.RSAKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@RestController
public class LoginController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private LoginService loginService;

    @Autowired
    private JwtTokenService jwtTokenService;

    @Resource
    private RedisTemplate<String, User> redisTemplate;

    @GetMapping("/test")
    public Result<String> test(){
        System.out.println(SecurityContextHolder.getContext());
        return Result.success("login success");
    }

    @PostMapping("/login")
    public Result<String> login(@RequestBody User user) throws JOSEException {

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
        Authentication authenticate = authenticationManager.authenticate(token);
        if(Objects.isNull(authenticate)){
            throw new RuntimeException("用户名或密码错误");
        }

        System.out.println("登录成功：" + SecurityContextHolder.getContext());

        //登录成功，返回JWT字符串
        RSAKey rsaKey = jwtTokenService.generateRsaKey();
        System.out.println("rsaKeyLogin: "+rsaKey);
        String key = UUID.randomUUID().toString();
        //将用户信息存入redis中
        User userInfo = (User) authenticate.getPrincipal();
        redisTemplate.opsForValue().set(key, userInfo, 3, TimeUnit.HOURS);

        return Result.success(jwtTokenService.generateTokenByRSA(key, rsaKey));
    }

    /**
     * 注册账号
     * @param user 请求体（用户名，密码）
     * @return  状态码
     */
    @PostMapping("/signUp")
    public Result<String> signUp(@RequestBody User user){
        //rsa解密
        String rawPassword = loginService.decodePassword(user.getPassword());
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        //bcrypt加密
        String password = encoder.encode(rawPassword);
        if(userMapper.addUser(user.getUsername(), password) > 0){
            return Result.success("200");
        }
        return Result.error("400");
    }



}
