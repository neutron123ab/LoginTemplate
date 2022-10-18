# LoginTemplate
# 项目仍在完善中

基于SpringSecurity + JWT的前后端分离登录模板

## 项目介绍

* 前端使用用户名+密码登录，密码会先进行RSA加密，后端再使用密钥对其进行解密得到原密码，保障了前后端密码传递的安全性
* 注册：后端会将密码进行Bcrypt加密后存入数据库
* 后端的登录接口在登录成功后会生成一个`UUID`，并将其作为key，用户信息作为value，存入reids中，同时设置过期时间，这个过期时间就是用户登录凭证失效时间
* 将上一步中的`UUID`作为`payload`生成一个JWT字符串，使用RSA密钥对其进行签名，RSA公钥进行验签
* 用户登录成功后会得到一个JWT字符串，之后用户每一次发请求都会在请求头的`Authorization`中携带这个字符串
* 后端定义一个过滤器，对请求头中的`Authorization`进行验签操作，校验JWT的合法性
* 如果校验通过，就根据payload字段从reids中取出相应的用户信息，并对其进行认证
* 认证通过，用户发出的请求可以完成对后端接口的访问

## 技术栈

### 前端

* 框架：Vue3
* 组件库：Element Plus
* RSA加密工具包：jsencrypt
* 状态管理：vuex
* 路由：VueRouter
* 请求：Axios

### 后端

* 基石框架：SpringBoot
* 安全框架：SpringSecurity
* 数据库：MySQL
* 持久层框架：MyBatis
* 缓存：redis
* 证书生成工具：jdk keytool
