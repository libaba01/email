package com.libaba.email.controller;

import com.libaba.email.vo.UserVo;
import com.libaba.email.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
public class UserController {
    @Autowired
    private MailService mailService;


    /*
    发送邮件
     */
    @PostMapping("/sendEmail")
    @ResponseBody
    public String sendEmail(String email, HttpSession httpsesion){
        mailService.sendMimeMail(email,httpsesion);
        return "success";
    }

    /*
    注册
     */
    @PostMapping("/regist")
    @ResponseBody
    public String regist(UserVo uservo, HttpSession session){
        mailService.registered(uservo,session);
        return "sucess";
    }

    /*
     登录
     */
    @PostMapping("/login")
    @ResponseBody
    public String login(String email, String password){
        mailService.loginIn(email,password);
        return "sucess";
    }

}
