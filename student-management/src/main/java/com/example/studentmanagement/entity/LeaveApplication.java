package com.example.studentmanagement.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class LeaveApplication {
    private Integer leaveId; // 主键
    private String studentId; // 学号
    private Integer leaveType; // 请假类型：1=事假，2=病假，3=其他
    private String reason; // 请假原因
    private LocalDateTime startTime; // 开始时间
    private LocalDateTime endTime; // 结束时间
    private Integer status; // 状态：0=待审批，1=已通过，2=已拒绝
    private Integer applicantId; // 申请人ID
    private Integer approverId; // 审批人ID
    private LocalDateTime applyTime; // 申请时间
    private LocalDateTime approveTime; // 审批时间
}