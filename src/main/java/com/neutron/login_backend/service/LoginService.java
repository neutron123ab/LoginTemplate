package com.neutron.login_backend.service;

public interface LoginService {

    String decodePassword(String password);

    String loginSuccess(String username);

}
