package com.example.studentmanagement.service;

import com.example.studentmanagement.entity.Student;
import com.example.studentmanagement.mapper.StudentMapper;
import com.example.studentmanagement.util.Result;
import com.alibaba.excel.EasyExcel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
public class StudentService {

    private final StudentMapper studentMapper;

    public StudentService(StudentMapper studentMapper) {
        this.studentMapper = studentMapper;
    }

    // 查询列表（不变）
    public Result list(String studentId, String realName) {
        List<Student> list = studentMapper.selectList(studentId, realName);
        return Result.success(list);
    }

    // 新增（不变）
    @Transactional
    public Result add(Student student) {
        int rows = studentMapper.insert(student);
        return rows > 0 ? Result.success(null) : Result.error("新增失败");
    }

    // 修改（不变，参数还是Student）
    @Transactional
    public Result update(Student student) {
        int rows = studentMapper.update(student);
        return rows > 0 ? Result.success(null) : Result.error("修改失败");
    }

    // ✅ 修改：参数从Long id改成String studentId
    @Transactional
    public Result delete(String studentId) {
        int rows = studentMapper.deleteByStudentId(studentId);
        return rows > 0 ? Result.success(null) : Result.error("删除失败");
    }

    // 导出（不变）
    public void export(HttpServletResponse response) throws IOException {
        List<Student> list = studentMapper.selectList(null, null);

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        String fileName = URLEncoder.encode("学生信息表", StandardCharsets.UTF_8).replaceAll("\\+", "%20");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");

        EasyExcel.write(response.getOutputStream(), Student.class).sheet("学生信息").doWrite(list);
    }

    // 导入（不变）
    @Transactional
    public Result importExcel(MultipartFile file) throws IOException {
        List<Student> list = EasyExcel.read(file.getInputStream()).head(Student.class).sheet().doReadSync();

        for (Student student : list) {
            studentMapper.insert(student);
        }

        return Result.success("成功导入" + list.size() + "条数据");
    }
}