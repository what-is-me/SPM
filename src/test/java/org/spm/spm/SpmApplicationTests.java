package org.spm.spm;

import org.junit.jupiter.api.Test;
import org.spm.spm.bean.CourseBean;
import org.spm.spm.mapper.CourseMapper;
import org.spm.spm.mapper.StudentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class SpmApplicationTests {
    @Autowired
    private StudentMapper studentMapper;
    @Autowired
    private CourseMapper courseMapper;

    @Test
    void contextLoads() {
        List<CourseBean> res = courseMapper.findAll();
        System.out.println(res);
    }

}
