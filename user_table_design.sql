-- =============================================
-- 医疗云智能交互系统 - 用户表设计方案
-- =============================================

-- 1. 统一用户表（核心用户信息）
CREATE TABLE `sys_user` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '用户ID',
    `user_no` VARCHAR(32) NOT NULL UNIQUE COMMENT '用户编号（系统生成）',
    `username` VARCHAR(32) NOT NULL UNIQUE COMMENT '登录用户名',
    `password` VARCHAR(64) NOT NULL COMMENT '登录密码',
    `real_name` VARCHAR(32) NOT NULL COMMENT '真实姓名',
    `phone` VARCHAR(11) UNIQUE COMMENT '手机号',
    `email` VARCHAR(64) UNIQUE COMMENT '邮箱',
    `id_card` VARCHAR(18) UNIQUE COMMENT '身份证号',
    
    -- 基本信息
    `gender` TINYINT COMMENT '性别 1-男 0-女',
    `age` INT COMMENT '年龄',
    `avatar` VARCHAR(500) COMMENT '头像URL',
    `birthday` DATE COMMENT '出生日期',
    
    -- 账户状态
    `status` TINYINT NOT NULL DEFAULT 1 COMMENT '账户状态 0-禁用 1-正常 2-锁定',
    `is_deleted` TINYINT NOT NULL DEFAULT 0 COMMENT '是否删除 0-否 1-是',
    
    -- 登录信息
    `last_login_time` DATETIME COMMENT '最后登录时间',
    `last_login_ip` VARCHAR(50) COMMENT '最后登录IP',
    `login_count` INT DEFAULT 0 COMMENT '登录次数',
    
    -- 系统字段
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `create_by` BIGINT COMMENT '创建人',
    `update_by` BIGINT COMMENT '更新人',
    
    PRIMARY KEY (`id`),
    KEY `idx_username` (`username`),
    KEY `idx_phone` (`phone`),
    KEY `idx_email` (`email`),
    KEY `idx_user_no` (`user_no`),
    KEY `idx_status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统用户表';

-- 2. 角色表
CREATE TABLE `sys_role` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '角色ID',
    `role_code` VARCHAR(32) NOT NULL UNIQUE COMMENT '角色编码',
    `role_name` VARCHAR(50) NOT NULL COMMENT '角色名称',
    `description` VARCHAR(200) COMMENT '角色描述',
    `status` TINYINT NOT NULL DEFAULT 1 COMMENT '状态 0-禁用 1-正常',
    `sort_order` INT DEFAULT 0 COMMENT '排序',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    KEY `idx_role_code` (`role_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统角色表';

-- 3. 用户角色关联表
CREATE TABLE `sys_user_role` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键',
    `user_id` BIGINT NOT NULL COMMENT '用户ID',
    `role_id` BIGINT NOT NULL COMMENT '角色ID',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_user_role` (`user_id`, `role_id`),
    KEY `idx_user_id` (`user_id`),
    KEY `idx_role_id` (`role_id`),
    CONSTRAINT `fk_user_role_user` FOREIGN KEY (`user_id`) REFERENCES `sys_user` (`id`) ON DELETE CASCADE,
    CONSTRAINT `fk_user_role_role` FOREIGN KEY (`role_id`) REFERENCES `sys_role` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户角色关联表';

-- 4. 医生扩展信息表
CREATE TABLE `doctor_profile` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键',
    `user_id` BIGINT NOT NULL UNIQUE COMMENT '用户ID',
    `doctor_no` VARCHAR(20) NOT NULL UNIQUE COMMENT '医生工号',
    `department_id` BIGINT COMMENT '科室ID',
    `title` VARCHAR(50) COMMENT '职称',
    `specialty` TEXT COMMENT '专业特长',
    `work_years` INT COMMENT '工作年限',
    `introduction` TEXT COMMENT '医生简介',
    `consultation_fee` DECIMAL(10,2) COMMENT '挂号费',
    `work_status` TINYINT DEFAULT 1 COMMENT '工作状态 0-休息 1-正常 2-忙碌',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    KEY `idx_user_id` (`user_id`),
    KEY `idx_doctor_no` (`doctor_no`),
    KEY `idx_department_id` (`department_id`),
    CONSTRAINT `fk_doctor_profile_user` FOREIGN KEY (`user_id`) REFERENCES `sys_user` (`id`) ON DELETE CASCADE,
    CONSTRAINT `fk_doctor_profile_dept` FOREIGN KEY (`department_id`) REFERENCES `department` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='医生扩展信息表';

-- 5. 患者扩展信息表
CREATE TABLE `patient_profile` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键',
    `user_id` BIGINT NOT NULL UNIQUE COMMENT '用户ID',
    `patient_no` VARCHAR(20) NOT NULL UNIQUE COMMENT '患者编号',
    `emergency_contact` VARCHAR(32) COMMENT '紧急联系人',
    `emergency_phone` VARCHAR(11) COMMENT '紧急联系电话',
    `blood_type` VARCHAR(10) COMMENT '血型',
    `allergy_history` TEXT COMMENT '过敏史',
    `medical_history` TEXT COMMENT '病史',
    `family_history` TEXT COMMENT '家族病史',
    `address` VARCHAR(200) COMMENT '详细地址',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    KEY `idx_user_id` (`user_id`),
    KEY `idx_patient_no` (`patient_no`),
    CONSTRAINT `fk_patient_profile_user` FOREIGN KEY (`user_id`) REFERENCES `sys_user` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='患者扩展信息表';

-- 6. 初始化角色数据
INSERT INTO `sys_role` (`role_code`, `role_name`, `description`, `sort_order`) VALUES
('SUPER_ADMIN', '超级管理员', '系统超级管理员，拥有所有权限', 1),
('ADMIN', '系统管理员', '系统管理员，负责日常管理工作', 2),
('DOCTOR', '医生', '医生角色，负责诊疗工作', 3),
('NURSE', '护士', '护士角色，负责护理工作', 4),
('PATIENT', '患者', '患者角色，使用医疗服务', 5);

-- 7. 创建默认超级管理员
INSERT INTO `sys_user` (`user_no`, `username`, `password`, `real_name`, `phone`, `email`, `gender`, `age`) VALUES
('U000001', 'admin', 'e10adc3949ba59abbe56e057f20f883e', '超级管理员', '13888888888', 'admin@medical.com', 1, 30);

-- 关联超级管理员角色
INSERT INTO `sys_user_role` (`user_id`, `role_id`) VALUES
(1, 1);
