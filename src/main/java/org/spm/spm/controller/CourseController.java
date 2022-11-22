package org.spm.spm.controller;

import com.google.gson.Gson;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.spm.spm.bean.Course;
import org.spm.spm.mapper.CourseMapper;
import org.spm.spm.mapper.TeacherMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequestMapping("course")
@Api(tags = {"课程相关"})
public class CourseController {
    @Autowired
    CourseMapper courseMapper;
    @Autowired
    TeacherMapper teacherMapper;

    @RequestMapping(value = "/", method = {RequestMethod.GET, RequestMethod.POST})
    @ApiOperation("列出所有课程")
    public String listAll() {
        return new Gson().toJson(courseMapper.findAll());
    }

    @ApiOperation("根据Id查询课程")
    @ApiImplicitParam(name = "courseId", value = "课程号", dataTypeClass = String.class, paramType = "query")
    @RequestMapping(value = "/byId", method = {RequestMethod.GET, RequestMethod.POST})
    public String listById(@RequestParam("courseId") Integer id) {
        return new Gson().toJson(new Course(courseMapper.findById(id).get(0), teacherMapper));
    }

    @ApiOperation("新建公告,返回更新后的课程")
    @RequestMapping(value = "/addBoard", method = {RequestMethod.GET, RequestMethod.POST})
    public String addBoard(@RequestParam("courseId") Integer courseId, @RequestParam("data") String data) {
        Course course = new Course(courseMapper.findById(courseId).get(0), teacherMapper);
        course.board.addFirst(new SimpleDateFormat("yyyy年MM月dd日HH点mm分").format(new Date()) + ": " + data);
        courseMapper.updateBrd(courseId, new Gson().toJson(course.board));
        return new Gson().toJson(course);
    }

    @ApiOperation("更改课程内容部分")
    @ApiImplicitParam(name = "data", value = "更新后的内容", dataTypeClass = String.class, paramType = "query")
    @RequestMapping(value = "src", method = {RequestMethod.GET, RequestMethod.POST})
    public String updateSrc(@RequestParam("courseId") Integer courseId, @RequestParam("data") String data) {
        courseMapper.updateSrc(courseId, data);
        return new Gson().toJson(new Course(courseMapper.findById(courseId).get(0), teacherMapper));
    }
}
