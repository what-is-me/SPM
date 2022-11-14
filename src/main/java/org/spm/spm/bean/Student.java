package org.spm.spm.bean;

import lombok.Data;

@Data
public class Student implements User {
    private String email;
    private String password;
    private String name;
    private String courseId;
    private Integer teacherAgreed;

    @Override
    public String userType() {
        return "student";
    }
}
