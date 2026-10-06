package com.example.studentmanagement.controller;

import com.example.studentmanagement.entity.Role;
import com.example.studentmanagement.mapper.RoleMapper;
import com.example.studentmanagement.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/role")
@CrossOrigin(origins = "http://localhost:8081", allowCredentials = "true")
public class RoleController {

    @Autowired
    private RoleMapper roleMapper;

    @GetMapping("/list")
    public Result list() {
        List<Role> list = roleMapper.selectList();
        return Result.success(list);
    }

    @GetMapping("/{roleId}")
    public Result getById(@PathVariable Integer roleId) {
        Role role = roleMapper.selectById(roleId);
        return Result.success(role);
    }

    @PostMapping("/add")
    public Result add(@RequestBody Role role) {
        roleMapper.insert(role);
        return Result.success("添加成功");
    }

    @PostMapping("/update")
    public Result update(@RequestBody Role role) {
        roleMapper.updateById(role);
        return Result.success("更新成功");
    }

    @PostMapping("/delete/{roleId}")
    public Result delete(@PathVariable Integer roleId) {
        roleMapper.deleteById(roleId);
        return Result.success("删除成功");
    }
}