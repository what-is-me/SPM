package org.spm.spm.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.spm.spm.bean.Student;
import org.spm.spm.mapper.StudentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@Api(tags = "学生的各种操作(未完成)")
public class StudentController {
    @Autowired
    private StudentMapper studentMapper;

    @ApiOperation(value = "学生选择课程（未完成）")
    @RequestMapping(path = "/select-course", method = {RequestMethod.GET, RequestMethod.POST})
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
