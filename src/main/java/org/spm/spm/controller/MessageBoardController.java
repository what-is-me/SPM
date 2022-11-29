package org.spm.spm.controller;

import com.google.gson.GsonBuilder;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.spm.spm.bean.Message;
import org.spm.spm.bean.User;
import org.spm.spm.mapper.MessageMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Api(tags = "留言板")
@RestController
@RequestMapping(path = "/message")
@Slf4j
public class MessageBoardController {
    @Autowired
    MessageMapper messageMapper;

    @ApiImplicitParam(name = "page", value = "第page页留言", dataTypeClass = Integer.class, defaultValue = "0", paramType = "query")
    @ApiOperation(value = "聊天记录", notes = "时间降序排列的聊天记录，每页20条")
    @RequestMapping(method = RequestMethod.GET)
    public String messagesPage(@RequestParam(value = "page", required = false) Integer page) {
        if (page == null) page = 0;
        return new GsonBuilder().setDateFormat("yyyy年MM月dd日 HH:mm:ss").create().toJson(messageMapper.find(page * 5));
    }

    @ApiImplicitParam(name = "msg", value = "留言内容", dataTypeClass = String.class, paramType = "query", required = true)
    @ApiOperation(value = "进行留言", notes = "留言成功则返回true，否则返回失败原因")
    @RequestMapping(method = RequestMethod.POST)
    public String sendMessage(@RequestParam String msg, HttpServletRequest req) {
        User user = (User) req.getSession().getAttribute("user");
        if (user == null) return "未登录";
        try {
            Message message = Message.builder().name(user.getName()).msg(msg).build();
            messageMapper.insert(message);
        } catch (Exception e) {
            return "数据库插入错误";
        }
        return "true";
    }
}
