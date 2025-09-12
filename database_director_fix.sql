-- 修复科室主任字段的SQL脚本
-- 将现有的 director_id 为 NULL 的记录更新为 0，表示暂无主任

-- 1. 查看当前 director_id 为 NULL 的科室
SELECT id, dept_name, director_id 
FROM department 
WHERE director_id IS NULL;

-- 2. 将 director_id 为 NULL 的记录更新为 0
UPDATE department 
SET director_id = 0, 
    update_time = NOW()
WHERE director_id IS NULL;

-- 3. 验证修复结果
SELECT id, dept_name, director_id 
FROM department 
WHERE director_id = 0;

-- 4. 查看所有科室的主任情况
SELECT 
    d.id,
    d.dept_name,
    d.director_id,
    CASE 
        WHEN d.director_id = 0 THEN '该科室暂未任命主任'
        WHEN doc.real_name IS NOT NULL THEN doc.real_name
        ELSE '主任信息异常'
    END as director_name
FROM department d
LEFT JOIN doctor doc ON d.director_id = doc.id AND d.director_id != 0
ORDER BY d.id;

SELECT '科室主任字段修复完成！' as message;
