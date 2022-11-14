package org.spm.spm;

import org.junit.jupiter.api.Test;
import org.spm.spm.bean.Student;
import org.spm.spm.mapper.StudentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class SpmApplicationTests {
    @Autowired
    private StudentMapper studentMapper;

    @Test
    void contextLoads() {
        List<Student> res = studentMapper.findAll();
        System.out.println(res);
    }

}
