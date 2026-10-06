package com.example.studentmanagement.entity;

import lombok.Data;
import java.util.Date;

@Data
public class User {
    private Integer userId;
    private String username;
    private String password;
    private String realName;
    private Integer roleId;
    private Integer status; // 1:正常 0:禁用
    private Date createTime;
    private Date updateTime;
}