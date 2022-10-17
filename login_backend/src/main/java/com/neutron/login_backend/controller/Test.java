package com.neutron.login_backend.controller;

import com.neutron.login_backend.service.JwtTokenService;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.jwk.RSAKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@RestController
public class Test {

    @Autowired
    private JwtTokenService jwtTokenService;

    @GetMapping("/jwt")
    public String test() throws JOSEException, ParseException {
        RSAKey rsaKey = jwtTokenService.generateRsaKey();
        String token = jwtTokenService.generateTokenByRSA("123", rsaKey);
        String s = jwtTokenService.verifyToken(token, rsaKey);
        System.out.println(rsaKey);
        System.out.println(s + "------");
        return s;
    }

}
