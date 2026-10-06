package com.example.studentmanagement.mapper;

import com.example.studentmanagement.entity.LeaveApplication;
import org.apache.ibatis.annotations.*;
import java.util.List;

@Mapper
public interface LeaveApplicationMapper {

    // 查询所有请假申请
    @Select("SELECT * FROM dbo.leave_application ORDER BY apply_time DESC")
    List<LeaveApplication> selectAll();

    // 新增请假申请
    @Insert("INSERT INTO dbo.leave_application(student_id, leave_type, reason, start_time, end_time, status, applicant_id, apply_time) " +
            "VALUES(#{studentId}, #{leaveType}, #{reason}, #{startTime}, #{endTime}, 0, #{applicantId}, GETDATE())")
    int insert(LeaveApplication leave);

    // 审批通过
    @Update("UPDATE dbo.leave_application SET status = 1, approver_id = #{approverId}, approve_time = GETDATE() WHERE leave_id = #{leaveId}")
    int approve(@Param("leaveId") Integer leaveId, @Param("approverId") Integer approverId);

    // 审批拒绝
    @Update("UPDATE dbo.leave_application SET status = 2, approver_id = #{approverId}, approve_time = GETDATE() WHERE leave_id = #{leaveId}")
    int reject(@Param("leaveId") Integer leaveId, @Param("approverId") Integer approverId);
}