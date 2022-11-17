package org.spm.spm.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.spm.spm.bean.CourseBean;

import java.util.List;

@Mapper
public interface CourseMapper {
    @Select("select * from spm.course;")
    List<CourseBean> findAll();
}
