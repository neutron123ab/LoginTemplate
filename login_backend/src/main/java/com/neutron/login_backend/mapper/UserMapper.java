package com.neutron.login_backend.mapper;

import com.neutron.login_backend.entity.Permission;
import com.neutron.login_backend.entity.Resources;
import com.neutron.login_backend.entity.Role;
import com.neutron.login_backend.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserMapper {

    Integer addUser(@Param("username") String username, @Param("password") String password);

    User queryUserByUsername(String username);

    List<Role> getRolesByUserId(@Param("user_id") Integer user_id);

    List<Resources> getAllResources();
}
