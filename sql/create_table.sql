# 数据库初始化

-- 创建库
create database if not exists `pig-nest`;

-- 切换库
use `pig-nest`;

-- 用户表
create table if not exists user
(
    id            bigint auto_increment comment '用户编号'
        primary key,
    user_role     int      default 0                 not null comment '用户角色（0->用户,1->管理员）',
    user_name     varchar(256)                       null comment '用户名',
    user_account  varchar(256)                       not null comment '用户账号',
    avatar_url    varchar(1024)                      null comment '用户头像地址',
    gender        tinyint                            null comment '性别（0->女,1->男）',
    user_password varchar(512)                       not null comment '用户密码',
    phone         varchar(128)                       null comment '电话',
    email         varchar(256)                       null comment '邮箱',
    user_status   int      default 0                 null comment '用户状态（0->正常）',
    create_time   datetime default CURRENT_TIMESTAMP null comment '创建时间',
    update_time   datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '更新时间',
    is_delete     tinyint  default 0                 null comment '是否删除（0->正常，1->删除）',
    access_key    varchar(512)                       null comment 'accessKey',
    secret_key    varchar(512)                       null comment 'secretKey'
)
    comment '用户表' collate = utf8mb4_unicode_ci;

create table encouragement
(
    id          bigint auto_increment comment 'id'
        primary key,
    value        varchar(1024)                       not null comment '内容',
    is_delete   int      default 0                 not null comment '是否删除（0->正常，1->删除）',
    create_id   bigint                             not null comment '创建者ID',
    create_time datetime default CURRENT_TIMESTAMP null comment '创建时间',
    update_time datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '更新时间'
)
    comment '心灵鸡汤';
