package com.neutron.login_backend.service;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.jwk.RSAKey;

import java.text.ParseException;

public interface JwtTokenService {

    RSAKey generateRsaKey();

    String generateTokenByRSA(String payloadStr, RSAKey rsaKey) throws JOSEException;

    String verifyToken(String token, RSAKey rsaKey) throws ParseException, JOSEException;

}
