package com.medical.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.medical.context.BaseContext;
import com.medical.dto.DoctorAddDTO;
import com.medical.dto.DoctorPageQueryDTO;
import com.medical.dto.DoctorUpdateDTO;
import com.medical.entity.Department;
import com.medical.entity.Doctor;
import com.medical.exception.DoctorStatusErrorException;
import com.medical.mapper.DepartmentMapper;
import com.medical.mapper.DoctorMapper;
import com.medical.options.DoctorOptions;
import com.medical.result.PageResult;
import com.medical.service.DoctorService;
import com.medical.vo.DoctorPageQueryVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.time.LocalDateTime;
import java.util.List;

@Service("adminDoctorService")
public class DoctorServiceImpl implements DoctorService {

    @Autowired
    private DoctorMapper doctorMapper;

    @Autowired
    private DepartmentMapper departmentMapper;

    /**
     * 医生分页查询
     * @param doctorPageQueryDTO （搜索条件）
     * @return PageResult
     */
    @Override
    public PageResult page(DoctorPageQueryDTO doctorPageQueryDTO) {
        PageHelper.startPage(doctorPageQueryDTO.getPage(), doctorPageQueryDTO.getPageSize());
        Page<DoctorPageQueryVO> page = doctorMapper.page(doctorPageQueryDTO);
        return new PageResult(page.getTotal(), page.getResult());
    }

    /**
     * 新增医生（简化版！）
     */
    @Override
    @Transactional
    public void add(DoctorAddDTO doctorAddDTO) {
        // 1. 构建医生对象
        Doctor doctor = new Doctor();
        BeanUtils.copyProperties(doctorAddDTO, doctor);

        // 2. 设置系统字段
        doctor.setDoctorNo(String.valueOf(System.currentTimeMillis()));
        doctor.setPassword(DigestUtils.md5DigestAsHex(doctorAddDTO.getPassword().getBytes()));
        doctor.setStatus(0); // 默认禁用

        // 3. 插入医生
        doctorMapper.insert(doctor);

    }

    /**
     * 更新医生信息（超级简化版！）
     */
    @Override
    @Transactional
    public void update(DoctorUpdateDTO doctorUpdateDTO) {
        // 1. 获取原医生信息，只有禁用状态才能修改
        Doctor oldDoctor = doctorMapper.getById(doctorUpdateDTO.getId());
        if (oldDoctor.getStatus() != 0) {
            throw new DoctorStatusErrorException("只有禁用状态的医生才能被修改");
        }
        // 2. 处理密码属性
        if (doctorUpdateDTO.getPassword() != null) {
            doctorUpdateDTO.setPassword(DigestUtils.md5DigestAsHex(doctorUpdateDTO.getPassword().getBytes()));
        }

        // 3. 更新医生基本信息
        Doctor doctor = new Doctor();
        BeanUtils.copyProperties(doctorUpdateDTO, doctor);
        doctorMapper.update(doctor);
    }

    @Override
    @Transactional
    public void updateStatus(Integer status, List<Long> ids) {
        for (Long doctorId : ids) {
            // 如果要禁用医生，检查是否为科室主任
            if (status == 0) {
                Department department = departmentMapper.getByDoctorId(doctorId);
                if (department != null) {
                    // 该医生是科室主任，清空科室主任
                    clearDirectorRole(department.getId());
                }
            }

            Doctor doctor = Doctor.builder()
                    .id(doctorId)
                    .status(status)
                    .build();
            doctorMapper.update(doctor);
        }
    }

    @Override
    @Transactional
    public void deleteByIds(List<Long> ids) {
        // 1. 只有禁用的医生才能删除
        for (Long doctorId : ids) {
            Doctor doctor = doctorMapper.getById(doctorId);
            if (doctor.getStatus() != 0) {
                throw new DoctorStatusErrorException("只有禁用状态的医生才能被删除");
            }
        }
        // 2. 删除医生
        doctorMapper.deleteByIds(ids);
    }

    @Override
    public List<DoctorOptions> options() {
        return doctorMapper.options();
    }

    @Override
    public Doctor getById(Long id) {
        return doctorMapper.getById(id);
    }

    /**
     * 清除科室主任身份
     */
    private void clearDirectorRole(Long departmentId) {
        final Long ban = 0L;
        Department updateDept = Department.builder()
                .id(departmentId)
                .directorId(ban)
                .updateTime(LocalDateTime.now())
                .updateAdmin(BaseContext.getCurrentId())
                .build();
        departmentMapper.update(updateDept);
    }
}
