package com.neutron.login_backend.components;

import com.neutron.login_backend.entity.Permission;
import com.neutron.login_backend.entity.Resources;
import com.neutron.login_backend.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Component
public class CustomSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

    @Autowired
    private UserMapper userMapper;
    AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {

        String requestURI = ((FilterInvocation) object).getRequest().getRequestURI();
        System.out.println(((FilterInvocation) object).getRequest().getMethod());
        List<Resources> allResources = userMapper.getAllResources();
        System.out.println("requestURI: "+requestURI);
        for (Resources resource : allResources) {
            if(antPathMatcher.match(resource.getUrl(), requestURI)){
                String[] permissions = resource.getPermissions().stream()
                        .map(Permission::getName).toArray(String[]::new);
                System.out.println("permissions: " + Arrays.toString(permissions));
                return SecurityConfig.createList(permissions);
            }
        }
        return null;
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return FilterInvocation.class.isAssignableFrom(clazz);
    }
}
