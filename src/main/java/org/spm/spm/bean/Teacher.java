package org.spm.spm.bean;

import lombok.Data;

import java.util.List;

@Data
public class Teacher implements User {
    private String name;
    private String password;
    private String email;
    private List<String> courses;

    @Override
    public String userType() {
        return "teacher";
    }
}
