package org.spm.spm.mapper;

import org.apache.ibatis.annotations.*;
import org.spm.spm.bean.ExamBean;
import org.spm.spm.bean.Student;

import java.util.List;
import java.util.Map;

@Mapper
public interface StudentMapper {
    @Select("select * from spm.student where email like #{email};")
    List<Student> find(Student student);

    @Update("update spm.student set courseId=#{courseId}, teacherAgreed=#{teacherAgreed} where email like #{email};")
    void update(Student student);

    @Insert("insert into spm.student (email, password, name, teacherAgreed)values(#{email}, #{password}, #{name}, 0);")
    void insert(Student student);

    @Select("select * from spm.student;")
    List<Student> findAll();

    @Update("update spm.student set teacherAgreed=1 where email like '${email}';")
    void agree(@Param("email") String email);

    @Update("update spm.student set courseId=null, teacherAgreed=0 where email like '${email}';")
    void deny(@Param("email") String email);

    @Select("select email,name from spm.student where courseId=${courseId} and teacherAgreed=${ag};")
    List<Student> studentC(@Param("courseId") Integer courseId, @Param("ag") Integer ag);

    @Select("select exam.eid,exam.name,score from spm.se se left join spm.exam exam on se.eid = exam.eid where se.email = '${email}';")
    List<Map<String, Object>> studentScore(@Param("email") String email);

    @Select("select *from spm.exam where eid=${eid};")
    List<ExamBean> exam(@Param("eid") Integer eid);

    @Select("select * from spm.exam where cid = #{courseId};")
    List<ExamBean> listExams(Student stu);

    @Insert("insert into spm.se(email, eid, score) VALUES ('${email}',${eid},${score});")
    void insertScore(@Param("email") String email, @Param("eid") Integer eid, @Param("score") Double score);

    @Update("update spm.se set score=${score} where email like '${email}' and eid=${eid};")
    void updateScore(@Param("email") String email, @Param("eid") Integer eid, @Param("score") Double score);
}
