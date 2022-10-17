package com.neutron.login_backend.service.impl;

import com.neutron.login_backend.entity.User;
import com.neutron.login_backend.mapper.UserMapper;
import com.neutron.login_backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userMapper.queryUserByUsername(username);
        if(user == null){
            throw new UsernameNotFoundException("用户不存在");
        }
        user.setRoles(userMapper.getRolesByUserId(user.getId()));

        return user;
    }
}
