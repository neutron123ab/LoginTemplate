package com.neutron.login_backend.service.impl;

import cn.hutool.crypto.asymmetric.RSA;
import com.neutron.login_backend.common.RSAUtils;
import com.neutron.login_backend.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class LoginServiceImpl implements LoginService {

    @Resource
    private RedisTemplate<String, String> redisTemplate;

    /**
     * 对rsa加密过的密码进行解密
     * @param password 密文
     * @return  明文
     */
    @Override
    public String decodePassword(String password) {
        RSAUtils rsaUtils = RSAUtils.getRsaUtils();
        return rsaUtils.decodePassword(password);
    }

    /**
     * 登录成功操作，返回jwt
     * @param username 用户名
     * @return jwt 登录凭证
     */
    @Override
    public String loginSuccess(String username) {
        String key = UUID.randomUUID().toString();
        //用户信息存入缓存，1小时过期
        redisTemplate.opsForValue().set(key, username, 1, TimeUnit.HOURS);

        return null;
    }
}
