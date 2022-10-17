package com.neutron.login_backend.filter;

import com.neutron.login_backend.entity.User;
import com.neutron.login_backend.service.JwtTokenService;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.jwk.RSAKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.util.Objects;

@Component
public class LoginFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenService jwtTokenService;

    @Resource
    private RedisTemplate<String, User> redisTemplate;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String authorization = request.getHeader("Authorization");
        System.out.println(authorization);

        if(request.getRequestURI().equals("/login")){
            filterChain.doFilter(request, response);
        } else if(authorization == null){
            throw new RuntimeException("用户未登录");
        } else{
            RSAKey rsaKey = jwtTokenService.generateRsaKey();
            //验签失败返回false
            //成功返回true
            String payload = "";
            try {
                payload = jwtTokenService.verifyToken(authorization, rsaKey);
            } catch (ParseException | JOSEException e) {
                e.printStackTrace();
            }

            if(payload.equals("")){
                throw new RuntimeException("用户未登录");
            } else {
                //已登录，获取用户信息，进行授权
                User userInfo = redisTemplate.opsForValue().get(payload);
                if(userInfo == null){
                    //用户凭证过期
                } else {
                    System.out.println("SecurityContext1: "+SecurityContextHolder.getContext());
                    UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userInfo, null, null);

                    SecurityContextHolder.getContext().setAuthentication(token);
                    System.out.println("SecurityContext2: "+SecurityContextHolder.getContext());
                    filterChain.doFilter(request, response);
                }


                //System.out.println("payload: "+ payload);

            }

        }
    }
}
