package com.example.studentmanagement.mapper;

import com.example.studentmanagement.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {

    @Select("SELECT * FROM sys_user WHERE username = #{username}")
    User selectByUsername(String username);

    User selectById(Integer userId);

    int updateById(User user);
}