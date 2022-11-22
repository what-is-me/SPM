package org.spm.spm.mapper;

import org.apache.ibatis.annotations.*;
import org.spm.spm.bean.Student;

import java.util.List;

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
}
