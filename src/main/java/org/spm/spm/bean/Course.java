package org.spm.spm.bean;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.spm.spm.mapper.TeacherMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class Course {
    private static final Gson gson = new Gson();
    @Autowired
    private TeacherMapper teacherMapper;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    class Sourse {
        String title;
        String type;
        String url;
    }

    public Integer courseId;
    public List<String> board;
    public List<Sourse> src;
    public Teacher teacher;

    public Course(CourseBean cb) {
        courseId = cb.getCourseId();
        if (cb.getBoard() != null) board = gson.fromJson(cb.getBoard(), new TypeToken<List<String>>() {
        }.getType());
        if (cb.getSource() != null) src = gson.fromJson(cb.getSource(), new TypeToken<List<Sourse>>() {
        }.getType());
        try {
            List<Teacher> res = teacherMapper.find(Teacher.builder().email(cb.getTEmail()).build());
            if (res.size() < 1) throw new Exception("null");
            teacher = res.get(0);
        } catch (Exception ignored) {
        }
    }

    public CourseBean toCourseBean() {
        return CourseBean.builder()
                .courseId(courseId)
                .board(gson.toJson(board))
                .source(gson.toJson(src))
                .tEmail(teacher.getEmail())
                .build();
    }
}
