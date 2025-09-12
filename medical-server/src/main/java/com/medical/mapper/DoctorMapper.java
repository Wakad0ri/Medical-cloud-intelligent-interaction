package com.medical.mapper;

import com.github.pagehelper.Page;
import com.medical.annotation.AutoFill;
import com.medical.dto.DoctorPageQueryDTO;
import com.medical.entity.Doctor;
import com.medical.options.DoctorOptions;
import com.medical.vo.DoctorInfoVO;
import com.medical.vo.DoctorPageQueryVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DoctorMapper {

    /**
     * 分页查询医生信息
     * @param doctorPageQueryDTO （姓名，科室，职称，状态）
     * @return Page<DoctorPageQueryVO>
     */
    Page<DoctorPageQueryVO> page(DoctorPageQueryDTO doctorPageQueryDTO);

    /**
     * 添加医生信息
     * @param doctor 医生信息
     */
    @AutoFill(AutoFill.OperationType.INSERT)
    void insert(Doctor doctor);

    Doctor getById(@Param("id") Long id);

    /**
     * 修改医生信息
     * @param doctor 医生信息
     */
    @AutoFill(AutoFill.OperationType.UPDATE)
    void update(Doctor doctor);


    /**
     * 批量删除医生
     * @param ids 医生ID列表
     */
    void deleteByIds(@Param("ids") List<Long> ids);

    /**
     * 获取医生选项
     * @return 医生选项列表
     */
    List<DoctorOptions> options();
}
