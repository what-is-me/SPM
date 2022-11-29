package org.spm.spm.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.spm.spm.bean.Student;
import org.spm.spm.mapper.StudentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@Api(tags = "学生的各种操作")
public class StudentController {
    @Autowired
    private StudentMapper studentMapper;

    @ApiOperation(value = "学生选择课程")
    @RequestMapping(path = "/select-course", method = {RequestMethod.GET, RequestMethod.POST})
    public String selectCourse(@RequestParam("courseId") Integer courseId, HttpServletRequest req) {
        Student stu = ((Student) req.getSession().getAttribute("user"));
        stu.setCourseId(String.valueOf(courseId));
        stu.setTeacherAgreed(1);
        try {
            studentMapper.update(stu);
        } catch (Exception e) {
            return "false";
        }
        return "true";
    }
}
