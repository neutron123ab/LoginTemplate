package com.neutron.login_backend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Resources implements Serializable {

    private Integer id;
    private String name;//权限名
    private String url; //接口地址
    private List<Permission> permissions;   //访问受保护对象所需要的权限

}
