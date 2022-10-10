package com.libaba.email.service;

import com.libaba.email.mapper.UserMapper;
import com.libaba.email.model.User;
import com.libaba.email.vo.UserVo;
import com.libaba.email.vo.UserVoToUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Random;

@Service
public class MailService {

    @Autowired
    private JavaMailSender mailSender;//一定要用@Autowired,是spring提供的发送邮件的接口

    @Autowired
    private UserMapper userMapper;

    @Value("${spring.mail.username}")   //配置文件中的值传给from
    private String from;

    /*
    给前端输入的邮箱发送验证码
     */
    public boolean sendMimeMail(String email, HttpSession httpsesion) {
        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();

            mailMessage.setSubject("验证码邮件");//主题
            //生成随机数
            String code = randomCode();

            //将随机数放置到session中
            httpsesion.setAttribute("email",email);
            httpsesion.setAttribute("code",code);

            mailMessage.setText("您收到的验证码是："+code);//内容

            mailMessage.setTo(email);//发给谁

            mailMessage.setFrom(from);//你自己的邮箱

            mailSender.send(mailMessage);//发送
            return  true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
    /*
    随机生成6位验证码
     */
    public String randomCode(){
        StringBuilder str=new StringBuilder();
        Random random = new Random();
        for(int i=0;i<10;i++){
            str.append(random.nextInt(10));
        }
        return str.toString();
    }


    /*
     校验验证码是否一致
     */
    public boolean registered(UserVo uservo, HttpSession session) {
        String email = (String) session.getAttribute("email");
        String code = (String) session.getAttribute("code");
        //获取表单中提交的验证信息
        String vocode=uservo.getCode();

        //判断如果email为空或者不一致，注册失败
        if(email==null||email.isEmpty()){
            return false;
        }else if(!code.equals(vocode)){
            return false;
        }

        //保存数据
        User user= UserVoToUser.toUser(uservo);

        //将数据写入数据库
        userMapper.insertUser(user);

        //跳转成功
        return true;

    }

    public boolean loginIn(String email, String password) {
        User user=userMapper.queryByEmail(email);
        if(!user.getPassword().equals(password)){
            return false;
        }
        System.out.println("登录成功：数据库密码是："+user.getPassword());
        return true;
    }
}
