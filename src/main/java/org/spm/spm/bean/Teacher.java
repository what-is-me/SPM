package org.spm.spm.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Teacher implements User {
    private String name;
    private String password;
    private String email;

    @Override
    public String userType() {
        return "teacher";
    }
}
