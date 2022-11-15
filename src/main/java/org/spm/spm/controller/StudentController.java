package org.spm.spm.controller;

import org.spm.spm.bean.Student;
import org.spm.spm.mapper.StudentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
public class StudentController {
    @Autowired
    private StudentMapper studentMapper;

    @RequestMapping(path = "/student-login")
    public String login(HttpServletRequest req) {
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        if (email == null || "".equals(email) || password == null || "".equals(password)) return "false";
        Student stu, query = new Student();
        query.setEmail(email);
        List<Student> res = studentMapper.find(query);
        if (res.size() > 0)
            stu = res.get(0);
        else return "false";
        if (stu.getPassword().equals(password)) {
            req.getSession().setAttribute("user", stu);
            System.out.println(stu);
            return "true";
        } else return "false";
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

    @RequestMapping(path = "/select-course")
    public String selectCourse(HttpServletRequest req) {
        Student stu = ((Student) req.getSession().getAttribute("user"));
        stu.setCourseId(req.getParameter("courseId"));
        stu.setTeacherAgreed(0);
        try {
            studentMapper.update(stu);
        } catch (Exception e) {
            return "false";
        }
        return "true";
    }
}
