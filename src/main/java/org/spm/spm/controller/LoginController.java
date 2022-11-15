package org.spm.spm.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.spm.spm.bean.Student;
import org.spm.spm.bean.Teacher;
import org.spm.spm.mapper.StudentMapper;
import org.spm.spm.mapper.TeacherMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@Api(tags = "登录")
public class LoginController {
    @Autowired
    private StudentMapper studentMapper;
    @Autowired
    private TeacherMapper teacherMapper;

    @ApiImplicitParams({
            @ApiImplicitParam(name = "email", dataTypeClass = String.class, value = "用户邮箱", required = true, paramType = "query"),
            @ApiImplicitParam(name = "password", dataTypeClass = String.class, value = "密码", required = true, paramType = "query"),
            @ApiImplicitParam(name = "type", dataTypeClass = String.class, value = "身份('teacher'表示老师，否则为学生)", required = true, paramType = "query", defaultValue = "student")
    })
    @ApiOperation(value = "是否登录成功", notes = "成功返回true，否则返回失败原因")
    @RequestMapping(path = "/login", method = {RequestMethod.GET, RequestMethod.POST})
    public String login(
            @RequestParam String email,
            @RequestParam String password,
            @RequestParam String type,
            HttpServletRequest req) {
        if (email == null || "".equals(email)) return "邮箱为空";
        if (password == null || "".equals(password)) return "密码为空";
        HttpSession session = req.getSession();
        if ("teacher".equals(type)) {
            Teacher t = new Teacher();
            t.setEmail(email);
            List<Teacher> res = teacherMapper.find(t);
            if (res.size() > 0)
                t = res.get(0);
            else return "用户不存在";
            if (t.getPassword().equals(password)) {
                session.setAttribute("user", t);
                return "true";
            } else return "密码错误";
        } else {
            Student stu = new Student();
            stu.setEmail(email);
            List<Student> res = studentMapper.find(stu);
            if (res.size() > 0)
                stu = res.get(0);
            else return "用户不存在";
            if (stu.getPassword().equals(password)) {
                session.setAttribute("user", stu);
                return "true";
            } else return "密码错误";
        }
    }
}
