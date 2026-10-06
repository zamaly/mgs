package com.example.studentmanagement.mapper;

import com.example.studentmanagement.entity.StudentStatusChange;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface StudentStatusChangeMapper {

    List<StudentStatusChange> selectList(@Param("studentId") String studentId,
                                         @Param("changeType") String changeType);

    int insert(StudentStatusChange change);
}