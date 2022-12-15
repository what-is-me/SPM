package org.spm.spm.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.spm.spm.bean.ExamBean;
import org.spm.spm.bean.Student;
import org.spm.spm.bean.pro.Exam;
import org.spm.spm.mapper.StudentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@Api(tags = "学生的各种操作")
@RequestMapping("student")
@Slf4j
public class StudentController {
    @Autowired
    private StudentMapper studentMapper;
    private final Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();

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

    @ApiOperation("学生各个考试成绩")
    @RequestMapping(path = "/scores", method = {RequestMethod.GET, RequestMethod.POST})
    public String studentScores(@RequestParam(value = "email", required = false) String email, HttpServletRequest req) {
        if (email == null || "".equals(email)) email = ((Student) req.getSession().getAttribute("user")).getEmail();
        return gson.toJson(studentMapper.studentScore(email));
    }

    @ApiOperation("查询学生的试卷")
    @RequestMapping(path = "/exams", method = {RequestMethod.GET, RequestMethod.POST})
    public String studentExams(@RequestParam(value = "email", required = false) String email, @RequestParam(value = "eid", required = false) Integer eid, HttpServletRequest req) throws Exception {
        Student stu = ((Student) req.getSession().getAttribute("user"));
        if (email != null && !"".equals(email)) {
            stu = Student.builder().email(email).build();
            stu = studentMapper.find(stu).get(0);
            log.info(stu.toString());
        }
        List<Object> examViews = new ArrayList<>();
        Date now = new Date();
        List<ExamBean> list;

        if (eid == null) list = studentMapper.listExams(stu);
        else list = studentMapper.exam(eid);
        for (ExamBean examBean : list) {
            if (examBean.getBegin().before(now))
                examViews.add(new Exam(examBean).view());
        }
        return gson.toJson(examViews);
    }

    @ApiOperation("评卷,返回每题得分")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "exam_id", value = "考试id")
    })
    @RequestMapping(path = "/check", method = RequestMethod.POST)
    public Map<String, Double> checkExam(@RequestParam(value = "email", required = false) String email,
                                         @RequestParam("exam_id") Integer eid,
                                         HttpServletRequest req, HttpServletResponse resp) throws Exception {
        if (email == null || "".equals(email)) email = ((Student) req.getSession().getAttribute("user")).getEmail();
        Exam exam = new Exam(studentMapper.exam(eid).get(0));
        Map<String, String[]> stuAns = req.getParameterMap();
        Map<String, Double> res = exam.check(stuAns);
        double score = res.get("all");
        try {
            studentMapper.insertScore(email, eid, score);
        } catch (Exception e) {
            studentMapper.updateScore(email, eid, score);
        }
        return res;
    }
}
