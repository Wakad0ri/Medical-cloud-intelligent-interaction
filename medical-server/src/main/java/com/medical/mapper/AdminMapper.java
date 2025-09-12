package com.medical.mapper;

import com.github.pagehelper.Page;
import com.medical.annotation.AutoFill;
import com.medical.dto.AdminPageQueryDTO;
import com.medical.entity.Admin;
import com.medical.options.AdminOptions;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface AdminMapper {

    /**
     * 根据用户名查询用户（用户登录需要！！！）
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
            "values (#{username}, #{password}, #{realName}, #{phone}, #{status}, #{idCard}, #{email}, #{gender}, #{age}, #{avatar}, #{createTime}, #{updateTime}, #{createAdmin}, #{updateAdmin})")
    void insert(Admin admin);

    /**
     * 分页查询用户
     * @param adminPageQueryDTO 分页参数
     * @return 用户列表
     */
    Page<Admin> page(AdminPageQueryDTO adminPageQueryDTO);

    /**
     * 修改用户, 包括启用禁用
     * @param admin 用户
     */
    @AutoFill(AutoFill.OperationType.UPDATE)
    void update(Admin admin);

    /**
     * 根据ID查询用户
     * @param id 用户ID
     * @return 用户
     */
    @Select("select * from admin where id = #{id}")
    Admin getById(Long id);

    /**
     * 批量删除用户
     * @param ids 用户ID列表
     */
    int deleteBatch(@Param("ids") List<Long> ids);

    /**
     * 获取用户选项
     * @return 用户选项列表
     */
    @Select("select id, real_name as adminName from admin where status = 1 order by real_name")
    List<AdminOptions> options();
}
