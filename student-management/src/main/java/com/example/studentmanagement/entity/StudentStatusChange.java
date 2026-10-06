package com.example.studentmanagement.entity;

import lombok.Data;
import java.util.Date;

@Data
public class StudentStatusChange {
    private Integer changeId;
    private String studentId;
    private String changeType;
    private Date changeDate;
    private String reason;
    private Integer operatorId;
    private Date createTime;

    // 关联字段
    private String studentName;
    private String operatorName;
}