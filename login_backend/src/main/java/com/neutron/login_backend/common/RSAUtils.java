package com.neutron.login_backend.common;

import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import lombok.Getter;

/**
 * RSA工具类
 */
@Getter
public class RSAUtils {

    private final String publicKey;
    private final String privateKey;

    private static RSAUtils rsaUtils;
    private static RSA rsa;

    /**
     * 生成密钥对
     */
    private RSAUtils(){
        rsa = new RSA();
        publicKey = rsa.getPublicKeyBase64();
        privateKey = rsa.getPrivateKeyBase64();
    }

    /**
     * 单例模式获得工具类实例，防止频繁生成密钥对
     * @return 实例
     */
    public static RSAUtils getRsaUtils(){
        if(rsaUtils == null){
            rsaUtils = new RSAUtils();
        }
        return rsaUtils;
    }

    /**
     * 解密
     * @param password 密文
     * @return 明文
     */
    public String decodePassword(String password){

        String publicKey = rsaUtils.getPublicKey();
        String privateKey = rsaUtils.getPrivateKey();
        RSA rsa = new RSA(privateKey, publicKey);
        byte[] decrypt = rsa.decrypt(password, KeyType.PrivateKey);

        return new String(decrypt);
    }

}
