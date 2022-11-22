package org.spm.spm;

import org.junit.jupiter.api.Test;
import org.spm.spm.bean.Teacher;
import org.spm.spm.mapper.CourseMapper;
import org.spm.spm.mapper.StudentMapper;
import org.spm.spm.mapper.TeacherMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SpmApplicationTests {
    @Autowired
    private StudentMapper studentMapper;
    @Autowired
    private CourseMapper courseMapper;
    @Autowired
    private TeacherMapper teacherMapper;

    @Test
    void contextLoads() {
        System.out.println(teacherMapper.find(Teacher.builder().email("admin@usst.com").build()).toString());
    }

}
