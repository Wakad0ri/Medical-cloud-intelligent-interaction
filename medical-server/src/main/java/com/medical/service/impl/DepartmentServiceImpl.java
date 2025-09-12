package com.medical.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.medical.context.BaseContext;
import com.medical.dto.DepartmentAddDTO;
import com.medical.dto.DepartmentPageQueryDTO;
import com.medical.dto.DepartmentUpdateDTO;
import com.medical.entity.Department;
import com.medical.exception.DepartmentStatusErrorException;
import com.medical.mapper.DepartmentMapper;
import com.medical.options.DepartmentOptions;
import com.medical.result.PageResult;
import com.medical.service.DepartmentService;
import com.medical.vo.DepartmentPageQueryVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service("adminDepartmentService")
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    private DepartmentMapper departmentMapper;

    /**
     * 分页查询部门
     * @param departmentPageQueryDTO json
     * @return PageResult
     */
    @Override
    public PageResult page(DepartmentPageQueryDTO departmentPageQueryDTO) {
        PageHelper.startPage(departmentPageQueryDTO.getPage(), departmentPageQueryDTO.getPageSize());
        Page<DepartmentPageQueryVO> page = departmentMapper.page(departmentPageQueryDTO);
        return new PageResult(page.getTotal(), page);
    }

    /**
     * 添加部门
     * @param departmentAddDTO json
     */
    @Override
    public void add(DepartmentAddDTO departmentAddDTO) {
        // 倘若 directorId 不为空 -> 判断选取的医生是否已经有科室主任了
        if (departmentAddDTO.getDirectorId() != null) {
            Department oldDepartment = departmentMapper.getByDoctorId(departmentAddDTO.getDirectorId());
            if (oldDepartment != null) {
                //TODO：这里要让前端做提示然后重复确认后提交
                Department updateOldDepartment = Department.builder()
                        .id(oldDepartment.getId())
                        .directorId(0L)  // 设置为0表示暂无主任
                        .updateTime(LocalDateTime.now())
                        .updateAdmin(BaseContext.getCurrentId())
                        .build();
                departmentMapper.update(updateOldDepartment);
            }
        }
        // 先复制属性
        Department department = new Department();

        BeanUtils.copyProperties(departmentAddDTO, department);
        // 处理 deptCode（根据当前时间戳生成）
        department.setDeptCode("DP" + System.currentTimeMillis());

        departmentMapper.insert(department);
    }

    /**
     * 部门选项
     * @return List<DepartmentOptions>
     */
    @Override
    public List<DepartmentOptions> options() {return departmentMapper.options();}


    /**
     * 更新部门信息
     * @param departmentUpdateDTO json
     */
    @Override
    public void update(DepartmentUpdateDTO departmentUpdateDTO) {
        Department departmentDB = departmentMapper.getById(departmentUpdateDTO.getId());
        if (departmentDB.getStatus() != 0) {
            throw new DepartmentStatusErrorException("只有禁用状态的科室才能被修改");
        }
        // 倘若 directorId 不为空 -> 判断选取的医生是否已经有科室主任了
        if (departmentUpdateDTO.getDirectorId() != null) {
            Department oldDepartment = departmentMapper.getByDoctorId(departmentUpdateDTO.getDirectorId());
            if (oldDepartment != null) {
                //TODO：这里要让前端做提示然后重复确认后提交
                departmentMapper.clearDirector(departmentUpdateDTO.getDirectorId());
            }
        }
        // 正常处理更新逻辑
        Department department = new Department();
        BeanUtils.copyProperties(departmentUpdateDTO, department);
        departmentMapper.update(department);


    }

    /**
     * 更新部门状态
     * @param status Integer
     * @param ids List<Long>
     */
    @Override
    public void updateStatus(Integer status, List<Long> ids) {
        for (Long id : ids) {
            Department department = Department.builder()
                    .id(id)
                    .status(status)
                    .build();
            departmentMapper.update(department);
        }
    }

    /**
     * 删除部门
     * @param ids List<Long>
     */
    @Override
    public void delete(List<Long> ids) {
        for (Long id : ids) {
            Department department = departmentMapper.getById(id);
            if (department.getStatus() != 0) {
                throw new DepartmentStatusErrorException("只有禁用状态的科室才能被删除");
            }
        }
        departmentMapper.deleteBatch(ids);
    }

    /**
     * 获取部门信息
     * @param id Long
     * @return Department
     */
    @Override
    public Department getById(Long id) {
        return departmentMapper.getById(id);
    }
}
