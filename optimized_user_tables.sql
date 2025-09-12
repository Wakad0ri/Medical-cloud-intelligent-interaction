-- =============================================
-- 优化现有用户表结构（保持三表分离）
-- =============================================

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

-- 2. 优化医生表
DROP TABLE IF EXISTS `doctor`;
CREATE TABLE `doctor` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键',
    `doctor_no` VARCHAR(20) NOT NULL UNIQUE COMMENT '医生工号',
    `username` VARCHAR(32) NOT NULL UNIQUE COMMENT '登录用户名',
    `password` VARCHAR(64) NOT NULL COMMENT '登录密码',
    `real_name` VARCHAR(32) NOT NULL COMMENT '医生姓名',
    `gender` TINYINT COMMENT '性别 1-男 0-女',
    `age` INT COMMENT '年龄',
    `phone` VARCHAR(11) UNIQUE COMMENT '手机号',
    `email` VARCHAR(64) UNIQUE COMMENT '邮箱',
    `id_card` VARCHAR(18) UNIQUE COMMENT '身份证号',
    `avatar` VARCHAR(500) COMMENT '头像',
    
    -- 医生专业信息
    `department_id` BIGINT COMMENT '科室ID',
    `title` VARCHAR(50) COMMENT '职称',
    `specialty` TEXT COMMENT '专业特长',
    `work_years` INT COMMENT '工作年限',
    `introduction` TEXT COMMENT '医生简介',
    `consultation_fee` DECIMAL(10,2) COMMENT '挂号费',
    
    -- 状态信息
    `status` TINYINT NOT NULL DEFAULT 1 COMMENT '账号状态 0-禁用 1-正常 2-工作',
    `work_status` TINYINT DEFAULT 1 COMMENT '工作状态 0-休息 1-正常 2-忙碌',
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
    KEY `idx_department_id` (`department_id`),
    KEY `idx_doctor_no` (`doctor_no`),
    KEY `idx_username` (`username`),
    CONSTRAINT `fk_doctor_department` FOREIGN KEY (`department_id`) REFERENCES `department` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='医生信息表';

-- 3. 优化管理员表
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键',
    `admin_no` VARCHAR(20) NOT NULL UNIQUE COMMENT '管理员编号',
    `username` VARCHAR(32) NOT NULL UNIQUE COMMENT '用户名',
    `password` VARCHAR(64) NOT NULL COMMENT '密码',
    `real_name` VARCHAR(32) NOT NULL COMMENT '真实姓名',
    `phone` VARCHAR(11) UNIQUE COMMENT '手机号',
    `email` VARCHAR(64) UNIQUE COMMENT '邮箱',
    `gender` TINYINT COMMENT '性别 1-男 0-女',
    `age` INT COMMENT '年龄',
    `id_card` VARCHAR(18) UNIQUE COMMENT '身份证号',
    `avatar` VARCHAR(500) COMMENT '头像',
    
    -- 管理员特有字段
    `role_level` TINYINT DEFAULT 1 COMMENT '管理级别 1-普通管理员 2-高级管理员 3-超级管理员',
    `department_id` BIGINT COMMENT '所属科室（可选）',
    
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
    KEY `idx_admin_no` (`admin_no`),
    KEY `idx_department_id` (`department_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='管理员表';

-- 4. 插入默认数据
-- 插入默认管理员
INSERT INTO `admin` (`admin_no`, `username`, `password`, `real_name`, `phone`, `email`, `gender`, `age`, `role_level`) VALUES
('A000001', 'admin', 'e10adc3949ba59abbe56e057f20f883e', '超级管理员', '13888888888', 'admin@medical.com', 1, 30, 3),
('A000002', 'manager', 'e10adc3949ba59abbe56e057f20f883e', '系统管理员', '13800138000', 'manager@medical.com', 1, 28, 2);

-- 5. 数据迁移脚本（如果需要从旧表迁移）
-- 迁移现有admin数据（如果表结构不同）
-- INSERT INTO admin (username, password, real_name, phone, email, gender, age, avatar, create_time, update_time)
-- SELECT username, password, real_name, phone, email, gender, age, avatar, create_time, update_time
-- FROM old_admin_table;

-- 6. 创建视图（方便查询）
-- 医生详细信息视图
CREATE VIEW `v_doctor_detail` AS
SELECT 
    d.id,
    d.doctor_no,
    d.username,
    d.real_name,
    d.gender,
    d.age,
    d.phone,
    d.email,
    d.title,
    d.specialty,
    d.work_years,
    d.status,
    d.work_status,
    dept.dept_name,
    CASE WHEN dept.director_id = d.id THEN 1 ELSE 0 END as is_director
FROM doctor d
LEFT JOIN department dept ON d.department_id = dept.id
WHERE d.is_deleted = 0;

-- 用户统计视图
CREATE VIEW `v_user_stats` AS
SELECT 
    'admin' as user_type,
    COUNT(*) as total_count,
    SUM(CASE WHEN status = 1 THEN 1 ELSE 0 END) as active_count
FROM admin WHERE is_deleted = 0
UNION ALL
SELECT 
    'doctor' as user_type,
    COUNT(*) as total_count,
    SUM(CASE WHEN status = 1 THEN 1 ELSE 0 END) as active_count
FROM doctor WHERE is_deleted = 0
UNION ALL
SELECT 
    'user' as user_type,
    COUNT(*) as total_count,
    SUM(CASE WHEN status = 1 THEN 1 ELSE 0 END) as active_count
FROM user WHERE is_deleted = 0;
