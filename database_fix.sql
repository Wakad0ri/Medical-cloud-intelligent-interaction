-- 修复双向关联问题的SQL脚本

-- 1. 删除医生表的 is_director 字段（去掉冗余）
ALTER TABLE doctor DROP COLUMN is_director;

-- 2. 确保科室表有 director_id 字段（如果没有的话）
-- ALTER TABLE department ADD COLUMN director_id BIGINT COMMENT '科室主任ID';

-- 3. 添加外键约束（可选，确保数据一致性）
-- ALTER TABLE department ADD CONSTRAINT fk_department_director 
--   FOREIGN KEY (director_id) REFERENCES doctor(id) ON DELETE SET NULL;

-- 4. 清理数据：确保数据一致性
-- 清空所有科室的主任，重新设置
UPDATE department SET director_id = NULL;

-- 如果需要重新设置主任，可以手动执行：
-- UPDATE department SET director_id = 1 WHERE id = 1; -- 示例：设置科室1的主任为医生1

SELECT '数据库结构修复完成！' as message;
