package org.spm.spm.mapper;

import org.apache.ibatis.annotations.*;
import org.spm.spm.bean.CourseBean;

import java.util.List;

@Mapper
public interface CourseMapper {
    @Select("select courseId,email from spm.course;")
    List<CourseBean> findAll();

    @Select("select courseId,email,board,source from spm.course where courseId = ${courseId};")
    List<CourseBean> findById(@Param("courseId") Integer courseId);

    @Select("select courseId,email,board,source from spm.course where email like '${email}';")
    List<CourseBean> findByTeacher(@Param("email") String email);

    @Insert("insert into spm.course (email,board,source)values('${email}','[]','')")
    void createCourse(@Param("email") String email);

    @Update("update spm.course set source='${src}' where courseId=${courseId};")
    void updateSrc(@Param("courseId") Integer courseId, @Param("src") String src);

    @Update("update spm.course set board='${brd}' where courseId=${courseId};")
    void updateBrd(@Param("courseId") Integer courseId, @Param("brd") String brd);
}
