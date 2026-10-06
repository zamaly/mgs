package com.example.studentmanagement.entity;

import lombok.Data;
import java.util.Date;
import java.time.LocalDateTime;

@Data
public class Student {
    private String studentId;
    private String realName;
    private String name;
    private String gender;
    private Date birthDate;
    private String major;
    private String className;
    private String phone;
    private String email;
    private String address;
    private Integer status; // 1:在校 2:休学 3:退学 4:毕业
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}