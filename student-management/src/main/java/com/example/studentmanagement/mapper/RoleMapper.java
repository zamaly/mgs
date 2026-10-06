package com.example.studentmanagement.mapper;

import com.example.studentmanagement.entity.Role;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface RoleMapper {

    List<Role> selectList();

    Role selectById(Integer roleId);

    int insert(Role role);

    int updateById(Role role);

    int deleteById(Integer roleId);
}