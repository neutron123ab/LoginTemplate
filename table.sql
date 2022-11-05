# 用户表
CREATE TABLE IF NOT EXISTS `user`
(
    `id`                   INT(11) NOT NULL AUTO_INCREMENT COMMENT '用户id',
    `username`             VARCHAR(32)  DEFAULT NULL COMMENT '用户名',
    `password`             VARCHAR(255) DEFAULT NULL COMMENT '加密后的密码',
    `enabled`              TINYINT(1)   DEFAULT NULL COMMENT '账户是否可用',
    `accountNonExpired`    TINYINT(1)   DEFAULT NULL COMMENT '账户是否没有过期',
    `accountNonLocked`     TINYINT(1)   DEFAULT NULL COMMENT '账户是否没有锁定',
    `credentialsNonExpired` TINYINT(1)   DEFAULT NULL COMMENT '凭证是否没有过期',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

# 角色表
CREATE TABLE IF NOT EXISTS `role`
(
    `id`     INT(11) NOT NULL AUTO_INCREMENT COMMENT '角色id',
    `name`   VARCHAR(32) DEFAULT NULL COMMENT '角色英文名',
    `nameZh` VARCHAR(32) DEFAULT NULL COMMENT '角色中文名',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

# 权限表
CREATE TABLE IF NOT EXISTS `permission`
(
    `id`     INT(11) NOT NULL AUTO_INCREMENT COMMENT '权限id',
    `name`   VARCHAR(32) DEFAULT NULL COMMENT '权限英文名',
    `nameZh` VARCHAR(32) DEFAULT NULL COMMENT '权限中文名',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

# 资源表
CREATE TABLE IF NOT EXISTS `resources`
(
    `id`   INT(11) NOT NULL AUTO_INCREMENT COMMENT '权限id',
    `name` VARCHAR(32) DEFAULT NULL COMMENT '权限中文名',
    `url`  varchar(32) DEFAULT NULL COMMENT '接口地址',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

# 用户-角色关联表
CREATE TABLE IF NOT EXISTS `user_role`
(
    `id`      INT(11) NOT NULL AUTO_INCREMENT COMMENT '表id',
    `user_id` INT(11) DEFAULT NULL COMMENT '用户id',
    `role_id` INT(11) DEFAULT NULL COMMENT '角色id',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

# 角色-权限关联表
CREATE TABLE IF NOT EXISTS `role_permission`
(
    `id`            INT(11) NOT NULL AUTO_INCREMENT COMMENT '表id',
    `role_id`       INT(11) DEFAULT NULL COMMENT '角色id',
    `permission_id` INT(11) DEFAULT NULL COMMENT '权限id',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

# 权限-资源关联表
CREATE TABLE IF NOT EXISTS `permission-resources`
(
    `id`            INT(11) NOT NULL AUTO_INCREMENT COMMENT '表id',
    `permission_id` INT(11) DEFAULT NULL COMMENT '权限id',
    `resources_id`  INT(11) DEFAULT NULL COMMENT '资源id',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;
