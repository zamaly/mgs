package com.example.studentmanagement.entity;

import lombok.Data;
import java.util.Date;

@Data
public class Role {
    private Integer roleId;
    private String roleName;
    private String roleDesc;
    private Date createTime;
}