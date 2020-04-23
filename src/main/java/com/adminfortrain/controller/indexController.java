package com.adminfortrain.controller;


import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class indexController {

    //登录页面
    @GetMapping({"/","/index"})
    public String index(){
        return "index";
    }

    //主页
    @GetMapping("/main")
    public String mainpage(){
        return "mainpage";
    }

    @RequestMapping("/login")
    public String login(String username, String password, Model model, HttpServletRequest request){
        //获取当前用户
        Subject subject = SecurityUtils.getSubject();
        //封装用户的登录信息
        UsernamePasswordToken token = new UsernamePasswordToken(username,password);
        request.getSession().setAttribute("user",token.getUsername());

        try {
            //如果登录成功，进入主页
            subject.login(token);
            return "redirect:/main";
        } catch (UnknownAccountException e) {
            model.addAttribute("msg","账号错误或不存在");
            return "index";
        }catch (IncorrectCredentialsException e){
            model.addAttribute("msg","密码错误");
            return  "index";
        }

    }

}
