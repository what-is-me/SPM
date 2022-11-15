package org.spm.spm.bean;

import lombok.Data;

@Data
public class Teacher implements User {
    private String name;
    private String password;
    private String email;

    @Override
    public String userType() {
        return "teacher";
    }
}
