package org.spm.spm.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.spm.spm.bean.Teacher;

import java.util.List;

@Mapper
public interface TeacherMapper {
    @Select("select * from spm.teacher where email like #{email};")
    List<Teacher> find(Teacher teacher);

    @Select("select * from spm.teacher where email like '${email}';")
    List<Teacher> findE(@Param("email") String email);
}
