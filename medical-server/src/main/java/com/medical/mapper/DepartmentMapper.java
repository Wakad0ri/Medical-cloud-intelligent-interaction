package com.medical.mapper;

import com.github.pagehelper.Page;
import com.medical.annotation.AutoFill;
import com.medical.dto.DepartmentPageQueryDTO;
import com.medical.entity.Department;
import com.medical.options.DepartmentOptions;
import com.medical.vo.DepartmentPageQueryVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface DepartmentMapper {

    /**
     * 部门分页查询
     * @param departmentPageQueryDTO DepartmentPageQueryDTO
     * @return Page<DepartmentPageQueryVO>
     */
    Page<DepartmentPageQueryVO> page(DepartmentPageQueryDTO departmentPageQueryDTO);



    /**
     * 部门下拉选项
     * @return List<DepartmentOptions>
     */
    @Select("select id, dept_name from department where status = 1 order by dept_name")
    List<DepartmentOptions> options();

    /**
     * 添加部门
     * @param department Department
     */
    @AutoFill(AutoFill.OperationType.INSERT)
    void insert(Department department);


    /**
     * 更新部门
     * @param department Department
     */
    @AutoFill(AutoFill.OperationType.UPDATE)
    void update(Department department);

    /**
     * 批量删除部门
     * @param ids List<Long>
     */
    void deleteBatch(List<Long> ids);

    /**
     * 根据ID查询部门
     * @param id Long
     * @return Department
     */
    @Select("select * from department where id = #{id}")
    Department getById(@Param("id") Long id);

    /**
     * 根据医生ID查询部门（在DoctorService中使用）
     * @param doctorId Long
     * @return Department
     */
    @Select("select * from department where director_id = #{doctorId}")
    Department getByDoctorId(@Param("doctorId") Long doctorId);

    /**
     * 清除指定医生的主任身份
     * @param doctorId 医生ID
     */
    void clearDirector(@Param("doctorId") Long doctorId);


}

