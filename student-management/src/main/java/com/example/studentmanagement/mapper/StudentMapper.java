package com.example.studentmanagement.mapper;

import com.example.studentmanagement.entity.Student;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface StudentMapper {

    // 查询列表（不变）
    @Select("<script>" +
            "SELECT * FROM dbo.student " +
            "<where>" +
            "   <if test='studentId != null and studentId != \"\"'> AND student_id = #{studentId} </if>" +
            "   <if test='realName != null and realName != \"\"'> AND real_name LIKE CONCAT('%', #{realName}, '%') </if>" +
            "</where>" +
            "ORDER BY create_time DESC" +
            "</script>")
    List<Student> selectList(@Param("studentId") String studentId, @Param("realName") String realName);

    // 新增（不变）
    @Insert("INSERT INTO dbo.student(student_id, real_name, gender, class_name, major, phone, create_time) " +
            "VALUES(#{studentId}, #{realName}, #{gender}, #{className}, #{major}, #{phone}, GETDATE())")
    int insert(Student student);

    // ✅ 修改：WHERE student_id = #{studentId}
    @Update("UPDATE dbo.student SET " +
            "real_name = #{realName}, " +
            "gender = #{gender}, " +
            "class_name = #{className}, " +
            "major = #{major}, " +
            "phone = #{phone}, " +
            "update_time = GETDATE() " +
            "WHERE student_id = #{studentId}")
    int update(Student student);

    // ✅ 修改：WHERE student_id = #{studentId}
    @Delete("DELETE FROM dbo.student WHERE student_id = #{studentId}")
    int deleteByStudentId(String studentId);

    // ✅ 修改：WHERE student_id = #{studentId}
    @Select("SELECT * FROM dbo.student WHERE student_id = #{studentId}")
    Student selectByStudentId(String studentId);
}