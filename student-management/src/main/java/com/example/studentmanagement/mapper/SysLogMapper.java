package com.example.studentmanagement.mapper;

import com.example.studentmanagement.entity.SysLog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface SysLogMapper {

    List<SysLog> selectList(@Param("logType") Integer logType,
                            @Param("startDate") String startDate,
                            @Param("endDate") String endDate);

    int insert(SysLog log);
}