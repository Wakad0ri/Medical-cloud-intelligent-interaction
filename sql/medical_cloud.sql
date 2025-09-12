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
                              `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键',
                              `dept_code` VARCHAR(20) NOT NULL UNIQUE COMMENT '科室编码',
                              `dept_name` VARCHAR(50) NOT NULL COMMENT '科室名称',
                              `dept_type` TINYINT NOT NULL DEFAULT '1' COMMENT '科室类型 1-临床科室 2-医技科室 3-行政科室',
                              `parent_id` BIGINT DEFAULT NULL COMMENT '上级科室ID（支持科室层级）',
                              `level` TINYINT NOT NULL DEFAULT '1' COMMENT '科室层级 1-一级科室 2-二级科室',

    -- 科室详细信息
                              `description` TEXT DEFAULT NULL COMMENT '科室描述',
                              `location` VARCHAR(100) DEFAULT NULL COMMENT '科室位置',
                              `phone` VARCHAR(20) DEFAULT NULL COMMENT '科室电话',
                              `director_id` BIGINT DEFAULT NULL COMMENT '科室主任ID（关联doctor表）',

    -- 业务信息
                              `bed_count` INT DEFAULT NULL COMMENT '床位数量',
                              `consultation_fee` DECIMAL(10,2) DEFAULT NULL COMMENT '挂号费用',
                              `sort_order` INT DEFAULT '0' COMMENT '排序字段',

    -- 状态信息
                              `status` TINYINT NOT NULL DEFAULT '1' COMMENT '状态 1-正常 0-停用',
                              `is_outpatient` TINYINT DEFAULT '1' COMMENT '是否支持门诊 1-是 0-否',
                              `is_inpatient` TINYINT DEFAULT '1' COMMENT '是否支持住院 1-是 0-否',
                              `is_emergency` TINYINT DEFAULT '0' COMMENT '是否急诊科室 1-是 0-否',

    -- 系统字段
                              `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                              `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                              `create_admin` BIGINT DEFAULT NULL COMMENT '创建人',
                              `update_admin` BIGINT DEFAULT NULL COMMENT '更新人',

                              PRIMARY KEY (`id`),
                              UNIQUE KEY `uk_dept_code` (`dept_code`),
                              KEY `idx_parent_id` (`parent_id`),
                              KEY `idx_director_id` (`director_id`),
                              KEY `idx_dept_type` (`dept_type`),
                              KEY `idx_status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='科室信息表';

-- 医生表
CREATE TABLE `doctor` (
                          `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键',
                          `doctor_no` VARCHAR(20) NOT NULL UNIQUE COMMENT '医生工号',
                          `username` VARCHAR(32) NOT NULL UNIQUE COMMENT '登录用户名',
                          `password` VARCHAR(64) NOT NULL COMMENT '登录密码',
                          `real_name` VARCHAR(32) NOT NULL COMMENT '医生姓名',
                          `gender` TINYINT DEFAULT NULL COMMENT '性别 1-男 0-女',
                          `age` INT DEFAULT NULL COMMENT '年龄',
                          `phone` VARCHAR(11) DEFAULT NULL COMMENT '手机号',
                          `email` VARCHAR(64) DEFAULT NULL COMMENT '邮箱',
                          `id_card` VARCHAR(18) DEFAULT NULL COMMENT '身份证号',

    -- 职业相关信息
                          `department_id` BIGINT NOT NULL COMMENT '科室ID',
                          `title` VARCHAR(32) DEFAULT NULL COMMENT '职称',
                          `specialty` TEXT DEFAULT NULL COMMENT '专业特长',
                          `work_years` INT DEFAULT NULL COMMENT '从业年限',
                          `introduction` TEXT DEFAULT NULL COMMENT '医生简介',

    -- 状态信息
                          `status` TINYINT NOT NULL COMMENT '账号状态 2-工作 1-启用 0-禁用',
                          `is_director` TINYINT NOT NULL COMMENT '是否为部门主任 1-是 0-不是'
    -- 系统字段
                          `avatar` VARCHAR(500) DEFAULT NULL COMMENT '头像',
                          `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                          `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                          `create_admin` BIGINT DEFAULT NULL COMMENT '创建人',
                          `update_admin` BIGINT DEFAULT NULL COMMENT '更新人',

                          PRIMARY KEY (`id`),
                          KEY `idx_department_id` (`department_id`),
                          KEY `idx_doctor_no` (`doctor_no`),
                          KEY `idx_username` (`username`),
                          CONSTRAINT `fk_doctor_department` FOREIGN KEY (`department_id`) REFERENCES `department` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='医生信息表';

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

-- 1. 优化用户表（患者）
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
                        `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键',
                        `user_no` VARCHAR(20) NOT NULL UNIQUE COMMENT '用户编号',
                        `username` VARCHAR(32) NOT NULL UNIQUE COMMENT '用户名',
                        `password` VARCHAR(64) NOT NULL COMMENT '密码',
                        `real_name` VARCHAR(32) NOT NULL COMMENT '真实姓名',
                        `phone` VARCHAR(11) UNIQUE COMMENT '手机号',
                        `email` VARCHAR(64) UNIQUE COMMENT '邮箱',
                        `gender` TINYINT COMMENT '性别 1-男 0-女',
                        `age` INT COMMENT '年龄',
                        `id_card` VARCHAR(18) UNIQUE COMMENT '身份证号',
                        `avatar` VARCHAR(500) COMMENT '头像',
                        `birthday` DATE COMMENT '出生日期',

    -- 患者特有字段
                        `emergency_contact` VARCHAR(32) COMMENT '紧急联系人',
                        `emergency_phone` VARCHAR(11) COMMENT '紧急联系电话',
                        `blood_type` VARCHAR(10) COMMENT '血型',
                        `allergy_history` TEXT COMMENT '过敏史',
                        `address` VARCHAR(200) COMMENT '地址',

    -- 状态字段
                        `status` TINYINT NOT NULL DEFAULT 1 COMMENT '状态 0-禁用 1-正常',
                        `is_deleted` TINYINT NOT NULL DEFAULT 0 COMMENT '是否删除 0-否 1-是',

    -- 登录信息
                        `last_login_time` DATETIME COMMENT '最后登录时间',
                        `last_login_ip` VARCHAR(50) COMMENT '最后登录IP',

    -- 系统字段
                        `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                        `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                        `create_by` BIGINT COMMENT '创建人',
                        `update_by` BIGINT COMMENT '更新人',

                        PRIMARY KEY (`id`),
                        KEY `idx_username` (`username`),
                        KEY `idx_phone` (`phone`),
                        KEY `idx_user_no` (`user_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表（患者）';

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
                                                                                                                                                                                              ('admin', '123456', '管理员', '13888888888', 1, '420000000000000000', 'admin@example.com', 1, 18, 'https://example.com/avatar.png', '2023-05-01 10:00:00', '2023-05-01 10:00:00', NULL, NULL),

                                                                                                                                                                                              ('caier', 'e10adc3949ba59abbe56e057f20f883e', '蔡二', '13800138020', 0, '110101199108081234', 'caier@example.com', 0, 24, '', NOW(), NOW(), 1, 1);

