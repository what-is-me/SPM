package org.spm.spm.controller;

import org.spm.spm.bean.Student;
import org.spm.spm.mapper.StudentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class StudentController {
    @Autowired
    private StudentMapper studentMapper;

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
