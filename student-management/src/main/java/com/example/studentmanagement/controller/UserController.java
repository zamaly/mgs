package com.example.studentmanagement.controller;

import com.example.studentmanagement.entity.User;
import com.example.studentmanagement.service.UserService;
import com.example.studentmanagement.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;  // 注意这里是 jakarta

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "http://localhost:8081", allowCredentials = "true")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public Result login(@RequestParam String username,
                        @RequestParam String password,
                        HttpSession session) {
        User user = userService.login(username, password);
        if (user == null) {
            return Result.error("用户名或密码错误");
        }
        if (user.getStatus() == 0) {
            return Result.error("账号已被禁用");
        }
        session.setAttribute("loginUser", user);
        return Result.success(user);
    }

    @GetMapping("/info")
    public Result getInfo(HttpSession session) {
        User user = (User) session.getAttribute("loginUser");
        if (user == null) {
            return Result.error("未登录");
        }
        return Result.success(user);
    }

    @PostMapping("/logout")
    public Result logout(HttpSession session) {
        session.invalidate();
        return Result.success("退出成功");
    }

    @PostMapping("/updatePassword")
    public Result updatePassword(@RequestParam String oldPassword,
                                 @RequestParam String newPassword,
                                 HttpSession session) {
        User user = (User) session.getAttribute("loginUser");
        if (user == null) {
            return Result.error("未登录");
        }
        if (!user.getPassword().equals(oldPassword)) {
            return Result.error("原密码错误");
        }
        user.setPassword(newPassword);
        userService.updateById(user);
        return Result.success("密码修改成功");
    }

    @PostMapping("/updateInfo")
    public Result updateInfo(@RequestBody User updateUser, HttpSession session) {
        User user = (User) session.getAttribute("loginUser");
        if (user == null) {
            return Result.error("未登录");
        }
        updateUser.setUserId(user.getUserId());
        userService.updateById(updateUser);
        return Result.success("信息更新成功");
    }
}