package org.spm.spm.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.spm.spm.bean.Student;
import org.spm.spm.mapper.StudentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = "注册")
public class SignInController {
    @Autowired
    StudentMapper studentMapper;

    @ApiImplicitParams({
            @ApiImplicitParam(name = "email", dataTypeClass = String.class, value = "用户邮箱", required = true, paramType = "query"),
            @ApiImplicitParam(name = "password", dataTypeClass = String.class, value = "密码", required = true, paramType = "query"),
            @ApiImplicitParam(name = "password_again", dataTypeClass = String.class, value = "密码", required = true, paramType = "query"),
            @ApiImplicitParam(name = "name", dataTypeClass = String.class, value = "用户名", required = true, paramType = "query")
    })
    @ApiOperation(value = "学生注册", notes = "成功返回true，否则返回失败原因")
    @RequestMapping(path = "/sign-in", method = {RequestMethod.GET, RequestMethod.POST})
    public String signIn(
            @RequestParam String email,
            @RequestParam String password,
            @RequestParam("password_again") String passwordAgain,
            @RequestParam String name) {
        if (email == null || "".equals(email)) return "邮箱为空";
        if (password == null || "".equals(password)) return "密码为空";
        if (!password.equals(passwordAgain)) return "两次密码输入不同";
        Student stu = Student.builder().name(name).email(email).password(password).build();
        try {
            studentMapper.insert(stu);
        } catch (Exception e) {
            return "注册失败，可能是邮箱已存在";
        }
        return "true";
    }
}
