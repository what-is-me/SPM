package org.spm.spm.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.spm.spm.bean.Teacher;

import java.util.List;

@Mapper
public interface TeacherMapper {
    @Select("select * from spm.teacher where email=#{email};")
    List<Teacher> find(Teacher teacher);
}
