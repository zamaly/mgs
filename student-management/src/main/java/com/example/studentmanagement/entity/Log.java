package com.example.studentmanagement.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class Log {
    private Integer logId;
    private Integer logType; // 1=一般信息，2=错误信息
    private String operation;
    private String operator;
    private String ipAddress;
    private String exceptionMessage;
    private LocalDateTime createTime;
}