package com.example.studentmanagement.service;

import com.example.studentmanagement.entity.LeaveApplication;
import com.example.studentmanagement.mapper.LeaveApplicationMapper;
import com.example.studentmanagement.util.Result;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class LeaveService {

    private final LeaveApplicationMapper leaveMapper;

    public LeaveService(LeaveApplicationMapper leaveMapper) {
        this.leaveMapper = leaveMapper;
    }

    // 查询请假列表
    public Result list() {
        List<LeaveApplication> list = leaveMapper.selectAll();
        return Result.success(list);
    }

    // 提交请假申请
    @Transactional
    public Result apply(LeaveApplication leave) {
        // 这里可以从登录用户信息中获取
        leave.setStudentId("2024001");
        leave.setApplicantId(1); // 申请人ID

        int rows = leaveMapper.insert(leave);
        return rows > 0 ? Result.success(null) : Result.error("申请提交失败");
    }

    // 审批通过
    @Transactional
    public Result approve(Integer leaveId) {
        int rows = leaveMapper.approve(leaveId, 1); // 审批人ID
        return rows > 0 ? Result.success(null) : Result.error("审批失败");
    }

    // 审批拒绝
    @Transactional
    public Result reject(Integer leaveId) {
        int rows = leaveMapper.reject(leaveId, 1); // 审批人ID
        return rows > 0 ? Result.success(null) : Result.error("操作失败");
    }
}