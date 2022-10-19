package com.neutron.login_backend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Permission implements Serializable {

    private Integer id;
    private String name;    //权限名
    private String nameZh;  //权限中文名

}
