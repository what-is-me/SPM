package org.spm.spm.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.spm.spm.bean.Student;

import java.util.List;

@Mapper
public interface StudentMapper {
    @Select("select * from spm.student where email like #{email};")
    List<Student> find(Student student);

    @Update("update spm.student set courseId=#{courseId}, teacherAgreed=#{teacherAgreed} where email like #{email};")
    void update(Student student);

    @Insert("insert into spm.student values(#{email}, #{password}, #{name}, null, null);")
    void insert(Student student);

    @Select("select * from spm.student;")
    List<Student> findAll();

    @Update("update spm.student set teacherAgreed=1 where email like #{email};")
    void agree(Student student);

    @Update("update spm.student set courseId=null where email like #{email};")
    void deny(Student student);
}
