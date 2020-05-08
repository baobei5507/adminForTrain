package com.adminfortrain.controller;


import com.adminfortrain.admin.impl.UserServiceImpl;
import com.adminfortrain.admin.mapper.UserMapper;
import com.adminfortrain.admin.model.User;
import com.adminfortrain.vipAccount.impl.VipServiceImpl;
import com.adminfortrain.vipAccount.mapper.VipMapper;
import com.adminfortrain.vipAccount.model.Vip;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Controller
public class indexController {

    @Autowired
    VipServiceImpl vipService;

    @Autowired
    VipMapper vipMapper;

    @Autowired
    UserMapper userMapper;

    //登录页面
    @GetMapping({"/","/index"})
    public String index(){
        return "index";
    }

    //主页
    @GetMapping("/main")
    public String mainpage(
            Model model,
            @RequestParam(value = "pageNum", defaultValue = "1") int currentpage
    ){
        //分页查询
        Page<Vip> vipPage = new Page<>(currentpage,5);
         vipMapper.selectPage(vipPage, null);
        List<Vip> vips = vipPage.getRecords();

        //“懒加载” 验证是否过期
        for (Vip vip : vips) {
            if(vip.getEndtime().compareTo(new Date()) == -1)
                vipMapper.deleteById(vip.getId());
        }


        model.addAttribute("page",vipPage);
        model.addAttribute("vips",vipPage.getRecords());
        model.addAttribute("totalPage",vipPage.getPages());

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

        if( StringUtils.isBlank(request.getParameter("password") )|| StringUtils.isBlank(request.getParameter("password2") )){
            model.addAttribute("error","密码不能为空");
            return "sign";
        }
        user.setUsername(username);
        user.setPassword(request.getParameter("password"));
        user.setDeleted(0);
        userMapper.insert(user);
        return "redirect:/";
    }

    @RequestMapping("/checkout")
    @ResponseBody
    public String checkout(String username){

        System.out.println(username);

        User user = null;
        try {
            QueryWrapper<User> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("username",username);
            user=userMapper.selectOne(queryWrapper);
        } catch (Exception e) {
            e.printStackTrace();
        }


        try {
            return user.getUsername();
        } catch (Exception e) {
            return "null";
        }
    }

}
