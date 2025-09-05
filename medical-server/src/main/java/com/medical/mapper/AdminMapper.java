package com.medical.mapper;

import com.medical.entity.Admin;
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
}
