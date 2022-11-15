package org.spm.spm.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.spm.spm.bean.Message;

import java.util.List;

@Mapper
public interface MessageMapper {
    @Select("select * from spm.messageboard order by time limit ${page},20;")
    List<Message> find(@Param("page") Integer page);

    @Insert("insert into spm.messageboard (name, msg) values (#{name}, #{msg});")
    void insert(Message message);
}
