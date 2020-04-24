package com.adminfortrain.controller;


import com.adminfortrain.admin.impl.UserServiceImpl;
import com.adminfortrain.admin.mapper.UserMapper;
import com.adminfortrain.admin.model.User;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;

@Controller
public class indexController {

    @Autowired
    UserServiceImpl userService;

    @Autowired
    UserMapper userMapper;

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

        //session获取当前用户名
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

    @RequestMapping("/sign")
    public String sign(){
        return "sign";
    }

    @RequestMapping("/handlesign")
    public String handlesign(
            HttpServletRequest request,
            Model model){
        User user = new User();
        String username = request.getParameter("username");

        HashMap<String, Object> map = new HashMap<>();
        map.put("username",username);
        if(userMapper.selectByMap(map).size() !=0 ){
            model.addAttribute("error","该用户已存在，请换一个用户名");
            return "sign";
        }

        if(
               ! ((request.getParameter("password"))
                 .equals(request.getParameter("password2")))
        ){
            model.addAttribute("error","两次密码输入不同，请再次尝试");
            return "sign";
        }

        user.setUsername(username);
        user.setPassword(request.getParameter("password"));
        user.setDeleted(0);
        userService.save(user);
        return "redirect:/";
    }

}
