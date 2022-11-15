package org.spm.spm.controller;

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
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
public class LoginController {
    @Autowired
    private StudentMapper studentMapper;
    @Autowired
    private TeacherMapper teacherMapper;

    @ApiImplicitParams({
            @ApiImplicitParam(name = "email", dataTypeClass = String.class, value = "用户邮箱", required = true),
            @ApiImplicitParam(name = "password", dataTypeClass = String.class, value = "密码", required = true),
            @ApiImplicitParam(name = "type", dataTypeClass = String.class, value = "用户身份", required = true)
    })
    @ApiOperation(value = "是否登录成功", notes = "true 表示成功, false 表示失败")
    @RequestMapping(path = "/login", method = {RequestMethod.GET, RequestMethod.POST})
    public String login(HttpServletRequest req) {
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        if (email == null || "".equals(email) || password == null || "".equals(password)) return "false";
        String type = req.getParameter("type");
        HttpSession session = req.getSession();
        if ("teacher".equals(type)) {
            Teacher t = new Teacher();
            t.setEmail(email);
            List<Teacher> res = teacherMapper.find(t);
            if (res.size() > 0)
                t = res.get(0);
            else return "false";
            if (t.getPassword().equals(password)) {
                session.setAttribute("user", t);
                return "true";
            } else return "false";
        } else {
            Student stu = new Student();
            stu.setEmail(email);
            List<Student> res = studentMapper.find(stu);
            if (res.size() > 0)
                stu = res.get(0);
            else return "false";
            if (stu.getPassword().equals(password)) {
                session.setAttribute("user", stu);
                return "true";
            } else return "false";
        }
    }

    @RequestMapping(path = "/sign-in")
    public String signIn(HttpServletRequest req) {
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String password_again = req.getParameter("password_again");
        String name = req.getParameter("name");
        if (email == null || "".equals(email)) return "false";
        if (password == null || "".equals(password)) return "false";
        if (!password.equals(password_again)) return "false";
        Student stu = new Student();
        stu.setName(name);
        stu.setEmail(email);
        stu.setPassword(password);
        try {
            studentMapper.insert(stu);
        } catch (Exception e) {
            return "false";
        }
        return "true";
    }
}
