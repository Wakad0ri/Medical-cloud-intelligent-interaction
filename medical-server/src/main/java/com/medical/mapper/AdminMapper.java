package com.medical.mapper;

import com.github.pagehelper.Page;
import com.medical.annotation.AutoFill;
import com.medical.dto.AdminPageQueryDTO;
import com.medical.entity.Admin;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface AdminMapper {

    /**
     * 根据用户名查询用户
     * @param username 用户名
     * @return 用户
     */
    @Select("select * from admin where username = #{username}")
    Admin getAdminByUsername(String username);

    /**
     * 添加用户
     * @param admin 用户
     */
    @AutoFill(AutoFill.OperationType.INSERT)
    @Insert("insert into admin (username, password, real_name, phone, status, id_card, email, gender, age, avatar, create_time, update_time, create_admin, update_admin) " +
            "values (#{username}, #{password}, #{realName}, #{phone}, #{status}, #{idCard}, #{email}, #{sex}, #{age}, #{avatar}, #{createTime}, #{updateTime}, #{createAdmin}, #{updateAdmin})")
    void insert(Admin admin);

    /**
     * 分页查询用户
     * @param adminPageQueryDTO 分页参数
     * @return 用户列表
     */
    Page<Admin> page(AdminPageQueryDTO adminPageQueryDTO);
}
