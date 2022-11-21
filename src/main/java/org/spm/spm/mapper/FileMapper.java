package org.spm.spm.mapper;

import org.apache.ibatis.annotations.*;
import org.spm.spm.bean.File;

import java.util.List;

@Mapper
public interface FileMapper {
    @Select("select * from spm.files where email = '${email}';")
    List<File> fileOf(@Param("email") String email);

    @Insert("insert into spm.files (email,filename,url)values(#{email},#{filename},#{url});")
    void insert(File file);

    @Delete("delete from spm.files where email = '${email}' and filename = '${filename}';")
    void delete(@Param("email") String email, @Param("filename") String filename);
}
