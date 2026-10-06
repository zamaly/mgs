package com.example.studentmanagement.service;

import com.example.studentmanagement.entity.Log;
import com.example.studentmanagement.mapper.LogMapper;
import com.example.studentmanagement.util.Result;
import com.alibaba.excel.EasyExcel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
public class LogService {

    private final LogMapper logMapper;

    public LogService(LogMapper logMapper) {
        this.logMapper = logMapper;
    }

    // 查询日志列表
    public Result list(Integer logType) {
        List<Log> list = logMapper.selectAll(logType);
        return Result.success(list);
    }

    // 新增日志（供AOP调用）
    @Transactional
    public void addLog(Log log) {
        logMapper.insert(log);
    }

    // 导出日志为Excel
    public void export(HttpServletResponse response, Integer logType) throws IOException {
        List<Log> list = logMapper.selectAll(logType);

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        String fileName = URLEncoder.encode("系统日志表", StandardCharsets.UTF_8).replaceAll("\\+", "%20");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");

        EasyExcel.write(response.getOutputStream(), Log.class).sheet("系统日志").doWrite(list);
    }
}