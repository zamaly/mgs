package com.example.studentmanagement.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 跨域配置类
 * 解决前端http://localhost:5173访问后端http://localhost:8080的跨域问题
 */
@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // 允许所有接口路径
                .allowedOrigins("http://localhost:5173") // 只允许前端这个地址访问
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // 允许的HTTP方法
                .allowCredentials(true) // 允许携带Cookie和Session
                .maxAge(3600); // 预检请求的有效期，单位秒
    }
}