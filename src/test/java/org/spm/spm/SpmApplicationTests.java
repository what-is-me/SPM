package org.spm.spm;

import org.junit.jupiter.api.Test;
import org.spm.spm.mapper.CourseMapper;
import org.spm.spm.mapper.StudentMapper;
import org.spm.spm.mapper.TeacherMapper;
import org.spm.spm.tools.MailTool;
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
    @Autowired
    MailTool mailSender;

    @Test
    void sendMail() {

        mailSender.sendMail("whatisme@outlook.jp", "作业", "作业已经发送到你的邮箱");
    }
}
