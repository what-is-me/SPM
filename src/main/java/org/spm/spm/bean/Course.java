package org.spm.spm.bean;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.extern.slf4j.Slf4j;
import org.spm.spm.mapper.TeacherMapper;

import java.util.LinkedList;
import java.util.List;

@Slf4j
public class Course {
    private static final Gson gson = new Gson();

    public Integer courseId;
    public LinkedList<String> board;
    public String src;
    public Teacher teacher;

    public Course(CourseBean cb, TeacherMapper teacherMapper) {
        if (cb == null) return;
        courseId = cb.getCourseId();
        if (cb.getBoard() != null) board = gson.fromJson(cb.getBoard(), new TypeToken<LinkedList<String>>() {
        }.getType());
        if (cb.getSource() != null) src = cb.getSource();
        try {
            List<Teacher> res = teacherMapper.findE(cb.getEmail());
            if (res.size() < 1) throw new Exception("null");
            teacher = res.get(0);
            teacher.setPassword(null);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    public CourseBean toCourseBean() {
        return CourseBean.builder()
                .courseId(courseId)
                .board(gson.toJson(board))
                .source(src)
                .email(teacher.getEmail())
                .build();
    }
}
