package com.example.studentmanagement.controller;

import com.example.studentmanagement.entity.LeaveApplication;
import com.example.studentmanagement.service.LeaveService;
import com.example.studentmanagement.util.Result;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/leave")
@CrossOrigin(origins = "http://localhost:5173")
public class LeaveController {

    private final LeaveService leaveService;

    public LeaveController(LeaveService leaveService) {
        this.leaveService = leaveService;
    }

    // 查询请假列表
    @GetMapping("/list")
    public Result list() {
        return leaveService.list();
    }

    // 提交请假申请
    @PostMapping("/apply")
    public Result apply(@RequestBody LeaveApplication leave) {
        return leaveService.apply(leave);
    }

    // 审批通过
    @PutMapping("/approve/{leaveId}")
    public Result approve(@PathVariable Integer leaveId) {
        return leaveService.approve(leaveId);
    }

    // 审批拒绝
    @PutMapping("/reject/{leaveId}")
    public Result reject(@PathVariable Integer leaveId) {
        return leaveService.reject(leaveId);
    }
}