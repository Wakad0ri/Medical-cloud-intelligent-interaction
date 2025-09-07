-- 医云智能互动系统数据库初始化脚本

-- 创建数据库
CREATE DATABASE IF NOT EXISTS medical_cloud DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE medical_cloud;

-- 用户表
CREATE TABLE `user` (
                        `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
                        `username` varchar(32) NOT NULL COMMENT '用户名',
                        `password` varchar(64) NOT NULL COMMENT '密码',
                        `real_name` varchar(32) DEFAULT NULL COMMENT '真实姓名',
                        `phone` varchar(11) DEFAULT NULL COMMENT '手机号',
                        `email` varchar(64) DEFAULT NULL COMMENT '邮箱',
                        `gender` tinyint DEFAULT NULL COMMENT '性别 1-男 2-女',
                        `age` int DEFAULT NULL COMMENT '年龄',
                        `id_card` varchar(18) DEFAULT NULL COMMENT '身份证号',
                        `avatar` varchar(500) DEFAULT NULL COMMENT '头像',
                        `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                        `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                        PRIMARY KEY (`id`),
                        UNIQUE KEY `idx_username` (`username`),
                        KEY `idx_phone` (`phone`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户表';

-- 科室表
CREATE TABLE `department` (
                              `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
                              `name` varchar(64) NOT NULL COMMENT '科室名称',
                              `code` varchar(32) NOT NULL COMMENT '科室编码',
                              `description` text COMMENT '科室描述',
                              `location` varchar(128) DEFAULT NULL COMMENT '科室位置',
                              `phone` varchar(20) DEFAULT NULL COMMENT '科室电话',
                              `director_id` bigint DEFAULT NULL COMMENT '科室主任ID',
                              `sort` int DEFAULT '0' COMMENT '排序',
                              `status` tinyint NOT NULL DEFAULT '1' COMMENT '状态 1-正常 0-停用',
                              `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                              `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                              PRIMARY KEY (`id`),
                              UNIQUE KEY `idx_code` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='科室表';

-- 医生表
CREATE TABLE `doctor` (
                          `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
                          `user_id` bigint NOT NULL COMMENT '用户ID',
                          `doctor_no` varchar(32) NOT NULL COMMENT '医生编号',
                          `department_id` bigint NOT NULL COMMENT '科室ID',
                          `title` varchar(32) DEFAULT NULL COMMENT '职称',
                          `specialty` text COMMENT '专业特长',
                          `introduction` text COMMENT '简介',
                          `registration_fee` decimal(10,2) DEFAULT '0.00' COMMENT '挂号费',
                          `work_years` int DEFAULT '0' COMMENT '工作年限',
                          `education` varchar(32) DEFAULT NULL COMMENT '学历',
                          `graduate_school` varchar(128) DEFAULT NULL COMMENT '毕业院校',
                          `license_number` varchar(64) DEFAULT NULL COMMENT '执业证书号',
                          `status` tinyint NOT NULL DEFAULT '1' COMMENT '状态 1-在职 0-离职',
                          `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                          `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                          PRIMARY KEY (`id`),
                          UNIQUE KEY `idx_doctor_no` (`doctor_no`),
                          KEY `idx_user_id` (`user_id`),
                          KEY `idx_department_id` (`department_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='医生表';

-- 管理员表
create table `admin` (
                         `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
                         `username` varchar(32) NOT NULL COMMENT '用户名',
                         `password` varchar(64) NOT NULL COMMENT '密码',
                         `real_name` varchar(32) DEFAULT NULL COMMENT '真实姓名',
                         `phone` varchar(11) DEFAULT NULL COMMENT '手机号',
                         `status` tinyint NOT NULL DEFAULT '1' COMMENT '状态 1-正常 0-停用',
                         `id_card` varchar(18) DEFAULT NULL COMMENT '身份证号',
                         `email` varchar(64) DEFAULT NULL COMMENT '邮箱',
                         `gender` tinyint DEFAULT NULL COMMENT '性别 1-男 2-女',
                         `age` int DEFAULT NULL COMMENT '年龄',
                         `avatar` varchar(500) DEFAULT NULL COMMENT '头像',
                         `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                         `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                         `create_admin` bigint DEFAULT NULL COMMENT '创建者ID',
                         `update_admin` bigint DEFAULT NULL COMMENT '更新者ID',
                         PRIMARY KEY (`id`),
                         UNIQUE KEY `idx_username` (`username`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='管理员表';



-- 插入初始数据
#
#
#
# INSERT INTO `department` (`name`, `code`, `description`, `location`, `phone`) VALUES
#                                                                                   ('内科', 'NK001', '内科诊疗', '1楼101室', '010-12345678'),
#                                                                                   ('外科', 'WK001', '外科诊疗', '2楼201室', '010-12345679'),
#                                                                                   ('儿科', 'EK001', '儿科诊疗', '3楼301室', '010-12345680');

-- 给admin表插入一条数据
INSERT INTO `admin` (`username`, `password`, `real_name`, `phone`, `status`, `id_card`, `email`, `gender`, `age`, `avatar`, `create_time`, `update_time`, `create_admin`, `update_admin`) VALUES
    ('admin', '123456', '管理员', '13888888888', 1, '420000000000000000', 'admin@example.com', 1, 18, 'https://example.com/avatar.png', '2023-05-01 10:00:00', '2023-05-01 10:00:00', NULL, NULL)

    ('caier', 'e10adc3949ba59abbe56e057f20f883e', '蔡二', '13800138020', 0, '110101199108081234', 'caier@example.com', 0, 24, '', NOW(), NOW(), 1, 1);
