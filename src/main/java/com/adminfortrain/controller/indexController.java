package com.adminfortrain.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.stream.Stream;

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

}
