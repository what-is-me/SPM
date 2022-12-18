package org.spm.spm.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.spm.spm.bean.Course;
import org.spm.spm.bean.CourseBean;
import org.spm.spm.bean.ExamBean;
import org.spm.spm.bean.User;
import org.spm.spm.bean.pro.Exam;
import org.spm.spm.mapper.CourseMapper;
import org.spm.spm.mapper.StudentMapper;
import org.spm.spm.mapper.TeacherMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@Api(tags = "老师")
@RequestMapping("teacher")
@Slf4j
public class TeacherController {
    @Autowired
    CourseMapper courseMapper;
    @Autowired
    StudentMapper studentMapper;
    @Autowired
    TeacherMapper teacherMapper;
    private final Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();

    @ApiOperation("列出老师所有课程")
    @ApiImplicitParam(name = "email", value = "老师邮箱,测试用", dataTypeClass = String.class, paramType = "query")
    @RequestMapping(value = "/courses", method = {RequestMethod.GET, RequestMethod.POST})
    public String courseLists(@RequestParam(value = "email", required = false) String email, HttpServletRequest req) {
        if (email == null || "".equals(email)) email = ((User) req.getSession().getAttribute("user")).getEmail();
        List<Course> cl = new ArrayList<>();
        for (CourseBean c : courseMapper.findByTeacher(email)) {
            Course course = new Course(c, teacherMapper);
            cl.add(course);
            //log.info(course.teacher.toString());
        }
        return gson.toJson(cl);
    }

    @ApiOperation("老师新建课程")
    @ApiImplicitParam(name = "email", value = "老师邮箱,测试用", dataTypeClass = String.class, paramType = "query")
    @RequestMapping(value = "/makeCourse", method = {RequestMethod.GET, RequestMethod.POST})
    public String createCourse(@RequestParam(value = "email", required = false) String email, HttpServletRequest req) {
        if (email == null || "".equals(email)) email = ((User) req.getSession().getAttribute("user")).getEmail();
        try {
            courseMapper.createCourse(email);
        } catch (Exception e) {
            return "数据库出错";
        }
        return "true";
    }

    @ApiOperation("列出选择某门课的学生")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "courseId", value = "课程号", dataTypeClass = Integer.class, paramType = "query"),
            @ApiImplicitParam(name = "agreed", value = "老师是否同意,1表同意0表不同意", dataTypeClass = Integer.class, paramType = "query")
    })
    @RequestMapping(value = "/sc", method = {RequestMethod.GET, RequestMethod.POST})
    public String studentC(@RequestParam Integer courseId, @RequestParam Integer agreed) {
        return gson.toJson(studentMapper.studentC(courseId, agreed));
    }

    @ApiOperation("[decrepted]同意或拒绝某同学选某课")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Semail", value = "学生邮箱", dataTypeClass = String.class, paramType = "query"),
            @ApiImplicitParam(name = "agreed", value = "老师是否同意,1表同意0表不同意", dataTypeClass = Integer.class, paramType = "query")
    })
    @RequestMapping(value = "/agreeStudent", method = {RequestMethod.GET})//{RequestMethod.GET, RequestMethod.POST})
    public String newCourse(@RequestParam("agreed") Integer agreed, @RequestParam("Semail") String sEmail) {
        if (1 == agreed) studentMapper.agree(sEmail);
        else studentMapper.deny(sEmail);
        return "true";
    }

    @ApiOperation("某个老师的所有试卷")
    @RequestMapping(value = "/exams", method = RequestMethod.GET)
    public String Exams(@RequestParam(value = "email", required = false) String email, HttpServletRequest req) throws Exception {
        if (email == null || "".equals(email)) email = ((User) req.getSession().getAttribute("user")).getEmail();
        List<ExamBean> examBeans = teacherMapper.listExam(email);
        List<Exam> exams = new ArrayList<>();
        for (ExamBean examBean : examBeans) {
            exams.add(new Exam(examBean));
        }
        return gson.toJson(exams);
    }

    @ApiOperation("某门课的学生成绩")
    @RequestMapping(path = "/students_score", method = RequestMethod.GET)
    public String studentsScore(@RequestParam("courseId") Integer cid) {
        return gson.toJson(teacherMapper.listStudentScoresOfCourse(cid));
    }

    @ApiOperation("添加或者修改试卷，新建就不需要试卷编号,返回true或者false代表成功失败")
    @RequestMapping(path = "/update_exam", method = {RequestMethod.POST, RequestMethod.GET})
    public String updateExam(@RequestParam(value = "eid", required = false) Integer eid,
                             @RequestParam Integer cid,
                             @RequestParam String begin,
                             @RequestParam String end,
                             @RequestParam String name,
                             @RequestParam String text) throws ParseException {
        ExamBean examBean = ExamBean.builder()
                .eid(eid)
                .cid(cid)
                .begin(convert(begin))
                .end(convert(end))
                .name(name)
                .text(text)
                .build();
        try {
            try {
                new Exam(examBean);
            } catch (Exception e) {
                return "invalid_examtext";
            }
            if (eid == null)
                teacherMapper.addExam(examBean);
            else
                teacherMapper.updateExam(examBean);
        } catch (Exception e) {
            return "database_error";
        }
        return "true";
    }

    public Date convert(String s) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
        return sdf.parse(s);
    }
}