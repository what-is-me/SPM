package org.spm.spm.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
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
