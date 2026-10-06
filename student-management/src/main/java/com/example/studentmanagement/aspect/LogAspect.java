package com.example.studentmanagement.aspect;

import com.example.studentmanagement.entity.Log;
import com.example.studentmanagement.service.LogService;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
@Component
public class LogAspect {

    private final LogService logService;

    public LogAspect(LogService logService) {
        this.logService = logService;
    }

    // 记录正常操作日志（所有Controller的public方法）
    @AfterReturning("execution(* com.example.studentmanagement.controller.*.*(..))")
    public void logOperation(JoinPoint joinPoint) {
        Log log = new Log();
        log.setLogType(1); // 一般信息
        log.setOperation("执行方法：" + joinPoint.getSignature().getName());
        log.setOperator("admin"); // 这里可以从登录用户信息中获取
        log.setIpAddress(getIpAddress());
        log.setExceptionMessage(null);

        logService.addLog(log);
    }

    // 记录异常日志
    @AfterThrowing(pointcut = "execution(* com.example.studentmanagement.controller.*.*(..))", throwing = "e")
    public void logException(JoinPoint joinPoint, Exception e) {
        Log log = new Log();
        log.setLogType(2); // 错误信息
        log.setOperation("执行方法：" + joinPoint.getSignature().getName() + " 发生异常");
        log.setOperator("admin");
        log.setIpAddress(getIpAddress());
        log.setExceptionMessage(e.getMessage());

        logService.addLog(log);
    }

    // 获取客户端IP地址
    private String getIpAddress() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes == null) {
            return "unknown";
        }
        HttpServletRequest request = attributes.getRequest();
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}