package com.neutron.login_backend.service.impl;

import com.neutron.login_backend.service.JwtTokenService;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jose.crypto.RSASSAVerifier;
import com.nimbusds.jose.jwk.RSAKey;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.rsa.crypto.KeyStoreKeyFactory;
import org.springframework.stereotype.Service;

import java.security.KeyPair;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.text.ParseException;

@Service
public class JwtTokenServiceImpl implements JwtTokenService {

    /**
     * 获取密钥对
     * @return RSAKey
     */
    @Override
    public RSAKey generateRsaKey() {
        KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(new ClassPathResource("jwt.jks"), "123456".toCharArray());
        KeyPair keyPair = keyStoreKeyFactory.getKeyPair("jwt", "123456".toCharArray());
        //RSA公钥
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        //RSA私钥
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        return new RSAKey.Builder(publicKey).privateKey(privateKey).build();
    }

    /**
     * 生成JWT字符串
     * @param payloadStr 作为payload的信息
     * @param rsaKey 密钥对
     * @return jwt字符串
     */
    @Override
    public String generateTokenByRSA(String payloadStr, RSAKey rsaKey) throws JOSEException {
        //JWS头
        JWSHeader jwsHeader = new JWSHeader.Builder(JWSAlgorithm.RS256)
                .type(JOSEObjectType.JWT)
                .build();

        //荷载
        Payload payload = new Payload(payloadStr);
        //签名
        JWSObject jwsObject = new JWSObject(jwsHeader, payload);
        //生成签名器
        RSASSASigner rsassaSigner = new RSASSASigner(rsaKey);
        jwsObject.sign(rsassaSigner);

        return jwsObject.serialize();
    }

    /**
     * 验签
     * @param token jwt字符串
     * @param rsaKey rsaKey
     * @return  荷载信息
     * @throws ParseException
     * @throws JOSEException
     */
    @Override
    public String verifyToken(String token, RSAKey rsaKey) throws ParseException, JOSEException {
        //由jwt字符串生成jwsObject对象
        JWSObject jwsObject = JWSObject.parse(token);
        RSAKey publicKey = rsaKey.toPublicJWK();
        RSASSAVerifier verifier = new RSASSAVerifier(publicKey);
        if(!jwsObject.verify(verifier)){
            return null;    //验证失败则返回空
        }

        return jwsObject.getPayload().toString();
    }
}
