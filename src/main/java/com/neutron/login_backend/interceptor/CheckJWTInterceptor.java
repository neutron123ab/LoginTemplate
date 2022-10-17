package com.neutron.login_backend.interceptor;

import com.neutron.login_backend.service.JwtTokenService;
import com.nimbusds.jose.jwk.RSAKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class CheckJWTInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtTokenService jwtTokenService;
    /**
     * JWT验证拦截器
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("12313231231231231");
        String authorization = request.getHeader("Authorization");
        if(authorization == null){
            return false;
        } else{
            RSAKey rsaKey = jwtTokenService.generateRsaKey();
            //验签失败返回false
            //成功返回true
            System.out.println("rsaKeyPreHandler: "+rsaKey);
            return jwtTokenService.verifyToken(authorization, rsaKey) != null;
        }
    }


}
