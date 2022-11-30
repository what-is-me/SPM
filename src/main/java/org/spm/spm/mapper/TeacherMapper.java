package org.spm.spm.mapper;

import org.apache.ibatis.annotations.*;
import org.spm.spm.bean.ExamBean;
import org.spm.spm.bean.Teacher;

import java.util.List;
import java.util.Map;

@Mapper
public interface TeacherMapper {
    @Select("select * from spm.teacher where email like #{email};")
    List<Teacher> find(Teacher teacher);

    @Select("select * from spm.teacher where email like '${email}';")
    List<Teacher> findE(@Param("email") String email);

    @Insert("insert into spm.exam (cid, `begin`, `end`, `name`, `text`) values(#{cid},#{begin},#{end},#{name},#{text});")
    void addExam(ExamBean examBean);

    @Update("update spm.exam set cid=#{cid}, `begin`=#{begin}, `end`=#{end},`name`=#{name},`text`=#{text} where eid=#{eid};")
    void updateExam(ExamBean examBean);

    @Select("select * from spm.exam where cid in (select courseId from spm.course where email='${email}');")
    List<ExamBean> listExam(@Param("email") String email);

    @Select("select * from spm.student_score where courseId like ${courseId};")
    List<Map<String, Object>> listStudentScoresOfCourse(@Param("courseId") Integer courseId);
}
