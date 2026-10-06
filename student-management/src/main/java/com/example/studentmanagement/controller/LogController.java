package com.example.studentmanagement.controller;

import com.example.studentmanagement.service.LogService;
import com.example.studentmanagement.util.Result;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/log")
@CrossOrigin(origins = "http://localhost:5173")
public class LogController {

    private final LogService logService;

    public LogController(LogService logService) {
        this.logService = logService;
    }

    // 查询日志列表
    @GetMapping("/list")
    public Result list(@RequestParam(required = false) Integer logType) {
        return logService.list(logType);
    }

    // 导出日志
    @GetMapping("/export")
    public void export(HttpServletResponse response, @RequestParam(required = false) Integer logType) throws IOException {
        logService.export(response, logType);
    }
}