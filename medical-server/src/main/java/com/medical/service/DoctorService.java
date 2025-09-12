package com.medical.service;

import com.medical.dto.DoctorAddDTO;
import com.medical.dto.DoctorPageQueryDTO;
import com.medical.dto.DoctorUpdateDTO;
import com.medical.entity.Doctor;
import com.medical.options.DoctorOptions;
import com.medical.result.PageResult;
import com.medical.vo.DoctorInfoVO;

import java.util.List;

public interface DoctorService {

    /**
     * 分页查询医生
     */
    PageResult page(DoctorPageQueryDTO doctorPageQueryDTO);
    
    /**
     * 获取医生选项
     */
    List<DoctorOptions> options();
    
    /**
     * 新增医生
     */
    void add(DoctorAddDTO doctorAddDTO);

    /**
     * 根据ID查询医生
     */
    Doctor getById(Long id);

    /**
     * 更新医生信息
     */
    void update(DoctorUpdateDTO doctorUpdateDTO);

    /**
     * 批量更新状态
     */
    void updateStatus(Integer status, List<Long> ids);

    /**
     * 批量删除医生
     */
    void deleteByIds(List<Long> ids);

}
