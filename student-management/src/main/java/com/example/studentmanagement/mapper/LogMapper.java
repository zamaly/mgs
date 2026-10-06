package com.example.studentmanagement.mapper;

import com.example.studentmanagement.entity.Log;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface LogMapper {

    // 新增日志
    @Insert("INSERT INTO dbo.system_log(log_type, operation, operator, ip_address, exception_message) " +
            "VALUES(#{logType}, #{operation}, #{operator}, #{ipAddress}, #{exceptionMessage})")
    int insert(Log log);

    // 查询所有日志
    @Select("<script>" +
            "SELECT * FROM dbo.system_log " +
            "<where>" +
            "   <if test='logType != null'> AND log_type = #{logType} </if>" +
            "</where>" +
            "ORDER BY create_time DESC" +
            "</script>")
    List<Log> selectAll(Integer logType);
}