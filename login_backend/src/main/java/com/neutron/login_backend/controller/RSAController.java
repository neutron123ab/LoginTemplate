package com.neutron.login_backend.controller;

import com.neutron.login_backend.common.RSAUtils;
import com.neutron.login_backend.common.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RSAController {

    /**
     * 获取公钥
     * @return 公钥
     */
    @GetMapping("/getPublicKey")
    public Result<String> getPublicKey(){
        RSAUtils rsaUtils = RSAUtils.getRsaUtils();
        return Result.success(rsaUtils.getPublicKey());
    }

}
