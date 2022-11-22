package org.spm.spm.bean;

import com.google.gson.annotations.Expose;
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
    @Expose(serialize = false)
    private String password;
    private String email;

    @Override
    public String userType() {
        return "teacher";
    }
}
