package com.example.studentmanagement.entity;

import lombok.Data;
import java.util.Date;

@Data
public class SysLog {
    private Integer logId;
    private Integer userId;
    private String username;
    private String operation;
    private String method;
    private String params;
    private String ip;
    private Integer logType;
    private Date createTime;
}