package com.example.studentmanagement.controller;

import com.example.studentmanagement.entity.Student;
import com.example.studentmanagement.service.StudentService;
import com.example.studentmanagement.util.Result;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

// ✅ 关键：把 javax.servlet 改成 jakarta.servlet
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@RestController
@RequestMapping("/student")
@CrossOrigin(origins = "http://localhost:5173")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    // 查询学生列表
    @GetMapping("/list")
    public Result list(
            @RequestParam(required = false) String studentId,
            @RequestParam(required = false) String realName
    ) {
        return studentService.list(studentId, realName);
    }

    // 新增学生
    @PostMapping("/add")
    public Result add(@RequestBody Student student) {
        return studentService.add(student);
    }

    // 修改学生
    @PutMapping("/update")
    public Result update(@RequestBody Student student) {
        return studentService.update(student);
    }

    // 删除学生
    // ✅ 修改：路径变量从{id}改成{studentId}
    @DeleteMapping("/delete/{studentId}")
    public Result delete(@PathVariable String studentId) {
        return studentService.delete(studentId);
    }

    // 导出Excel
    @GetMapping("/export")
    public void export(HttpServletResponse response) throws IOException {
        studentService.export(response);
    }

    // 导入Excel
    @PostMapping("/import")
    public Result importExcel(@RequestParam("file") MultipartFile file) throws IOException {
        return studentService.importExcel(file);
    }
}